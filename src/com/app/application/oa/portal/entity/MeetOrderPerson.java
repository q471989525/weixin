package com.app.application.oa.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 参会人员
 */
@Entity
@Table(name = "T_PORTAL_MEET_ORDER_PERSON")
public class MeetOrderPerson extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String userName;
	private String meetOrderId;
	private String viewFlag; //查看标志

	public MeetOrderPerson() {
	}

	public MeetOrderPerson(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.viewFlag = "n";
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

	@Column(name = "MEET_ORDER_ID", length = 32)
	public String getMeetOrderId() {
		return this.meetOrderId;
	}

	public void setMeetOrderId(String meetOrderId) {
		this.meetOrderId = meetOrderId;
	}

	@Column(name = "VIEW_FLAG", length = 1)
	public String getViewFlag() {
		return this.viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

}