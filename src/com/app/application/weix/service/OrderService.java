package com.app.application.weix.service;

import com.app.application.weix.entity.ProductOrder;
import com.app.core.service.BaseService;
import java.util.List;

/**
 * Created by haoya on 3/26/15.
 */
public interface OrderService extends BaseService<ProductOrder> {
    public ProductOrder saveOrder(ProductOrder productOrder);
    
    public List<ProductOrder> queryByUser(String openId);
}
