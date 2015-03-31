package com.app.workflow.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysUser;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.workflow.entity.WfDefineActivity;
import com.app.workflow.entity.WfDefineActivityCandidate;
import com.app.workflow.entity.WfDefineProcess;
import com.app.workflow.service.WfDefineActivityCandidateService;
import com.app.workflow.service.WfDefineActivityService;
import com.app.workflow.service.WfDefineProcessService;

/**
 * 活动定义
 */
@Controller
@Scope("prototype")
@RequestMapping("/wfDefineActivity")
public class WfDefineActivityController extends BaseController {

	private static Logger logger = Logger.getLogger(WfDefineActivityController.class);

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
	public void exeDataGrid(WfDefineActivity activity, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String processId = request.getParameter("processId");

		List<WfDefineActivity> pageList = new ArrayList<WfDefineActivity>();
		if (StringUtils.isNotEmpty(processId)) {
			QueryParameter param = new QueryParameter();
			param.addEquals("processId", processId);
			pageList = wfDefineActivityService.findPageList(page, param);

			if (CollectionUtils.isNotEmpty(pageList)) {
				for (WfDefineActivity defineActivity : pageList) {
					if (StringUtils.isNotEmpty(defineActivity.getSwimActivityId())) { // 泳道不为空
						WfDefineActivity swimActivity = wfDefineActivityService.load(defineActivity.getSwimActivityId());
						defineActivity.setSwimActivityName(swimActivity.getActivityName());
					}
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
		String processId = request.getParameter("processId");
		String processName = request.getParameter("processName");
		String orderBy = request.getParameter("orderBy");

		SysUser user = SpringContextUtil.getUser();
		WfDefineActivity activity = new WfDefineActivity(processId, user.getResourceid(), user.getUsername(), new Date());
		if(NumberUtils.isNumber(orderBy)) activity.setOrderBy(Integer.valueOf(orderBy));
		activity.setTakebackBtn("Y");

		QueryParameter param = new QueryParameter();
		param.addEquals("processId", processId);
		param.setOrderBy("orderBy");
		List<WfDefineActivity> list = wfDefineActivityService.findList(param);

		WfDefineActivityCandidate candidate = new WfDefineActivityCandidate();

		model.addAttribute("list", list);
		model.addAttribute("activity", activity);
		model.addAttribute("candidate", candidate);
		model.addAttribute("processName", URLDecoder.decode(processName, "UTF-8"));
		return "workflow/activity/edit_DefineActivity";
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

		WfDefineActivity activity = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			activity = wfDefineActivityService.load(resourceid);

			QueryParameter param = new QueryParameter();
			param.addLt("orderBy", activity.getOrderBy() + "");
			param.addEquals("processId", activity.getProcessId());
			param.setOrderBy("orderBy");
			List<WfDefineActivity> list = wfDefineActivityService.findList(param); // 查询泳道活动

			model.addAttribute("list", list);

			List<WfDefineActivityCandidate> candidates = wfDefineActivityCandidateService.findListByHql("from WfDefineActivityCandidate where activityId=?", resourceid);
			if (CollectionUtils.isNotEmpty(candidates))
				model.addAttribute("candidate", candidates.get(0));

			WfDefineProcess process = wfDefineProcessService.load(activity.getProcessId());
			model.addAttribute("processName", process.getMenuName());
		}
		model.addAttribute("activity", activity);
		return "workflow/activity/edit_DefineActivity";
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
	public void exeSave(WfDefineActivity activity, WfDefineActivityCandidate candidate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (!StringUtils.equals(activity.getActivityType(), "T")) {
				activity.setTaskFlag(null);
			}
			if (StringUtils.equals(activity.getForkJoin(), "G") || StringUtils.equals(activity.getForkJoin(), "F")) {
				activity.setJoinActivityCount(null);
			}
			if (!StringUtils.equals(activity.getOpinionCustomFlag(), "C")) {
				activity.setOpinionCustomDesc(null);
			}
			
			String selectdFormElemIds = request.getParameter("selectdFormElemIds"); //已选择元素ID
			if (StringUtils.isEmpty(activity.getResourceid())) { // 新增
				wfDefineActivityService.save(activity, candidate, selectdFormElemIds);
			} else {
				String candidateResourceid = request.getParameter("candidateResourceid");
				candidate.setResourceid(candidateResourceid);
				wfDefineActivityService.update(activity, candidate, selectdFormElemIds); // 修改
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
				wfDefineActivityService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "wfDefineActivityService")
	WfDefineActivityService wfDefineActivityService;

	@Resource(name = "wfDefineActivityCandidateService")
	WfDefineActivityCandidateService wfDefineActivityCandidateService;

	@Resource(name = "wfDefineProcessService")
	WfDefineProcessService wfDefineProcessService;

}
