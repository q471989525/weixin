package com.app.utils;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * TODO：ztree生成树json对象类
 * 
 * @author zhoufeng
 */
public class ZtreeObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; // id
	private String pId; // 父id
	private String name; // 名称
	private String value; // 值
	private Boolean open; // 打开状态 open=true打开
	private Boolean checked; // 选中状态checked:true选中
	private Boolean isParent; // 父节点isParent:true
	private String icon; // 节点图片
	private String nodeType; // 节点类型

	public ZtreeObject() {
	}

	public ZtreeObject(String id, String pId, String name) {
		super();
		this.id = id;
		if (StringUtils.isNotEmpty(pId)) {
			this.pId = pId;
		} else {
			this.pId = "0";
		}
		this.name = name;
	}

	public ZtreeObject(String id, String pId, String name, String value) {
		super();
		this.id = id;
		if (StringUtils.isNotEmpty(pId)) {
			this.pId = pId;
		} else {
			this.pId = "0";
		}
		this.name = name;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
