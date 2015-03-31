package com.app.application.crm.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.BusinessOpportunityDao;
import com.app.application.crm.entity.BusinessOpportunity;
import com.app.application.crm.service.BusinessOpportunityService;

@Transactional
@Service("businessOpportunityService")
public class BusinessOpportunityServiceImpl extends BaseServiceImpl<BusinessOpportunity> implements BusinessOpportunityService {

	BusinessOpportunityDao businessOpportunityDao;

	@Autowired
	public void setBusinessOpportunityDao(BusinessOpportunityDao businessOpportunityDao) {
		this.businessOpportunityDao = businessOpportunityDao;
		setBaseDao(businessOpportunityDao);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			BusinessOpportunity bOpportunity = load(id);
			bOpportunity.setDeleteFlag("y");
		}
	}

}
