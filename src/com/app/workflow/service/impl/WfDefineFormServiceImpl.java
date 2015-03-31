package com.app.workflow.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.workflow.dao.WfDefineActivityFormDao;
import com.app.workflow.dao.WfDefineFormDao;
import com.app.workflow.entity.WfDefineActivityForm;
import com.app.workflow.entity.WfDefineForm;
import com.app.workflow.service.WfDefineFormService;

/**
 * 表單元素表接口
 */
@Transactional
@Service("wfDefineFormService")
public class WfDefineFormServiceImpl extends BaseServiceImpl<WfDefineForm> implements WfDefineFormService {

	WfDefineFormDao wfDefineFormDao;

	@Autowired
	public void setWfDefineFormDao(WfDefineFormDao wfDefineFormDao) {
		this.wfDefineFormDao = wfDefineFormDao;
		setBaseDao(wfDefineFormDao);
	}

	@Autowired
	WfDefineActivityFormDao wfDefineActivityFormDao;

	@Override
	public void deleteByIds(Serializable[] ids) {
		// 删除表单与活动中间表
		for (Serializable id : ids) {
			List<WfDefineActivityForm> fromList = wfDefineActivityFormDao._findListByHql("from WfDefineActivityForm where formId=?", id);
			if (CollectionUtils.isNotEmpty(fromList))
				wfDefineActivityFormDao._deleteCollection(fromList);
		}
		super.deleteByIds(ids);
	}
}
