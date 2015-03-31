package com.app.application.crm.entity;

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
 * TODO：合同表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_CONTRACT")
public class Contract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String contractNo;
	private String contractName;
	private String cooperator;
	private Double contractAmount;
	private String contractAmountUpper;
	private Date contractDate;
	private Date contractStart;
	private Date contractEnd;
	private Short contractCopis;
	private String contractRemark;
	private String contractContent;
	private String attachmentId;
	private String projectId;
	private String deleteFlag;
	private String marginFlag;
	private String marginRatio;
	private Double marginAmount;
	private Date marginReceive;
	private String createId;
	private String creator;
	private Date createTime;
	private Date updateTime;

	// temp
	private String customerName;
	private String projectName;
	private String contractDateFmt;
	private String valideFmt;
	private String marginReceiveFmt;

	@Transient
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Transient
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Transient
	public String getContractDateFmt() {
		if (null != contractDate)
			contractDateFmt = DateTimeUtil.formatDate(contractDate);
		return contractDateFmt;
	}

	public void setContractDateFmt(String contractDateFmt) {
		this.contractDateFmt = contractDateFmt;
	}

	@Transient
	public String getValideFmt() {
		if (null != contractStart && null != contractEnd) {
			valideFmt = DateTimeUtil.formatDate(contractStart) + " 至 " + DateTimeUtil.formatDate(contractEnd);
		}
		return valideFmt;
	}

	public void setValideFmt(String valideFmt) {
		this.valideFmt = valideFmt;
	}

	@Transient
	public String getMarginReceiveFmt() {
		if (null != marginReceive) {
			marginReceiveFmt = DateTimeUtil.formatDate(marginReceive);
		}
		return marginReceiveFmt;
	}

	public void setMarginReceiveFmt(String marginReceiveFmt) {
		this.marginReceiveFmt = marginReceiveFmt;
	}

	public Contract() {
	}

	@Column(name = "CONTRACT_NO", length = 50)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "CONTRACT_NAME", length = 200)
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column(name = "COOPERATOR", length = 200)
	public String getCooperator() {
		return this.cooperator;
	}

	public void setCooperator(String cooperator) {
		this.cooperator = cooperator;
	}

	@Column(name = "CONTRACT_AMOUNT", precision = 14, scale = 4)
	public Double getContractAmount() {
		return this.contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	@Column(name = "CONTRACT_AMOUNT_UPPER", length = 50)
	public String getContractAmountUpper() {
		return this.contractAmountUpper;
	}

	public void setContractAmountUpper(String contractAmountUpper) {
		this.contractAmountUpper = contractAmountUpper;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_DATE", length = 7)
	public Date getContractDate() {
		return this.contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_START", length = 7)
	public Date getContractStart() {
		return this.contractStart;
	}

	public void setContractStart(Date contractStart) {
		this.contractStart = contractStart;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_END", length = 7)
	public Date getContractEnd() {
		return this.contractEnd;
	}

	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}

	@Column(name = "CONTRACT_COPIS", precision = 4, scale = 0)
	public Short getContractCopis() {
		return this.contractCopis;
	}

	public void setContractCopis(Short contractCopis) {
		this.contractCopis = contractCopis;
	}

	@Column(name = "CONTRACT_REMARK", length = 1550)
	public String getContractRemark() {
		return this.contractRemark;
	}

	public void setContractRemark(String contractRemark) {
		this.contractRemark = contractRemark;
	}

	@Column(name = "CONTRACT_CONTENT")
	public String getContractContent() {
		return this.contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	@Column(name = "ATTACHMENT_ID", length = 36)
	public String getAttachmentId() {
		return this.attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Column(name = "PROJECT_ID", length = 32)
	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "MARGIN_FLAG", length = 1)
	public String getMarginFlag() {
		return this.marginFlag;
	}

	public void setMarginFlag(String marginFlag) {
		this.marginFlag = marginFlag;
	}

	@Column(name = "MARGIN_RATIO", length = 50)
	public String getMarginRatio() {
		return this.marginRatio;
	}

	public void setMarginRatio(String marginRatio) {
		this.marginRatio = marginRatio;
	}

	@Column(name = "MARGIN_AMOUNT", precision = 14, scale = 4)
	public Double getMarginAmount() {
		return this.marginAmount;
	}

	public void setMarginAmount(Double marginAmount) {
		this.marginAmount = marginAmount;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "MARGIN_RECEIVE")
	public Date getMarginReceive() {
		return marginReceive;
	}

	public void setMarginReceive(Date marginReceive) {
		this.marginReceive = marginReceive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}