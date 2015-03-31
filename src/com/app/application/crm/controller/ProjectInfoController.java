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
import com.app.application.crm.entity.Competitor;
import com.app.application.crm.entity.CustomerContacts;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.CompetitorService;
import com.app.application.crm.service.CustomerContactsService;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ProjectInfoService;

/**
 * 
 * TODO：项目信息
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/projectInfo")
public class ProjectInfoController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoController.class);

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
		return "crm/project/list_projectInfo";
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
	public void exeDataGrid(String menuId, ProjectInfo projectInfo, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(projectInfo.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + projectInfo.getCustomerName() + "%");
			if (CollectionUtils.isNotEmpty(customers)) {
				List<String> idsList = new ArrayList<String>();
				for (CustomerInfo info : customers) {
					idsList.add(info.getResourceid());
				}
				param.addIn("customerId", "'" + StringUtils.join(idsList, "','") + "'");
			} else {
				param.addIn("customerId", "'null'");
			}
		}
		if (StringUtils.isNotEmpty(projectInfo.getProjectName())) {
			param.addLike("projectName", projectInfo.getProjectName());
		}
		if (StringUtils.isNotEmpty(projectInfo.getFileNo())) {
			param.addLike("fileNo", projectInfo.getFileNo());
		}

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<ProjectInfo> pageList = projectInfoService.findPageList(page, param);

		for (ProjectInfo info : pageList) {
			//客户
			CustomerInfo customer = customerInfoService.load(info.getCustomerId());
			info.setCustomerName(customer.getCustomerName());
			
			//联系人
			List<CustomerContacts> contactlist = customerContactsService.findListByRelation(info.getResourceid());
			if (CollectionUtils.isNotEmpty(contactlist)) {
				List<String> names = new ArrayList<String>();
				for (CustomerContacts contacts : contactlist) {
					names.add(contacts.getUserName());
				}
				info.setContactsName(StringUtils.join(names, ","));
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

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setCreateId(user.getResourceid());
		projectInfo.setCreator(user.getUsername());
		projectInfo.setCreateTime(new Date());
		projectInfo.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("user", user);
		return "crm/project/edit_projectInfo";
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
		SysUser user = SpringContextUtil.getUser();
		String resourceid = request.getParameter("resourceid");

		ProjectInfo projectInfo = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			projectInfo = projectInfoService.load(resourceid);

			if (StringUtils.isEmpty(projectInfo.getAttachmentId()))
				projectInfo.setAttachmentId(UUID.randomUUID().toString());

			// 客户信息
			CustomerInfo customer = customerInfoService.load(projectInfo.getCustomerId());
			projectInfo.setCustomerName(customer.getCustomerName());

			// 联系人
			List<CustomerContacts> contactlist = customerContactsService.findListByRelation(projectInfo.getResourceid());
			if (CollectionUtils.isNotEmpty(contactlist)) {
				List<String> ids = new ArrayList<String>();
				List<String> names = new ArrayList<String>();
				for (CustomerContacts contacts : contactlist) {
					ids.add(contacts.getResourceid());
					names.add(contacts.getUserName());
				}
				projectInfo.setContactsIds(StringUtils.join(ids, ","));
				projectInfo.setContactsName(StringUtils.join(names, ","));
			}
			model.addAttribute("contactlist", contactlist);

			// 竞争对手
			List<Competitor> competitors = competitorService.findListByRelation(projectInfo.getResourceid());
			if (CollectionUtils.isNotEmpty(competitors)) {
				List<String> ids = new ArrayList<String>();
				List<String> names = new ArrayList<String>();
				for (Competitor competitor : competitors) {
					ids.add(competitor.getResourceid());
					names.add(competitor.getCompetitorName());
				}
				projectInfo.setCompetitorIds(StringUtils.join(ids, ","));
				projectInfo.setCompetitorName(StringUtils.join(names, ","));
			}
			model.addAttribute("competitors", competitors);

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(projectInfo.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("user", user);
		return "crm/project/edit_projectInfo";
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
	public void exeSave(ProjectInfo projectInfo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			projectInfo.setDeleteFlag("n");
			if (StringUtils.isEmpty(projectInfo.getResourceid())) { // 新增
				projectInfoService.save(projectInfo);
			} else {
				projectInfoService.update(projectInfo); // 修改
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
				projectInfoService.deleteByIds(id.split(","));
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
		return "crm/project/select_project";
	}

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Resource(name = "customerContactsService")
	CustomerContactsService customerContactsService;

	@Resource(name = "competitorservice")
	CompetitorService competitorService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
