package com.app.application.oa.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：通知用户表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_PORTAL_NOTIFICATION_USER")
public class NotificationUser extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2497795345347657102L;
	private String notifyId;
	private String userId;
	private String userName;
	private String viewFlag;

	public NotificationUser() {
	}

	@Column(name = "NOTIFY_ID", length = 32)
	public String getNotifyId() {
		return this.notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	@Column(name = "USER_ID", length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "VIEW_FLAG", length = 1)
	public String getViewFlag() {
		return this.viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NotificationUser) || (obj == null)) {
			return false;
		}
		NotificationUser notificationUser = (NotificationUser) obj;
		return this.getUserId().equals(notificationUser.getUserId());
	}

}