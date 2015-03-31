package com.app.workflow.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;
import com.app.workflow.dao.WfDefineProcessDao;
import com.app.workflow.entity.WfDefineProcess;
import com.app.workflow.service.WfDefineProcessService;

/**
 * 流程定义接口
 */
@Transactional
@Service("wfDefineProcessService")
public class WfDefineProcessServiceImpl extends BaseServiceImpl<WfDefineProcess> implements WfDefineProcessService {

	WfDefineProcessDao wfDefineProcessDao;

	@Autowired
	public void setWfDefineProcessDao(WfDefineProcessDao wfDefineProcessDao) {
		this.wfDefineProcessDao = wfDefineProcessDao;
		setBaseDao(wfDefineProcessDao);
	}
	
	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Override
	public void deleteById(Serializable id) {
		deleteStateByIds(new Object[] { id });
	}

	/**
	 * 通过菜单ID，查找流程定义
	 */
	@Override
	public WfDefineProcess findProcessByMenuId(String menuId) {
		QueryParameter param = new QueryParameter();
		param.addEquals("menuId", menuId);
		List<WfDefineProcess> list = findList(param);
		if (CollectionUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}

	/**
	 * 查询流程实例流水号，默认从：1000开始
	 */
	@Override
	public synchronized Integer selectSerialNumber() {
		String sql = "SELECT max(p.SERIAL_NUMBER) FROM wf_instance_process p";
		int serialNum = jdbcTemplate.queryForInt(sql);
		if (serialNum == 0) serialNum = 1000;
		return serialNum;
	}

}
