package com.app.base.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysGlobalRegionDao;
import com.app.base.entity.SysGlobalRegion;
import com.app.base.service.SysGlobalRegionService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;

/**
 * 
 * TODO：省市区域
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysGlobalRegionService")
public class SysGlobalRegionServiceImpl extends BaseServiceImpl<SysGlobalRegion> implements SysGlobalRegionService {

	SysGlobalRegionDao sysGlobalRegionDao;
	
	@Autowired
	public void setSysGlobalRegionDao(SysGlobalRegionDao sysGlobalRegionDao) {
		this.sysGlobalRegionDao = sysGlobalRegionDao;
		super.setBaseDao(sysGlobalRegionDao);
	}

	@Override
	public void deleteById(Serializable id) {
		SysGlobalRegion region = super.load(id);
		region.setDeleteState("y");

		QueryParameter param = new QueryParameter();
		param.addEquals("deleteState", "n");
		param.addEquals("parentId", region.getResourceid());
		List<SysGlobalRegion> list = super.findList(param);
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysGlobalRegion r : list) {
				r.setDeleteState("y"); // 删除标志
			}
		}
	}

	@Override
	public List<SysGlobalRegion> findProvince() {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("deleteState", "n");
		conditionMap.put("parentId", "1");
		return super.findListByMap(conditionMap, "orderBy");
	}

	@Override
	public List<SysGlobalRegion> findCity(String provinceId) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("deleteState", "n");
		conditionMap.put("parentId", provinceId);
		return super.findListByMap(conditionMap, "orderBy");
	}

}
