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
 * TODO：竞争对手
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_COMPETITOR")
public class Competitor extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String competitorName;
	private String companyScale;
	private String companyProperty;
	private String companyDesc;
	private String superiority;
	private String disadvantages;
	private String createId;
	private String creator;
	private Date createTime;
	private String deleteFlag;
	// temp
	private String createTimeFmt;

	public Competitor() {
	}

	@Column(name = "COMPETITOR_NAME", length = 150)
	public String getCompetitorName() {
		return this.competitorName;
	}

	public void setCompetitorName(String competitorName) {
		this.competitorName = competitorName;
	}

	@Column(name = "COMPANY_SCALE", length = 200)
	public String getCompanyScale() {
		return this.companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	@Column(name = "COMPANY_PROPERTY", length = 200)
	public String getCompanyProperty() {
		return this.companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	@Column(name = "COMPANY_DESC", length = 2000)
	public String getCompanyDesc() {
		return this.companyDesc;
	}

	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}

	@Column(name = "SUPERIORITY", length = 2000)
	public String getSuperiority() {
		return this.superiority;
	}

	public void setSuperiority(String superiority) {
		this.superiority = superiority;
	}

	@Column(name = "DISADVANTAGES", length = 2000)
	public String getDisadvantages() {
		return this.disadvantages;
	}

	public void setDisadvantages(String disadvantages) {
		this.disadvantages = disadvantages;
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

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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