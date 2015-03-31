package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.CustomerInfoDao;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.service.CustomerInfoService;

/**
 * 
 * TODO：客户信息
 * 
 * @author zhoufeng
 */
@Transactional
@Service("customerinfoservice")
public class CustomerInfoServiceImpl extends BaseServiceImpl<CustomerInfo> implements CustomerInfoService {

	CustomerInfoDao customerInfoDao;

	@Resource(name = "customerinfodao")
	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
		super.setBaseDao(customerInfoDao);
	}

	/**
	 * 覆盖删除方法,只更新删除标识位
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		for (Serializable id : ids) {
			CustomerInfo customer = load(id);
			customer.setDeleteFlag("y");
		}
	}

}
