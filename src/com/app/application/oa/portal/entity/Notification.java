package com.app.application.oa.portal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.app.core.entity.BaseEntity;
import com.app.utils.DateTimeUtil;

/**
 * 
 * TODO：通知表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_PORTAL_NOTIFICATION")
public class Notification extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5945048452353933670L;

	private String createId;
	private String creator;
	private Date createTime;
	private String createDept;
	private String notifyType;
	private String notifyTitle;
	private Date releaseTime;
	private String releaseFlag;
	private Integer viewCount;
	private String topFlag;
	private Date topTime;
	private String attachmentId;

	// temp
	private String notifyContent;
	private String releaseTimeFmt;
	private String createTimeFmt;
	private String topTimeFmt;
	private String viewFlag;

	public Notification() {
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
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATE_DEPT", length = 100)
	public String getCreateDept() {
		return this.createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	@Column(name = "NOTIFY_TYPE", length = 50)
	public String getNotifyType() {
		return this.notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	@Column(name = "NOTIFY_TITLE", length = 200)
	public String getNotifyTitle() {
		return this.notifyTitle;
	}

	public void setNotifyTitle(String notifyTitle) {
		this.notifyTitle = notifyTitle;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RELEASE_TIME")
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Column(name = "RELEASE_FLAG", length = 1)
	public String getReleaseFlag() {
		return this.releaseFlag;
	}

	public void setReleaseFlag(String releaseFlag) {
		this.releaseFlag = releaseFlag;
	}

	@Column(name = "VIEW_COUNT")
	public Integer getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Column(name = "TOP_FLAG", length = 1)
	public String getTopFlag() {
		return this.topFlag;
	}

	public void setTopFlag(String topFlag) {
		this.topFlag = topFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TOP_TIME")
	public Date getTopTime() {
		return this.topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Transient
	public String getReleaseTimeFmt() {
		if (releaseTime != null)
			releaseTimeFmt = DateTimeUtil.formatDateTime(releaseTime);
		return releaseTimeFmt;
	}

	public void setReleaseTimeFmt(String releaseTimeFmt) {
		this.releaseTimeFmt = releaseTimeFmt;
	}

	@Transient
	public String getCreateTimeFmt() {
		if (createTime != null)
			createTimeFmt = DateTimeUtil.formatDateTime(createTime);
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}

	@Transient
	public String getTopTimeFmt() {
		if (topTime != null)
			topTimeFmt = DateTimeUtil.formatDateTime(topTime);
		return topTimeFmt;
	}

	public void setTopTimeFmt(String topTimeFmt) {
		this.topTimeFmt = topTimeFmt;
	}

	@Transient
	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	@Transient
	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

}