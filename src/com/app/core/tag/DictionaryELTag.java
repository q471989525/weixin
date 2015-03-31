package com.app.core.tag;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.app.base.entity.SysGlobalRegion;
import com.app.base.entity.SysDictionary;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;

/**
 * 
 * TODO：格式化EL表达式；返回json数据对象
 * 
 * @author zhoufeng
 */
public class DictionaryELTag {

	private static Logger logger = Logger.getLogger(DictionaryELTag.class);

	/**
	 * 字典编码
	 * 
	 * @param dictionaryCode
	 * @return
	 */
	public static String fmt(String dictionaryCode) {
		String textString = "";
		try {
			List<String> textList = new ArrayList<String>();
			if (StringUtils.isNotEmpty(dictionaryCode)) {
				
				if (StringUtils.equalsIgnoreCase(dictionaryCode, "D_Province")) { //省
					List<SysGlobalRegion> provinceList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysGlobalRegion.class);
					if (CollectionUtils.isNotEmpty(provinceList)) {
						for (SysGlobalRegion region : provinceList) {
							textList.add("{\"value\":\"" + region.getResourceid() + "\",\"name\":\"" + region.getRegionName() + "\"}");
						}
					}
				} else if(StringUtils.equalsIgnoreCase(dictionaryCode, "D_City")){ //市
					List<SysGlobalRegion> allCityList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList("CODE_ALL_CITY", SysGlobalRegion.class);
					if (CollectionUtils.isNotEmpty(allCityList)) {
						for (SysGlobalRegion region : allCityList) {
							textList.add("{\"value\":\"" + region.getResourceid() + "\",\"name\":\"" + region.getRegionName() + "\"}");
						}
					}
				} else {
					// 从缓存中获取字典对象
					List<SysDictionary> childrens = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysDictionary.class);
					if (null != childrens) {
						for (SysDictionary childDict : childrens) {
							textList.add("{\"value\":\"" + childDict.getItemValue() + "\",\"name\":\"" + childDict.getItemName() + "\"}");
						}
					}
				}
				
			}
			if (CollectionUtils.isNotEmpty(textList))
				textString = "[" + StringUtils.join(textList, ",") + "]";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EL表达式异常:" + e.getMessage());
		}
		logger.info(textString);
		return textString;
	}

	/**
	 * 字典编码,值，获取文本
	 * 
	 * @param dictionaryCode
	 * @param value
	 * @return
	 */
	public static String getText(String dictionaryCode, String value) {
		String textString = "";
		try {
			if (StringUtils.isNotEmpty(dictionaryCode) && StringUtils.isNotEmpty(value)) {
				// 从缓存中获取字典对象
				List<SysDictionary> childrens = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysDictionary.class);
				if (null != childrens) {
					for (SysDictionary childDict : childrens) {
						if (StringUtils.equals(value, childDict.getItemValue()))
							textString = childDict.getItemName();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EL表达式异常:" + e.getMessage());
		}
		logger.info(textString);
		return textString;
	}

	/**
	 * 字典编码,获取集合
	 * 
	 * @param dictionaryCode
	 * @return
	 */
	public static List<SysDictionary> getList(String dictionaryCode) {
		List<SysDictionary> childrens = new ArrayList<SysDictionary>();
		try {
			if (StringUtils.isNotEmpty(dictionaryCode)) {
				// 从缓存中获取字典对象
				childrens = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(dictionaryCode, SysDictionary.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EL表达式异常:" + e.getMessage());
		}
		return childrens;
	}

}
