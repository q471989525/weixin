package com.app.application.crm.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.core.service.impl.BaseServiceImpl;
import com.app.application.crm.dao.ContractPaymentDao;
import com.app.application.crm.entity.ContractPayment;
import com.app.application.crm.service.ContractPaymentService;

/**
 * 
 * TODO：合同支付表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("contractPaymentService")
public class ContractPaymentServiceImpl extends BaseServiceImpl<ContractPayment> implements ContractPaymentService {

	ContractPaymentDao contractPaymentDao;

	@Resource(name = "contractPaymentDao")
	public void setContractPaymentDao(ContractPaymentDao contractPaymentDao) {
		this.contractPaymentDao = contractPaymentDao;
		setBaseDao(contractPaymentDao);
	}

	@Override
	public void deleteById(Serializable id) {
		ContractPayment payment = load(id);
		payment.setDeleteFlag("y");
	}

}
