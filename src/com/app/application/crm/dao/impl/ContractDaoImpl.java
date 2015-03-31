package com.app.application.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.app.core.dao.impl.BaseDaoImpl;
import com.app.application.crm.dao.ContractDao;
import com.app.application.crm.entity.Contract;

/**
 * 
 * TODO：合同
 * 
 * @author zhoufeng
 */
@Repository("contractDao")
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao {

}
