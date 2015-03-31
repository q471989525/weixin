package com.app.application.crm.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.application.crm.entity.CustomerContacts;

/**
 * 
 * TODO：客户联系人
 * 
 * @author zhoufeng
 */
public interface CustomerContactsService extends BaseService<CustomerContacts> {

	/**
	 * 通过中间关系表查找联系人
	 * 
	 * @param relationid
	 * @return
	 */
	List<CustomerContacts> findListByRelation(String relationid);

}
