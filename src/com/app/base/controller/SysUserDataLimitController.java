package com.app.base.controller;

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
import com.app.base.entity.SysUserDataLimit;
import com.app.base.service.SysUserDataLimitService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;

/**
 * 
 * TODO：用户数据权限表
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysUserDataLimit")
public class SysUserDataLimitController extends BaseController {

	private static Logger logger = Logger.getLogger(SysUserDataLimitController.class);

	@Resource(name = "sysuserdatalimitservice")
	SysUserDataLimitService sysUserDataLimitService;

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
		return "base/userdata/list_userdata";
	}

	/**
	 * 列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(SysUserDataLimit userdate, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(userdate.getUserName())) {
			param.addLike("userName", userdate.getUserName());
		}
		if (StringUtils.isNotEmpty(userdate.getResourceName()))
			param.addLike("resourceName", userdate.getResourceName());

		param.addEquals("deleteState", "n");
		List<SysUserDataLimit> pageList = sysUserDataLimitService.findPageList(page, param);

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

		SysUserDataLimit userdate = new SysUserDataLimit();

		model.addAttribute("userdate", userdate);
		return "base/userdata/edit_userdata";
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

		SysUserDataLimit userdate = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			userdate = sysUserDataLimitService.load(resourceid);
		}

		model.addAttribute("userdate", userdate);
		return "base/userdata/edit_userdata";
	}

	/**
	 * 保存页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void exeSave(SysUserDataLimit userdate, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userdate.setDeleteState("n");
			if (StringUtils.isEmpty(userdate.getResourceid())) { // 新增
				sysUserDataLimitService.save(userdate);
			} else {
				sysUserDataLimitService.update(userdate); // 修改
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
				sysUserDataLimitService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 刷新缓存
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/refresh.do")
	public void exeRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysUserDataLimitService.refresh("refresh");
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

}
