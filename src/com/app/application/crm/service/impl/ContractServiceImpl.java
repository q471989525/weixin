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
import com.app.application.crm.dao.ContractDao;
import com.app.application.crm.entity.Contract;
import com.app.application.crm.entity.ContractPayment;
import com.app.application.crm.service.ContractPaymentService;
import com.app.application.crm.service.ContractService;

/**
 * 
 * TODO：合同表
 * 
 * @author zhoufeng
 */
@Transactional
@Service("contractService")
public class ContractServiceImpl extends BaseServiceImpl<Contract> implements ContractService {

	ContractDao contractDao;

	@Resource(name = "contractDao")
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
		setBaseDao(contractDao);
	}

	@Resource(name = "contractPaymentService")
	ContractPaymentService contractPaymentService;

	@Override
	public void save(Contract contract, List<ContractPayment> payments) {
		save(contract);

		if (CollectionUtils.isNotEmpty(payments)) {
			for (ContractPayment payment : payments) {
				payment.setContractId(contract.getResourceid());
				contractPaymentService.save(payment);
			}
		}

	}

	@Override
	public void update(Contract contract, List<ContractPayment> payments) {
		update(contract);

		if (CollectionUtils.isNotEmpty(payments)) {
			for (ContractPayment payment : payments) {
				payment.setContractId(contract.getResourceid());
				if (StringUtils.isEmpty(payment.getResourceid())) {
					contractPaymentService.save(payment);
				} else {
					contractPaymentService.update(payment);
				}
			}
		}

	}

	/**
	 * 覆盖删除方法
	 */
	@Override
	public void deleteByIds(Serializable[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Serializable id : ids) {
				Contract contract = load(id);
				contract.setDeleteFlag("y");
			}
		}
	}

}
