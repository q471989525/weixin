package com.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 活动表单中间表
 * 
 */
@Entity
@Table(name = "wf_define_activity_form")
public class WfDefineActivityForm extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String activityId; // 活动ID
	private String formId; // 表单定义ID

	public WfDefineActivityForm() {
	}

	public WfDefineActivityForm(String activityId, String formId) {
		super();
		this.activityId = activityId;
		this.formId = formId;
	}

	@Column(name = "ACTIVITY_ID", length = 32)
	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Column(name = "FORM_ID", length = 32)
	public String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

}