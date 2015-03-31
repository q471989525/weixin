package com.app.application.oa.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：通知内容
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_PORTAL_NOTIFICATION_CLOB")
public class NotificationClob extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -961648980262042148L;

	private String notifyId;
	private String notifyContent;

	public NotificationClob() {
	}

	public NotificationClob(String notifyContent) {
		super();
		this.notifyContent = notifyContent;
	}

	@Column(name = "NOTIFY_ID", length = 32)
	public String getNotifyId() {
		return this.notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	@Column(name = "NOTIFY_CONTENT")
	public String getNotifyContent() {
		return this.notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

}