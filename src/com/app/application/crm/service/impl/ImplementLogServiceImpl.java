package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ImplementLogDao;
import com.app.application.crm.entity.ImplementLog;
import com.app.application.crm.service.ImplementLogService;

@Transactional
@Service("implementLogService")
public class ImplementLogServiceImpl extends BaseServiceImpl<ImplementLog> implements ImplementLogService {

	ImplementLogDao implementLogDao;

	@Resource(name = "implementLogDao")
	public void setImplementLogDao(ImplementLogDao implementLogDao) {
		this.implementLogDao = implementLogDao;
		setBaseDao(implementLogDao);
	}
	
	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			ImplementLog log = load(id);
			log.setDeleteFlag("y");
		}
	}

}
