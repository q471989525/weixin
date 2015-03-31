package com.app.core.security3;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * 根据用户的角色与URL拥有的角色对比，以确认是否有权限 authentication:访问者所拥有的角色 object:请求URL
	 * configAttributes：请求资源所拥有的角色
	 */
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null)
			return;

		Iterator<ConfigAttribute> ite = configAttributes.iterator(); // 请求URL所对应的角色集合
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) { // 用户所拥有角色
				if (StringUtils.equals(StringUtils.trim(needRole), StringUtils.trim(ga.getAuthority()))) {
					return;
				}
			}
		}
		throw new AccessDeniedException("请求失败，请检查您的权限...");
	}

	public boolean supports(ConfigAttribute configattribute) {
		return true;
	}

	public boolean supports(Class<?> class1) {
		return true;
	}

}
