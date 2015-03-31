package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ImplementProductDao;
import com.app.application.crm.entity.ImplementProduct;
import com.app.application.crm.service.ImplementProductService;

/**
 * 
 * TODO：实施产品
 * 
 * @author zhoufeng
 */
@Transactional
@Service("implementProductService")
public class ImplementProductServiceImpl extends BaseServiceImpl<ImplementProduct> implements ImplementProductService {

	ImplementProductDao implementProductDao;

	@Resource(name = "implementProductDao")
	public void setImplementProductDao(ImplementProductDao implementProductDao) {
		this.implementProductDao = implementProductDao;
		setBaseDao(implementProductDao);
	}

	@Override
	public void deleteById(Serializable id) {
		ImplementProduct product = load(id);
		product.setDeleteFlag("y");
	}

}
