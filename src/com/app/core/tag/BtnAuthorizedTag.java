package com.app.core.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;

/**
 * 按钮权限判断标签
 * 
 * @author Administrator
 * 
 */
public class BtnAuthorizedTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	String btnUrl;

	@Override
	public int doStartTag() throws JspException {
		SysUser user = null;

		SecurityContext context = SecurityContextHolder.getContext();
		if (null != context) {
			Authentication auth = context.getAuthentication();
			if (null != auth) {
				Object principal = auth.getPrincipal();
				if (principal instanceof SysUser)
					user = (SysUser) principal; // 获取系统用户
			}
		}
		if (null == user) {
			return SKIP_BODY; // 不显示标签间的代码
		}

		List<ConfigAttribute> atts = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).getList(btnUrl, ConfigAttribute.class); // 从缓存读取对应按钮,所包含的角色集合
		if (CollectionUtils.isNotEmpty(atts) && CollectionUtils.isNotEmpty(user.getRoles())) {
			for (SysRole role : user.getRoles()) { // 当前用户拥有的角色
				for (ConfigAttribute ca : atts) {
					String needRole = ((SecurityConfig) ca).getAttribute();
					if (StringUtils.equals(StringUtils.trim(role.getRoleCode()), StringUtils.trim(needRole))) {
						return EVAL_BODY_INCLUDE; // 已分配权限,则显示标签间的代码
					}
				}
			}
		}

		return SKIP_BODY; // 不显示标签间的代码
	}

	public String getBtnUrl() {
		return btnUrl;
	}

	public void setBtnUrl(String btnUrl) {
		this.btnUrl = btnUrl;
	}

}
