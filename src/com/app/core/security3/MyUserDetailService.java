package com.app.core.security3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.base.entity.SysOrganization;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.base.service.SysOrganizationService;
import com.app.base.service.SysUserService;
import com.app.core.tag.DictionaryELTag;

public class MyUserDetailService implements UserDetailsService {

	private static Logger logger = Logger.getLogger(MyUserDetailService.class);

	// 注入用户dao
	// SysUserDao sysUserDao;
	SysUserService sysUserService;

	SysOrganizationService sysOrganizationService;

	public SysUser loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		SysUser details = new SysUser();
		if (StringUtils.isNotEmpty(username)) {
			logger.info("登陆名：" + username + " 准备登陆...");

			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("deleteState", "n");
			conditionMap.put("loginId", username);
			List<SysUser> users = sysUserService.findListByMap(conditionMap); //通过loginId查询用户

			if (CollectionUtils.isNotEmpty(users) && users.size() == 1) {
				details = users.get(0);
				details.setPostName(DictionaryELTag.getText("D_Post", details.getPost()));
				
				//查询组织
				SysOrganization organization = sysOrganizationService.getOrgByUserid(details.getResourceid());
				if (null != organization)
					details.setOrgName(organization.getOrgName());
				
				//查询用户拥有角色
				List<SysRole> roles = sysUserService.findSysRoleByUserId(details.getResourceid());
				if (CollectionUtils.isNotEmpty(roles))
					details.setRoles(roles);
			} else {
				logger.info("用户名(" + username + ")不存在...");
				throw new UsernameNotFoundException("用户名(" + username + ")不存在...");
			}
		}
		logger.info("登陆名：" + username + " 登陆成功...");
		return details;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public SysOrganizationService getSysOrganizationService() {
		return sysOrganizationService;
	}

	public void setSysOrganizationService(SysOrganizationService sysOrganizationService) {
		this.sysOrganizationService = sysOrganizationService;
	}

}