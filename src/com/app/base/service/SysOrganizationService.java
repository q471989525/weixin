package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysOrganization;
import com.app.base.entity.SysUser;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：组织机构Service
 * 
 * @author zhoufeng
 */
public interface SysOrganizationService extends BaseService<SysOrganization> {

	/**
	 * 通过用户id查询,用户所在组织
	 * 
	 * @param userid
	 *            用户id
	 * @return
	 */
	SysOrganization getOrgByUserid(String userid);

	/**
	 * 通过SQL查询有效用户
	 * 
	 * @return
	 */
	List<SysUser> findValidUserBySQL();

	/**
	 * 通过SQL查询有效用户，组装成treegird对象
	 * 
	 * @return
	 */
	List<SysOrganization> getOrgUser();

}
