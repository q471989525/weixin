package com.app.application.crm.entity;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.math.NumberUtils;

import com.app.core.entity.BaseEntity;
import com.app.utils.DateTimeUtil;

/**
 * 
 * TODO：合同支付表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_CONTRACT_PAYMENT")
public class ContractPayment extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String contractId;
	private String periods;
	private Double amount;
	private Date payDate;
	private String remark;
	private String payFlag;
	private String createId;
	private String creator;
	private Date createTime;
	private Date updateTime;
	private String updateId;
	private String updateName;
	private Double actualAmount;
	private String deleteFlag;
	// temp
	private String contractNo;
	private String payDateFmt;
	private String updateTimeFmt;
	private String createTimeFmt;

	public ContractPayment() {
	}

	public ContractPayment(String periods, String amount, String payDate, String createId, String creator) throws ParseException {
		super();
		this.periods = periods;
		if (NumberUtils.isNumber(amount))
			this.amount = Double.valueOf(amount);
		this.payDate = DateTimeUtil.parseDate(payDate);
		this.createId = createId;
		this.creator = creator;
		this.createTime = new Date();
		this.payFlag = "n";
		this.deleteFlag = "n";
	}

	@Column(name = "CONTRACT_ID", length = 32)
	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "PERIODS", length = 50)
	public String getPeriods() {
		return this.periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	@Column(name = "AMOUNT", precision = 14, scale = 4)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PAY_DATE", length = 7)
	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "REMARK", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PAY_FLAG", length = 1)
	public String getPayFlag() {
		return this.payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_ID", length = 32)
	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	@Column(name = "UPDATE_NAME", length = 20)
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	@Column(name = "ACTUAL_AMOUNT", precision = 14, scale = 4)
	public Double getActualAmount() {
		return this.actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Transient
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Transient
	public String getPayDateFmt() {
		if (null != payDate)
			payDateFmt = DateTimeUtil.formatDate(payDate);
		return payDateFmt;
	}

	public void setPayDateFmt(String payDateFmt) {
		this.payDateFmt = payDateFmt;
	}

	@Transient
	public String getUpdateTimeFmt() {
		if (null != updateTime)
			updateTimeFmt = DateTimeUtil.formatDateTime(updateTime);
		return updateTimeFmt;
	}

	public void setUpdateTimeFmt(String updateTimeFmt) {
		this.updateTimeFmt = updateTimeFmt;
	}

	@Transient
	public String getCreateTimeFmt() {
		if (null != createTime)
			createTimeFmt = DateTimeUtil.formatDateTime(createTime);
		return createTimeFmt;
	}

	public void setCreateTimeFmt(String createTimeFmt) {
		this.createTimeFmt = createTimeFmt;
	}

}