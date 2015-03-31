package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：角色资源中间表
 * 
 */
@Entity
@Table(name = "SYS_ROLE_RESOURCE")
public class SysRoleResource extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String roleId;
	private String resourceId;

	public SysRoleResource() {
	}

	public SysRoleResource(String roleId, String resourceId) {
		super();
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	@Column(name = "ROLE_ID", length = 32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RESOURCE_ID", length = 32)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

}