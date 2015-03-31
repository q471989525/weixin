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
 * 会议室预定
 */
@Entity
@Table(name = "T_PORTAL_MEET_ORDER")
public class MeetOrder extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String meetId;
	private String meetSubject;
	private String meetContent;
	private Date startTime;
	private Date endTime;
	private String createId;
	private String creator;
	private Date createTime;
	private String compereId;
	private String compereName;
	private String recorderId;
	private String recorder;
	private String attachmentId;

	// temp
	private String startTimeFmt;
	private String endTimeFmt;
	private String createTimeFmt;
	private String meetName;
	private String stating;

	@Transient
	public String getStartTimeFmt() {
		startTimeFmt = null != startTime ? DateTimeUtil.formatDate(startTime, DateTimeUtil.yyyyMMddHHmm) : "";
		return startTimeFmt;
	}
	
	@Transient
	public String getEndTimeFmt() {
		endTimeFmt = null != endTime ? DateTimeUtil.formatDate(endTime, DateTimeUtil.yyyyMMddHHmm) : "";
		return endTimeFmt;
	}

	@Transient
	public String getCreateTimeFmt() {
		createTimeFmt = null != createTime ? DateTimeUtil.formatDate(createTime, DateTimeUtil.yyyyMMddHHmm) : "";
		return createTimeFmt;
	}
	
	@Transient
	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}
	
	@Transient
	public String getStating() {
		if(getStartTime().compareTo(new Date())<0 && getEndTime().compareTo(new Date()) > 0){
			stating = "ing";
		}else if(getEndTime().compareTo(new Date()) < 0){
			stating = "end";
		}
		return stating;
	}

	public void setStating(String stating) {
		this.stating = stating;
	}

	public MeetOrder() {
	}

	@Column(name = "MEET_ID", length = 32)
	public String getMeetId() {
		return this.meetId;
	}

	public void setMeetId(String meetId) {
		this.meetId = meetId;
	}

	@Column(name = "MEET_SUBJECT", length = 100)
	public String getMeetSubject() {
		return this.meetSubject;
	}

	public void setMeetSubject(String meetSubject) {
		this.meetSubject = meetSubject;
	}

	@Column(name = "MEET_CONTENT", length = 500)
	public String getMeetContent() {
		return this.meetContent;
	}

	public void setMeetContent(String meetContent) {
		this.meetContent = meetContent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	@Column(name = "COMPERE_ID", length = 32)
	public String getCompereId() {
		return compereId;
	}

	public void setCompereId(String compereId) {
		this.compereId = compereId;
	}

	@Column(name = "COMPERE_NAME", length = 45)
	public String getCompereName() {
		return compereName;
	}

	public void setCompereName(String compereName) {
		this.compereName = compereName;
	}

	@Column(name = "RECORDER_ID", length = 32)
	public String getRecorderId() {
		return recorderId;
	}

	public void setRecorderId(String recorderId) {
		this.recorderId = recorderId;
	}

	@Column(name = "RECORDER", length = 45)
	public String getRecorder() {
		return recorder;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

}