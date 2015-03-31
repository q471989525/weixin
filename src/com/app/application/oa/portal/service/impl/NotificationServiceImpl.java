package com.app.application.oa.portal.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.entity.SysUser;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.oa.portal.dao.NotificationClobDao;
import com.app.application.oa.portal.dao.NotificationDao;
import com.app.application.oa.portal.dao.NotificationUserDao;
import com.app.application.oa.portal.entity.Notification;
import com.app.application.oa.portal.entity.NotificationClob;
import com.app.application.oa.portal.entity.NotificationUser;
import com.app.application.oa.portal.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl extends BaseServiceImpl<Notification> implements NotificationService {

	NotificationDao notificationDao;

	@Autowired
	NotificationUserDao notificationUserDao;

	@Autowired
	NotificationClobDao notificationClobDao;

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
		super.setBaseDao(notificationDao);
	}

	@Override
	public void save(Notification notification, List<NotificationUser> nList) {
		if (StringUtils.equals(notification.getReleaseFlag(), "y")) {
			notification.setReleaseTime(new Date());
		}

		NotificationClob clob = null;
		if (StringUtils.isNotEmpty(notification.getNotifyContent()))
			clob = new NotificationClob(notification.getNotifyContent());

		notificationDao._save(notification);

		//保存内容
		if (clob != null) {
			clob.setNotifyId(notification.getResourceid());
			notificationClobDao._save(clob);
		}

		if (CollectionUtils.isNotEmpty(nList)) {
			for (NotificationUser notificationUser : nList) {
				notificationUser.setNotifyId(notification.getResourceid());
				notificationUserDao._save(notificationUser);
			}
		}
	}

	@Override
	public void update(Notification notification, List<NotificationUser> nList, String submitType) {
		if (StringUtils.equals(notification.getReleaseFlag(), "y")) { // 已发布
			if (StringUtils.equals(submitType, "save")) { // 保存,只修改内容
				// 更新内容
				updateClob(notification);

			} else {
				notification.setViewCount(0);
				notification.setReleaseTime(new Date()); // 重新发布
				updateRelate(notification, nList);
			}
		} else {
			updateRelate(notification, nList);
		}

	}

	private void updateClob(Notification notification) {
		List<NotificationClob> clobList = notificationClobDao._findList(NotificationClob.class, "notifyId='" + notification.getResourceid() + "'", "");
		if (CollectionUtils.isNotEmpty(clobList)) {
			NotificationClob clob = clobList.get(0);
			if (StringUtils.isNotEmpty(notification.getNotifyContent()))
				clob.setNotifyContent(notification.getNotifyContent());
			notificationDao._update(notification);
		}
	}

	private void updateRelate(Notification notification, List<NotificationUser> nList) {
		notificationDao._update(notification);

		// 更新内容
		updateClob(notification);

		// 用户表,先删除，再新增
		List<NotificationUser> list = notificationUserDao._findList(NotificationUser.class, "notifyId='" + notification.getResourceid() + "'", "");
		if (CollectionUtils.isNotEmpty(list))
			notificationUserDao._deleteCollection(list);

		if (CollectionUtils.isNotEmpty(nList)) {
			for (NotificationUser notificationUser : nList) {
				notificationUser.setNotifyId(notification.getResourceid());
				notificationUserDao._save(notificationUser);
			}
		}
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		super.deleteByIds(ids);

		for (int i = 0; i < ids.length; i++) {
			List<NotificationClob> clobList = notificationClobDao._findList(NotificationClob.class, "notifyId='" + ids[i] + "'", "");
			notificationClobDao._deleteCollection(clobList);
		}

		for (int i = 0; i < ids.length; i++) {
			List<NotificationUser> list = notificationUserDao._findList(NotificationUser.class, "notifyId='" + ids[i] + "'", "");
			notificationUserDao._deleteCollection(list);
		}

	}

	@Override
	public List<Notification> findReleaseListByUser(SysUser user, String type) {
		String sqlType = "";
		if (StringUtils.isNotEmpty(type)) {
			if (StringUtils.equals(type, "unread")) {
				sqlType = " and u.view_flag='n'";
			} else if (!StringUtils.equals(type, "all"))
				sqlType = " and t.notify_type='" + type + "'";
		}
		String sql = "select t.resourceid,t.creator,t.notify_type,t.notify_title,t.release_time,t.view_count,u.view_flag from T_PORTAL_NOTIFICATION t, T_PORTAL_NOTIFICATION_USER u where t.release_flag = 'y' " + sqlType
				+ " and u.user_id = ? and t.resourceid = u.notify_id order by t.release_time desc";
		List<Notification> list = jdbcTemplate.query(sql, new Object[] { user.getResourceid() }, new NotificationRowMapper());
		return list;
	}

	private class NotificationRowMapper implements RowMapper<Notification> {
		public Notification mapRow(ResultSet rs, int index) throws SQLException {
			Notification notification = new Notification();
			notification.setResourceid(rs.getString("resourceid"));
			notification.setCreator(rs.getString("creator"));
			notification.setNotifyType(rs.getString("notify_type"));
			notification.setNotifyTitle(rs.getString("notify_title"));
			notification.setReleaseTime(rs.getTimestamp("release_time"));
			notification.setViewCount(rs.getInt("view_count"));
			notification.setViewFlag(rs.getString("view_flag"));
			return notification;
		}
	}

	@Override
	public void updateViewFlag(Notification notification, SysUser user) {
		List<NotificationUser> list = notificationUserDao._findList(NotificationUser.class, "userId='" + user.getResourceid() + "' and notifyId='" + notification.getResourceid() + "'", "");
		if (CollectionUtils.isNotEmpty(list)) {
			NotificationUser nUser = list.get(0);

			if (StringUtils.equals(nUser.getViewFlag(), "n")) {
				nUser.setViewFlag("y");
				notificationUserDao._update(nUser);

				notification.setViewCount(notification.getViewCount() + 1);
			}
		}
	}

}
