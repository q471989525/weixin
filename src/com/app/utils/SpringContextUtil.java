package com.app.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.base.entity.SysUser;

/**
 * Spring 静态获取Bean
 * 
 * @author Administrator
 * 
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext; // spring 应用上下文环境

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文。
	 */
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContextUtil.applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 *            bean注册名
	 * @return
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 判断Bean是否存在
	 * 
	 * @param name
	 *            bean注册名
	 * @return
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断Bean是否是单例模式
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	/** 返回当前登录用户 */
	public static SysUser getUser() {
		SysUser userDetails = null;
		SecurityContext context = SecurityContextHolder.getContext();
		if (null != context) {
			Authentication auth = context.getAuthentication();
			if (null != auth) {
				Object principal = auth.getPrincipal();
				if (principal instanceof SysUser)
					userDetails = (SysUser) principal;
			}
		}
		return userDetails;
	}

}
