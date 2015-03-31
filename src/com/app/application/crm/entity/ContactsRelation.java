package com.app.application.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.app.core.entity.BaseEntity;

/**
 * 
 * TODO：联系人中间表
 * 
 * @author zhoufeng
 */
@Entity
@Table(name = "T_CRM_CONTACTS_RELATION")
public class ContactsRelation extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String contactsId;
	private String relationId;

	public ContactsRelation() {
	}

	public ContactsRelation(String contactsId) {
		super();
		this.contactsId = contactsId;
	}

	public ContactsRelation(String contactsId, String relationId) {
		super();
		this.contactsId = contactsId;
		this.relationId = relationId;
	}

	@Column(name = "CONTACTS_ID", length = 32)
	public String getContactsId() {
		return this.contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	@Column(name = "RELATION_ID", length = 32)
	public String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

}