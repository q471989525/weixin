package com.app.application.crm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.PubAttachment;
import com.app.base.entity.SysDictionary;
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.PropertiesUtil;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.BusinessOpportunity;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.BusinessOpportunityService;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ProjectInfoService;

/**
 * 
 * TODO：商业机会
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/businessopportunity")
public class BusinessOpportunityController extends BaseController {

	private static Logger logger = Logger.getLogger(BusinessOpportunityController.class);

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String exeList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		return "crm/business/list_business";
	}

	/**
	 * 列表方法
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(String menuId, BusinessOpportunity business, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		// 查询客户
		if (StringUtils.isNotEmpty(business.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + business.getCustomerName() + "%");
			if (CollectionUtils.isNotEmpty(customers)) {
				List<String> idsList = new ArrayList<String>();
				for (CustomerInfo info : customers) {
					idsList.add(info.getResourceid());
				}

				List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and customerId in ('" + StringUtils.join(idsList, "','") + "')");
				if (CollectionUtils.isNotEmpty(projectInfos)) {
					List<String> idsList2 = new ArrayList<String>();
					for (ProjectInfo info : projectInfos) {
						idsList2.add(info.getResourceid());
					}
					param.addIn("projectId", "'" + StringUtils.join(idsList2, "','") + "'");
				} else {
					param.addIn("projectId", "'null'");
				}
			} else {
				param.addIn("projectId", "'null'");
			}
		}
		// 查询项目
		if (StringUtils.isNotEmpty(business.getProjectName())) {
			List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and projectName like ?", "%" + business.getProjectName() + "%");
			if (CollectionUtils.isNotEmpty(projectInfos)) {
				List<String> idsList = new ArrayList<String>();
				for (ProjectInfo info : projectInfos) {
					idsList.add(info.getResourceid());
				}
				param.addIn("projectId", "'" + StringUtils.join(idsList, "','") + "'");
			} else {
				param.addIn("projectId", "'null'");
			}
		}

		if (StringUtils.isNotEmpty(business.getBusinessName()))
			param.addLike("businessName", business.getBusinessName());
		if (StringUtils.isNotEmpty(business.getBusinessType()))
			param.addEquals("businessType", business.getBusinessType());

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}

		param.addEquals("deleteFlag", "n");
		List<BusinessOpportunity> pageList = businessOpportunityService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (BusinessOpportunity opportunity : pageList) {
				ProjectInfo info = projectInfoService.load(opportunity.getProjectId());
				if (null != info) {
					opportunity.setProjectName(info.getProjectName());

					CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
					opportunity.setCustomerName(customerInfo.getCustomerName());
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 新增页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	public String exeAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("add...........");

		SysUser user = SpringContextUtil.getUser();
		BusinessOpportunity business = new BusinessOpportunity();
		business.setCreateId(user.getResourceid());
		business.setCreator(user.getUsername());
		business.setCreateTime(new Date());
		business.setCreateDept(user.getOrgName());
		business.setDeleteFlag("n");
		business.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", user);
		model.addAttribute("business", business);
		return "crm/business/edit_business";
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String exeEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("edit...........");
		String resourceid = request.getParameter("resourceid");

		BusinessOpportunity business = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			business = businessOpportunityService.load(resourceid);

			ProjectInfo info = projectInfoService.load(business.getProjectId());
			if (null != info) {
				business.setProjectName(info.getProjectName());

				CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
				business.setCustomerName(customerInfo.getCustomerName());
			}

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(business.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("business", business);
		return "crm/business/edit_business";
	}

	/**
	 * 保存页面
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void exeSave(BusinessOpportunity business, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(business.getResourceid())) { // 新增
				businessOpportunityService.save(business);
			} else {
				businessOpportunityService.update(business); // 修改
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete.do")
	public void exeDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				businessOpportunityService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 商机首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.do")
	public String exeIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysDictionary> saleStep = DictionaryELTag.getList("Code_Sale_Step");
		String type = StringUtils.defaultString(request.getParameter("type"));

		String menuId = PropertiesUtil.instance().get("BUSINESS_ID");
		;

		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(type) && !type.equals("all"))
			param.addEquals("saleStep", type);

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");
		param.setOrderBy(" createTime desc");

		List<BusinessOpportunity> list = businessOpportunityService.findList(param);

		request.setAttribute("list", list);
		request.setAttribute("typeflag", type);
		request.setAttribute("saleStep", saleStep);
		return "crm/business/index_business";
	}

	@Resource(name = "businessOpportunityService")
	BusinessOpportunityService businessOpportunityService;

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
