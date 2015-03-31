package com.app.workflow.controller;

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
import com.app.base.entity.SysUser;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.workflow.entity.WfDefineAuthorize;
import com.app.workflow.service.WfDefineAuthorizeService;

/**
 * 委托定义
 */
@Controller
@Scope("prototype")
@RequestMapping("/wfDefineAuthorize")
public class WfDefineAuthorizeController extends BaseController {
	
	private static Logger logger = Logger.getLogger(WfDefineAuthorizeController.class);

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
		return "workflow/authorize/list_DefineAuthorize";
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
	public void exeDataGrid(WfDefineAuthorize authorize, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("processId", null);

		// if (StringUtils.isNotEmpty(dictionary.getDictCode()))
		// param.addLike("dictCode", dictionary.getDictCode());
		// if (StringUtils.isNotEmpty(dictionary.getDictName()))
		// param.addLike("dictName", dictionary.getDictName());

		param.setOrderBy("orderBy");
		List<WfDefineAuthorize> pageList = wfDefineAuthorizeService.findPageList(page, param);

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
		WfDefineAuthorize authorize = new WfDefineAuthorize(user.getResourceid(), user.getUsername(), new Date());
		model.addAttribute("authorize", authorize);
		return "workflow/authorize/edit_DefineAuthorize";
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

		WfDefineAuthorize authorize = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			authorize = wfDefineAuthorizeService.load(resourceid);
		}
		model.addAttribute("authorize", authorize);
		return "workflow/authorize/edit_DefineAuthorize";
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
	public void exeSave(WfDefineAuthorize authorize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(authorize.getResourceid())) { // 新增
				wfDefineAuthorizeService.save(authorize);
			} else {
				wfDefineAuthorizeService.update(authorize); // 修改
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
				wfDefineAuthorizeService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "wfDefineAuthorizeService")
	WfDefineAuthorizeService wfDefineAuthorizeService;


}
