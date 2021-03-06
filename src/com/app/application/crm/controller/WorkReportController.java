package com.app.application.crm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.DateTimeUtil;
import com.app.utils.PropertiesUtil;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.WorkReport;
import com.app.application.crm.service.WorkReportService;

/**
 * 
 * TODO：工作报告
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/workreport")
public class WorkReportController extends BaseController {

	private static Logger logger = Logger.getLogger(WorkReportController.class);

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
		return "crm/workReport/list_workReport";
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
	public void exeDataGrid(String menuId, WorkReport workReport, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (null != workReport.getStartDate()) {
			String hql = "startDate >= to_date('" + DateTimeUtil.formatDate(workReport.getStartDate()) + "','yyyy-MM-dd')";
			param.addHql(hql);
		}
		if (null != workReport.getEndDate()) {
			Date endDate = DateTimeUtil.addDays(workReport.getEndDate(), 1);
			String hql = "endDate < to_date('" + DateTimeUtil.formatDate(endDate) + "','yyyy-MM-dd')";
			param.addHql(hql);
		}
		if (StringUtils.isNotEmpty(workReport.getCreator()))
			param.addLike("creator", workReport.getCreator());
		if (StringUtils.isNotEmpty(workReport.getReportType()))
			param.addEquals("reportType", workReport.getReportType());
		if (StringUtils.isNotEmpty(workReport.getVerifyFlag()))
			param.addEquals("verifyFlag", workReport.getVerifyFlag());

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

		List<WorkReport> pageList = workReportService.findPageList(page, param);

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

		WorkReport workReport = new WorkReport();

		String saveFlag = "new";
		workReport.setCreateId(SpringContextUtil.getUser().getResourceid());
		workReport.setCreator(SpringContextUtil.getUser().getUsername());
		workReport.setCreateTime(new Date());

		model.addAttribute("saveFlag", saveFlag);
		model.addAttribute("workReport", workReport);
		return "crm/workReport/edit_workReport";
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

		WorkReport workReport = null;
		String saveFlag = "new";

		if (StringUtils.isNotEmpty(resourceid)) {
			workReport = workReportService.load(resourceid);

			if (StringUtils.equals(workReport.getVerifyFlag(), "y"))
				saveFlag = "disabled";
		}

		model.addAttribute("saveFlag", saveFlag);
		model.addAttribute("workReport", workReport);
		return "crm/workReport/edit_workReport";
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
	public void exeSave(WorkReport workReport, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(workReport.getResourceid())) { // 新增
				workReportService.save(workReport);
			} else {
				workReportService.update(workReport); // 修改
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
				workReportService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 审核页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/verify.do")
	public String exeVerify(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("verify...........");
		String resourceid = request.getParameter("resourceid");

		WorkReport workReport = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			workReport = workReportService.load(resourceid);
		}
		model.addAttribute("workReport", workReport);
		return "crm/workReport/verify_workReport";
	}

	/**
	 * 保存
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveVerify.do")
	public void exeSaveVerify(String resourceid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("saveVerify...........");
		String verifyDesc = request.getParameter("verifyDesc");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(resourceid)) {
				WorkReport workReport = workReportService.load(resourceid);
				workReport.setVerifyFlag("y");
				workReport.setVerifyDesc(verifyDesc);
				workReportService.update(workReport);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 日志首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.do")
	public String exeIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String type = StringUtils.defaultString(request.getParameter("type"));
		String menuId = "";
		QueryParameter param = new QueryParameter();
		if (StringUtils.equals(type, "d") || StringUtils.isEmpty(type)) {
			param.addEquals("reportType", "d");
			menuId = PropertiesUtil.instance().get("WORK_DAY_MENUID");
		}
		if (StringUtils.equals(type, "w")) {
			param.addEquals("reportType", "w");
			menuId = PropertiesUtil.instance().get("WORK_WEEK_MENUID");
		}
		if (StringUtils.equals(type, "m")) {
			param.addEquals("reportType", "m");
			menuId = PropertiesUtil.instance().get("WORK_MONTH_MENUID");
		}

		String viewFlag = "";
		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
				viewFlag = "all";
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.setOrderBy("createTime desc");

		List<WorkReport> list = workReportService.findList(param);

		request.setAttribute("list", list);
		request.setAttribute("typeflag", type);
		request.setAttribute("viewFlag", viewFlag);
		return "crm/workReport/index_workReport";
	}

	/**
	 * 审核首页页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/verifyIndex.do")
	public String exeVerifyIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("verifyIndex...........");
		String resourceid = request.getParameter("resourceid");
		String viewFlag = request.getParameter("viewFlag");

		WorkReport workReport = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			workReport = workReportService.load(resourceid);
		}

		model.addAttribute("viewFlag", viewFlag);
		model.addAttribute("workReport", workReport);
		return "crm/workReport/verify2_workReport";
	}

	@Resource(name = "workreportservice")
	WorkReportService workReportService;

}
