package com.app.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.app.base.dao.SysUserDao;
import com.app.base.entity.SysUser;
import com.app.core.dao.impl.BaseDaoImpl;

/**
 * 
 * TODO：系统用户Dao实现
 * 
 * @author zhoufeng
 */
@Repository("sysuserdao")
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

}
