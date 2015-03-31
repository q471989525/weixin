package com.app.application.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.WorkReportDao;
import com.app.application.crm.entity.WorkReport;
import com.app.application.crm.service.WorkReportService;

/**
 * 
 * TODO：工作报告
 * 
 * @author zhoufeng
 */
@Transactional
@Service("workreportservice")
public class WorkReportServiceImpl extends BaseServiceImpl<WorkReport> implements WorkReportService {
	
	WorkReportDao workReportDao;
	
	@Resource(name = "workreportdao")
	public void setWorkReportDao(WorkReportDao workReportDao) {
		this.workReportDao = workReportDao;
		super.setBaseDao(workReportDao);
	}

}
