package com.app.application.oa.portal.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.PubAttachment;
import com.app.base.entity.SysDictionary;
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.SpringContextUtil;
import com.app.application.oa.portal.dao.NotificationClobDao;
import com.app.application.oa.portal.dao.NotificationUserDao;
import com.app.application.oa.portal.entity.Notification;
import com.app.application.oa.portal.entity.NotificationClob;
import com.app.application.oa.portal.entity.NotificationUser;
import com.app.application.oa.portal.service.NotificationService;

/**
 * 
 * TODO：通知
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/notification")
public class NotificationController extends BaseController {

	private static Logger logger = Logger.getLogger(NotificationController.class);

	@Autowired
	NotificationService notificationService;

	@Autowired
	NotificationUserDao notificationUserDao;

	@Autowired
	NotificationClobDao notificationClobDao;

	@Autowired
	PubAttachmentService pubAttachmentService;

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
		return "oa/portal/notification/list_notification";
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
	public void exeDataGrid(String menuId, Notification notification, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(notification.getNotifyTitle()))
			param.addLike("notifyTitle", notification.getNotifyTitle());
		if (StringUtils.isNotEmpty(notification.getNotifyType()))
			param.addEquals("notifyType", notification.getNotifyType());
		if (StringUtils.isNotEmpty(notification.getReleaseFlag()))
			param.addEquals("releaseFlag", notification.getReleaseFlag());
		if (StringUtils.isNotEmpty(notification.getTopFlag()))
			param.addEquals("topFlag", notification.getTopFlag());
		if (StringUtils.isNotEmpty(notification.getCreator()))
			param.addLike("creator", notification.getCreator());

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
		if (StringUtils.startsWith(page.getSort(), "releaseTimeFmt"))
			page.setSort("releaseTime");

		List<Notification> pageList = notificationService.findPageList(page, param);

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

		Notification notification = new Notification();
		notification.setCreateId(sysUser.getResourceid());
		notification.setCreator(sysUser.getUsername());
		notification.setCreateTime(new Date());
		notification.setCreateDept(sysUser.getOrgName());
		notification.setReleaseFlag("n");
		notification.setViewCount(0);
		notification.setTopFlag("n");
		notification.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", sysUser);
		model.addAttribute("notification", notification);
		return "oa/portal/notification/edit_notification";
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
		
		Notification notification = null;
		List<String> useridList = new ArrayList<String>();
		List<String> usernameList = new ArrayList<String>();

		if (StringUtils.isNotEmpty(resourceid)) {
			notification = notificationService.load(resourceid);

			List<NotificationUser> list = notificationUserDao._findList(NotificationUser.class, "notifyId='" + resourceid + "'", "");
			for (NotificationUser user : list) {
				useridList.add(user.getUserId());
				usernameList.add(user.getUserName());
			}
			model.addAttribute("userids", StringUtils.join(useridList, ","));
			model.addAttribute("usernames", StringUtils.join(usernameList, ","));

			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(notification.getAttachmentId());
			model.addAttribute("attList", attList);

			List<NotificationClob> clobList = notificationClobDao._findList(NotificationClob.class, "notifyId='" + resourceid + "'", "");
			if (CollectionUtils.isNotEmpty(clobList))
				model.addAttribute("contentDesc", clobList.get(0).getNotifyContent());
		}

		SysUser sysUser = SpringContextUtil.getUser();
		model.addAttribute("user", sysUser);
		model.addAttribute("notification", notification);
		return "oa/portal/notification/edit_notification";
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
	public void exeSave(Notification notification, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		Map<String, Object> map = new HashMap<String, Object>();
		String userids = request.getParameter("userids");
		String userNames = request.getParameter("userNames");
		String submitType = request.getParameter("submitType");
		
		try {
			List<NotificationUser> nList = new ArrayList<NotificationUser>();
			if (StringUtils.isNotEmpty(userids) && StringUtils.isNotEmpty(userNames)) {
				String[] ids = userids.split(",");
				String[] names = userNames.split(",");
				for (int i = 0; i < ids.length; i++) {
					NotificationUser user = new NotificationUser();
					user.setUserId(ids[i]);
					user.setUserName(names[i]);
					user.setViewFlag("n");
					if (!nList.contains(user))
						nList.add(user);
				}
			}

			if (StringUtils.isEmpty(notification.getResourceid())) { // 新增
				notificationService.save(notification, nList);
			} else {
				notificationService.update(notification, nList, submitType); // 修改
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
				notificationService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 通知首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.do")
	public String exeIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysDictionary> notiType = DictionaryELTag.getList("D_Notification_Type");
		String type = StringUtils.defaultString(request.getParameter("type"));

		List<Notification> list = notificationService.findReleaseListByUser(SpringContextUtil.getUser(), type);

		request.setAttribute("list", list);
		request.setAttribute("typeflag", type);
		request.setAttribute("notType", notiType);
		return "oa/portal/notification/index_notification";
	}

	/**
	 * 通知查看
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/view.do")
	public String exeView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String notid = StringUtils.defaultString(request.getParameter("notid"));

		Notification notification = new Notification();
		List<NotificationClob> clobList = new ArrayList<NotificationClob>();
		List<PubAttachment> attList = new ArrayList<PubAttachment>();
		if (StringUtils.isNotEmpty(notid)) {
			notification = notificationService.load(notid);

			clobList = notificationClobDao._findList(NotificationClob.class, "notifyId='" + notid + "'", "");

			attList = pubAttachmentService.getAttachmentsByUuid(notification.getAttachmentId());

			notificationService.updateViewFlag(notification, SpringContextUtil.getUser());
		}

		request.setAttribute("attList", attList);
		request.setAttribute("clobList", clobList);
		request.setAttribute("notification", notification);
		return "oa/portal/notification/view_notification";
	}

}
