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
 * TODO：工作报告
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_WORK_REPORT")
public class WorkReport extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date endDate;
	private String workContent;
	private String reportType;
	private String createId;
	private String creator;
	private Date createTime;
	private String verifyFlag;
	private String verifyDesc;

	// temp
	private String startDateFmt;
	private String endDateFmt;
	private String createTimeFmt;

	public WorkReport() {
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE", length = 7)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_DATE", length = 7)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "WORK_CONTENT")
	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	@Column(name = "REPORT_TYPE", length = 1)
	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
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

	@Column(name = "VERIFY_FLAG", length = 1)
	public String getVerifyFlag() {
		return this.verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	@Column(name = "VERIFY_DESC", length = 2000)
	public String getVerifyDesc() {
		return this.verifyDesc;
	}

	public void setVerifyDesc(String verifyDesc) {
		this.verifyDesc = verifyDesc;
	}

	@Transient
	public String getStartDateFmt() {
		startDateFmt = startDate != null ? DateTimeUtil.formatDate(startDate) : "";
		return startDateFmt;
	}

	public void setStartDateFmt(String startDateFmt) {
		this.startDateFmt = startDateFmt;
	}

	@Transient
	public String getEndDateFmt() {
		endDateFmt = endDate != null ? DateTimeUtil.formatDate(endDate) : "";
		return endDateFmt;
	}

	public void setEndDateFmt(String endDateFmt) {
		this.endDateFmt = endDateFmt;
	}

	@Transient
	public String getCreateTimeFmt() {
		createTimeFmt = createTime != null ? DateTimeUtil.formatDateTime(createTime) : "";
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}

}