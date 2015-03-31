package com.app.application.weix.service.impl;

import com.app.application.weix.dao.ProductDao;
import com.app.application.weix.entity.Product;
import com.app.application.weix.service.ProductServer;
import com.app.core.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fuq
 */
@Repository("productServer")
public class ProductServerImpl extends BaseServiceImpl<Product> implements ProductServer {

    private ProductDao productDao;

    /**
     * @param productDao the productDao to set
     */
    @Resource(name = "productDao")
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
        super.setBaseDao(productDao);
    }

}
