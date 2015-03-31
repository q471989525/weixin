package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.RunRecordDao;
import com.app.application.crm.entity.RunRecord;
import com.app.application.crm.service.RunRecordService;

/**
 * 
 * TODO：运行记录
 * 
 * @author zhoufeng
 */
@Transactional
@Service("runRecordService")
public class RunRecordServiceImpl extends BaseServiceImpl<RunRecord> implements RunRecordService {

	RunRecordDao runRecordDao;

	@Resource(name = "runRecordDao")
	public void setRunRecordDao(RunRecordDao runRecordDao) {
		this.runRecordDao = runRecordDao;
		super.setBaseDao(runRecordDao);
	}

	/**
	 * 覆盖删除方法
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				RunRecord record = load(id);
				record.setDeleteFlag("y");
			}
		}
	}

}
