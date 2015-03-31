package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：系统资源
 * 
 */
@Entity
@Table(name = "SYS_RESOURCE")
public class SysResource extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String menuName; // 名称
	private String menuCode; // 编码
	private String menuUrl; // url地址
	private String menuType; // 1:菜单,2:按钮
	private String parentId;
	private String parentName;
	private String parentIds;
	private String parentNames;
	private String stateFlag; // 1:有效,2:无效,3:隐藏
	private Integer orderBy;
	private String remark;
	private String deleteState; // 默认 n:未删除

	// temp
	private String subDeleteIds;

	public SysResource() {
	}

	/** 按钮 **/
	public SysResource(String resourceid, String menuName, String menuCode, String menuUrl, String orderBy, String remark) {
		if (StringUtils.isNotEmpty(resourceid))
			super.setResourceid(resourceid);
		this.menuName = menuName;
		this.menuCode = menuCode;
		this.menuUrl = menuUrl;
		this.menuType = "2";
		this.stateFlag = "1";
		if (NumberUtils.isNumber(orderBy))
			this.orderBy = Integer.valueOf(orderBy);
		this.remark = remark;
		this.deleteState = "n";
	}

	@Column(name = "MENU_NAME", length = 50)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MENU_CODE", length = 50)
	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Column(name = "MENU_URL", length = 200)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "MENU_TYPE", length = 1)
	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	@Column(name = "PARENT_ID", length = 32)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "PARENT_NAME", length = 50)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "PARENT_IDS", length = 200)
	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name = "PARENT_NAMES", length = 200)
	public String getParentNames() {
		return this.parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	@Column(name = "STATE_FLAG", length = 1)
	public String getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
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

	@Transient
	public String getSubDeleteIds() {
		return subDeleteIds;
	}

	public void setSubDeleteIds(String subDeleteIds) {
		this.subDeleteIds = subDeleteIds;
	}

}