package com.app.application.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ContactsRelationDao;
import com.app.application.crm.entity.ContactsRelation;
import com.app.application.crm.service.ContactsRelationService;

/**
 * 
 * TODO：联系人中间表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("contactsRelationService")
public class ContactsRelationServiceImpl extends BaseServiceImpl<ContactsRelation> implements ContactsRelationService {

	ContactsRelationDao contactsRelationDao;

	@Resource(name = "contactsRelationDao")
	public void setContactsRelationDao(ContactsRelationDao contactsRelationDao) {
		this.contactsRelationDao = contactsRelationDao;
		setBaseDao(contactsRelationDao);
	}

}
