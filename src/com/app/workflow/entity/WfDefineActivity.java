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
 * 活动定义
 * 
 */
@Entity
@Table(name = "wf_define_activity")
public class WfDefineActivity extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String processId; // 流程定义ID
	private String activityName; // 活动名称
	private String activityAlias; // 活动别名
	private String activityType; // 活动类型,S:开始,T:任务,E:结束
	private String taskFlag; // 任务标志,Y:强制、N:非强制、M:多人
	private String forkJoin; // 分支聚合,F:分支、J:聚合、B:聚合分支、G:普通
	private Integer joinActivityCount; // 聚合节点数,对应聚合节点
	private Integer timeLimit; // 时间限制,单位小时
	private String swimActivityId; // 泳道活动ID
	private String gobackType; // 回退类型,D:默认回退，R:直接回退，A:全部
	private String backActivityId; // 回退活动ID
	private String queryHistory; // 查询历史办理人,查询最近一次,Y
	private String hideAgent; // 隐藏经办人
	private String copyBtn; // 抄送
	private String signBtn; // 会签
	private String authorizeBtn; // 委托
	private String takebackBtn; // 收回
	private String stopBtn; // 终止
	private String emailRemind; // 邮件提醒
	private String smsRemind; // 短信提醒
	private String remindUserId; // 提醒用户ID
	private String remindUserName; // 提醒用户名称
	private String opinionCustomFlag; // 意见自定义标志,S:show显示、H:hide隐藏意见、C:
										// custom自定义意见
	private String opinionCustomDesc; // 自定义意见
	private Integer orderBy;
	private String remark;
	private String createId;
	private String createName;
	private Date createTime;

	// temp
	private String swimActivityName; // 泳道名称

	public WfDefineActivity() {
	}

	public WfDefineActivity(String processId, String createId, String createName, Date createTime) {
		super();
		this.processId = processId;
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
	}

	@Column(name = "PROCESS_ID", length = 32)
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	@Column(name = "ACTIVITY_TYPE", length = 10)
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

	@Column(name = "TIME_LIMIT")
	public Integer getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Column(name = "SWIM_ACTIVITY_ID", length = 32)
	public String getSwimActivityId() {
		return this.swimActivityId;
	}

	public void setSwimActivityId(String swimActivityId) {
		this.swimActivityId = swimActivityId;
	}

	@Column(name = "GOBACK_TYPE", length = 1)
	public String getGobackType() {
		return this.gobackType;
	}

	public void setGobackType(String gobackType) {
		this.gobackType = gobackType;
	}

	@Column(name = "BACK_ACTIVITY_ID", length = 500)
	public String getBackActivityId() {
		return this.backActivityId;
	}

	public void setBackActivityId(String backActivityId) {
		this.backActivityId = backActivityId;
	}

	@Column(name = "QUERY_HISTORY", length = 1)
	public String getQueryHistory() {
		return this.queryHistory;
	}

	public void setQueryHistory(String queryHistory) {
		this.queryHistory = queryHistory;
	}

	@Column(name = "HIDE_AGENT", length = 1)
	public String getHideAgent() {
		return this.hideAgent;
	}

	public void setHideAgent(String hideAgent) {
		this.hideAgent = hideAgent;
	}

	@Column(name = "COPY_BTN", length = 1)
	public String getCopyBtn() {
		return this.copyBtn;
	}

	public void setCopyBtn(String copyBtn) {
		this.copyBtn = copyBtn;
	}

	@Column(name = "SIGN_BTN", length = 1)
	public String getSignBtn() {
		return this.signBtn;
	}

	public void setSignBtn(String signBtn) {
		this.signBtn = signBtn;
	}

	@Column(name = "AUTHORIZE_BTN", length = 1)
	public String getAuthorizeBtn() {
		return this.authorizeBtn;
	}

	public void setAuthorizeBtn(String authorizeBtn) {
		this.authorizeBtn = authorizeBtn;
	}

	@Column(name = "TAKEBACK_BTN", length = 1)
	public String getTakebackBtn() {
		return this.takebackBtn;
	}

	public void setTakebackBtn(String takebackBtn) {
		this.takebackBtn = takebackBtn;
	}

	@Column(name = "STOP_BTN", length = 1)
	public String getStopBtn() {
		return stopBtn;
	}

	public void setStopBtn(String stopBtn) {
		this.stopBtn = stopBtn;
	}

	@Column(name = "EMAIL_REMIND", length = 1)
	public String getEmailRemind() {
		return this.emailRemind;
	}

	public void setEmailRemind(String emailRemind) {
		this.emailRemind = emailRemind;
	}

	@Column(name = "SMS_REMIND", length = 1)
	public String getSmsRemind() {
		return this.smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	@Column(name = "REMIND_USER_ID", length = 1000)
	public String getRemindUserId() {
		return this.remindUserId;
	}

	public void setRemindUserId(String remindUserId) {
		this.remindUserId = remindUserId;
	}

	@Column(name = "REMIND_USER_NAME", length = 500)
	public String getRemindUserName() {
		return this.remindUserName;
	}

	public void setRemindUserName(String remindUserName) {
		this.remindUserName = remindUserName;
	}

	@Column(name = "OPINION_CUSTOM_FLAG", length = 1)
	public String getOpinionCustomFlag() {
		return this.opinionCustomFlag;
	}

	public void setOpinionCustomFlag(String opinionCustomFlag) {
		this.opinionCustomFlag = opinionCustomFlag;
	}

	@Column(name = "OPINION_CUSTOM_DESC", length = 500)
	public String getOpinionCustomDesc() {
		return this.opinionCustomDesc;
	}

	public void setOpinionCustomDesc(String opinionCustomDesc) {
		this.opinionCustomDesc = opinionCustomDesc;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
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
	public String getSwimActivityName() {
		return swimActivityName;
	}

	public void setSwimActivityName(String swimActivityName) {
		this.swimActivityName = swimActivityName;
	}

}