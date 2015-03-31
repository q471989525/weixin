package com.app.application.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.CompetitorRelationDao;
import com.app.application.crm.entity.CompetitorRelation;
import com.app.application.crm.service.CompetitorRelationService;

/**
 * 
 * TODO：竞争对手关系表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("competitorRelationService")
public class CompetitorRelationServiceImpl extends BaseServiceImpl<CompetitorRelation> implements CompetitorRelationService {

	CompetitorRelationDao competitorRelationDao;

	@Resource(name = "competitorRelationDao")
	public void setCompetitorRelationDao(CompetitorRelationDao competitorRelationDao) {
		this.competitorRelationDao = competitorRelationDao;
		super.setBaseDao(competitorRelationDao);
	}

}
