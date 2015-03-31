package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：用户数据权限表
 * 
 */
@Entity
@Table(name = "SYS_USER_DATA_LIMIT")
public class SysUserDataLimit extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String userId; // 用户ID
	private String userName; // 用户
	private String resourceId; // 资源ID
	private String resourceName; // 资源名称
	private String hqlCondition; // 条件
	private Integer orderBy;
	private String remark;
	private String deleteState; // 默认 n:未删除

	public SysUserDataLimit() {
	}

	@Column(name = "USER_ID", length = 32)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "RESOURCE_ID", length = 32)
	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "HQL_CONDITION", length = 1000)
	public String getHqlCondition() {
		return this.hqlCondition;
	}

	public void setHqlCondition(String hqlCondition) {
		this.hqlCondition = hqlCondition;
	}

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "RESOURCE_NAME", length = 200)
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}
}