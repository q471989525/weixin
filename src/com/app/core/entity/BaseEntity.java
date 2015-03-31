package com.app.core.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 基础实体类，所有Entity都需要继承
 * 
 * @author ZF
 * @May 11, 2011
 */
@MappedSuperclass
public class BaseEntity {

	private String resourceid; // 主键，统一定义为resourceid

	@Id
	@Column(name = "RESOURCEID", unique = true, nullable = false, length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

}
