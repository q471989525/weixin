package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：组织用户中间表
 * 
 */
@Entity
@Table(name = "SYS_ORG_USER")
public class SysOrgUser extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String orgId;
	private String userId;
	private String orgUserType; // 组织用户类型, r:默认直接关系，q:引用关系，主要对应一个用户可以属于多个组织作区分。

	public SysOrgUser() {
	}

	public SysOrgUser(String orgId, String userId) {
		super();
		this.orgId = orgId;
		this.userId = userId;
		this.orgUserType = "r";
	}

	@Column(name = "ORG_ID", length = 32)
	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name = "USER_ID", length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "ORG_USER_TYPE", length = 1)
	public String getOrgUserType() {
		return orgUserType;
	}

	public void setOrgUserType(String orgUserType) {
		this.orgUserType = orgUserType;
	}

}