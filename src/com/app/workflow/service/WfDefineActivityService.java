package com.app.workflow.service;

import com.app.core.service.BaseService;
import com.app.workflow.entity.WfDefineActivity;
import com.app.workflow.entity.WfDefineActivityCandidate;

/**
 * 活动定义接口
 * 
 */
public interface WfDefineActivityService extends BaseService<WfDefineActivity> {

	/**
	 * 新增
	 * 
	 * @param activity
	 * @param candidate
	 * @param selectdFormElemIds
	 */
	void save(WfDefineActivity activity, WfDefineActivityCandidate candidate, String selectdFormElemIds);

	/**
	 * 修改
	 * 
	 * @param activity
	 * @param candidate
	 * @param selectdFormElemIds
	 */
	void update(WfDefineActivity activity, WfDefineActivityCandidate candidate, String selectdFormElemIds);
	
	/**
	 * 通过流程定义ID，查询开始节点活动
	 * @param processid
	 * @return
	 */
	WfDefineActivity findStartActivityByProcessId(String processid);

}
