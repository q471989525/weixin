package com.app.application.crm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysUser;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.Contract;
import com.app.application.crm.entity.ContractPayment;
import com.app.application.crm.service.ContractPaymentService;
import com.app.application.crm.service.ContractService;

/**
 * 
 * TODO：合同收款
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/contractPayment")
public class ContractPaymentController extends BaseController {

	private static Logger logger = Logger.getLogger(ContractPaymentController.class);

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	public String exeList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		return "crm/payment/list_contractPayment";
	}

	/**
	 * 列表方法
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(String menuId, ContractPayment contractPayment, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(contractPayment.getContractNo())){
			 List<Contract> contracts = contractService.findListByHql("from Contract where deleteFlag='n' and contractNo like ?", "%"+contractPayment.getContractNo()+"%");
			 if(CollectionUtils.isNotEmpty(contracts)){
				 List<String> contractIds = new ArrayList<String>();
				for (Contract contract : contracts) {
					contractIds.add(contract.getResourceid());
				}
				param.addIn("contractId", "'"+StringUtils.join(contractIds, "','")+"'");
			 }else {
				param.addEquals("contractId", "");
			}
		}

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<ContractPayment> pageList = contractPaymentService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (ContractPayment payment : pageList) {
				Contract contract = contractService.load(payment.getContractId());
				payment.setContractNo(contract.getContractNo());
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 编辑页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit.do")
	public String exeEdit(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("edit...........");
		String resourceid = request.getParameter("resourceid");

		ContractPayment payment = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			payment = contractPaymentService.load(resourceid);
			
			Contract contract = contractService.load(payment.getContractId());
			payment.setContractNo(contract.getContractNo());
		}

		model.addAttribute("payment", payment);
		return "crm/payment/edit_contractPayment";
	}

	/**
	 * 保存页面
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void exeSave(ContractPayment payment, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser user = SpringContextUtil.getUser();
			if (StringUtils.isNotEmpty(payment.getResourceid())) { // 修改
				//待修改
				ContractPayment oldPayment = contractPaymentService.load(payment.getResourceid());
				
				if(null != payment.getActualAmount() && payment.getActualAmount()>0){
					if(payment.getActualAmount().compareTo(oldPayment.getAmount()) > 0){ //付款大于待付款金额
						Double subTract = BigDecimal.valueOf(payment.getActualAmount()).subtract(BigDecimal.valueOf(oldPayment.getAmount())).doubleValue();
						oldPayment.setRemark("本期多付款："+subTract+"元");
					}else if(payment.getActualAmount().compareTo(oldPayment.getAmount()) < 0){ //付款小于待付款金额
						Double subTract = BigDecimal.valueOf(oldPayment.getAmount()).subtract(BigDecimal.valueOf(payment.getActualAmount())).doubleValue();
						oldPayment.setRemark("本期还剩下："+subTract+"元未付");
					}
					oldPayment.setActualAmount(payment.getActualAmount());
					oldPayment.setPayFlag("y");
					oldPayment.setUpdateId(user.getResourceid());
					oldPayment.setUpdateName(user.getUsername());
					oldPayment.setUpdateTime(new Date());
					contractPaymentService.update(oldPayment);
				}
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "contractPaymentService")
	ContractPaymentService contractPaymentService;

	@Resource(name = "contractService")
	ContractService contractService;

}
