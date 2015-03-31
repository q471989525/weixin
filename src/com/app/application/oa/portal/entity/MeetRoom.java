package com.app.application.oa.portal.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.app.core.entity.BaseEntity;
import com.app.utils.DateTimeUtil;

/**
 * 会议室
 */
@Entity
@Table(name = "T_PORTAL_MEET_ROOM")
public class MeetRoom extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String meetName;
	private String meetCapacity;
	private String meetLocation;
	private String meetEquip;
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteFlag;
	private Integer orderBy;
	private String adminId;
	private String adminName;
	private String validFlag;

	// temp
	private String createTimeFmt;
	private List<MeetOrder> orders = new ArrayList<MeetOrder>();

	@Transient
	public String getCreateTimeFmt() {
		createTimeFmt = null != createTime ? DateTimeUtil.formatDateTime(createTime) : "";
		return createTimeFmt;
	}

	@Transient
	public List<MeetOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<MeetOrder> orders) {
		this.orders = orders;
	}

	public MeetRoom() {
	}

	@Column(name = "MEET_NAME", length = 50)
	public String getMeetName() {
		return this.meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}

	@Column(name = "MEET_CAPACITY", length = 100)
	public String getMeetCapacity() {
		return this.meetCapacity;
	}

	public void setMeetCapacity(String meetCapacity) {
		this.meetCapacity = meetCapacity;
	}

	@Column(name = "MEET_LOCATION", length = 100)
	public String getMeetLocation() {
		return this.meetLocation;
	}

	public void setMeetLocation(String meetLocation) {
		this.meetLocation = meetLocation;
	}

	@Column(name = "MEET_EQUIP", length = 200)
	public String getMeetEquip() {
		return this.meetEquip;
	}

	public void setMeetEquip(String meetEquip) {
		this.meetEquip = meetEquip;
	}

	@Column(name = "CREATE_ID", length = 32)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATOR", length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "ADMIN_ID", length = 32)
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Column(name = "ADMIN_NAME", length = 45)
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "VALID_FLAG", length = 1)
	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

}