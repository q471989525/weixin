package com.app.application.oa.portal.service;

import java.util.List;

import com.app.base.entity.SysUser;
import com.app.core.service.BaseService;
import com.app.application.oa.portal.entity.Notification;
import com.app.application.oa.portal.entity.NotificationUser;

/**
 * 
 * TODO：通知
 * 
 * @author zhoufeng
 */
public interface NotificationService extends BaseService<Notification> {

	void save(Notification notification, List<NotificationUser> nList);

	void update(Notification notification, List<NotificationUser> nList, String submitType);

	/**
	 * 查找已经发布通知
	 * @param user
	 * @return
	 */
	List<Notification> findReleaseListByUser(SysUser user, String type);

	/**
	 * 更新用户查看标示
	 * @param notification
	 * @param user
	 */
	void updateViewFlag(Notification notification, SysUser user);

}
