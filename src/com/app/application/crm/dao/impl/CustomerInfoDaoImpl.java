package com.app.application.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.app.core.dao.impl.BaseDaoImpl;
import com.app.application.crm.dao.CustomerInfoDao;
import com.app.application.crm.entity.CustomerInfo;

/**
 * 
 * TODO：客户信息
 * 
 * @author zhoufeng
 */
@Repository("customerinfodao")
public class CustomerInfoDaoImpl extends BaseDaoImpl<CustomerInfo> implements CustomerInfoDao {

}
