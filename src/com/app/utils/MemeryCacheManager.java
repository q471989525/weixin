package com.app.utils;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

/**
 * 
 * TODO：Ehcache帮助类
 * 
 * @author zhoufeng
 */

public class MemeryCacheManager {

	protected static Logger logger = LoggerFactory.getLogger(MemeryCacheManager.class);

	private static MemeryCacheManager memeryCacheManager = null;

	/** CacheManager 实例 */
	private static CacheManager cacheManager = null;

	/** cache 实例 */
	private static Cache cache;

	private MemeryCacheManager() {

	}

	/**
	 * 获取指定配置的cache
	 * 
	 * @param cache
	 * @return
	 */
	public static synchronized MemeryCacheManager getCache(String cacheName) {
		if (null == memeryCacheManager) {
			if (null == cacheManager) {
				MemeryCacheManager.cacheManager = new CacheManager();
			}
			memeryCacheManager = new MemeryCacheManager();
		}

		setCache(MemeryCacheManager.cacheManager.getCache(cacheName));
		return memeryCacheManager;
	}

	private static void setCache(Cache cache) {
		MemeryCacheManager.cache = cache;
	}

	/**
	 * 将一个对象放入缓存池中.
	 * 
	 * @param key
	 *            缓存名称
	 * @param obj
	 *            缓存对象
	 */
	public synchronized void put(String key, Object obj) {
		Element element = new Element(key, obj);
		cache.put(element);
		logger.debug(key + "(" + (obj == null ? "NULL" : obj.toString()) + ")已放入缓存...");
	}

	/**
	 * 从缓存池中获取一个缓存对象
	 * 
	 * @param key
	 *            缓存对象名
	 * @return 如果没有可获取的对象，则返回<code>null</code>.
	 */
	public synchronized Object get(String key) {
		Element element = null;
		try {
			element = cache.get(key);
		} catch (CacheException cacheException) {
			logger.error("获取缓存失败：" + cacheException.getMessage());
			throw new DataRetrievalFailureException("获取缓存失败: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return element.getObjectValue();
		}
	}

	/**
	 * 从缓存池中获取一个缓存对象,针对用户数据权限
	 * 
	 * @param key
	 *            缓存对象名
	 * @return 如果没有可获取的对象，则返回<code>null</code>.
	 */
	public synchronized Object get(String userId, String menuId) {
		Element element = null;
		try {
			element = cache.get(userId + "-" + menuId);
		} catch (CacheException cacheException) {
			logger.error(userId + "-" + menuId + "获取缓存失败：" + cacheException.getMessage());
			throw new DataRetrievalFailureException("获取缓存失败: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			logger.info("获取数据权限：" + userId + "-" + menuId + " = " + element);
			return element.getObjectValue();
		}
	}

	/**
	 * 获取指定类型缓存对象
	 * 
	 * @param <T>
	 *            指定类型
	 * @param key
	 *            缓存KEY
	 * @param classz
	 *            类型Class
	 * @return 指定类型缓存对象
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T> T getClazz(String key, Class<T> clazz) {
		if (null == get(key)) {
			return null;
		}
		return (T) get(key);
	}

	/**
	 * 获取指定缓存的所有key集合
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getKeys() {
		return cache.getKeys();
	}

	/**
	 * 获取指定类型LIST缓存
	 * 
	 * @param <T>
	 *            指定类型
	 * @param key
	 *            缓存KEY
	 * @param classz
	 *            类型Class
	 * @return 指定类型LIST缓存
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T> List<T> getList(String key, Class<T> classz) {
		if (null == get(key)) {
			return null;
		}
		return (List<T>) get(key);
	}

	/**
	 * 从缓存池中移除缓存对象.
	 * 
	 * @param key
	 *            缓存对象名称
	 */
	public synchronized void remove(String key) {
		cache.remove(key);
		logger.debug(key + "已从缓存中移除...");
	}

	/**
	 * 从缓存池中移除所有缓存对象.
	 * 
	 */
	public synchronized void removeAll() {
		cache.removeAll();
		logger.debug("已经全部移除...");
	}

}
