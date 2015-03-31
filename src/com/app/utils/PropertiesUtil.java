package com.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * TODO：Properties文件工具类
 * 
 * @author zhoufeng
 */
public class PropertiesUtil {

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	private static PropertiesUtil propertiesUtil = null;

	private static Properties prop = null;

	private PropertiesUtil() {
	}

	public static synchronized PropertiesUtil instance() {
		if (null == propertiesUtil) {
			propertiesUtil = new PropertiesUtil();
			prop = new Properties();
			InputStream in = PropertiesUtil.class.getResourceAsStream("Prop.properties");
			try {
				prop.load(in);
			} catch (IOException e) {
				logger.info("初始化properties文件错误......");
				e.printStackTrace();
			}
		}

		return propertiesUtil;
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public synchronized String get(String key) {
		String valString = prop.getProperty(key, "");
		logger.info("key:"+key+" value:"+valString);
		return valString;
	}

}
