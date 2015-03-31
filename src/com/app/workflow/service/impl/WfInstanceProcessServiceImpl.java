package com.app.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfInstanceProcessDao;
import com.app.workflow.entity.WfInstanceProcess;
import com.app.workflow.service.WfInstanceProcessService;

/**
 * 流程实例接口
 */
@Transactional
@Service("wfInstanceProcessService")
public class WfInstanceProcessServiceImpl extends BaseServiceImpl<WfInstanceProcess> implements WfInstanceProcessService {

	WfInstanceProcessDao wfInstanceProcessDao;

	@Autowired
	public void setWfInstanceProcessDao(WfInstanceProcessDao wfInstanceProcessDao) {
		this.wfInstanceProcessDao = wfInstanceProcessDao;
		setBaseDao(wfInstanceProcessDao);
	}
}
