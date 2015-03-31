package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 活动实例处理人
 */
@Entity
@Table(name = "wf_instance_activity_person")
public class WfInstanceActivityPerson extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String activityInsId; // 活动实例ID
	private String status; // 状态;A:活动、F:完成、S:停止
	private String personType; // 办理类型;M:master主办，A:assist经办
	private String assigneeId; // 处理人ID
	private String assigneeName; // 处理人
	private String acceptFlag; // 接收标志;U:未接收,P:办理中,F:办理完成
	private Date acceptTime; // 接收时间
	private Date finishTime; // 完成时间
	private String selectOpinion; // 下拉意见
	private String textOpinion; // 文本意见

	public WfInstanceActivityPerson() {
	}

	@Column(name = "ACTIVITY_INS_ID", length = 32)
	public String getActivityInsId() {
		return this.activityInsId;
	}

	public void setActivityInsId(String activityInsId) {
		this.activityInsId = activityInsId;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "PERSON_TYPE", length = 1)
	public String getPersonType() {
		return this.personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	@Column(name = "ASSIGNEE_ID", length = 32)
	public String getAssigneeId() {
		return this.assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	@Column(name = "ASSIGNEE_NAME", length = 50)
	public String getAssigneeName() {
		return this.assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	@Column(name = "ACCEPT_FLAG", length = 1)
	public String getAcceptFlag() {
		return this.acceptFlag;
	}

	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACCEPT_TIME")
	public Date getAcceptTime() {
		return this.acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISH_TIME")
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Column(name = "SELECT_OPINION", length = 100)
	public String getSelectOpinion() {
		return this.selectOpinion;
	}

	public void setSelectOpinion(String selectOpinion) {
		this.selectOpinion = selectOpinion;
	}

	@Column(name = "TEXT_OPINION", length = 600)
	public String getTextOpinion() {
		return this.textOpinion;
	}

	public void setTextOpinion(String textOpinion) {
		this.textOpinion = textOpinion;
	}

}