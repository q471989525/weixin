package com.app.base.service;

import java.util.List;

import com.app.base.entity.SysGlobalRegion;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：省市区域
 * 
 * @author zhoufeng
 */
public interface SysGlobalRegionService extends BaseService<SysGlobalRegion> {

	/**
	 * 查询省
	 * 
	 * @return
	 */
	List<SysGlobalRegion> findProvince();

	/**
	 * 查询市
	 * 
	 * @param provinceId
	 *            省id
	 * @return
	 */
	List<SysGlobalRegion> findCity(String provinceId);

}
