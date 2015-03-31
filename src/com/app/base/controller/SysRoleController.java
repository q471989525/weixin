package com.app.base.controller;

import java.util.ArrayList;
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
import com.app.base.entity.SysResource;
import com.app.base.entity.SysRole;
import com.app.base.service.SysResourceService;
import com.app.base.service.SysRoleService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：系统角色
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysrole")
public class SysRoleController extends BaseController {

	private static Logger logger = Logger.getLogger(SysRoleController.class);

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
		return "base/role/list_role";
	}

	/**
	 * 列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(SysRole role, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(role.getRoleName()))
			param.addLike("roleName", role.getRoleName());
		if (StringUtils.isNotEmpty(role.getRoleCode()))
			param.addLike("roleCode", role.getRoleCode());

		param.addEquals("deleteState", "n");
		List<SysRole> pageList = sysRoleService.findPageList(page, param);

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

		SysRole role = new SysRole();
		role.setRoleCode("R_");

		model.addAttribute("role", role);
		return "base/role/edit_role";
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

		SysRole role = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			role = sysRoleService.load(resourceid);
		}

		model.addAttribute("role", role);
		return "base/role/edit_role";
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
	public void exeSave(SysRole role, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			role.setDeleteState("n");
			if (StringUtils.isEmpty(role.getResourceid())) { // 新增
				sysRoleService.save(role);
			} else {
				sysRoleService.update(role); // 修改
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
				sysRoleService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * ajax验证角色编码唯一
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/unique.do")
	public void isUnique(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String roleCode = StringUtils.defaultString(request.getParameter("roleCode")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("deleteState", "n");
			para.addEquals("roleCode", roleCode);
			List<SysRole> lists = sysRoleService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("deleteState", "n");
			para.addEquals("roleCode", roleCode);
			List<SysRole> lists = sysRoleService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		}
		printWriterString(response, state);
	}

	/**
	 * 查询全部角色,用于公共选择页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/roleAll.do")
	public void exeRoleAll(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("deleteState", "n");
		param.setOrderBy("orderBy");

		List<SysRole> roleList = sysRoleService.findList(param);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", roleList.size());
		map.put("rows", roleList);

		String jsonString = JSON.toJSONString(map);
		printWriterStringToJson(response, jsonString);
	}

	/**
	 * 角色分配资源页面跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resourceList.do")
	public String exeResourceList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String roleId = request.getParameter("roleId");

		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'1','3'");
		param.addEquals("deleteState", "n");
		param.setOrderBy("orderBy");
		List<SysResource> resourceList = sysResourceService.findList(param); // 查询所有资源

		// 查询已分配资源
		SysRole role = null;
		List<String> rsIds = new ArrayList<String>();
		if (StringUtils.isNotEmpty(roleId)) {
			role = sysRoleService.load(roleId); // 查询角色

			rsIds = sysRoleService.getResourceByRoleId(roleId); // 查询角色已分配资源ID集合
		}

		// 组装ztree对象
		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>();
		if (CollectionUtils.isNotEmpty(resourceList)) {
			for (SysResource rs : resourceList) {
				ZtreeObject ztree = new ZtreeObject(rs.getResourceid(), rs.getParentId(), rs.getMenuName());
				if (rsIds.contains(rs.getResourceid()))
					ztree.setChecked(true); // 已经受权，默认勾选中
				ztreeList.add(ztree);
			}
		}
		String ztreeString = JSON.toJSONString(ztreeList); // 转化为ztree对象

		request.setAttribute("role", role);
		request.setAttribute("ztreeString", ztreeString);
		return "base/role/role_resource";
	}

	/**
	 * 保存角色资源关系
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveRoleResource.do")
	public void exeSaveRoleResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roleid = request.getParameter("roleid");
		String resourceids = request.getParameter("resourceids");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(roleid) && StringUtils.isNotEmpty(resourceids)) {
				sysRoleService.saveRoleResource(roleid, resourceids);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
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
			sysResourceService.init("refresh");
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 通过角色查看用户列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/userlist.do")
	public String exeUserList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		return "base/role/role_user";
	}
	
	/**
	 * 角色列表
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/select.do")
	public String exeSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		return "base/role/select_role";
	}

	@Resource(name = "sysroleservice")
	SysRoleService sysRoleService;

	@Resource(name = "sysresourceservice")
	SysResourceService sysResourceService;

}
