package com.app.application.oa.processes.hr.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysResource;
import com.app.base.entity.SysUser;
import com.app.base.service.SysResourceService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.DateTimeUtil;
import com.app.utils.SpringContextUtil;
import com.app.workflow.entity.WfDefineActivity;
import com.app.workflow.entity.WfDefineProcess;
import com.app.workflow.entity.WfDefineRoute;
import com.app.workflow.entity.WfInstanceActivity;
import com.app.workflow.entity.WfInstanceProcess;
import com.app.workflow.service.WfDefineActivityService;
import com.app.workflow.service.WfDefineProcessService;
import com.app.workflow.service.WfDefineRouteService;
import com.app.application.oa.processes.hr.entity.LeaveApply;
import com.app.application.oa.processes.hr.service.LeaveApplyService;

/**
 * 请假流程
 */
@Controller
@Scope("prototype")
@RequestMapping("/leaveApply")
public class LeaveApplyController extends BaseController {

	private static Logger logger = Logger.getLogger(LeaveApplyController.class);

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
		return "oa/processes/hr/leave/list_leave";
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
	public void exeDataGrid(String menuId, LeaveApply leave, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(leave.getLeaveType()))
			param.addEquals("leaveType", leave.getLeaveType());
		if (null != leave.getStartTime()) {
			// String hql = "startTime >= to_date('" +
			// DateTimeUtil.formatDate(leave.getStartTime()) +
			// "','yyyy-MM-dd')";
			String hql = "startTime >= str_to_date('" + DateTimeUtil.formatDate(leave.getStartTime()) + "','%Y-%m-%d')";
			param.addHql(hql);
		}
		if (null != leave.getEndTime()) {
			Date endDate = DateTimeUtil.addDays(leave.getEndTime(), 1);
			// String hql = "endTime < to_date('" +
			// DateTimeUtil.formatDate(endDate) + "','yyyy-MM-dd')";
			String hql = "endTime < str_to_date('" + DateTimeUtil.formatDate(endDate) + "','%Y-%m-%d')";
			param.addHql(hql);
		}

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}

		if (StringUtils.startsWith(page.getSort(), "createTimeFmt"))
			page.setSort("createTime");

		List<LeaveApply> pageList = leaveApplyService.findPageList(page, param);

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
	public String exeAdd(String menuId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("add...........");

		SysUser user = SpringContextUtil.getUser();
		LeaveApply leave = new LeaveApply(user.getResourceid(), user.getUsername(), user.getOrgName(), user.getPostName(), user.getResourceid(), user.getUsername(), new Date());
		model.addAttribute("leave", leave);

		WfDefineProcess defineProcess = wfDefineProcessService.findProcessByMenuId(menuId);
		WfDefineActivity defineActivity = wfDefineActivityService.findStartActivityByProcessId(defineProcess.getResourceid());
		List<WfDefineRoute> defineRoutes = wfDefineRouteService.findRoutesByActivityId(defineActivity.getResourceid());
		String processTitle = defineProcess.getMenuName() + " - " + user.getOrgName() + " - " + user.getUsername();

		model.addAttribute("defineProcess", defineProcess);
		model.addAttribute("defineActivity", defineActivity);
		model.addAttribute("defineRoutes", defineRoutes);
		model.addAttribute("processTitle", processTitle);
		return "oa/processes/hr/leave/edit_leave";
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

		LeaveApply leave = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			leave = leaveApplyService.load(resourceid);
		}

		model.addAttribute("leave", leave);
		return "oa/processes/hr/leave/edit_leave";
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
	public void exeSave(LeaveApply leave, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		Map<String, Object> map = new HashMap<String, Object>();
		String defineProcessId = request.getParameter("defineProcessId"); // 流程定义ID
		String defineActivityId = request.getParameter("defineActivityId"); // 流程实例ID
		String submitType = request.getParameter("submitType"); // 提交类型；save:保存、submit:提交
		String processTitle = request.getParameter("processTitle"); // 流程标题

		SysUser user = SpringContextUtil.getUser();
		WfDefineProcess defineProcess = wfDefineProcessService.load(defineProcessId);
		WfDefineActivity defineActivity = wfDefineActivityService.load(defineActivityId);

		try {
			if (StringUtils.isEmpty(leave.getResourceid())) { // 新增
				leaveApplyService.save(leave);

				if (StringUtils.equals(submitType, "save")) { // 保存操作
					SysResource resource = sysResourceService.load(defineProcess.getMenuId());
					Integer serialNumber = wfDefineProcessService.selectSerialNumber();
					WfInstanceProcess instanceProcess = new WfInstanceProcess(serialNumber, processTitle, "A", leave.getResourceid(), StringUtils.trim(resource.getMenuUrl()), defineProcess.getIsPriority(), defineProcess.getResourceid(), user.getResourceid(), user.getUsername(), new Date());
					
					String url = "";
					WfInstanceActivity instanceActivity = new WfInstanceActivity(0, "A", defineActivity.getActivityType(), defineActivity.getTaskFlag(), defineActivity.getActivityName(), defineActivity.getActivityAlias(), "", new Date(), null, null, "", defineActivity.getForkJoin(), defineActivity.getJoinActivityCount(), instanceProcess.getResourceid(), defineActivity.getResourceid(), url, defineActivity.getOrderBy());
					
				}

				if (StringUtils.equals(submitType, "submit")) { // 提交操作
					
				}
				
			} else {
				leaveApplyService.update(leave); // 修改
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
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				leaveApplyService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Autowired
	LeaveApplyService leaveApplyService;

	@Autowired
	WfDefineProcessService wfDefineProcessService;

	@Autowired
	WfDefineActivityService wfDefineActivityService;

	@Autowired
	WfDefineRouteService wfDefineRouteService;
	
	@Autowired
	SysResourceService sysResourceService;
}
