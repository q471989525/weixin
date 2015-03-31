package com.app.core.init;

import org.apache.log4j.Logger;

import com.app.base.service.SysDictionaryService;
import com.app.base.service.SysResourceService;
import com.app.base.service.SysUserDataLimitService;

/**
 * 
 * TODO：初始化系统各种数据
 * 
 * @author zhoufeng
 */
public class InitSystemDate {

	private static Logger logger = Logger.getLogger(InitSystemDate.class);

	SysDictionaryService sysdictionaryservice;

	SysResourceService sysResourceService;

	SysUserDataLimitService sysUserDataLimitService;

	public void init() {
		logger.info("数据字典加载中......");
		sysdictionaryservice.refresh("init");
		logger.info("数据字典加载完毕......");

		logger.info("资源角色关系加载中......");
		sysResourceService.init("init");
		logger.info("资源角色关系完毕......");

		logger.info("用户数据权限加载中......");
		sysUserDataLimitService.refresh("init");
		logger.info("用户数据权限完毕......");
	}

	public void setSysdictionaryservice(SysDictionaryService sysdictionaryservice) {
		this.sysdictionaryservice = sysdictionaryservice;
	}

	public void setSysResourceService(SysResourceService sysResourceService) {
		this.sysResourceService = sysResourceService;
	}

	public void setSysUserDataLimitService(SysUserDataLimitService sysUserDataLimitService) {
		this.sysUserDataLimitService = sysUserDataLimitService;
	}

}
