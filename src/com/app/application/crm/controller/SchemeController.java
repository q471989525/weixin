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
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.entity.Scheme;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ProjectInfoService;
import com.app.application.crm.service.SchemeService;

/**
 * 
 * TODO：方案管理
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/scheme")
public class SchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(SchemeController.class);

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
		return "crm/scheme/list_scheme";
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
	public void exeDataGrid(String menuId, Scheme scheme, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		// 查询客户
		if (StringUtils.isNotEmpty(scheme.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + scheme.getCustomerName() + "%");
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
		if (StringUtils.isNotEmpty(scheme.getProjectName())) {
			List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and projectName like ?", "%" + scheme.getProjectName() + "%");
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

		if (StringUtils.isNotEmpty(scheme.getSchemeName()))
			param.addLike("schemeName", scheme.getSchemeName());

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<Scheme> pageList = schemeService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (Scheme scheme2 : pageList) {
				ProjectInfo info = projectInfoService.load(scheme2.getProjectId());
				if (null != info) {
					scheme2.setProjectName(info.getProjectName());

					CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
					scheme2.setCustomerName(customerInfo.getCustomerName());
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
		Scheme scheme = new Scheme();
		scheme.setCreateId(user.getResourceid());
		scheme.setCreator(user.getUsername());
		scheme.setCreateTime(new Date());
		scheme.setDeleteFlag("n");
		scheme.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", user);
		model.addAttribute("scheme", scheme);
		return "crm/scheme/edit_scheme";
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

		Scheme scheme = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			scheme = schemeService.load(resourceid);

			ProjectInfo info = projectInfoService.load(scheme.getProjectId());
			if (null != info) {
				scheme.setProjectName(info.getProjectName());

				CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
				scheme.setCustomerName(customerInfo.getCustomerName());
			}

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(scheme.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("scheme", scheme);
		return "crm/scheme/edit_scheme";
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
	public void exeSave(Scheme scheme, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(scheme.getResourceid())) { // 新增
				schemeService.save(scheme);
			} else {
				schemeService.update(scheme); // 修改
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
				schemeService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "schemeService")
	SchemeService schemeService;

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
