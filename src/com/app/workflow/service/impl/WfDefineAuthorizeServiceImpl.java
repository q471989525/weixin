package com.app.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfDefineAuthorizeDao;
import com.app.workflow.entity.WfDefineAuthorize;
import com.app.workflow.service.WfDefineAuthorizeService;

/**
 * 委托定义接口
 */
@Transactional
@Service("wfDefineAuthorizeService")
public class WfDefineAuthorizeServiceImpl extends BaseServiceImpl<WfDefineAuthorize> implements WfDefineAuthorizeService {

	WfDefineAuthorizeDao wfDefineAuthorizeDao;

	@Autowired
	public void setWfDefineAuthorizeDao(WfDefineAuthorizeDao wfDefineAuthorizeDao) {
		this.wfDefineAuthorizeDao = wfDefineAuthorizeDao;
		setBaseDao(wfDefineAuthorizeDao);
	}

}
