package com.app.application.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：实施产品
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_IMPLEMENT_PRODUCT")
public class ImplementProduct extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String implementId;
	private String productName;
	private String productModel;
	private String productUnit;
	private String productNumber;
	private String productRemark;
	private String deleteFlag;

	public ImplementProduct() {
	}

	public ImplementProduct(String productName, String productModel, String productUnit, String productNumber, String productRemark) {
		super();
		this.productName = productName;
		this.productModel = productModel;
		this.productUnit = productUnit;
		this.productNumber = productNumber;
		this.productRemark = productRemark;
		this.deleteFlag = "n";
	}

	@Column(name = "IMPLEMENT_ID", length = 32)
	public String getImplementId() {
		return this.implementId;
	}

	public void setImplementId(String implementId) {
		this.implementId = implementId;
	}

	@Column(name = "PRODUCT_NAME", length = 100)
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "PRODUCT_MODEL", length = 100)
	public String getProductModel() {
		return this.productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	@Column(name = "PRODUCT_UNIT", length = 50)
	public String getProductUnit() {
		return this.productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	@Column(name = "PRODUCT_NUMBER", length = 50)
	public String getProductNumber() {
		return this.productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	@Column(name = "PRODUCT_REMARK", length = 500)
	public String getProductRemark() {
		return this.productRemark;
	}

	public void setProductRemark(String productRemark) {
		this.productRemark = productRemark;
	}

	@Column(name = "DELETE_FLAG", length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}