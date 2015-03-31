package com.app.application.crm.service;

import java.util.List;

import com.app.core.service.BaseService;
import com.app.application.crm.entity.Contract;
import com.app.application.crm.entity.ContractPayment;

/**
 * 
 * TODO：合同表
 * 
 * @author zhoufeng
 */
public interface ContractService extends BaseService<Contract> {

	void save(Contract contract, List<ContractPayment> payments);

	void update(Contract contract, List<ContractPayment> payments);

}
