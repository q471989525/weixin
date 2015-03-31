package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：数据字典
 * 
 */
@Entity
@Table(name = "SYS_DICTIONARY")
public class SysDictionary extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String parentId = "0";// 默认值
	private String dictName; // 名称
	private String dictCode; // 编码
	private String itemName; // 子项名称
	private String itemValue; // 子项值
	private String isDefault; // 是否默认
	private String isValid; // 是否有效
	private Integer orderBy;
	private String remark;
	private String deleteState; // 默认 n:未删除

	// temp
	private String deleteIds;

	public SysDictionary() {
	}

	public SysDictionary(String resourceid, String itemName, String itemValue, String isDefault, String isValid, Integer orderBy, String remark) {
		if (StringUtils.isNotEmpty(resourceid))
			super.setResourceid(resourceid);
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.isDefault = isDefault;
		this.isValid = isValid;
		this.orderBy = orderBy;
		this.remark = remark;
		this.deleteState = "n";
	}

	@Column(name = "PARENT_ID", length = 32)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "DICT_NAME", length = 100)
	public String getDictName() {
		return this.dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@Column(name = "DICT_CODE", length = 100)
	public String getDictCode() {
		return this.dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	@Column(name = "ITEM_NAME", length = 100)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "ITEM_VALUE", length = 100)
	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	@Column(name = "IS_DEFAULT", length = 1)
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 250)
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
	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

}