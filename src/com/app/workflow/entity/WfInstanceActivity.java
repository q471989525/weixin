package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 活动实例
 */
@Entity
@Table(name = "wf_instance_activity")
public class WfInstanceActivity extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer stepNumber; // 步骤
	private String status; // 活动状态;A:活动、F：完成、S: 挂起
	private String activityType; // 活动类型;S:开始,T:任务,M:多人任务,E:结束,C:抄送
	private String taskFlag; // 强制会签标志;Y:强制、N:非强制
	private String activityName; // 活动名称
	private String activityAlias; // 活动别名
	private String upActivityInsId; // 上一活动ID
	private Date createTime; // 创建时间
	private Date finishTime; // 完成时间
	private Date predictTime; // 预计结束时间
	private String timeOut; // 超时状态;Y:超时，N:及时
	private String forkJoin; // 分支聚合;F:分支、J:聚合、B:聚合分支、G:普通
	private Integer joinActivityCount; // 聚合节点数
	private String processInsId; // 流程实例ID
	private String activityId; // 活动定义ID
	private String gobackType; // 回退类型;D:默认，R:直接回退
	private String goActivityInsId; // 回退活动实例ID
	private String urlAddress; // URL地址;完整地址
	private Integer orderBy; // 排序

	public WfInstanceActivity() {
	}

	public WfInstanceActivity(Integer stepNumber, String status, String activityType, String taskFlag, String activityName, String activityAlias, String upActivityInsId, Date createTime, Date finishTime, Date predictTime, String timeOut, String forkJoin,
			Integer joinActivityCount, String processInsId, String activityId, String urlAddress, Integer orderBy) {
		super();
		this.stepNumber = stepNumber;
		this.status = status;
		this.activityType = activityType;
		this.taskFlag = taskFlag;
		this.activityName = activityName;
		this.activityAlias = activityAlias;
		this.upActivityInsId = upActivityInsId;
		this.createTime = createTime;
		this.finishTime = finishTime;
		this.predictTime = predictTime;
		this.timeOut = timeOut;
		this.forkJoin = forkJoin;
		this.joinActivityCount = joinActivityCount;
		this.processInsId = processInsId;
		this.activityId = activityId;
		this.urlAddress = urlAddress;
		this.orderBy = orderBy;
	}

	@Column(name = "STEP_NUMBER")
	public Integer getStepNumber() {
		return this.stepNumber;
	}

	public void setStepNumber(Integer stepNumber) {
		this.stepNumber = stepNumber;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ACTIVITY_TYPE", length = 1)
	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Column(name = "TASK_FLAG", length = 1)
	public String getTaskFlag() {
		return this.taskFlag;
	}

	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}

	@Column(name = "ACTIVITY_NAME", length = 100)
	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(name = "ACTIVITY_ALIAS", length = 50)
	public String getActivityAlias() {
		return this.activityAlias;
	}

	public void setActivityAlias(String activityAlias) {
		this.activityAlias = activityAlias;
	}

	@Column(name = "UP_ACTIVITY_INS_ID", length = 32)
	public String getUpActivityInsId() {
		return this.upActivityInsId;
	}

	public void setUpActivityInsId(String upActivityInsId) {
		this.upActivityInsId = upActivityInsId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISH_TIME")
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PREDICT_TIME")
	public Date getPredictTime() {
		return this.predictTime;
	}

	public void setPredictTime(Date predictTime) {
		this.predictTime = predictTime;
	}

	@Column(name = "TIME_OUT", length = 1)
	public String getTimeOut() {
		return this.timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	@Column(name = "FORK_JOIN", length = 1)
	public String getForkJoin() {
		return this.forkJoin;
	}

	public void setForkJoin(String forkJoin) {
		this.forkJoin = forkJoin;
	}

	@Column(name = "JOIN_ACTIVITY_COUNT")
	public Integer getJoinActivityCount() {
		return this.joinActivityCount;
	}

	public void setJoinActivityCount(Integer joinActivityCount) {
		this.joinActivityCount = joinActivityCount;
	}

	@Column(name = "PROCESS_ INS _ID", length = 32)
	public String getProcessInsId() {
		return this.processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	@Column(name = "ACTIVITY_ID", length = 32)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "GOBACK_TYPE", length = 1)
	public String getGobackType() {
		return this.gobackType;
	}

	public void setGobackType(String gobackType) {
		this.gobackType = gobackType;
	}

	@Column(name = "GO_ACTIVITY_INS_ID", length = 32)
	public String getGoActivityInsId() {
		return this.goActivityInsId;
	}

	public void setGoActivityInsId(String goActivityInsId) {
		this.goActivityInsId = goActivityInsId;
	}

	@Column(name = "URL_ADDRESS", length = 200)
	public String getUrlAddress() {
		return this.urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

}