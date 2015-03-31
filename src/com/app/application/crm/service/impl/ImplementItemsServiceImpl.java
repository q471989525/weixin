package com.app.application.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ImplementItemsDao;
import com.app.application.crm.entity.ImplementItems;
import com.app.application.crm.service.ImplementItemsService;

/**
 * 
 * TODO：实施任务
 * 
 * @author zhoufeng
 */
@Transactional
@Service("implementItemsService")
public class ImplementItemsServiceImpl extends BaseServiceImpl<ImplementItems> implements ImplementItemsService {

	ImplementItemsDao implementItemsDao;

	@Resource(name = "implementItemsDao")
	public void setImplementItemsDao(ImplementItemsDao implementItemsDao) {
		this.implementItemsDao = implementItemsDao;
		setBaseDao(implementItemsDao);
	}

}
