package com.app.base.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysResource;
import com.app.base.entity.SysRole;
import com.app.base.service.SysResourceService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：系统资源
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysresource")
public class SysResourceController extends BaseController {

	private static Logger logger = Logger.getLogger(SysResourceController.class);

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
		return "base/resource/list_resource";
	}

	/**
	 * 列表方法，菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(SysResource resource, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		String query = "";
		if (StringUtils.isNotEmpty(resource.getMenuName())) {
			String hql = "(menuName like '%" + resource.getMenuName() + "%' or menuUrl like '%" + resource.getMenuName() + "%')";
			param.addHql(hql);
			query = "y";
		}
		if (StringUtils.isNotEmpty(resource.getMenuCode())) {
			param.addLike("menuCode", resource.getMenuCode());
			query = "y";
		}
		param.addEquals("deleteState", "n");
		param.addEquals("menuType", "1"); // 菜单
		param.setOrderBy("orderBy");

		List<SysResource> pageList = sysResourceService.findList(param);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageList.size());
		map.put("rows", pageList);

		String jsonString = JSON.toJSONString(map);
		if (StringUtils.isEmpty(query))
			jsonString = jsonString.replaceAll("\"parentId\"", "\"_parentId\""); // treegrid，父ID定义
		printWriterStringToJson(response, jsonString);
	}

	/**
	 * 子列表方法,按钮
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/subgrid.do")
	public void exeSubGrid(String parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysResource> pageList = new ArrayList<SysResource>();
		
		if(StringUtils.isNotEmpty(parentId)){
			QueryParameter param = new QueryParameter();
			param.addEquals("parentId", parentId);
			param.addEquals("deleteState", "n");
			param.addEquals("menuType", "2"); // 按钮
			param.setOrderBy("orderBy");
			pageList = sysResourceService.findList(param);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageList.size());
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
		String parentId = request.getParameter("parentId"); // 父ID
		String parentName = request.getParameter("parentName");

		SysResource resource = new SysResource();
		resource.setMenuType("1"); // 1:菜单,2:按钮
		if (StringUtils.isNotEmpty(parentId)) resource.setParentId(parentId);
		if (StringUtils.isNotEmpty(parentName)) resource.setParentName(URLDecoder.decode(parentName, "UTF-8"));

		model.addAttribute("resource", resource);
		return "base/resource/edit_resource";
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

		SysResource resource = null;
		List<SysResource> butList = new ArrayList<SysResource>();

		if (StringUtils.isNotEmpty(resourceid)) {
			resource = sysResourceService.load(resourceid);

			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("menuType", "2");
			conditionMap.put("deleteState", "n");
			conditionMap.put("parentId", resourceid);
			butList = sysResourceService.findListByMap(conditionMap, "orderBy");
		}

		model.addAttribute("resource", resource);
		model.addAttribute("butList", butList);
		return "base/resource/edit_resource";
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
	public void exeSave(SysResource resource, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SysResource> buttonList = new ArrayList<SysResource>();
			String[] rowIndex = request.getParameterValues("rowIndex");
			if (ArrayUtils.isNotEmpty(rowIndex)) {
				for (int i = 0; i < rowIndex.length; i++) {
					String btnId = request.getParameter("btnId" + rowIndex[i]);
					String btnName = request.getParameter("btnName" + rowIndex[i]);
					String btnCode = request.getParameter("btnCode" + rowIndex[i]);
					String btnUrl = request.getParameter("btnUrl" + rowIndex[i]);
					String btnOrder = request.getParameter("btnOrder" + rowIndex[i]);
					String btnRemark = request.getParameter("btnRemark" + rowIndex[i]);
					buttonList.add(new SysResource(btnId, btnName, btnCode, btnUrl, btnOrder, btnRemark));
				}
			}

			// 资源父节点
			if (StringUtils.isNotEmpty(resource.getParentId())) {
				SysResource parentRes = sysResourceService.load(resource.getParentId());
				if (StringUtils.isEmpty(parentRes.getParentId())) {
					resource.setParentIds(parentRes.getResourceid());
					resource.setParentNames(parentRes.getMenuName());
				} else {
					resource.setParentIds(parentRes.getParentIds() + "," + parentRes.getResourceid());
					resource.setParentNames(parentRes.getParentNames() + "," + parentRes.getMenuName());
				}
			}

			resource.setDeleteState("n");
			if (StringUtils.isEmpty(resource.getResourceid())) { // 新增
				sysResourceService.save(resource, buttonList);
			} else {
				sysResourceService.update(resource, buttonList); // 修改
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
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				sysResourceService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * ajax验证菜单编码唯一
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/unique.do")
	public void isUnique(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String menuCode = StringUtils.defaultString(request.getParameter("menuCode")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		para.addEquals("menuType", "1");
		para.addEquals("deleteState", "n");
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("menuCode", menuCode);
			List<SysResource> lists = sysResourceService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("menuCode", menuCode);
			List<SysResource> lists = sysResourceService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		}
		printWriterString(response, state);
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
		return "base/resource/select_resource";
	}

	/**
	 * treeGrid列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/treegrid.do")
	public void exeTreeGrid(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("menuType", "1"); // 菜单
		param.addEquals("stateFlag", "1"); // 有效
		param.addEquals("deleteState", "n");
		param.setOrderBy("orderBy");
		
		List<SysResource> pageList = sysResourceService.findList(param);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageList.size());
		map.put("rows", pageList);

		String jsonString = JSON.toJSONString(map);
		jsonString = jsonString.replaceAll("\"parentId\"", "\"_parentId\""); // treegrid，父ID定义
		printWriterStringToJson(response, jsonString);
	}

	/**
	 * 树型页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rolelist.do")
	public String exeRoleList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("select...........");
		return "base/resource/resource_role";
	}

	/**
	 * 通过资源id查询分配的角色
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/roleGrid.do")
	public void exeRoleGrid(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String resourceid = request.getParameter("resourceid");
		List<SysRole> roles = new ArrayList<SysRole>();
		
		if (StringUtils.isNotEmpty(resourceid)) {
			roles = sysResourceService.findRolesByResourceid(resourceid);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", roles.size());
		map.put("rows", roles);
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 首页父菜单，查找用户拥有权限的子菜单
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findChildMenuById.do")
	public void exeFindChildMenuById(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String parentId = request.getParameter("parentId");

		Map<String, Object> map = new HashMap<String, Object>();
		String ztreeString = "";

		if (StringUtils.isNotEmpty(parentId)) {
			List<ZtreeObject> ztreeList = null;
			
			HttpSession session = request.getSession(false);
			if(null != session){
				Object ztreeTempList = WebUtils.getSessionAttribute(request, parentId); //从session中获取
				if(null == ztreeTempList){
					ztreeList = sysResourceService.findListByUser(parentId, SpringContextUtil.getUser());
					WebUtils.setSessionAttribute(request, parentId, ztreeList); //放入session中
				}else{
					ztreeList = (List<ZtreeObject>) ztreeTempList;
				}
			}
			
			if (CollectionUtils.isNotEmpty(ztreeList))
				ztreeString = JSON.toJSONString(ztreeList); // 转化为ztree对象
			map.put("state", "success");
		} else {
			map.put("state", "failed");
		}
		map.put("ztreeJson", ztreeString);
		String jsonString = JSON.toJSONString(map);
		printWriterStringToJson(response, jsonString);
	}

	@Resource(name = "sysresourceservice")
	SysResourceService sysResourceService;

}
