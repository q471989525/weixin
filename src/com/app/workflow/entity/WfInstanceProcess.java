package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 流程实例
 */
@Entity
@Table(name = "wf_instance_process")
public class WfInstanceProcess extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer serialNumber; // 流水号
	private String serialTitle; // 流程标题
	private String status; // 流程状态;A:活动、F：正常完成、C：关闭:异常完成、S:停止
	private String formId; // 表单ID
	private String formUrl; // 表单ID
	private Integer priority; // 优先级
	private String processId; // 流程定义ID
	private String createId; // 创建人ID
	private String createName; // 创建人
	private Date createTime; // 创建时间
	private Date finishTime; // 结束时间

	public WfInstanceProcess() {
	}

	public WfInstanceProcess(Integer serialNumber, String serialTitle, String status, String formId, String formUrl, Integer priority, String processId, String createId, String createName, Date createTime) {
		super();
		this.serialNumber = serialNumber;
		this.serialTitle = serialTitle;
		this.status = status;
		this.formId = formId;
		this.formUrl = formUrl;
		this.priority = priority;
		this.processId = processId;
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
	}

	@Column(name = "SERIAL_NUMBER")
	public Integer getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "SERIAL_TITLE", length = 200)
	public String getSerialTitle() {
		return this.serialTitle;
	}

	public void setSerialTitle(String serialTitle) {
		this.serialTitle = serialTitle;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "FORM_ID", length = 32)
	public String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name = "FORM_URL", length = 100)
	public String getFormUrl() {
		return this.formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Column(name = "PROCESS_ID", length = 32)
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FINISH_TIME")
	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}