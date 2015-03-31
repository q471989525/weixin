package com.app.core.security3;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.SpringContextUtil;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Logger logger = Logger.getLogger(MyInvocationSecurityMetadataSource.class);

	public MyInvocationSecurityMetadataSource() {
		logger.debug("===============================初始化数据=================================");
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl(); // 请求地址
		List<String> allUrlList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).getList("ALL_URL_LIST", String.class); // 从缓存中读取系统中所有url集合，进行对比

		logger.info((SpringContextUtil.getUser() != null ? SpringContextUtil.getUser().getUsername() : "用户没登陆") + "  access：" + url);
		if (CollectionUtils.isNotEmpty(allUrlList)) {
			for (String resURL : allUrlList) {
				if (StringUtils.startsWith(url, resURL)) {
					List<ConfigAttribute> atts = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_RESOURCE_ROLE).getList(resURL, ConfigAttribute.class); // 从缓存读取对应请求url,所包含的角色集合
					if (CollectionUtils.isNotEmpty(atts)) {
						return atts;
					} else {
						return null;
					}
				}
			} // end for
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}