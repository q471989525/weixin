package com.app.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：组织机构
 * 
 */
@Entity
@Table(name = "SYS_ORGANIZATION")
public class SysOrganization extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String orgName; // 组织名称
	private String orgCode; // 编码
	private String shortName; // 简称
	private String orgType; // 组织类型，数据字典
	private String contactPeople; // 联系人
	private String contactPhone; // 联系电话
	private String email; // 邮箱
	private String contactAddress; // 联系地址
	private String dutyDesc; // 职责
	private String parentId;
	private String parentName;
	private String parentIds;
	private String parentNames;
	private String stateFlag; // 状态 y:有效，n:无效，h:隐藏
	private Integer orderBy;
	private String remark;
	private String deleteState; // 默认 n:未删除

	// temp
	private String userMobile;
	private String userEmail;
	private String userSex;
	private String userPost;
	private String userLogin;
	private String iconCls;

	private String state;// treegrid 文件夹状态

	@Transient
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Transient
	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	@Transient
	public String getUserPost() {
		return userPost;
	}

	public void setUserPost(String userPost) {
		this.userPost = userPost;
	}

	@Transient
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@Transient
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Transient
	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public SysOrganization() {
	}

	@Column(name = "ORG_NAME", length = 100)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "SHORT_NAME", length = 50)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "ORG_TYPE", length = 50)
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "CONTACT_PEOPLE", length = 50)
	public String getContactPeople() {
		return this.contactPeople;
	}

	public void setContactPeople(String contactPeople) {
		this.contactPeople = contactPeople;
	}

	@Column(name = "CONTACT_PHONE", length = 100)
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CONTACT_ADDRESS", length = 250)
	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Column(name = "DUTY_DESC", length = 650)
	public String getDutyDesc() {
		return this.dutyDesc;
	}

	public void setDutyDesc(String dutyDesc) {
		this.dutyDesc = dutyDesc;
	}

	@Column(name = "PARENT_ID", length = 32)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "PARENT_NAME", length = 100)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "PARENT_IDS", length = 600)
	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Column(name = "PARENT_NAMES", length = 600)
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
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}