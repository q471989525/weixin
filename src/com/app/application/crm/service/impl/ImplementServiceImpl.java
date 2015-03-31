package com.app.application.crm.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ImplementDao;
import com.app.application.crm.entity.Implement;
import com.app.application.crm.entity.ImplementProduct;
import com.app.application.crm.service.ImplementProductService;
import com.app.application.crm.service.ImplementService;

/**
 * 
 * TODO：实施
 * 
 * @author zhoufeng
 */
@Transactional
@Service("implementService")
public class ImplementServiceImpl extends BaseServiceImpl<Implement> implements ImplementService {

	ImplementDao implementDao;

	@Resource(name = "implementDao")
	public void setImplementDao(ImplementDao implementDao) {
		this.implementDao = implementDao;
		setBaseDao(implementDao);
	}

	@Resource(name = "implementProductService")
	ImplementProductService implementProductService;

	/**
	 * 覆盖删除方法
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				Implement implement = load(id);
				implement.setDeleteFlag("y");
			}
		}
	}

	@Override
	public void save(Implement implement, List<ImplementProduct> products) {
		save(implement);

		if (CollectionUtils.isNotEmpty(products)) {
			for (ImplementProduct product : products) {
				product.setImplementId(implement.getResourceid());
				implementProductService.save(product);
			}
		}
	}

	@Override
	public void update(Implement implement, List<ImplementProduct> products) {
		update(implement);

		if (CollectionUtils.isNotEmpty(products)) {
			for (ImplementProduct product : products) {
				product.setImplementId(implement.getResourceid());
				if (StringUtils.isEmpty(product.getResourceid())) {
					implementProductService.save(product);
				} else {
					implementProductService.update(product);
				}
			}
		}
	}

}
