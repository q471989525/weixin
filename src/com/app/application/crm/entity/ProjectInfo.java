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
 * TODO：项目信息
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_PROJECT_INFO")
public class ProjectInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String projectName;
	private String fileNo;
	private String procurementMethod;
	private String projectRequire;
	private Double priceLimit;
	private String priceLimit2;
	private String timeLimit;
	private Date openTime;
	private String openTimeFmt;
	private String openAddress;
	private String attachmentId;
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteFlag;
	// temp
	private String customerName;
	private String contactsIds;
	private String contactsName;
	private String competitorIds;
	private String competitorName;

	public ProjectInfo() {
	}

	@Column(name = "CUSTOMER_ID", length = 32)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "FILE_NO", length = 100)
	public String getFileNo() {
		return this.fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	@Column(name = "PROCUREMENT_METHOD", length = 50)
	public String getProcurementMethod() {
		return this.procurementMethod;
	}

	public void setProcurementMethod(String procurementMethod) {
		this.procurementMethod = procurementMethod;
	}

	@Column(name = "PROJECT_REQUIRE")
	public String getProjectRequire() {
		return this.projectRequire;
	}

	public void setProjectRequire(String projectRequire) {
		this.projectRequire = projectRequire;
	}

	@Column(name = "PRICE_LIMIT", precision = 12)
	public Double getPriceLimit() {
		return this.priceLimit;
	}

	public void setPriceLimit(Double priceLimit) {
		this.priceLimit = priceLimit;
	}

	@Column(name = "PRICE_LIMIT2", length = 50)
	public String getPriceLimit2() {
		return this.priceLimit2;
	}

	public void setPriceLimit2(String priceLimit2) {
		this.priceLimit2 = priceLimit2;
	}

	@Column(name = "TIME_LIMIT", length = 200)
	public String getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPEN_TIME")
	public Date getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Transient
	public String getOpenTimeFmt() {
		if (null != openTime)
			openTimeFmt = DateTimeUtil.formatDate(openTime, DateTimeUtil.yyyyMMddHHmm);
		return openTimeFmt;
	}

	public void setOpenTimeFmt(String openTimeFmt) {
		this.openTimeFmt = openTimeFmt;
	}

	@Column(name = "OPEN_ADDRESS", length = 200)
	public String getOpenAddress() {
		return this.openAddress;
	}

	public void setOpenAddress(String openAddress) {
		this.openAddress = openAddress;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return attachmentId;
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

	@Transient
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Transient
	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	@Transient
	public String getCompetitorName() {
		return competitorName;
	}

	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}

	@Transient
	public String getContactsIds() {
		return contactsIds;
	}

	public void setContactsIds(String contactsIds) {
		this.contactsIds = contactsIds;
	}

	@Transient
	public String getCompetitorIds() {
		return competitorIds;
	}

	public void setCompetitorIds(String competitorIds) {
		this.competitorIds = competitorIds;
	}

}