package com.app.base.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysRoleDao;
import com.app.base.dao.SysRoleResourceDao;
import com.app.base.dao.SysUserRoleDao;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysRoleResource;
import com.app.base.service.SysRoleService;
import com.app.core.service.impl.BaseServiceImpl;

/**
 * 
 * TODO：系统角色
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysroleservice")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

	SysRoleDao sysRoleDao;

	@Resource(name = "sysroledao")
	public void setSysRoleDao(SysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
		super.setBaseDao(sysRoleDao);
	}

	@Resource(name = "sysroleresourcedao")
	SysRoleResourceDao sysRoleResourceDao;

	@Resource(name = "sysuserroledao")
	SysUserRoleDao sysUserRoleDao;

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	// 覆盖删除方法
	@Override
	public void deleteByIds(Serializable[] ids) {

		// 删除角色资源中间表
		/*
		 * for (int i = 0; i < ids.length; i++) { Map<String, Object>
		 * conditionMap = new HashMap<String, Object>();
		 * conditionMap.put("roleId", ids[i]); List<SysRoleResource>
		 * roleResources =
		 * sysRoleResourceDao._findListByMap(SysRoleResource.class,
		 * conditionMap, ""); if (CollectionUtils.isNotEmpty(roleResources))
		 * sysRoleResourceDao._deleteCollection(roleResources); }
		 */

		// 删除用户角色中间表
		/*
		 * for (int i = 0; i < ids.length; i++) { Map<String, Object>
		 * conditionMap = new HashMap<String, Object>();
		 * conditionMap.put("roleId", ids[i]); List<SysUserRole> userRoles =
		 * sysUserRoleDao._findListByMap(SysUserRole.class, conditionMap, "");
		 * if (CollectionUtils.isNotEmpty(userRoles))
		 * sysUserRoleDao._deleteCollection(userRoles); }
		 */

		// 删除角色
		// super.deleteByIds(ids);

		super.deleteStateByIds(ids);
	}

	// 通过角色ID，查询已经分配资源ID集合
	@Override
	public List<String> getResourceByRoleId(String roleId) {
		String sql = "select r.resourceid from sys_role_resource rr,sys_resource r where rr.resource_id=r.resourceid and r.delete_state='n' and rr.role_id=?";
		List<String> resourceids = jdbcTemplate.query(sql, new Object[] { roleId }, new RoleResourceidRowMapper());
		return resourceids;
	}

	private class RoleResourceidRowMapper implements RowMapper<String> {
		public String mapRow(ResultSet rs, int index) throws SQLException {
			return rs.getString("resourceid");
		}
	}

	/**
	 * 保存角色资源关系
	 */
	public void saveRoleResource(String roleid, String resourceids) {
		String[] rss = resourceids.split(",");
		if (ArrayUtils.isNotEmpty(rss)) {
			// 先删除中间表
			List<SysRoleResource> existRoleRs = sysRoleResourceDao._findList(SysRoleResource.class, "roleId='" + roleid + "'", "");
			if (CollectionUtils.isNotEmpty(existRoleRs))
				sysRoleResourceDao._deleteCollection(existRoleRs);

			// 然后再新增
			SysRoleResource roleResource = null;
			for (int i = 0; i < rss.length; i++) {
				roleResource = new SysRoleResource(roleid, rss[i]);
				sysRoleResourceDao._save(roleResource);
			}
		}
	}

}
