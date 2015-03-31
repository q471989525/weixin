package com.app.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 公共附件
 */
@Entity
@Table(name = "SYS_PUB_ATTACHMENT")
public class PubAttachment extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5831572100247788159L;

	private String fkUuid; // 主錶UUID
	private String fileName; // 文件名
	private String fileUrl; // 存儲文件路徑
	private String fileSize; // 文件大小，單位:byte
	private String fileType; // 文件類型
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteState; // 默认 n:未删除

	public PubAttachment() {
	}

	public PubAttachment(String fileName, String fileUrl, String fileSize, String createId, String creator, Date createTime) {
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.createId = createId;
		this.creator = creator;
		this.createTime = createTime;
		this.deleteState = "n";
	}

	public PubAttachment(String fkUuid, String fileName, String fileUrl, String fileSize, String createId, String creator, Date createTime) {
		this.fkUuid = fkUuid;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileSize = fileSize;
		this.createId = createId;
		this.creator = creator;
		this.createTime = createTime;
		this.deleteState = "n";
	}

	@Column(name = "FK_UUID", length = 36)
	public String getFkUuid() {
		return this.fkUuid;
	}

	public void setFkUuid(String fkUuid) {
		this.fkUuid = fkUuid;
	}

	@Column(name = "FILE_NAME", length = 100)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_URL", length = 100)
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Column(name = "FILE_SIZE", length = 20)
	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "FILE_TYPE", length = 20)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

}