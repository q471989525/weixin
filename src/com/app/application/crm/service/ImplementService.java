package com.app.application.crm.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.application.crm.entity.Implement;
import com.app.application.crm.entity.ImplementProduct;

/**
 * 
 * TODO：实施
 * 
 * @author zhoufeng
 */
public interface ImplementService extends BaseService<Implement> {

	void save(Implement implement, List<ImplementProduct> products);

	void update(Implement implement, List<ImplementProduct> products);

}
