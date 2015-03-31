package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.SchemeDao;
import com.app.application.crm.entity.Scheme;
import com.app.application.crm.service.SchemeService;

/**
 * 
 * TODO：方案
 * 
 * @author zhoufeng
 */
@Transactional
@Service("schemeService")
public class SchemeServiceImpl extends BaseServiceImpl<Scheme> implements SchemeService {

	SchemeDao schemeDao;

	@Resource(name = "schemeDao")
	public void setSchemeDao(SchemeDao schemeDao) {
		this.schemeDao = schemeDao;
		setBaseDao(schemeDao);
	}

	/**
	 * 覆盖删除方法
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				Scheme scheme = load(id);
				scheme.setDeleteFlag("y");
			}
		}
	}

}
