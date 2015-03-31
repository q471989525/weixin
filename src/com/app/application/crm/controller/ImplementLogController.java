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
import com.app.application.crm.entity.Implement;
import com.app.application.crm.entity.ImplementLog;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ImplementLogService;
import com.app.application.crm.service.ImplementService;
import com.app.application.crm.service.ProjectInfoService;

@Controller
@Scope("prototype")
@RequestMapping("/implementLog")
public class ImplementLogController extends BaseController {

	private static Logger logger = Logger.getLogger(ImplementLogController.class);

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
		return "crm/implementLog/list_implementLog";
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
	public void exeDataGrid(String menuId, ImplementLog implementLog, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		// 客户名称
		if (StringUtils.isNotEmpty(implementLog.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + implementLog.getCustomerName() + "%");
			if (CollectionUtils.isNotEmpty(customers)) {
				List<String> idsList = new ArrayList<String>();
				for (CustomerInfo info : customers) {
					idsList.add(info.getResourceid());
				}

				List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and customerId in ('" + StringUtils.join(idsList, "','") + "')");
				if (CollectionUtils.isNotEmpty(projectInfos)) {
					idsList.clear();
					for (ProjectInfo info : projectInfos) {
						idsList.add(info.getResourceid());
					}

					List<Implement> implementList = implementService.findListByHql("from Implement where deleteFlag='n' and projectId in ('" + StringUtils.join(idsList, "','") + "')");
					if (CollectionUtils.isNotEmpty(implementList)) {
						idsList.clear();
						for (Implement implement : implementList) {
							idsList.add(implement.getResourceid());
						}
						param.addIn("implementId", "'" + StringUtils.join(idsList, "','") + "'");
					} else {
						param.addIn("implementId", "'null'");
					}
				} else {
					param.addIn("implementId", "'null'");
				}

			} else {
				param.addIn("implementId", "'null'");
			}
		}

		// 查询项目
		if (StringUtils.isNotEmpty(implementLog.getProjectName())) {
			List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and projectName like ?", "%" + implementLog.getProjectName() + "%");
			if (CollectionUtils.isNotEmpty(projectInfos)) {
				List<String> idsList = new ArrayList<String>();
				for (ProjectInfo info : projectInfos) {
					idsList.add(info.getResourceid());
				}

				List<Implement> implementList = implementService.findListByHql("from Implement where deleteFlag='n' and projectId in ('" + StringUtils.join(idsList, "','") + "')");
				if (CollectionUtils.isNotEmpty(implementList)) {
					idsList.clear();
					for (Implement implement : implementList) {
						idsList.add(implement.getResourceid());
					}
					param.addIn("implementId", "'" + StringUtils.join(idsList, "','") + "'");
				} else {
					param.addIn("implementId", "'null'");
				}
			} else {
				param.addIn("implementId", "'null'");
			}
		}

		if (StringUtils.isNotEmpty(implementLog.getImplementItem()))
			param.addEquals("implementItem", implementLog.getImplementItem());

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<ImplementLog> pageList = implementLogService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (ImplementLog log : pageList) {
				Implement implement = implementService.load(log.getImplementId());
				ProjectInfo info = projectInfoService.load(implement.getProjectId());
				if (null != info) {
					log.setProjectName(info.getProjectName());

					CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
					log.setCustomerName(customerInfo.getCustomerName());
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
		ImplementLog implementLog = new ImplementLog();
		implementLog.setCreateId(user.getResourceid());
		implementLog.setCreator(user.getUsername());
		implementLog.setCreateTime(new Date());
		implementLog.setDeleteFlag("n");
		implementLog.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", user);
		model.addAttribute("implementLog", implementLog);
		return "crm/implementLog/edit_implementLog";
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

		ImplementLog implementLog = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			implementLog = implementLogService.load(resourceid);

			Implement implement = implementService.load(implementLog.getImplementId());
			ProjectInfo info = projectInfoService.load(implement.getProjectId());
			if (null != info) {
				implementLog.setProjectName(info.getProjectName());

				CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
				implementLog.setCustomerName(customerInfo.getCustomerName());
			}

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(implementLog.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("implementLog", implementLog);
		return "crm/implementLog/edit_implementLog";
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
	public void exeSave(ImplementLog implementLog, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(implementLog.getResourceid())) { // 新增
				implementLogService.save(implementLog);
			} else {
				implementLogService.update(implementLog); // 修改
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
				implementLogService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "implementLogService")
	ImplementLogService implementLogService;

	@Resource(name = "implementService")
	ImplementService implementService;

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
