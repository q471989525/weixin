package com.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 委托明细表
 * 
 */
@Entity
@Table(name = "wf_define_authorize_detail")
public class WfDefineAuthorizeDetail extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String authId; //委托表ID
	private String processId; //流程定义ID

	public WfDefineAuthorizeDetail() {
	}

	@Column(name = "AUTH_ID", length = 32)
	public String getAuthId() {
		return this.authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	@Column(name = "PROCESS_ID", length = 32)
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

}