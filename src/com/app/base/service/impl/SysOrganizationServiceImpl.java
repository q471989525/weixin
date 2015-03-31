package com.app.base.service.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysOrganizationDao;
import com.app.base.entity.SysOrganization;
import com.app.base.entity.SysUser;
import com.app.base.service.SysOrganizationService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;

/**
 * 
 * TODO：组织机构
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysorganizationservice")
public class SysOrganizationServiceImpl extends BaseServiceImpl<SysOrganization> implements SysOrganizationService {

	SysOrganizationDao sysOrganizationDao;

	// 注入Spring jdbcTemplate
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	// 注入dao
	@Resource(name = "sysorganizationdao")
	public void setSysOrganizationDao(SysOrganizationDao sysOrganizationDao) {
		this.sysOrganizationDao = sysOrganizationDao;
		setBaseDao(sysOrganizationDao);
	}

	@Override
	public void deleteById(Serializable id) {
		SysOrganization organization = load(id);
		organization.setDeleteState("y"); // 删除标志,y:删除

		// 更新子组织
		QueryParameter param = new QueryParameter();
		param.addLike("parentIds", id.toString());
		param.addEquals("deleteState", "n");
		List<SysOrganization> childList = findList(param); // 查询子
		if (CollectionUtils.isNotEmpty(childList)) {
			for (SysOrganization org : childList) {
				org.setDeleteState("y"); // 删除标志,y:删除
			}
		}
	}

	public SysOrganization getOrgByUserid(String userid) {
		String sql = "select o.resourceid,o.org_name from sys_organization o, sys_org_user t where o.resourceid=t.org_id and o.delete_state='n' and t.org_user_type='r' and t.user_id=?";
		List<SysOrganization> orgList = jdbcTemplate.query(sql, new Object[] { userid }, new OrgUserRowMapper());
		if (CollectionUtils.isNotEmpty(orgList)) {
			return orgList.get(0);
		}
		return null;
	}

	private class OrgUserRowMapper implements RowMapper<SysOrganization> {
		public SysOrganization mapRow(ResultSet rs, int index) throws SQLException {
			SysOrganization org = new SysOrganization();
			org.setResourceid(rs.getString("RESOURCEID"));
			org.setOrgName(rs.getString("ORG_NAME"));
			return org;
		}
	}

	@Override
	public List<SysUser> findValidUserBySQL() {
		// 查询组织：未删除、有效；用户：未删除、激活、在职、显示
		String sql = "select u.resourceid, u.username, u.sex, ou.org_id from sys_user u, sys_org_user ou, sys_organization o where u.resourceid = ou.user_id and ou.org_id=o.resourceid and u.show_state='y' and u.state_flag='1' and u.is_enabled='y' and u.delete_state='n' and o.delete_state='n' and o.state_flag='y' order by u.order_by";
		List<SysUser> userList = jdbcTemplate.query(sql, new SimpleUserOrgRowMapper());
		return userList;
	}

	private class SimpleUserOrgRowMapper implements RowMapper<SysUser> {
		public SysUser mapRow(ResultSet rs, int index) throws SQLException {
			SysUser user = new SysUser();
			user.setResourceid(rs.getString("resourceid"));
			user.setUsername(rs.getString("username"));
			user.setSex(rs.getString("sex"));
			user.setOrgId(rs.getString("org_id"));
			return user;
		}
	}

	@Override
	public List<SysOrganization> getOrgUser() {
		// 查询组织：未删除、有效；用户：未删除
		String sql = "select u.resourceid, u.username, u.login_id, u.post, u.mobile, u.email, u.sex, u.state_flag, u.order_by, u.duty_desc, o.resourceid orgid from sys_user u, sys_org_user ou, sys_organization o where u.resourceid = ou.user_id and ou.org_id = o.resourceid and u.delete_state='n' and ou.org_user_type = 'r' and o.state_flag in ('y' , 'h') and o.delete_state='n' order by u.order_by";
		List<SysOrganization> userList = jdbcTemplate.query(sql, new SysOrgUserRowMapper());
		return userList;
	}

	private class SysOrgUserRowMapper implements RowMapper<SysOrganization> {
		public SysOrganization mapRow(ResultSet rs, int index) throws SQLException {
			SysOrganization orgUser = new SysOrganization();
			orgUser.setResourceid(rs.getString("resourceid"));
			orgUser.setParentId(rs.getString("orgid"));
			orgUser.setOrgName(rs.getString("username"));
			orgUser.setUserLogin(rs.getString("login_id"));
			orgUser.setUserPost(rs.getString("post"));
			orgUser.setUserMobile(rs.getString("mobile"));
			orgUser.setUserEmail(rs.getString("email"));
			String sex = rs.getString("sex");
			orgUser.setUserSex(sex);
			if (StringUtils.equals(sex, "1")) { // 男
				orgUser.setIconCls("icon-user-black");
			} else if (StringUtils.equals(sex, "2")) { // 女
				orgUser.setIconCls("icon-user-black-female");
			} else {
				orgUser.setIconCls("icon-user-question"); // 未知
			}
			orgUser.setStateFlag(rs.getString("state_flag"));
			orgUser.setOrderBy(rs.getInt("order_by"));
			orgUser.setDutyDesc(rs.getString("duty_desc"));
			return orgUser;
		}
	}

}
