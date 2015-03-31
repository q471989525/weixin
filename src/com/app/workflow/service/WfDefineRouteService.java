package com.app.workflow.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.workflow.entity.WfDefineRoute;

/**
 * 路由定义表接口
 */
public interface WfDefineRouteService extends BaseService<WfDefineRoute> {
	
	/**
	 * 通过活动ID，查询路由
	 * @param activityId
	 * @return
	 */
	List<WfDefineRoute> findRoutesByActivityId(String activityId);

}
