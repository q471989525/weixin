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
import org.apache.commons.lang3.ArrayUtils;
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
import com.app.application.crm.entity.Implement;
import com.app.application.crm.entity.ImplementLog;
import com.app.application.crm.entity.ImplementProduct;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ImplementLogService;
import com.app.application.crm.service.ImplementProductService;
import com.app.application.crm.service.ImplementService;
import com.app.application.crm.service.ProjectInfoService;

/**
 * 
 * TODO：实施管理
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/implement")
public class ImplementController extends BaseController {

	private static Logger logger = Logger.getLogger(ImplementController.class);

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
		return "crm/implement/list_implement";
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
	public void exeDataGrid(String menuId, Implement implement, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		// 查询客户
		if (StringUtils.isNotEmpty(implement.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + implement.getCustomerName() + "%");
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
		if (StringUtils.isNotEmpty(implement.getProjectName())) {
			List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and projectName like ?", "%" + implement.getProjectName() + "%");
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

		if (StringUtils.isNotEmpty(implement.getDutyPerson()))
			param.addLike("dutyPerson", implement.getDutyPerson());

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<Implement> pageList = implementService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (Implement implement2 : pageList) {
				ProjectInfo info = projectInfoService.load(implement2.getProjectId());
				if (null != info) {
					implement2.setProjectName(info.getProjectName());

					CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
					implement2.setCustomerName(customerInfo.getCustomerName());
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
		Implement implement = new Implement();
		implement.setCreateId(user.getResourceid());
		implement.setCreator(user.getUsername());
		implement.setCreateTime(new Date());
		implement.setDeleteFlag("n");
		implement.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", user);
		model.addAttribute("implement", implement);
		return "crm/implement/edit_implement";
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

		Implement implement = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			implement = implementService.load(resourceid);

			ProjectInfo info = projectInfoService.load(implement.getProjectId());
			if (null != info) {
				implement.setProjectName(info.getProjectName());

				CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
				implement.setCustomerName(customerInfo.getCustomerName());
			}

			List<ImplementProduct> products = implementProductService.findListByHql("from ImplementProduct where deleteFlag='n' and implementId=?", resourceid);
			model.addAttribute("products", products);
			
			List<ImplementLog> logList = implementLogService.findListByHql("from ImplementLog where deleteFlag='n' and implementId=?", resourceid);
			model.addAttribute("logList", logList);

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(implement.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("implement", implement);
		return "crm/implement/edit_implement";
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
	public void exeSave(Implement implement, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 合同付款
			List<ImplementProduct> products = new ArrayList<ImplementProduct>();
			String[] proIds = request.getParameterValues("proId");
			if (ArrayUtils.isNotEmpty(proIds)) {
				for (int i = 0; i < proIds.length; i++) {
					String productName = request.getParameter("productName" + i);
					String productModel = request.getParameter("productModel" + i);
					String productUnit = request.getParameter("productUnit" + i);
					String productNumber = request.getParameter("productNumber" + i);
					String productRemark = request.getParameter("productRemark" + i);

					if (StringUtils.isEmpty(proIds[i])) {
						products.add(new ImplementProduct(productName, productModel, productUnit, productNumber, productRemark));
					} else {
						ImplementProduct product = implementProductService.load(proIds[i]);
						product.setProductName(productName);
						product.setProductModel(productModel);
						product.setProductUnit(productUnit);
						product.setProductNumber(productNumber);
						product.setProductRemark(productRemark);
						products.add(product);
					}
				}
			}

			if (StringUtils.isEmpty(implement.getResourceid())) { // 新增
				implementService.save(implement, products);
			} else {
				implementService.update(implement, products); // 修改
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
		String proId = request.getParameter("proId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				implementService.deleteByIds(id.split(","));
			}
			if (StringUtils.isNotEmpty(proId)) {
				implementProductService.deleteById(proId);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 选择页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select.do")
	public String exeSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("select...........");
		return "crm/implement/select_implement";
	}

	@Resource(name = "implementService")
	ImplementService implementService;

	@Resource(name = "implementProductService")
	ImplementProductService implementProductService;
	
	@Resource(name = "implementLogService")
	ImplementLogService implementLogService;

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
