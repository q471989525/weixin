package com.app.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.core.entity.BaseEntity;

/**
 * 流程定义表
 */
@Entity
@Table(name = "wf_define_process")
public class WfDefineProcess extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String menuId; // 菜单ID
	private String menuName; // 菜单名称
	private String isValid; // 是否可用,y:可用，n:禁用
	private Integer isPriority; // 优先级,按 1,2,3排序
	private Integer orderBy; // 排序
	private String remark; // 备注
	private String deleteState;
	private String createId;
	private String createName;
	private Date createTime;

	public WfDefineProcess() {
	}

	public WfDefineProcess(String createId, String createName, Date createTime) {
		super();
		this.createId = createId;
		this.createName = createName;
		this.createTime = createTime;
		this.deleteState = "n";
	}

	@Column(name = "MENU_ID", length = 32)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	@Column(name = "MENU_NAME", length = 50)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Column(name = "IS_PRIORITY")
	public Integer getIsPriority() {
		return this.isPriority;
	}

	public void setIsPriority(Integer isPriority) {
		this.isPriority = isPriority;
	}

	@Column(name = "ORDER_BY")
	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	@Column(name = "REMARK", length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DELETE_STATE", length = 1)
	public String getDeleteState() {
		return this.deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

	@Column(name = "CREATE_ID", length = 32)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATE_NAME", length = 50)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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