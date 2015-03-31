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
 * TODO：运行记录
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_RUN_RECORD")
public class RunRecord extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String projectId;
	private String serviceItem;
	private String runContent;
	private String servicePerson;
	private Date startTime;
	private Date endTime;
	private String attachmentId;
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteFlag;
	private String customerSign;

	// temp
	private String customerName;
	private String projectName;
	private String createTimeFmt;
	private String startTimeFmt;
	private String endTimeFmt;

	public RunRecord() {
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "SERVICE_ITEM", length = 50)
	public String getServiceItem() {
		return this.serviceItem;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}

	@Column(name = "RUN_CONTENT")
	public String getRunContent() {
		return this.runContent;
	}

	public void setRunContent(String runContent) {
		this.runContent = runContent;
	}

	@Column(name = "SERVICE_PERSON", length = 50)
	public String getServicePerson() {
		return this.servicePerson;
	}

	public void setServicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Column(name = "CUSTOMER_SIGN", length = 50)
	public String getCustomerSign() {
		return this.customerSign;
	}

	public void setCustomerSign(String customerSign) {
		this.customerSign = customerSign;
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

	@Transient
	public String getStartTimeFmt() {
		if (null != startTime)
			startTimeFmt = DateTimeUtil.formatDate(startTime, DateTimeUtil.yyyyMMddHHmm);
		return startTimeFmt;
	}

	public void setStartTimeFmt(String startTimeFmt) {
		this.startTimeFmt = startTimeFmt;
	}

	@Transient
	public String getEndTimeFmt() {
		if (null != endTime)
			endTimeFmt = DateTimeUtil.formatDate(endTime, DateTimeUtil.yyyyMMddHHmm);
		return endTimeFmt;
	}

	public void setEndTimeFmt(String endTimeFmt) {
		this.endTimeFmt = endTimeFmt;
	}

}