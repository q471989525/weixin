package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.app.core.entity.BaseEntity;

/**
 * 路由定义表
 */
@Entity
@Table(name = "wf_define_route")
public class WfDefineRoute extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String activityId; // 活动定义ID
	private String routeAlias; // 路由别名
	private String nextActivityId; // 下一个活动定义ID
	private String finishType; // 结束类型,Y:正常结束，N:异常结束
	private String conditionExpression; // 条件表达式
	private String conditionExplain; // 条件说明
	private String candidateId; // 主办人ID
	private String candidateName; // 主办人
	private String personId; // 经办人ID
	private String personName; // 经办人
	private Integer orderBy;
	private String remark;
	private String createId;
	private String createName;
	private Date createTime;
	// temp
	private String nextActivityName; // 下一个活动名称
	private String activityType; // 活动类型,S:开始,T:任务,E:结束

	public WfDefineRoute() {
	}

	public WfDefineRoute(String activityId, String createId, String createName,
			Date createTime) {
		super();
		this.activityId = activityId;
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
	}

	@Column(name = "ACTIVITY_ID", length = 32)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "ROUTE_ALIAS", length = 50)
	public String getRouteAlias() {
		return this.routeAlias;
	}

	public void setRouteAlias(String routeAlias) {
		this.routeAlias = routeAlias;
	}

	@Column(name = "NEXT_ACTIVITY_ID", length = 32)
	public String getNextActivityId() {
		return nextActivityId;
	}

	public void setNextActivityId(String nextActivityId) {
		this.nextActivityId = nextActivityId;
	}

	@Column(name = "FINISH_TYPE", length = 1)
	public String getFinishType() {
		return this.finishType;
	}

	public void setFinishType(String finishType) {
		this.finishType = finishType;
	}

	@Column(name = "CONDITION_EXPRESSION", length = 100)
	public String getConditionExpression() {
		return this.conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	@Column(name = "CONDITION_EXPLAIN", length = 200)
	public String getConditionExplain() {
		return this.conditionExplain;
	}

	public void setConditionExplain(String conditionExplain) {
		this.conditionExplain = conditionExplain;
	}

	@Column(name = "CANDIDATE_ID", length = 500)
	public String getCandidateId() {
		return this.candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	@Column(name = "CANDIDATE_NAME", length = 250)
	public String getCandidateName() {
		return this.candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	@Column(name = "PERSON_ID", length = 500)
	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(name = "PERSON_NAME", length = 250)
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 250)
	public String getRemark() {
		return remark;
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
	public String getNextActivityName() {
		return nextActivityName;
	}

	public void setNextActivityName(String nextActivityName) {
		this.nextActivityName = nextActivityName;
	}

	@Transient
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

}