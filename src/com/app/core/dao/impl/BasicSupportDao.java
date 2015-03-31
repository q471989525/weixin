package com.app.core.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * BasicSupportDao 目前功能只有一个用于继承Spring中的HibernateDaoSupport,
 * 然后把SessionFactory注入,如果不采用这个方法的话而采用直接extends HibernateDaoSupport会出现以下错误代码:"org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'baseDao' is defined”,
 * 因为Spring在采用JPA定义的时候会把当前类实例化,如果继承了HibernateDaoSupport的话,因为HibernateDaoSupport源码中SessionFactory和HibernateTemplate是final的不能修改的,
 * 所以会报错不能从Spring配置文件中注入,所以必须要新创建一个BasicSupportDao用来代替
 * @author ZF
 * @Apr 29, 2011
 */
public class BasicSupportDao extends HibernateDaoSupport {
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
