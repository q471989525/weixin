package com.app.base.service;

import com.app.base.entity.SysUserDataLimit;
import com.app.core.service.BaseService;

/**
 * 
 * TODO：用户数据权限表
 * 
 * @author zhoufeng
 */
public interface SysUserDataLimitService extends BaseService<SysUserDataLimit> {

	void refresh(String type);

}
