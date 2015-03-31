package com.app.application.weix.dao.impl;

import com.app.application.weix.dao.ProductDao;
import com.app.application.weix.entity.Product;
import com.app.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by haoya on 3/26/15.
 */

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao{

}
