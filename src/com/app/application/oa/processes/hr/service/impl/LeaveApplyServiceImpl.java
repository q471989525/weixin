package com.app.application.oa.processes.hr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.oa.processes.hr.dao.LeaveApplyDao;
import com.app.application.oa.processes.hr.entity.LeaveApply;
import com.app.application.oa.processes.hr.service.LeaveApplyService;

/**
 * 请假流程
 */
@Service
@Transactional
public class LeaveApplyServiceImpl extends BaseServiceImpl<LeaveApply> implements LeaveApplyService {

	LeaveApplyDao leaveApplyDao;

	@Autowired
	public void setLeaveApplyDao(LeaveApplyDao leaveApplyDao) {
		this.leaveApplyDao = leaveApplyDao;
		setBaseDao(leaveApplyDao);
	}

}
