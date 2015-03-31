package com.app.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * TODO：对数据库数码进行加密,采用数据源，暂停使用
 * 
 * @author zhoufeng
 */
public class SpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		// 对配置文件中的数据库密码密文进行解密
		String password = (String) props.get("password");
		if (password != null) {
			// props.setProperty("password",
			// SecurityUtils.decryptAES(password));
		}
		super.processProperties(beanFactoryToProcess, props);
	}

}
