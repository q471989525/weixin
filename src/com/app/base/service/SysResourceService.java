package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysResource;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.core.service.BaseService;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：系统资源
 * 
 * @author zhoufeng
 */
public interface SysResourceService extends BaseService<SysResource> {

	// 新增
	void save(SysResource resource, List<SysResource> butList);

	// 修改
	void update(SysResource resource, List<SysResource> butList);

	/**
	 * 系统启动初始化资源与角色之间关系
	 */
	void init(String type);

	/**
	 * 查询当前用户所拥有的权限资源
	 * 
	 * @param parentResourceId
	 *            父资源id
	 * @param user
	 * @return
	 */
	List<ZtreeObject> findListByUser(String parentResourceId, SysUser user);

	/**
	 * 通过资源id查询分配的角色
	 * 
	 * @param resourceid
	 * @return
	 */
	List<SysRole> findRolesByResourceid(String resourceid);

}
