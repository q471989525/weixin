package com.app.workflow.controller;

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
import com.app.base.entity.SysResource;
import com.app.base.entity.SysUser;
import com.app.base.service.SysResourceService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.workflow.entity.WfDefineProcess;
import com.app.workflow.service.WfDefineProcessService;

/**
 * 流程定义
 */
@Controller
@Scope("prototype")
@RequestMapping("/wfDefineProcess")
public class WfDefineProcessController extends BaseController {

	private static Logger logger = Logger.getLogger(WfDefineProcessController.class);

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
		return "workflow/process/list_DefineProcess";
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
	public void exeDataGrid(WfDefineProcess process, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		// if (StringUtils.isNotEmpty(dictionary.getDictCode()))
		// param.addLike("dictCode", dictionary.getDictCode());
		// if (StringUtils.isNotEmpty(dictionary.getDictName()))
		// param.addLike("dictName", dictionary.getDictName());

		param.addEquals("deleteState", "n");
		List<WfDefineProcess> pageList = wfDefineProcessService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (WfDefineProcess defineProcess : pageList) {
				SysResource resource = sysResourceService.load(defineProcess.getMenuId());
				defineProcess.setMenuName(resource.getMenuName());
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
		String orderBy = request.getParameter("orderBy");
		
		WfDefineProcess process = new WfDefineProcess(user.getResourceid(), user.getUsername(), new Date());
		if(NumberUtils.isNumber(orderBy)) process.setOrderBy(Integer.valueOf(orderBy));
		
		model.addAttribute("process", process);
		return "workflow/process/edit_DefineProcess";
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

		WfDefineProcess process = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			process = wfDefineProcessService.load(resourceid);
		}
		model.addAttribute("process", process);
		return "workflow/process/edit_DefineProcess";
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
	public void exeSave(WfDefineProcess process, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(process.getResourceid())) { // 新增
				wfDefineProcessService.save(process);
			} else {
				wfDefineProcessService.update(process); // 修改
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
				wfDefineProcessService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "wfDefineProcessService")
	WfDefineProcessService wfDefineProcessService;

	@Resource(name = "sysresourceservice")
	SysResourceService sysResourceService;

}
