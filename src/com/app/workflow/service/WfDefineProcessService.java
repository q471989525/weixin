package com.app.workflow.service;

import com.app.core.service.BaseService;
import com.app.workflow.entity.WfDefineProcess;

/**
 * 流程定义接口
 */
public interface WfDefineProcessService extends BaseService<WfDefineProcess> {

	/**
	 * 通过菜单ID，查找流程定义
	 * 
	 * @param menuId
	 * @return
	 */
	WfDefineProcess findProcessByMenuId(String menuId);

	/**
	 * 查询流程实例流水号，默认从：1000开始
	 * 
	 * @return
	 */
	Integer selectSerialNumber();

}
