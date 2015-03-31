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
 * TODO：实施日志
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_IMPLEMENT_LOG")
public class ImplementLog extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String implementId;
	private String implementItem;
	private String implementContent;
	private String implementPerson;
	private String customerSign;
	private String attachmentId;
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteFlag;
	private Date startTime;
	private Date endTime;

	// temp
	private String customerName;
	private String projectName;
	private String startTimeFmt;
	private String endTimeFmt;

	public ImplementLog() {
	}

	@Column(name = "IMPLEMENT_ID", length = 32)
	public String getImplementId() {
		return this.implementId;
	}

	public void setImplementId(String implementId) {
		this.implementId = implementId;
	}

	@Column(name = "IMPLEMENT_ITEM", length = 50)
	public String getImplementItem() {
		return this.implementItem;
	}

	public void setImplementItem(String implementItem) {
		this.implementItem = implementItem;
	}

	@Column(name = "IMPLEMENT_CONTENT")
	public String getImplementContent() {
		return this.implementContent;
	}

	public void setImplementContent(String implementContent) {
		this.implementContent = implementContent;
	}

	@Column(name = "IMPLEMENT_PERSON", length = 50)
	public String getImplementPerson() {
		return this.implementPerson;
	}

	public void setImplementPerson(String implementPerson) {
		this.implementPerson = implementPerson;
	}

	@Column(name = "CUSTOMER_SIGN", length = 50)
	public String getCustomerSign() {
		return this.customerSign;
	}

	public void setCustomerSign(String customerSign) {
		this.customerSign = customerSign;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public String getStartTimeFmt() {
		if (startTime != null)
			startTimeFmt = DateTimeUtil.formatDate(startTime, DateTimeUtil.yyyyMMddHHmm);
		return startTimeFmt;
	}

	public void setStartTimeFmt(String startTimeFmt) {
		this.startTimeFmt = startTimeFmt;
	}

	@Transient
	public String getEndTimeFmt() {
		if (endTime != null)
			endTimeFmt = DateTimeUtil.formatDate(endTime, DateTimeUtil.yyyyMMddHHmm);
		return endTimeFmt;
	}

	public void setEndTimeFmt(String endTimeFmt) {
		this.endTimeFmt = endTimeFmt;
	}

}