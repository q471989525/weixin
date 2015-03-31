package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysRole;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：系统角色
 * 
 * @author zhoufeng
 */
public interface SysRoleService extends BaseService<SysRole> {

	/**
	 * 保存角色资源关系
	 * @param roleid
	 * @param resourceids
	 */
	void saveRoleResource(String roleid, String resourceids);
	
	/**
	 * 通过角色ID，查询已经分配资源ID集合
	 * @param roleId
	 * @return
	 */
	List<String> getResourceByRoleId(String roleId);

}
