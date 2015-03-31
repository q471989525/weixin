package com.app.application.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.app.core.entity.BaseEntity;
import com.app.utils.DateTimeUtil;

/**
 * 
 * TODO：实施
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_IMPLEMENT")
public class Implement extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String projectId;
	private String dutyPerson;
	private String dutyPersonPhone;
	private String implementContent;
	private String attachmentId;
	private String deleteFlag;
	private String createId;
	private String creator;
	private Date createTime;

	// temp
	private String customerName;
	private String projectName;
	private String createTimeFmt;

	public Implement() {
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "DUTY_PERSON", length = 50)
	public String getDutyPerson() {
		return this.dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	@Column(name = "DUTY_PERSON_PHONE", length = 50)
	public String getDutyPersonPhone() {
		return this.dutyPersonPhone;
	}

	public void setDutyPersonPhone(String dutyPersonPhone) {
		this.dutyPersonPhone = dutyPersonPhone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IMPLEMENT_CONTENT")
	public String getImplementContent() {
		return this.implementContent;
	}

	public void setImplementContent(String implementContent) {
		this.implementContent = implementContent;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "CREATE_ID", length = 32)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATOR", length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Transient
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Transient
	public String getCreateTimeFmt() {
		if (null != createTime)
			createTimeFmt = DateTimeUtil.formatDateTime(createTime);
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}

}