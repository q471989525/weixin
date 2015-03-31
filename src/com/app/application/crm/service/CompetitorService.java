package com.app.application.crm.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.application.crm.entity.Competitor;

/**
 * 
 * TODO：竞争对手
 * 
 * @author zhoufeng
 */
public interface CompetitorService extends BaseService<Competitor> {

	/**
	 * 通过中间表查找竞争对手
	 * 
	 * @param relationid
	 * @return
	 */
	List<Competitor> findListByRelation(String relationid);

}
