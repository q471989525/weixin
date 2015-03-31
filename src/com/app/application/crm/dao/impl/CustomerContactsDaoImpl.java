package com.app.application.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.app.core.dao.impl.BaseDaoImpl;
import com.app.application.crm.dao.CustomerContactsDao;
import com.app.application.crm.entity.CustomerContacts;

/**
 * 
 * TODO：客户联系人
 * 
 * @author zhoufeng
 */
@Repository("customerContactsDao")
public class CustomerContactsDaoImpl extends BaseDaoImpl<CustomerContacts> implements CustomerContactsDao {

}
