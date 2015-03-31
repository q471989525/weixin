package com.app.workflow.controller;

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
import com.app.workflow.entity.WfDefineRoute;
import com.app.workflow.service.WfDefineActivityService;
import com.app.workflow.service.WfDefineRouteService;

/**
 * 流程定义
 */
@Controller
@Scope("prototype")
@RequestMapping("/wfDefineRoute")
public class WfDefineRouteController extends BaseController {

	private static Logger logger = Logger.getLogger(WfDefineRouteController.class);

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
	public void exeDataGrid(WfDefineRoute route, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String activityId = request.getParameter("activityId");

		List<WfDefineRoute> pageList = new ArrayList<WfDefineRoute>();
		if (StringUtils.isNotEmpty(activityId)) {
			QueryParameter param = new QueryParameter();
			param.addEquals("activityId", activityId);

			pageList = wfDefineRouteService.findPageList(page, param);
			if (CollectionUtils.isNotEmpty(pageList)) {
				WfDefineActivity activity = null;
				for (WfDefineRoute defineRoute : pageList) {
					activity = wfDefineActivityService.load(defineRoute.getNextActivityId());
					defineRoute.setNextActivityName(activity.getActivityName());
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
		String activityId = request.getParameter("activityId");
		String orderBy = request.getParameter("orderBy");

		SysUser user = SpringContextUtil.getUser();
		WfDefineRoute route = new WfDefineRoute(activityId, user.getResourceid(), user.getUsername(), new Date());
		if(NumberUtils.isNumber(orderBy)) route.setOrderBy(Integer.valueOf(orderBy));

		WfDefineActivity activity = wfDefineActivityService.load(activityId);

		QueryParameter param = new QueryParameter();
		param.addGt("orderBy", activity.getOrderBy() + "");
		param.addEquals("processId", activity.getProcessId());
		param.setOrderBy("orderBy");
		List<WfDefineActivity> list = wfDefineActivityService.findList(param);

		model.addAttribute("list", list);
		model.addAttribute("route", route);
		model.addAttribute("activityName", activity.getActivityName());
		return "workflow/route/edit_DefineRoute";
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

		WfDefineRoute route = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			route = wfDefineRouteService.load(resourceid);

			WfDefineActivity activity = wfDefineActivityService.load(route.getActivityId());

			QueryParameter param = new QueryParameter();
			param.addGt("orderBy", activity.getOrderBy() + "");
			param.addEquals("processId", activity.getProcessId());
			param.setOrderBy("orderBy");
			List<WfDefineActivity> list = wfDefineActivityService.findList(param);

			model.addAttribute("list", list);
			model.addAttribute("activityName", activity.getActivityName());
		}
		model.addAttribute("route", route);
		return "workflow/route/edit_DefineRoute";
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
	public void exeSave(WfDefineRoute route, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(route.getResourceid())) { // 新增
				wfDefineRouteService.save(route);
			} else {
				wfDefineRouteService.update(route); // 修改
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
				wfDefineRouteService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "wfDefineRouteService")
	WfDefineRouteService wfDefineRouteService;

	@Resource(name = "wfDefineActivityService")
	WfDefineActivityService wfDefineActivityService;

}
