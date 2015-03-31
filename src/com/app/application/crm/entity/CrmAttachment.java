package com.app.application.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：合同附件表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_ATTACHMENT")
public class CrmAttachment extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String fkUuid;
	private String fileName;
	private String fileUrl;
	private String fileSize;
	private String fileType;
	private String createId;
	private String creator;
	private Date createTime;

	public CrmAttachment() {
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

}