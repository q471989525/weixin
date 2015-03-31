package com.app.application.oa.portal.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.PubAttachment;
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.DateTimeUtil;
import com.app.utils.SpringContextUtil;
import com.app.application.oa.portal.dao.MeetOrderPersonDao;
import com.app.application.oa.portal.entity.MeetOrder;
import com.app.application.oa.portal.entity.MeetOrderPerson;
import com.app.application.oa.portal.entity.MeetRoom;
import com.app.application.oa.portal.service.MeetOrderService;
import com.app.application.oa.portal.service.MeetRoomService;

/**
 * 会议预定
 */
@Controller
@Scope("prototype")
@RequestMapping("/meetOrder")
public class MeetOrderController extends BaseController {

	private static Logger logger = Logger.getLogger(MeetOrderController.class);

	@Autowired
	MeetOrderService meetOrderService;

	@Autowired
	MeetRoomService meetRoomService;

	@Autowired
	MeetOrderPersonDao meetOrderPersonDao;

	@Autowired
	PubAttachmentService pubAttachmentService;

	/**
	 * 会议室预订页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/order.do")
	public String exeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		QueryParameter param = new QueryParameter();
		param.addEquals("validFlag", "y");
		param.addEquals("deleteFlag", "n");
		param.setOrderBy("orderBy");
		List<MeetRoom> meetRooms = meetRoomService.findList(param); // 查询有效会议室

		if (CollectionUtils.isNotEmpty(meetRooms)) {
			QueryParameter param2 = null;
			for (MeetRoom room : meetRooms) {
				param2 = new QueryParameter();
				param2.addGtEq("endTime", DateTimeUtil.formatShortDateTime(new Date()));
				param2.addEquals("meetId", room.getResourceid());
				param2.setOrderBy("startTime");
				List<MeetOrder> orders = meetOrderService.findList(param2);
				room.setOrders(orders);
			}
		}

		request.setAttribute("meetRooms", meetRooms);
		return "oa/portal/meet/order/order_meetOrder";
	}

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
	public String exeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roomId = request.getParameter("roomId");
		if (StringUtils.isNotEmpty(roomId)) {
			MeetRoom room = meetRoomService.load(roomId);
			request.setAttribute("room", room);
		}
		return "oa/portal/meet/order/list_meetOrder";
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
	public void exeDataGrid(String menuId, MeetOrder meetOrder, PageUtil page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(meetOrder.getMeetId()))
			param.addEquals("meetId", meetOrder.getMeetId());
		if (StringUtils.isNotEmpty(meetOrder.getMeetSubject()))
			param.addLike("meetSubject", meetOrder.getMeetSubject());
		if (null != meetOrder.getStartTime())
			param.addGtEq("startTime", DateTimeUtil.formatShortDateTime(meetOrder.getStartTime()));
		if (null != meetOrder.getEndTime())
			param.addLtEq("endTime", DateTimeUtil.formatShortDateTime(meetOrder.getEndTime()));

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}

		if (StringUtils.startsWith(page.getSort(), "startTimeFmt"))
			page.setSort("startTime");
		if (StringUtils.startsWith(page.getSort(), "endTimeFmt"))
			page.setSort("endTime");
		if (StringUtils.startsWith(page.getSort(), "createTimeFmt"))
			page.setSort("createTime");

		List<MeetOrder> pageList = meetOrderService.findPageList(page, param);

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
	public String exeAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("add...........");
		String roomId = request.getParameter("roomId");
		String roomName = request.getParameter("roomName");

		SysUser sysUser = SpringContextUtil.getUser();

		MeetOrder meetOrder = new MeetOrder();
		meetOrder.setCreateId(sysUser.getResourceid());
		meetOrder.setCreator(sysUser.getUsername());
		meetOrder.setCreateTime(new Date());
		meetOrder.setCompereId(sysUser.getResourceid());
		meetOrder.setCompereName(sysUser.getUsername());
		meetOrder.setRecorderId(sysUser.getResourceid());
		meetOrder.setRecorder(sysUser.getUsername());
		meetOrder.setAttachmentId(UUID.randomUUID().toString());
		if (StringUtils.isNotEmpty(roomId) && StringUtils.isNotEmpty(roomName)) {
			meetOrder.setMeetId(roomId);
			meetOrder.setMeetName(URLDecoder.decode(roomName, "UTF-8"));
		}

		request.setAttribute("meetOrder", meetOrder);
		return "oa/portal/meet/order/edit_meetOrder";
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
	public String exeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("edit...........");
		String resourceid = request.getParameter("resourceid");
		String view = request.getParameter("view");

		MeetOrder meetOrder = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			meetOrder = meetOrderService.load(resourceid);

			MeetRoom room = meetRoomService.load(meetOrder.getMeetId());
			meetOrder.setMeetId(room.getResourceid());
			meetOrder.setMeetName(room.getMeetName());

			List<String> userIds = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();
			List<MeetOrderPerson> orderPersons = meetOrderPersonDao._findListByHql("from MeetOrderPerson where meetOrderId=?", meetOrder.getResourceid());
			if (CollectionUtils.isNotEmpty(orderPersons)) {
				for (MeetOrderPerson p : orderPersons) {
					userIds.add(p.getUserId());
					userNames.add(p.getUserName());
				}
				request.setAttribute("userId", StringUtils.join(userIds, ","));
				request.setAttribute("userName", StringUtils.join(userNames, ","));
			}

			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(meetOrder.getAttachmentId());
			request.setAttribute("attList", attList);
		}

		request.setAttribute("meetOrder", meetOrder);
		if (StringUtils.equals(view, "flag")) {
			return "oa/portal/meet/order/view_meetOrder";
		} else {
			return "oa/portal/meet/order/edit_meetOrder";
		}
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
	public void exeSave(MeetOrder meetOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = request.getParameter("userId");
			String userName = request.getParameter("userName");

			List<MeetOrderPerson> persons = new ArrayList<MeetOrderPerson>();
			if (StringUtils.isNotEmpty(userId)) {
				String[] userids = userId.split(",");
				String[] userNames = userName.split(",");
				for (int i = 0; i < userids.length; i++) {
					persons.add(new MeetOrderPerson(userids[i], userNames[i]));
				}
			}

			if (StringUtils.isEmpty(meetOrder.getResourceid())) { // 新增
				meetOrderService.save(meetOrder, persons);
			} else {
				meetOrderService.update(meetOrder, persons); // 修改
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
				meetOrderService.deleteByIds(id.split(","));
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
	@RequestMapping("/validMeetTime.do")
	public void exeValidMeetTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String meetId = request.getParameter("meetId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String state = "failed";
			if (StringUtils.isNotEmpty(meetId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
				int count = meetOrderService.findValidMeetTime(meetId, startTime, endTime, resourceid);
				if (count == 0)
					state = "success"; // 没有时间冲突
			}
			map.put("state", state);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

}
