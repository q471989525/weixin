package com.app.base.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.base.dao.SysUserDataLimitDao;
import com.app.base.entity.SysUserDataLimit;
import com.app.base.service.SysUserDataLimitService;
import com.app.core.service.impl.BaseServiceImpl;
import com.app.core.service.util.QueryParameter;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;

/**
 * 
 * TODO：用户数据权限表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("sysuserdatalimitservice")
public class SysUserDataLimitServiceImpl extends BaseServiceImpl<SysUserDataLimit> implements SysUserDataLimitService {

	SysUserDataLimitDao sysUserDataLimitDao;

	@Resource
	public void setSysUserDataLimitDao(SysUserDataLimitDao sysUserDataLimitDao) {
		this.sysUserDataLimitDao = sysUserDataLimitDao;
		super.setBaseDao(sysUserDataLimitDao);
	}

	@Override
	public void deleteByIds(Serializable[] ids) {
		super.deleteStateByIds(ids);
	}

	@Override
	public void refresh(String type) {
		if (StringUtils.equals(type, "refresh")) { // 刷新操作,先清除再添加
			MemeryCacheManager.getCache(Constants_App.CACHE_SYS_USER_DATA).removeAll();
		}

		QueryParameter param = new QueryParameter();
		param.addEquals("deleteState", "n");
		param.setOrderBy("orderBy");
		List<SysUserDataLimit> lists = super.findList(param);
		if (CollectionUtils.isNotEmpty(lists)) {
			for (SysUserDataLimit userdata : lists) {
				MemeryCacheManager.getCache(Constants_App.CACHE_SYS_USER_DATA).put(userdata.getUserId() + "-" + userdata.getResourceId(), userdata.getHqlCondition());
			}
		}
	}

}
