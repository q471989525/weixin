package com.app.application.oa.portal.controller;

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
import com.app.base.entity.SysUser;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.application.oa.portal.entity.MeetRoom;
import com.app.application.oa.portal.service.MeetRoomService;

/**
 * 会议室管理
 */
@Controller
@Scope("prototype")
@RequestMapping("/meetRoom")
public class MeetRoomController extends BaseController {

	private static Logger logger = Logger.getLogger(MeetRoomController.class);

	@Autowired
	MeetRoomService meetRoomService;

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
		return "oa/portal/meet/room/list_meetRoom";
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
	public void exeDataGrid(String menuId, MeetRoom meetRoom, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(meetRoom.getMeetName()))
			param.addLike("meetName", meetRoom.getMeetName());

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

		param.addEquals("deleteFlag", "n");
		List<MeetRoom> pageList = meetRoomService.findPageList(page, param);

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

		SysUser sysUser = SpringContextUtil.getUser();

		MeetRoom meetRoom = new MeetRoom();
		meetRoom.setCreateId(sysUser.getResourceid());
		meetRoom.setCreator(sysUser.getUsername());
		meetRoom.setCreateTime(new Date());
		meetRoom.setDeleteFlag("n");

		model.addAttribute("user", sysUser);
		model.addAttribute("meetRoom", meetRoom);
		return "oa/portal/meet/room/edit_meetRoom";
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

		MeetRoom meetRoom = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			meetRoom = meetRoomService.load(resourceid);
		}

		SysUser sysUser = SpringContextUtil.getUser();
		model.addAttribute("user", sysUser);
		model.addAttribute("meetRoom", meetRoom);
		return "oa/portal/meet/room/edit_meetRoom";
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
	public void exeSave(MeetRoom meetRoom, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(meetRoom.getResourceid())) { // 新增
				meetRoomService.save(meetRoom);
			} else {
				meetRoomService.update(meetRoom); // 修改
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
				meetRoomService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

}
