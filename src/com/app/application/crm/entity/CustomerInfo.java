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
 * TODO：客户信息
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_CUSTOMER_INFO")
public class CustomerInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 7585142160196530357L;

	private String customerName;
	private String provinceName;
	private String cityName;
	private String customerNature;
	private String customerSource;
	private String customerType;
	private String customerIndustry;
	private String trustStatus;
	private String contactStrategy;
	private String industryStatus;
	private String clearingForm;
	private String telphone;
	private String fax;
	private String email;
	private String www;
	private String address;
	private String postCode;
	private String deleteFlag;
	private String createId;
	private String creator;
	private Date createTime;
	private String remark;

	public CustomerInfo() {
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "PROVINCE_NAME", length = 50)
	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Column(name = "CITY_NAME", length = 50)
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Column(name = "CUSTOMER_NATURE", length = 50)
	public String getCustomerNature() {
		return this.customerNature;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}

	@Column(name = "CUSTOMER_SOURCE", length = 50)
	public String getCustomerSource() {
		return this.customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	@Column(name = "CUSTOMER_TYPE", length = 50)
	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Column(name = "CUSTOMER_INDUSTRY", length = 50)
	public String getCustomerIndustry() {
		return this.customerIndustry;
	}

	public void setCustomerIndustry(String customerIndustry) {
		this.customerIndustry = customerIndustry;
	}

	@Column(name = "TRUST_STATUS", length = 50)
	public String getTrustStatus() {
		return this.trustStatus;
	}

	public void setTrustStatus(String trustStatus) {
		this.trustStatus = trustStatus;
	}

	@Column(name = "CONTACT_STRATEGY", length = 50)
	public String getContactStrategy() {
		return this.contactStrategy;
	}

	public void setContactStrategy(String contactStrategy) {
		this.contactStrategy = contactStrategy;
	}

	@Column(name = "INDUSTRY_STATUS", length = 50)
	public String getIndustryStatus() {
		return this.industryStatus;
	}

	public void setIndustryStatus(String industryStatus) {
		this.industryStatus = industryStatus;
	}

	@Column(name = "CLEARING_FORM", length = 50)
	public String getClearingForm() {
		return this.clearingForm;
	}

	public void setClearingForm(String clearingForm) {
		this.clearingForm = clearingForm;
	}

	@Column(name = "TELPHONE", length = 50)
	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "FAX", length = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "WWW", length = 50)
	public String getWww() {
		return this.www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POST_CODE", length = 50)
	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	@Column(name = "REMARK", length = 1500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}