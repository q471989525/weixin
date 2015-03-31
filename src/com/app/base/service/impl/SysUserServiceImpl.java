package com.app.base.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysOrgUserDao;
import com.app.base.dao.SysUserDao;
import com.app.base.dao.SysUserRoleDao;
import com.app.base.entity.SysOrgUser;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.base.entity.SysUserDataLimit;
import com.app.base.entity.SysUserRole;
import com.app.base.service.SysUserDataLimitService;
import com.app.base.service.SysUserService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.PropertiesUtil;

/**
 * 
 * TODO：系统用户Service实现
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysuserservice")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

	SysUserDao sysUserDao;

	// 组织用户
	@Resource(name = "sysorguserdao")
	SysOrgUserDao sysOrgUserDao;

	// 用户角色
	@Resource(name = "sysuserroledao")
	SysUserRoleDao sysUserRoleDao;

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	// 用户数据权限
	@Resource(name = "sysuserdatalimitservice")
	SysUserDataLimitService sysUserDataLimitService;

	// 注入dao
	@Resource(name = "sysuserdao")
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
		setBaseDao(sysUserDao);
	}

	// 自增编号
	@Override
	public String findIncrementEmployeeId() {
		String sql = "SELECT max(EMPLOYEE_ID) FROM sys_user";
		String employeeId = jdbcTemplate.queryForObject(sql, String.class);

		String code = PropertiesUtil.instance().get("EMPLOYEE_CODE"); //编码前缀

		String returnCode = "";
		if (StringUtils.isNotEmpty(employeeId) && StringUtils.isNotEmpty(code)) {
			String num = employeeId.replace(code, "");
			if (NumberUtils.isNumber(num)) {
				num = (Integer.parseInt(num) + 1) + "";
				if (num.length() == 1)
					returnCode = code + "0000" + num;
				if (num.length() == 2)
					returnCode = code + "000" + num;
				if (num.length() == 3)
					returnCode = code + "00" + num;
				if (num.length() == 4)
					returnCode = code + "0" + num;
				if (num.length() == 5)
					returnCode = code + num;
			}
		}
		return returnCode;
	}

	// 保存
	public void save(SysUser user, String orgId) {
		super.save(user);

		// 保存组织用户中间表
		SysOrgUser orgUser = new SysOrgUser(orgId, user.getResourceid());
		sysOrgUserDao._save(orgUser);
	}

	// 修改
	public void update(SysUser user, String orgId) {
		super.update(user);

		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("orgUserType", "r");
		conditionMap.put("userId", user.getResourceid());
		List<SysOrgUser> lists = sysOrgUserDao._findListByMap(SysOrgUser.class, conditionMap, ""); // 通过用户查找组织用户关系表
		if (CollectionUtils.isNotEmpty(lists))
			sysOrgUserDao._deleteCollection(lists); // 先删除再新增

		// 保存组织用户中间表
		SysOrgUser orgUser = new SysOrgUser(orgId, user.getResourceid());
		sysOrgUserDao._save(orgUser);
	}

	// 删除，更新删除标志
	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			SysUser user = load(id);
			user.setDeleteState("y"); // 删除标志
			//user.setStateFlag("2"); // 离职状态

			// 删除用户角色中间表
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("userId", id);
			/*List<SysUserRole> userRoleList = sysUserRoleDao._findListByMap(SysUserRole.class, conditionMap, "");
			if (CollectionUtils.isNotEmpty(userRoleList))
				sysUserRoleDao._deleteCollection(userRoleList);*/

			//删除用户数据权限表
			List<SysUserDataLimit> limitList = sysUserDataLimitService.findListByMap(conditionMap);
			if (CollectionUtils.isNotEmpty(limitList)){
				for (SysUserDataLimit dataLimit : limitList) {
					dataLimit.setDeleteState("y");
				}
				//sysUserDataLimitService.deleteCollection(limitList);
			}
		}
	}

	// 保存用户角色关系
	public void saveUserRole(String userid, String roleids) {
		String[] roles = roleids.split(",");
		if (ArrayUtils.isNotEmpty(roles)) {
			// 先删除用户角色中间表
			List<SysUserRole> existUserRoles = sysUserRoleDao._findList(SysUserRole.class, "userId='" + userid + "'", "");
			if (CollectionUtils.isNotEmpty(existUserRoles))
				sysUserRoleDao._deleteCollection(existUserRoles);

			//然后再新增
			List<SysUserRole> userRoleList = new ArrayList<SysUserRole>();
			for (int i = 0; i < roles.length; i++) {
				userRoleList.add(new SysUserRole(userid, roles[i]));
			}
			if(CollectionUtils.isNotEmpty(userRoleList)) sysUserRoleDao._saveOrUpdateCollection(userRoleList);
		}
	}

	//选择页面，角色查询用户
	public List<SysUser> findUsersByRoles(String roleIds, String hideState) {
		String condition = "and u.show_state='y'";
		if(StringUtils.isNotEmpty(hideState)) condition = ""; //显示全部状态
		String sql = "select distinct u.resourceid,u.username, u.employee_id, u.post, u.email, u.mobile, u.office_phone, u.sex from SYS_USER u, SYS_USER_ROLE ur, SYS_ORG_USER ou, SYS_ORGANIZATION o where u.resourceid = ur.user_id and u.resourceid = ou.user_id and ou.org_id = o.resourceid and ur.role_id in ('" + roleIds.replaceAll(",", "','") + "') "+condition+" and u.state_Flag='1' and u.is_enabled='y' and u.delete_state='n' and ou.org_user_type='r' and o.state_flag='y' and o.delete_state='n' order by u.order_by";
		List<SysUser> userList = jdbcTemplate.query(sql, new SysUserRowMapper());
		return userList;
	}

	private class SysUserRowMapper implements RowMapper<SysUser> {
		public SysUser mapRow(ResultSet rs, int index) throws SQLException {
			SysUser user = new SysUser();
			user.setResourceid(rs.getString("resourceid"));
			user.setUsername(rs.getString("username"));
			user.setEmployeeId(rs.getString("employee_id"));
			user.setPost(rs.getString("post"));
			user.setEmail(rs.getString("email"));
			user.setMobile(rs.getString("mobile"));
			user.setOfficePhone(rs.getString("office_phone"));
			user.setSex(rs.getString("sex"));
			return user;
		}
	}
	
	//选择页面，组织查询用户
	public List<SysUser> findUsersByOrgs(String orgIds, String hideState) {
		String condition = "and u.show_state='y'";
		if(StringUtils.isNotEmpty(hideState)) condition = "";
		String sql = "select distinct u.resourceid, u.username, u.employee_id, u.post, u.email, u.mobile, u.office_phone, u.sex from SYS_USER u, SYS_ORG_USER ou, SYS_ORGANIZATION o where u.resourceid = ou.user_id and ou.org_id = o.resourceid and ou.org_id in ('" + orgIds.replaceAll(",", "','") + "') "+condition+" and u.state_Flag='1' and u.is_enabled='y' and u.delete_state='n' and o.state_flag='y' and o.delete_state='n' order by u.order_by";
		List<SysUser> userList = jdbcTemplate.query(sql, new SysUserRowMapper());
		return userList;
	}

	//首页ajax查找用户
	@Override
	public List<SysUser> findUsersByName(String username) {
		String sql = "select u.resourceid,u.username,u.login_id,u.sex,u.post,u.email,u.mobile,u.office_phone,u.duty_desc,o.org_name from SYS_USER u, SYS_ORG_USER ou, SYS_ORGANIZATION o where u.resourceid=ou.user_id and ou.org_id=o.resourceid and (u.username like '%"+ username + "%' or u.login_id like '%" + username + "%' or u.email like '%" + username + "%' or u.mobile like '%" + username + "%' or u.office_phone like '%" + username + "%') and u.state_Flag = '1' and u.is_enabled = 'y' and u.show_state='y' and u.delete_state='n' and ou.org_user_type='r' and o.state_flag='y' and o.delete_state='n' order by u.order_by";
		List<SysUser> userList = jdbcTemplate.query(sql, new ajaxUserRowMapper());
		return userList;
	}

	private class ajaxUserRowMapper implements RowMapper<SysUser> {
		public SysUser mapRow(ResultSet rs, int index) throws SQLException {
			SysUser user = new SysUser();
			user.setResourceid(rs.getString("resourceid"));
			user.setUsername(rs.getString("username"));
			user.setLoginId(rs.getString("login_id"));
			user.setSex(DictionaryELTag.getText("D_Sex", rs.getString("sex")));
			user.setPost(DictionaryELTag.getText("D_Post", rs.getString("post")));
			user.setEmail(StringUtils.defaultString(rs.getString("email")));
			user.setMobile(StringUtils.defaultString(rs.getString("mobile")));
			user.setOfficePhone(StringUtils.defaultString(rs.getString("office_phone")));
			user.setDutyDesc(StringUtils.defaultString(rs.getString("duty_desc")));
			user.setOrgName(rs.getString("org_name"));
			return user;
		}
	}

	//登陆时，通过用户id，查找用户拥有角色
	@Override
	public List<SysRole> findSysRoleByUserId(String userid) {
		String sql = "select r.resourceid, r.role_name, r.role_code from sys_role r, sys_user_role ur where r.resourceid=ur.role_id and r.delete_state='n' and ur.user_id=? order by r.order_by";
		List<SysRole> roleList = jdbcTemplate.query(sql, new Object[] { userid }, new SysRoleRowMapper());
		return roleList;
	}

	private class SysRoleRowMapper implements RowMapper<SysRole> {
		public SysRole mapRow(ResultSet rs, int index) throws SQLException {
			SysRole role = new SysRole();
			role.setResourceid(rs.getString("resourceid"));
			role.setRoleName(rs.getString("role_name"));
			role.setRoleCode(rs.getString("role_code"));
			return role;
		}
	}

}
