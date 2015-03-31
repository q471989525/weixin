package com.app.application.weix.service.impl;

import com.app.application.oa.processes.hr.dao.LeaveApplyDao;
import com.app.application.weix.dao.OrderDao;
import com.app.application.weix.dao.ProductDao;
import com.app.application.weix.entity.Product;
import com.app.application.weix.entity.ProductOrder;
import com.app.application.weix.service.OrderService;
import com.app.core.service.impl.BaseServiceImpl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by haoya on 3/26/15.
 */
@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<ProductOrder>  implements OrderService {
    LeaveApplyDao leaveApplyDao;
    @Autowired OrderDao orderDao;
//    @Resource(name = "productDao")
//     ProductDao productDao;


    @Override
    public ProductOrder saveOrder(ProductOrder productOrder) {
        return orderDao._save(productOrder);
    }

    @Override
    public List<ProductOrder> queryByUser(String openId) {
        List<ProductOrder> _findPageList = orderDao._findPageList(ProductOrder.class, 0, 15, "openid='"+openId+"'", "createtime desc");
        if(_findPageList==null){
            return null;
        }
//        for (int i = 0; i < _findPageList.size(); i++) {
//            _findPageList.get(i).setProduct(productDao._get(Product.class, _findPageList.get(i).getProduct_id()));
//        }
        return _findPageList;
    }
    
    
}
