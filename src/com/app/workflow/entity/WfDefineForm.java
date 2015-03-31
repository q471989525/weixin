package com.app.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 表單元素表
 * 
 */
@Entity
@Table(name = "wf_define_form")
public class WfDefineForm extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String processId; // 流程定义ID
	private String elementType; // 元素类型,hidden,text,button,select,textarea,checkbox,radio,password,a
	private String elementId; // 元素ID
	private String elementName; // 元素名称
	private String isSub; // 子表元素，Y:是 / N:否
	private Integer orderBy;
	private String remark;

	public WfDefineForm() {
	}

	public WfDefineForm(String processId) {
		super();
		this.processId = processId;
	}

	@Column(name = "PROCESS_ID", length = 32)
	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name = "ELEMENT_TYPE", length = 20)
	public String getElementType() {
		return this.elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	@Column(name = "ELEMENT_ID", length = 50)
	public String getElementId() {
		return this.elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	@Column(name = "ELEMENT_NAME", length = 50)
	public String getElementName() {
		return this.elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@Column(name = "IS_SUB", length = 1)
	public String getIsSub() {
		return this.isSub;
	}

	public void setIsSub(String isSub) {
		this.isSub = isSub;
	}
	
	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}
	
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 450)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}