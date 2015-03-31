package com.app.base.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysResourceDao;
import com.app.base.dao.SysRoleResourceDao;
import com.app.base.entity.SysResource;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.base.service.SysResourceService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.PropertiesUtil;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：系统资源
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysresourceservice")
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService {

	SysResourceDao sysResourceDao;

	@Resource(name = "sysresourcedao")
	public void setSysResourceDao(SysResourceDao sysResourceDao) {
		this.sysResourceDao = sysResourceDao;
		super.setBaseDao(sysResourceDao);
	}

	@Resource(name = "sysroleresourcedao")
	SysRoleResourceDao sysRoleResourceDao;

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	// 新增
	public void save(SysResource resource, List<SysResource> butList) {
		super.save(resource);

		// 按钮
		if (CollectionUtils.isNotEmpty(butList)) {
			for (SysResource btnResource : butList) {
				btnResource.setParentId(resource.getResourceid());
				btnResource.setParentName(resource.getMenuName());

				if (StringUtils.isEmpty(resource.getParentId())) {
					btnResource.setParentIds(resource.getResourceid());
					btnResource.setParentNames(resource.getMenuName());
				} else {
					btnResource.setParentIds(resource.getParentIds() + "," + resource.getResourceid());
					btnResource.setParentNames(resource.getParentNames() + "," + resource.getMenuName());
				}
				super.save(btnResource);
			}
		}
	}

	// 修改
	public void update(SysResource resource, List<SysResource> butList) {
		super.update(resource);

		// 按钮
		if (CollectionUtils.isNotEmpty(butList)) {
			for (SysResource btnResource : butList) {
				btnResource.setParentId(resource.getResourceid());
				btnResource.setParentName(resource.getMenuName());

				if (StringUtils.isEmpty(resource.getParentId())) {
					btnResource.setParentIds(resource.getResourceid());
					btnResource.setParentNames(resource.getMenuName());
				} else {
					btnResource.setParentIds(resource.getParentIds() + "," + resource.getResourceid());
					btnResource.setParentNames(resource.getParentNames() + "," + resource.getMenuName());
				}

				if (StringUtils.isEmpty(btnResource.getResourceid())) {
					super.save(btnResource);
				} else {
					super.update(btnResource);
				}
			}// end for
		}

		// 删除按钮
		if (StringUtils.isNotEmpty(resource.getSubDeleteIds())) {

			// 删除角色资源中间表
			/*String rids = StringUtils.replace(resource.getSubDeleteIds(), ",", "','");
			String hql = "resourceId in ('" + rids + "')";
			List<SysRoleResource> roleResources = sysRoleResourceDao._findList(SysRoleResource.class, hql, "");
			if (CollectionUtils.isNotEmpty(roleResources))
				sysRoleResourceDao._deleteCollection(roleResources);*/

			//super.deleteByIds(resource.getSubDeleteIds().split(","));
			super.deleteStateByIds(resource.getSubDeleteIds().split(","));
		}
	}

	/**
	 * 覆盖删除方法
	 */
	@Override
	public void deleteById(Serializable id) {
		//List<String> resIds = new ArrayList<String>(); // 删除资源id集合
		SysResource resource = super.load(id);
		resource.setDeleteState("y");
		//super.deleteById(id);
		//resIds.add(id.toString());

		QueryParameter param = new QueryParameter();
		param.addLike("parentIds", id.toString());
		List<SysResource> childList = findList(param); // 查询子
		if (CollectionUtils.isNotEmpty(childList)) {
			for (SysResource cr : childList) {
				cr.setDeleteState("y");
//				resIds.add(cr.getResourceid());
			}
//			super.deleteCollection(childList); // 删除子集合
		}// end if

		// 删除角色资源中间表
		/*if (CollectionUtils.isNotEmpty(resIds)) {
			String rids = StringUtils.join(resIds, "','");
			String hql = "resourceId in ('" + rids + "')";
			List<SysRoleResource> roleResources = sysRoleResourceDao._findList(SysRoleResource.class, hql, "");
			if (CollectionUtils.isNotEmpty(roleResources))
				sysRoleResourceDao._deleteCollection(roleResources);
		}*/
	}

	/**
	 * 初始化或刷新缓存
	 */
	public void init(String type) {
		if (StringUtils.equals(type, "refresh")) { // 刷新操作,先清除再添加
			MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).removeAll();
		}

		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'1','3'");
		param.addEquals("deleteState", "n");
		param.setOrderBy("orderBy");
		List<SysResource> resources = super.findList(param);
		
		if (CollectionUtils.isNotEmpty(resources)) {
			List<String> allUrlList = new ArrayList<String>(); // 有效请求url，集合
			for (SysResource resource : resources) {
				if (StringUtils.startsWith(resource.getMenuUrl(), "/") && StringUtils.indexOf(resource.getMenuUrl(), ".do") > 0) { // 有效请求url
					allUrlList.add(resource.getMenuUrl());
					List<ConfigAttribute> atts = findRoleCodeById(resource.getResourceid());
					if (CollectionUtils.isNotEmpty(atts))
						MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).put(resource.getMenuUrl(), atts); // url与角色对应关系添加到缓存
				}
			}// end for

			MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).put("ALL_URL_LIST", allUrlList); // 全部有效url增加到缓存中
		}
	}

	/**
	 * 通过资源ID，查找已分配的角色编码集合，待访问URL与此比对角色关系。控制访问权限
	 */
	private List<ConfigAttribute> findRoleCodeById(String resourceid) {
		String sql = "select sr.role_code from sys_role_resource rr, sys_role sr where rr.role_id = sr.resourceid and sr.delete_state='n' and rr.resource_id=?";
		List<ConfigAttribute> attList = jdbcTemplate.query(sql, new Object[] { resourceid }, new RoleCodeRowMapper());
		return attList;
	}

	private class RoleCodeRowMapper implements RowMapper<ConfigAttribute> {
		public ConfigAttribute mapRow(ResultSet rs, int arg1) throws SQLException {
			ConfigAttribute att = new SecurityConfig(rs.getString("role_code"));
			return att;
		}
	}

	/**
	 * 首页查询当前用户所拥有的权限资源
	 */
	public List<ZtreeObject> findListByUser(String parentResourceId, SysUser user) {
		String wheresql = ""; //条件
		if (StringUtils.isNotEmpty(parentResourceId)){
			wheresql = " r.parent_ids like '" + parentResourceId + "%'";
		}else{
			String database = PropertiesUtil.instance().get("DATABASE"); //数据库类型
			if (StringUtils.equalsIgnoreCase("MySql", database)) {
				wheresql = "r.parent_id=''"; // mysql 为空值
			} else if (StringUtils.equalsIgnoreCase("Oracle", database)) {
				wheresql = "r.parent_id is null"; // Oracle 为空
			}
		}
		
		String sql = "select distinct r.resourceid, r.menu_name, r.parent_id, r.menu_url from SYS_RESOURCE r, SYS_ROLE_RESOURCE rr where r.resourceid = rr.resource_id and rr.role_id in (select ur.role_id from sys_user_role ur,sys_role sr where ur.role_id=sr.resourceid and sr.delete_state='n' and ur.user_id=?) and " + wheresql
				+ " and r.delete_state='n' and r.state_flag = '1' and r.menu_type='1' order by r.order_by";
		List<ZtreeObject> attList = jdbcTemplate.query(sql, new Object[] { user.getResourceid() }, new SysResourceRowMapper());
		return attList;
	}

	private class SysResourceRowMapper implements RowMapper<ZtreeObject> {
		public ZtreeObject mapRow(ResultSet rs, int arg1) throws SQLException {
			ZtreeObject ztree = new ZtreeObject(rs.getString("resourceid"), rs.getString("parent_id"), rs.getString("menu_name"), rs.getString("menu_url"));
			return ztree;
		}
	}

	// 通过资源id查询分配的角色
	public List<SysRole> findRolesByResourceid(String resourceid) {
		String sql = "select r.resourceid, r.role_name, r.role_code, r.order_by, r.remark from sys_role r,sys_role_resource rr where r.resourceid=rr.role_id and r.delete_state='n' and rr.resource_id=? order by r.order_by";
		List<SysRole> roles = jdbcTemplate.query(sql, new Object[] { resourceid }, new SysRoleRowMapper());
		return roles;
	}

	private class SysRoleRowMapper implements RowMapper<SysRole> {
		public SysRole mapRow(ResultSet rs, int index) throws SQLException {
			SysRole role = new SysRole();
			role.setResourceid(rs.getString("RESOURCEID"));
			role.setRoleName(rs.getString("ROLE_NAME"));
			role.setRoleCode(rs.getString("ROLE_CODE"));
			role.setOrderBy(rs.getInt("ORDER_BY"));
			role.setRemark(rs.getString("REMARK"));
			return role;
		}
	}

}
