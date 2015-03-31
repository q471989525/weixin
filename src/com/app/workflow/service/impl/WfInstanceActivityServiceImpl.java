package com.app.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfInstanceActivityDao;
import com.app.workflow.entity.WfInstanceActivity;
import com.app.workflow.service.WfInstanceActivityService;

/**
 * 活动实例接口
 */
@Transactional
@Service("wfInstanceActivityService")
public class WfInstanceActivityServiceImpl extends BaseServiceImpl<WfInstanceActivity> implements WfInstanceActivityService {

	WfInstanceActivityDao wfInstanceActivityDao;

	@Autowired
	public void setWfInstanceActivityDao(WfInstanceActivityDao wfInstanceActivityDao) {
		this.wfInstanceActivityDao = wfInstanceActivityDao;
		setBaseDao(wfInstanceActivityDao);
	}

}
