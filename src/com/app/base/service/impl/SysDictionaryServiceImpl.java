package com.app.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysDictionaryDao;
import com.app.base.entity.SysGlobalRegion;
import com.app.base.entity.SysDictionary;
import com.app.base.service.SysGlobalRegionService;
import com.app.base.service.SysDictionaryService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.PropertiesUtil;

/**
 * 
 * TODO：数据字典Service接口实现类
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysdictionaryservice")
public class SysDictionaryServiceImpl extends BaseServiceImpl<SysDictionary> implements SysDictionaryService {

	private static Logger logger = Logger.getLogger(SysDictionaryServiceImpl.class);

	SysDictionaryDao dictionaryDao;

	@Resource(name = "sysGlobalRegionService")
	SysGlobalRegionService sysGlobalRegionService;

	// 注入dao
	@Resource(name = "sysdictionarydao")
	public void setDictionaryDao(SysDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
		setBaseDao(dictionaryDao);
	}

	public void save(SysDictionary dictionary, List<SysDictionary> lists) {
		super.save(dictionary);

		for (SysDictionary sysDictionary : lists) {
			sysDictionary.setParentId(dictionary.getResourceid());
			super.save(sysDictionary);
		}
		logger.info("保存");
	}

	public void update(SysDictionary dictionary, List<SysDictionary> lists) {
		super.update(dictionary);

		for (SysDictionary childDict : lists) {
			childDict.setParentId(dictionary.getResourceid());
			if (StringUtils.isEmpty(childDict.getResourceid())) {
				super.save(childDict);
			} else {
				super.update(childDict);
			}
		}

		// 删除子项
		if (StringUtils.isNotEmpty(dictionary.getDeleteIds())) {
			// super.deleteByIds(dictionary.getDeleteIds().split(","));
			super.deleteStateByIds(dictionary.getDeleteIds().split(","));
		}
		logger.info("修改");
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			SysDictionary dictionary = load(id);
			dictionary.setDeleteState("y"); // 删除标志

			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("deleteState", "n");
			conditionMap.put("parentId", id);
			List<SysDictionary> list = findListByMap(conditionMap);
			if (CollectionUtils.isNotEmpty(list)) {
				for (SysDictionary childDict : list) {
					childDict.setDeleteState("y"); // 删除标志
				}
				// super.deleteCollection(list); //删除子表
			}
			// super.deleteById(id); //删除主表
		}
	}

	// 刷新缓存
	public void refresh(String operator) {
		if (StringUtils.equals(operator, "update")) { // 更新操作,先清除全部缓存
			MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).removeAll();
		}

		QueryParameter param = new QueryParameter();
		param.addEquals("isValid", "y"); // 有效
		param.addEquals("parentId", "0"); // 查父ID为0
		param.addEquals("deleteState", "n"); // 未删除
		param.setOrderBy("orderBy");
		List<SysDictionary> lists = super.findList(param);

		if (CollectionUtils.isNotEmpty(lists)) {
			QueryParameter childParam = null;
			for (SysDictionary dictionary : lists) {
				childParam = new QueryParameter();
				childParam.addEquals("isValid", "y");
				childParam.addEquals("deleteState", "n");
				childParam.addEquals("parentId", dictionary.getResourceid());
				childParam.setOrderBy("orderBy");
				List<SysDictionary> childLists = super.findList(childParam);

				if (CollectionUtils.isNotEmpty(childLists))
					MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).put(dictionary.getDictCode(), childLists);
			}
		}

		// 加载省市基础数据
		String Province_Code = PropertiesUtil.instance().get("D_PROVINCE");
		List<SysGlobalRegion> provinceList = sysGlobalRegionService.findProvince(); // 全部省
		MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).put(Province_Code, provinceList);

		List<SysGlobalRegion> allCityList = new ArrayList<SysGlobalRegion>();
		for (SysGlobalRegion province : provinceList) {
			List<SysGlobalRegion> cityList = sysGlobalRegionService.findCity(province.getResourceid()); // 市
			allCityList.addAll(cityList);
			MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).put(Province_Code + "_" + province.getResourceid(), cityList);
		}

		MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).put("CODE_ALL_CITY", allCityList); // 全部市
	}

}
