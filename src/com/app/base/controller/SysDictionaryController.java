package com.app.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysDictionary;
import com.app.base.service.SysDictionaryService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;

/**
 * 
 * TODO：数据字典控制层
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysdictionary")
public class SysDictionaryController extends BaseController {

	private static Logger logger = Logger.getLogger(SysDictionaryController.class);

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
		return "base/dictionary/list_dictionary";
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
	public void exeDataGrid(SysDictionary dictionary, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("deleteState", "n");
		param.addEquals("parentId", "0");
		if (StringUtils.isNotEmpty(dictionary.getDictCode()))
			param.addLike("dictCode", dictionary.getDictCode());
		if (StringUtils.isNotEmpty(dictionary.getDictName()))
			param.addLike("dictName", dictionary.getDictName());
		
		List<SysDictionary> pageList = dictionaryService.findPageList(page, param);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);
		
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 子表方法
	 * 
	 * @param dictionary
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/subgrid.do")
	public void exeSubGrid(String parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysDictionary> list = new ArrayList<SysDictionary>();
		if(StringUtils.isNotEmpty(parentId)){
			QueryParameter param = new QueryParameter();
			param.addEquals("deleteState", "n");
			param.addEquals("parentId", parentId);
			param.setOrderBy("orderBy");
			list = dictionaryService.findList(param);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);

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
		SysDictionary dictionary = new SysDictionary();
		dictionary.setDictCode("D_");
		model.addAttribute("dictionary", dictionary);
		return "base/dictionary/edit_dictionary";
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

		SysDictionary dictionary = null;
		List<SysDictionary> lists = new ArrayList<SysDictionary>();

		if (StringUtils.isNotEmpty(resourceid)) {
			dictionary = dictionaryService.load(resourceid);

			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("deleteState", "n");
			conditionMap.put("parentId", resourceid);
			lists = dictionaryService.findListByMap(conditionMap, "orderBy");
		}
		model.addAttribute("lists", lists);
		model.addAttribute("dictionary", dictionary);
		return "base/dictionary/edit_dictionary";
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
	public void exeSave(SysDictionary dictionary, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		String[] rowIndex = request.getParameterValues("rowIndex");
		List<SysDictionary> lists = new ArrayList<SysDictionary>();
		if (ArrayUtils.isNotEmpty(rowIndex)) {
			for (int i = 0; i < rowIndex.length; i++) {
				String childId = request.getParameter("childId" + rowIndex[i]);
				String itemName = request.getParameter("itemName" + rowIndex[i]);
				String itemValue = request.getParameter("itemValue" + rowIndex[i]);
				String isDefault = request.getParameter("isDefault" + rowIndex[i]);
				String isValid = request.getParameter("isValid" + rowIndex[i]);
				String orderBy = request.getParameter("orderBy" + rowIndex[i]);
				String remark = request.getParameter("remark" + rowIndex[i]);
				lists.add(new SysDictionary(childId, itemName, itemValue, isDefault, isValid, NumberUtils.isNumber(orderBy)?Integer.valueOf(orderBy):99, remark));
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionary.setDeleteState("n");
			if (StringUtils.isEmpty(dictionary.getResourceid())) { // 新增
				dictionaryService.save(dictionary, lists);
			} else {
				dictionaryService.update(dictionary, lists); // 修改
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
				dictionaryService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * ajax验证编码唯一
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/unique.do")
	public void isUnique(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String dictCode = StringUtils.defaultString(request.getParameter("dictCode")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("deleteState", "n");
			para.addEquals("dictCode", dictCode);
			List<SysDictionary> lists = dictionaryService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("deleteState", "n");
			para.addEquals("dictCode", dictCode);
			List<SysDictionary> lists = dictionaryService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		}
		printWriterString(response, state);
	}

	/**
	 * 刷新缓存
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/refresh.do")
	public void exeRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dictionaryService.refresh("update");
			map.put("state", "success");
		} catch (Exception e) {
			map.put("state", "failed");
			e.printStackTrace();
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Resource(name = "sysdictionaryservice")
	SysDictionaryService dictionaryService;

}
