package com.app.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfDefineActivityCandidateDao;
import com.app.workflow.entity.WfDefineActivityCandidate;
import com.app.workflow.service.WfDefineActivityCandidateService;

/**
 * 活动定义候选人接口
 */
@Transactional
@Service("wfDefineActivityCandidateService")
public class WfDefineActivityCandidateServiceImpl extends BaseServiceImpl<WfDefineActivityCandidate> implements WfDefineActivityCandidateService {

	WfDefineActivityCandidateDao wfDefineActivityCandidateDao;

	@Autowired
	public void setWfDefineActivityCandidateDao(WfDefineActivityCandidateDao wfDefineActivityCandidateDao) {
		this.wfDefineActivityCandidateDao = wfDefineActivityCandidateDao;
		setBaseDao(wfDefineActivityCandidateDao);
	}

}
