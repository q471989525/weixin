package com.app.application.crm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysDictionary;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.PropertiesUtil;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.CustomerContacts;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.service.CustomerContactsService;
import com.app.application.crm.service.CustomerInfoService;

/**
 * 
 * TODO：客户信息
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/customerinfo")
public class CustomerInfoController extends BaseController {

	private static Logger logger = Logger.getLogger(CustomerInfoController.class);

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
		return "crm/customer/list_customerInfo";
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
	public void exeDataGrid(String menuId, CustomerInfo customer, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(customer.getCustomerName())){
			param.addLike("customerName", customer.getCustomerName());
		}
		if (StringUtils.isNotEmpty(customer.getCustomerNature())) {
			param.addEquals("customerNature", customer.getCustomerNature());
		}
		if (StringUtils.isNotEmpty(customer.getTrustStatus())) {
			param.addEquals("trustStatus", customer.getTrustStatus());
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

		List<CustomerInfo> pageList = customerInfoService.findPageList(page, param);

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

		CustomerInfo customer = new CustomerInfo();
		customer.setCreateId(SpringContextUtil.getUser().getResourceid());
		customer.setCreator(SpringContextUtil.getUser().getUsername());
		customer.setCreateTime(new Date());
		
		model.addAttribute("customer", customer);
		return "crm/customer/edit_customerInfo";
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

		CustomerInfo customer = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			customer = customerInfoService.load(resourceid);
			
			List<CustomerContacts> contacts = customerContactsService.findListByHql("from CustomerContacts where deleteFlag=? and customerId=? order by createTime desc", "n", resourceid);
			model.addAttribute("contacts", contacts);
		}

		model.addAttribute("customer", customer);
		return "crm/customer/edit_customerInfo";
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
	public void exeSave(CustomerInfo customer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			customer.setDeleteFlag("n");
			if (StringUtils.isEmpty(customer.getResourceid())) { // 新增
				customerInfoService.save(customer);
			} else {
				customerInfoService.update(customer); // 修改
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
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				customerInfoService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 选择页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select.do")
	public String exeSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("select...........");
		return "crm/customer/select_customerInfo";
	}

	/**
	 * 客户首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index.do")
	public String exeIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysDictionary> sourceType = DictionaryELTag.getList("D_Customer_Source");
		String type = StringUtils.defaultString(request.getParameter("type"));

		String menuId = PropertiesUtil.instance().get("CUSTOMERINFO_ID");
		;

		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(type) && !type.equals("all"))
			param.addEquals("customerSource", type);

		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");
		param.setOrderBy(" createTime desc");

		List<CustomerInfo> list = customerInfoService.findList(param);

		request.setAttribute("list", list);
		request.setAttribute("typeflag", type);
		request.setAttribute("sourceType", sourceType);
		return "crm/customer/index_customerInfo";
	}

	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;
	
	@Resource(name = "customerContactsService")
	CustomerContactsService customerContactsService;

}
