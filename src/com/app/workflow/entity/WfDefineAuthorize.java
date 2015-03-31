package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 委托定义
 */
@Entity
@Table(name = "wf_define_authorize")
public class WfDefineAuthorize extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String authorizerId; // 委托人ID
	private String authorizer; // 委托人
	private String authorizeUserId; // 被委托人ID
	private String authorizeUserName; // 被委托人
	private Date startDate; // 开始时间
	private Date endDate; // 结束时间
	private String isAll; // 是否全部委托 Y/N
	private String remark;
	private String createId;
	private String createName;
	private Date createTime;

	public WfDefineAuthorize() {
	}

	public WfDefineAuthorize(String createId, String createName, Date createTime) {
		super();
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
	}

	@Column(name = "AUTHORIZER_ID", length = 32)
	public String getAuthorizerId() {
		return this.authorizerId;
	}

	public void setAuthorizerId(String authorizerId) {
		this.authorizerId = authorizerId;
	}

	@Column(name = "AUTHORIZER", length = 20)
	public String getAuthorizer() {
		return this.authorizer;
	}

	public void setAuthorizer(String authorizer) {
		this.authorizer = authorizer;
	}

	@Column(name = "AUTHORIZE_USER_ID", length = 32)
	public String getAuthorizeUserId() {
		return this.authorizeUserId;
	}

	public void setAuthorizeUserId(String authorizeUserId) {
		this.authorizeUserId = authorizeUserId;
	}

	@Column(name = "AUTHORIZE_USER_NAME", length = 20)
	public String getAuthorizeUserName() {
		return this.authorizeUserName;
	}

	public void setAuthorizeUserName(String authorizeUserName) {
		this.authorizeUserName = authorizeUserName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "IS_ALL", length = 1)
	public String getIsAll() {
		return this.isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}

	@Column(name = "REMARK", length = 450)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_ID", length = 32)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATE_NAME", length = 45)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}