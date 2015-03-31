package com.app.application.crm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.PubAttachment;
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.DateTimeUtil;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.Contract;
import com.app.application.crm.entity.ContractPayment;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.entity.ProjectInfo;
import com.app.application.crm.service.ContractPaymentService;
import com.app.application.crm.service.ContractService;
import com.app.application.crm.service.CustomerInfoService;
import com.app.application.crm.service.ProjectInfoService;

/**
 * 
 * TODO：合同管理
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/contract")
public class ContractController extends BaseController {

	private static Logger logger = Logger.getLogger(ContractController.class);

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
		return "crm/contract/list_contract";
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
	public void exeDataGrid(String menuId, Contract contract, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		// 查询客户
		if (StringUtils.isNotEmpty(contract.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%" + contract.getCustomerName() + "%");
			if (CollectionUtils.isNotEmpty(customers)) {
				List<String> idsList = new ArrayList<String>();
				for (CustomerInfo info : customers) {
					idsList.add(info.getResourceid());
				}

				List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and customerId in ('" + StringUtils.join(idsList, "','") + "')");
				if (CollectionUtils.isNotEmpty(projectInfos)) {
					List<String> idsList2 = new ArrayList<String>();
					for (ProjectInfo info : projectInfos) {
						idsList2.add(info.getResourceid());
					}
					param.addIn("projectId", "'" + StringUtils.join(idsList2, "','") + "'");
				} else {
					param.addIn("projectId", "'null'");
				}
			} else {
				param.addIn("projectId", "'null'");
			}
		}
		// 查询项目
		if (StringUtils.isNotEmpty(contract.getProjectName())) {
			List<ProjectInfo> projectInfos = projectInfoService.findListByHql("from ProjectInfo where deleteFlag='n' and projectName like ?", "%" + contract.getProjectName() + "%");
			if (CollectionUtils.isNotEmpty(projectInfos)) {
				List<String> idsList = new ArrayList<String>();
				for (ProjectInfo info : projectInfos) {
					idsList.add(info.getResourceid());
				}
				param.addIn("projectId", "'" + StringUtils.join(idsList, "','") + "'");
			} else {
				param.addIn("projectId", "'null'");
			}
		}
		if (StringUtils.isNotEmpty(contract.getContractNo()))
			param.addLike("contractNo", contract.getContractNo());

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<Contract> pageList = contractService.findPageList(page, param);

		if (CollectionUtils.isNotEmpty(pageList)) {
			for (Contract c : pageList) {
				ProjectInfo info = projectInfoService.load(c.getProjectId());
				if (null != info) {
					c.setProjectName(info.getProjectName());

					CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
					c.setCustomerName(customerInfo.getCustomerName());
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 新增页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	public String exeAdd(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("add...........");

		SysUser user = SpringContextUtil.getUser();
		Contract contract = new Contract();
		contract.setCreateId(user.getResourceid());
		contract.setCreator(user.getUsername());
		contract.setCreateTime(new Date());
		contract.setDeleteFlag("n");
		contract.setAttachmentId(UUID.randomUUID().toString());

		model.addAttribute("user", user);
		model.addAttribute("contract", contract);
		return "crm/contract/edit_contract";
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

		Contract contract = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			contract = contractService.load(resourceid);

			ProjectInfo info = projectInfoService.load(contract.getProjectId());
			if (null != info) {
				contract.setProjectName(info.getProjectName());

				CustomerInfo customerInfo = customerInfoService.load(info.getCustomerId());
				contract.setCustomerName(customerInfo.getCustomerName());
			}

			List<ContractPayment> payments = contractPaymentService.findListByHql("from ContractPayment where deleteFlag='n' and contractId=? order by payDate", contract.getResourceid());
			model.addAttribute("payments", payments);

			// 附件
			List<PubAttachment> attList = pubAttachmentService.getAttachmentsByUuid(contract.getAttachmentId());
			model.addAttribute("attList", attList);
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("contract", contract);
		return "crm/contract/edit_contract";
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
	public void exeSave(Contract contract, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser user = SpringContextUtil.getUser();

			// 合同付款
			List<ContractPayment> payments = new ArrayList<ContractPayment>();
			String[] payIds = request.getParameterValues("payId");
			if (ArrayUtils.isNotEmpty(payIds)) {
				for (int i = 0; i < payIds.length; i++) {
					String periods = request.getParameter("periods" + i);
					String amount = request.getParameter("amount" + i);
					String payDate = request.getParameter("payDate" + i);
					if (StringUtils.isEmpty(payIds[i])) {
						payments.add(new ContractPayment(periods, amount, payDate, user.getResourceid(), user.getUsername()));
					} else {
						ContractPayment payment = contractPaymentService.load(payIds[i]);
						payment.setPeriods(periods);
						if (NumberUtils.isNumber(amount))
							payment.setAmount(Double.valueOf(amount));
						payment.setPayDate(DateTimeUtil.parseDate(payDate));
						// payment.setUpdateTime(new Date());
						payments.add(payment);
					}
				}
			}

			if (!StringUtils.equals(contract.getMarginFlag(), "y")) {
				contract.setMarginRatio("");
				contract.setMarginAmount(null);
				contract.setMarginReceive(null);
			}
			if (StringUtils.isEmpty(contract.getResourceid())) { // 新增
				contractService.save(contract, payments);
			} else {
				contract.setUpdateTime(new Date());
				contractService.update(contract, payments); // 修改
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete.do")
	public void exeDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ids");
		String payId = request.getParameter("payId");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				contractService.deleteByIds(id.split(","));
			}
			if (StringUtils.isNotBlank(payId)) {
				contractPaymentService.deleteById(payId);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "contractService")
	ContractService contractService;

	@Resource(name = "contractPaymentService")
	ContractPaymentService contractPaymentService;

	@Resource(name = "projectInfoService")
	ProjectInfoService projectInfoService;

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;

	@Autowired
	PubAttachmentService pubAttachmentService;

}
