package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：省市区域
 * 
 */
@Entity
@Table(name = "SYS_GLOBAL_REGION")
public class SysGlobalRegion extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String parentId;
	private String regionName; // 名称
	private String regionType; // 0：国家,1:省,2：市
	private Integer orderBy;
	private String deleteState; // 默认 n:未删除

	public SysGlobalRegion() {
	}

	@Column(name = "PARENT_ID", length = 32)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "REGION_NAME", length = 50)
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Column(name = "REGION_TYPE", length = 10)
	public String getRegionType() {
		return this.regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

}