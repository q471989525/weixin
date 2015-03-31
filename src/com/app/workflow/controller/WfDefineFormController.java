package com.app.workflow.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.core.controller.BaseController;
import com.app.core.service.util.QueryParameter;
import com.app.workflow.entity.WfDefineForm;
import com.app.workflow.entity.WfDefineProcess;
import com.app.workflow.service.WfDefineFormService;
import com.app.workflow.service.WfDefineProcessService;

/**
 * 流程表单
 */
@Controller
@Scope("prototype")
@RequestMapping("/wfDefineForm")
public class WfDefineFormController extends BaseController {

	private static Logger logger = Logger.getLogger(WfDefineFormController.class);

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
		return "workflow/form/list_DefineForm";
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
	public void exeDataGrid(WfDefineForm form, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String processId = request.getParameter("processId");
		List<WfDefineForm> list = new ArrayList<WfDefineForm>();

		if (StringUtils.isNotEmpty(processId)) {
			QueryParameter param = new QueryParameter();
			if (StringUtils.isNotEmpty(form.getElementName()))
				param.addLike("elementName", form.getElementName());
			if (StringUtils.isNotEmpty(form.getElementType()))
				param.addEquals("elementType", form.getElementType());

			param.setOrderBy("orderBy");
			param.addEquals("processId", processId);
			list = wfDefineFormService.findList(param);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);

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

		WfDefineForm form = new WfDefineForm(processId);
		if(NumberUtils.isNumber(orderBy)) form.setOrderBy(Integer.valueOf(orderBy));

		model.addAttribute("form", form);
		model.addAttribute("processName", URLDecoder.decode(processName, "UTF-8"));
		return "workflow/form/edit_DefineForm";
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

		WfDefineForm form = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			form = wfDefineFormService.load(resourceid);

			WfDefineProcess process = wfDefineProcessService.load(form.getProcessId());
			model.addAttribute("processName", process.getMenuName());
		}
		model.addAttribute("form", form);
		return "workflow/form/edit_DefineForm";
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
	public void exeSave(WfDefineForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(form.getResourceid())) { // 新增
				wfDefineFormService.save(form);
			} else {
				wfDefineFormService.update(form); // 修改
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
				wfDefineFormService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 页面选择
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select.do")
	public String exeSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		return "workflow/form/select_DefineForm";
	}

	/**
	 * 查询活动关联元素列表方法
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/activityFormDg.do")
	public void exeactivityFormDG(String activityId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<WfDefineForm> list = new ArrayList<WfDefineForm>();

		if (StringUtils.isNotEmpty(activityId)) {
			QueryParameter param = new QueryParameter();
			param.setOrderBy("orderBy");
			param.addHql("resourceid in (SELECT af.formId FROM WfDefineActivityForm af where af.activityId='" + activityId + "')");
			list = wfDefineFormService.findList(param);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "wfDefineFormService")
	WfDefineFormService wfDefineFormService;

	@Resource(name = "wfDefineProcessService")
	WfDefineProcessService wfDefineProcessService;

}
