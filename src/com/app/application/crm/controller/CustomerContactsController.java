package com.app.application.crm.controller;

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
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.CustomerContacts;
import com.app.application.crm.entity.CustomerInfo;
import com.app.application.crm.service.CustomerContactsService;
import com.app.application.crm.service.CustomerInfoService;

/**
 * 
 * TODO：客户联系人
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/customerContacts")
public class CustomerContactsController extends BaseController {

	private static Logger logger = Logger.getLogger(CustomerContactsController.class);

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
		return "crm/customer/list_customerContacts";
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
	public void exeDataGrid(String menuId, CustomerContacts contacts, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(contacts.getCustomerName())) {
			List<CustomerInfo> customers = customerInfoService.findListByHql("from CustomerInfo where deleteFlag='n' and customerName like ?", "%"+contacts.getCustomerName()+"%");
			if(CollectionUtils.isNotEmpty(customers)){
				List<String> idsList = new ArrayList<String>();
				for (CustomerInfo info : customers) {
					idsList.add(info.getResourceid());
				}
				param.addIn("customerId", "'"+StringUtils.join(idsList, "','")+"'");
			}else {
				param.addIn("customerId", "'null'");
			}
		}
		if(StringUtils.isNotEmpty(contacts.getCustomerId())){
			param.addEquals("customerId", contacts.getCustomerId());
		}
		if (StringUtils.isNotEmpty(contacts.getUserName())) {
			param.addLike("userName", contacts.getUserName());
		}
		if (StringUtils.isNotEmpty(contacts.getMobile())) {
			String sql = "(mobile like '%" + contacts.getMobile() + "%' or telphone like '%" + contacts.getMobile() + "%')";
			param.addHql(sql);
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

		List<CustomerContacts> pageList = customerContactsService.findPageList(page, param);
		
		for (CustomerContacts contacts2 : pageList) {
			CustomerInfo customer = customerInfoService.load(contacts2.getCustomerId());
			contacts2.setCustomerName(customer.getCustomerName());
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

		CustomerContacts contacts = new CustomerContacts();
		contacts.setCreateId(SpringContextUtil.getUser().getResourceid());
		contacts.setCreator(SpringContextUtil.getUser().getUsername());
		contacts.setCreateTime(new Date());

		model.addAttribute("contacts", contacts);
		return "crm/customer/edit_customerContacts";
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

		CustomerContacts contacts = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			contacts = customerContactsService.load(resourceid);
			
			CustomerInfo customer = customerInfoService.load(contacts.getCustomerId());
			contacts.setCustomerName(customer.getCustomerName());
		}

		model.addAttribute("contacts", contacts);
		return "crm/customer/edit_customerContacts";
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
	public void exeSave(CustomerContacts contacts, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			contacts.setDeleteFlag("n");
			if (StringUtils.isEmpty(contacts.getResourceid())) { // 新增
				customerContactsService.save(contacts);
			} else {
				customerContactsService.update(contacts); // 修改
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
				customerContactsService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}
	
	/**
	 * 弹窗
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/select.do")
	public String exeSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String customerId = request.getParameter("customerId");
		model.addAttribute("customerId", customerId);
		return "crm/customer/select_customerContacts";
	}
	

	@Resource(name = "customerContactsService")
	CustomerContactsService customerContactsService;
	
	@Resource(name = "customerinfoservice")
	CustomerInfoService customerInfoService;
}
