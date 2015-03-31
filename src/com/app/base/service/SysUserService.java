package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：系统用户Service接口
 * 
 * @author zhoufeng
 */
public interface SysUserService extends BaseService<SysUser> {

	/**
	 * 查找自增长员工编号,默认5位数字
	 * 
	 * @return
	 */
	String findIncrementEmployeeId();

	/**
	 * 保存
	 * @param user
	 * @param orgId
	 */
	void save(SysUser user, String orgId);

	/**
	 * 修改
	 * @param user
	 * @param orgId
	 */
	void update(SysUser user, String orgId);

	/**
	 * 保存用户角色关系
	 * @param userid
	 * @param roleids
	 */
	void saveUserRole(String userid, String roleids);

	/**
	 * 多个角色查询
	 * 
	 * @param roleIds
	 * @return
	 */
	List<SysUser> findUsersByRoles(String roleIds, String hideState);

	/**
	 * 多个组织查询
	 * 
	 * @param orgIds
	 * @return
	 */
	List<SysUser> findUsersByOrgs(String orgIds, String hideState);

	/***
	 * 首页ajax查找用户
	 * 
	 * @param username
	 * @return
	 */
	List<SysUser> findUsersByName(String username);

	/**
	 * 登陆时，通过用户id，查找用户角色
	 * 
	 * @param userid
	 * @return
	 */
	List<SysRole> findSysRoleByUserId(String userid);

}
