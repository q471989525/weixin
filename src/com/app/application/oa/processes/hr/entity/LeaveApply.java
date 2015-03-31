package com.app.application.oa.processes.hr.entity;

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
 * 请假流程
 */
@Entity
@Table(name = "p_leave_apply")
public class LeaveApply extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String applyId;
	private String applyName;
	private String deptName;
	private String postName;
	private String leaveType;
	private Date startTime;
	private Date endTime;
	private Float sumDay;
	private String applyReason;
	private String createId;
	private String createName;
	private Date createTime;
	// temp
	private String startTimeFmt;
	private String endTimeFmt;
	private String createTimeFmt;

	public LeaveApply() {
	}

	public LeaveApply(String applyId, String applyName, String deptName, String postName, String createId, String createName, Date createTime) {
		super();
		this.applyId = applyId;
		this.applyName = applyName;
		this.deptName = deptName;
		this.postName = postName;
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
	}

	@Column(name = "APPLY_ID", length = 32)
	public String getApplyId() {
		return this.applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	@Column(name = "APPLY_NAME", length = 50)
	public String getApplyName() {
		return this.applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	@Column(name = "DEPT_NAME", length = 50)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "POST_NAME", length = 50)
	public String getPostName() {
		return this.postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(name = "LEAVE_TYPE", length = 10)
	public String getLeaveType() {
		return this.leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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

	@Column(name = "SUM_DAY", precision = 8)
	public Float getSumDay() {
		return this.sumDay;
	}

	public void setSumDay(Float sumDay) {
		this.sumDay = sumDay;
	}

	@Column(name = "APPLY_REASON", length = 500)
	public String getApplyReason() {
		return this.applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	@Column(name = "CREATE_ID", length = 32)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATE_NAME", length = 50)
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

	@Transient
	public String getStartTimeFmt() {
		if (null != startTime)
			startTimeFmt = DateTimeUtil.formatShortDateTime(startTime);
		return startTimeFmt;
	}

	public void setStartTimeFmt(String startTimeFmt) {
		this.startTimeFmt = startTimeFmt;
	}

	@Transient
	public String getEndTimeFmt() {
		if (null != endTime)
			endTimeFmt = DateTimeUtil.formatShortDateTime(endTime);
		return endTimeFmt;
	}

	public void setEndTimeFmt(String endTimeFmt) {
		this.endTimeFmt = endTimeFmt;
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