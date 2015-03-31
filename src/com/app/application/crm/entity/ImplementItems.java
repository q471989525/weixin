package com.app.application.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：实施任务
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_IMPLEMENT_ITEMS")
public class ImplementItems extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String implementId;
	private String planTask;
	private String principal;
	private Date startTime;
	private Date endTime;
	private String remark;
	private String deleteFlag;

	public ImplementItems() {
	}

	@Column(name = "IMPLEMENT_ID", length = 32)
	public String getImplementId() {
		return this.implementId;
	}

	public void setImplementId(String implementId) {
		this.implementId = implementId;
	}

	@Column(name = "PLAN_TASK")
	public String getPlanTask() {
		return this.planTask;
	}

	public void setPlanTask(String planTask) {
		this.planTask = planTask;
	}

	@Column(name = "PRINCIPAL", length = 50)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_TIME", length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_TIME", length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}