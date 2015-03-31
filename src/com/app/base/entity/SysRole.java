package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：系统角色
 * 
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String roleName; // 名称
	private String roleCode; // 编码
	private Integer orderBy;
	private String remark;
	private String deleteState; // 默认 n:未删除

	public SysRole() {
	}

	public SysRole(String roleName, String roleCode) {
		super();
		this.roleName = roleName;
		this.roleCode = roleCode;
	}

	@Column(name = "ROLE_NAME", length = 50)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_CODE", length = 50)
	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 600)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

}