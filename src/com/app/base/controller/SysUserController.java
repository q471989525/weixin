package com.app.base.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysOrganization;
import com.app.base.entity.SysRole;
import com.app.base.entity.SysUser;
import com.app.base.service.SysOrganizationService;
import com.app.base.service.SysRoleService;
import com.app.base.service.SysUserService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.PropertiesUtil;
import com.app.utils.SecurityUtils;
import com.app.utils.SpringContextUtil;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：系统用户
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysuser")
public class SysUserController extends BaseController {

	private static Logger logger = Logger.getLogger(SysUserController.class);

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
		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'y','h'"); // 包含有效和隐藏
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");
		List<SysOrganization> list = sysOrganizationService.findList(param);

		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>();

		// 组装ztree树对象
		if (CollectionUtils.isNotEmpty(list)) {
			String orgCode = PropertiesUtil.instance().get("ROOT_ORG_CODE");
			for (SysOrganization org : list) {
				ZtreeObject ztree = new ZtreeObject(org.getResourceid(), org.getParentId(), org.getOrgName());
				if (StringUtils.equals(org.getOrgCode(), orgCode)) ztree.setOpen(true); // root节点打开
				ztreeList.add(ztree);
			}
		}

		String ztreeOrgJson = JSON.toJSONString(ztreeList); // 转化为ztree对象
		request.setAttribute("ztreeOrgJson", ztreeOrgJson);
		return "base/user/list_user";
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
	public void exeDataGrid(SysUser user, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(user.getUsername())) param.addHql("(username like '%" + user.getUsername() + "%' or loginId like '%" + user.getUsername() + "%')");
		if (StringUtils.isNotEmpty(user.getMobile())) param.addLike("mobile", user.getMobile());
		if (StringUtils.isNotEmpty(user.getStateFlag())) param.addEquals("stateFlag", user.getStateFlag());
		if (StringUtils.isNotEmpty(user.getShowState())) param.addEquals("showState", user.getShowState());
		if (StringUtils.isNotEmpty(user.getIsEnabled())) param.addEquals("isEnabled", user.getIsEnabled());
		if (StringUtils.isNotEmpty(user.getOrgId())) // 通过组织ID查找用户
			param.addHql("resourceid in (SELECT ou.userId FROM SysOrgUser ou where ou.orgId='" + user.getOrgId() + "')");
		if (StringUtils.isNotEmpty(user.getRoleId())) // 通过角色ID查找用户
			param.addHql("resourceid in (SELECT ur.userId FROM SysUserRole ur where ur.roleId='" + user.getRoleId() + "')");

		param.addEquals("deleteState", "n");

		if (StringUtils.equalsIgnoreCase(page.getSort(), "joinDateFmt")) page.setSort("joinDate");
		if (StringUtils.equalsIgnoreCase(page.getSort(), "birthdayFmt")) page.setSort("birthday");

		List<SysUser> pageList = new ArrayList<SysUser>();
		pageList = sysUserService.findPageList(page, param);

		for (SysUser sysUser : pageList) {
			SysOrganization organization = sysOrganizationService.getOrgByUserid(sysUser.getResourceid());
			if (null != organization)
				sysUser.setOrgName(organization.getOrgName());
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
		String orgId = request.getParameter("orgId"); // 组织ID
		String orgName = request.getParameter("orgName");

		SysUser user = new SysUser();
		user.setDeleteState("n");
		if (StringUtils.isNotEmpty(orgId)) user.setOrgId(orgId);
		if (StringUtils.isNotEmpty(orgName)) user.setOrgName(URLDecoder.decode(orgName, "UTF-8"));

		String employeeId = sysUserService.findIncrementEmployeeId(); // 员工编号
		user.setEmployeeId(employeeId);
		String pwd = PropertiesUtil.instance().get("DEFAULT_PASSWORD"); // 默认密码
		user.setPassword(pwd);

		model.addAttribute("user", user);
		return "base/user/edit_user";
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

		SysUser user = null;
		SysOrganization organization = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			user = sysUserService.load(resourceid);

			organization = sysOrganizationService.getOrgByUserid(resourceid);
			if (null != organization) {
				user.setOrgId(organization.getResourceid());
				user.setOrgName(organization.getOrgName());
			}
		}

		model.addAttribute("user", user);
		return "base/user/edit_user";
	}

	/**
	 * 保存页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save.do")
	public void exeSave(SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");
		String orgId = request.getParameter("orgId"); // 组织id

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(user.getResourceid())) { // 新增
				user.setPassword(SecurityUtils.getMD5(user.getPassword()));

				String employeeId = sysUserService.findIncrementEmployeeId();
				user.setEmployeeId(employeeId);

				sysUserService.save(user, orgId);
			} else {
				sysUserService.update(user, orgId); // 修改
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
				sysUserService.deleteByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 重置为默认密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/reSetPwd.do")
	public void exeReSetPwd(String resourceid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("savePwd...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(resourceid)) {
				SysUser currentUser = sysUserService.load(resourceid);
				String pwd = PropertiesUtil.instance().get("DEFAULT_PASSWORD"); //默认密码
				currentUser.setPassword(SecurityUtils.getMD5(pwd));
				sysUserService.update(currentUser); // 修改密码
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPwd.do")
	public String exeModifyPwd(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("修改密码...........");
		SysUser user = SpringContextUtil.getUser();
		model.addAttribute("user", user);
		return "base/user/reset_user_pwd";
	}

	/**
	 * 保存修改密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/savePwd.do")
	public void exeSavePwd(SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("savePwd...........");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		String newpassword2 = request.getParameter("newpassword2");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (StringUtils.equals(newpassword, newpassword2)) { // 再次输入密码相同
				if (StringUtils.isNotEmpty(user.getResourceid())) {
					SysUser currentUser = sysUserService.load(user.getResourceid());
					if (StringUtils.equals(SecurityUtils.getMD5(password), currentUser.getPassword())) { // 判断原来密码相同
						currentUser.setPassword(SecurityUtils.getMD5(newpassword));
						sysUserService.update(currentUser); // 修改密码
						map.put("state", "success");
					} else {
						map.put("state", "failed");
					}
				}
			} else {
				map.put("state", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * ajax验证密码是否正确
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/validatePwd.do")
	public void isValidatePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String password = request.getParameter("password");

		String state = "false"; // 假设不正确
		if (StringUtils.isNotEmpty(resourceid) && StringUtils.isNotEmpty(password)) {
			SysUser user = sysUserService.load(resourceid);
			if (StringUtils.equals(user.getPassword(), SecurityUtils.getMD5(password)))
				state = "true";
		}

		printWriterString(response, state);
	}

	/**
	 * ajax验证登录账号唯一
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uniqueLogin.do")
	public void isUniqueLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String loginId = StringUtils.defaultString(request.getParameter("loginId")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		para.addEquals("deleteState", "n");
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("loginId", loginId);
			List<SysUser> lists = sysUserService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("loginId", loginId);
			List<SysUser> lists = sysUserService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		}
		printWriterString(response, state);
	}

	/**
	 * ajax验证员工编号唯一
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/uniqueNumber.do")
	public void isUniqueNumber(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resourceid = request.getParameter("resourceid");
		String employeeId = StringUtils.defaultString(request.getParameter("employeeId")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("employeeId", employeeId);
			List<SysUser> lists = sysUserService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("employeeId", employeeId);
			List<SysUser> lists = sysUserService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		}
		printWriterString(response, state);
	}

	/**
	 * 用户角色授权页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userRoleAuth.do")
	public String exeUserRoleAuth(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userid = request.getParameter("userid");

		// 查询已分配角色
		SysUser user = null; // 查询用户
		List<SysRole> userRoles = new ArrayList<SysRole>(); // 用户拥有角色
		if (StringUtils.isNotEmpty(userid)) {
			user = sysUserService.load(userid);

			userRoles = sysUserService.findSysRoleByUserId(userid);
		}

		request.setAttribute("user", user);
		request.setAttribute("userRoles", userRoles);
		return "base/user/user_role_authorize";
	}

	/**
	 * 保存用户角色关系
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveUserRoleAuth.do")
	public void exeSaveUserRoleAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");
		String roleIds = request.getParameter("roleIds");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(userid) && StringUtils.isNotEmpty(roleIds)) {
				sysUserService.saveUserRole(userid, roleIds);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 查询用户所拥有的角色
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/userRoleDG.do")
	public void exeUserRoleDG(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userid = request.getParameter("userid");

		List<SysRole> userRoles = new ArrayList<SysRole>(); // 用户拥有角色
		try {
			if (StringUtils.isNotEmpty(userid)) {
				userRoles = sysUserService.findSysRoleByUserId(userid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", userRoles.size());
		map.put("rows", userRoles);
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 用户信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userInfo.do")
	public String exeUserInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtils.isNotEmpty(userid)) {
			SysOrganization organization = sysOrganizationService.getOrgByUserid(userid);

			request.setAttribute("user", sysUserService.load(userid));
			request.setAttribute("organization", organization);
		}
		return "base/user/user_info";
	}

	/**
	 * 个人信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myinfo.do")
	public String exeMyinfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		SysOrganization organization = sysOrganizationService.getOrgByUserid(SpringContextUtil.getUser().getResourceid());
		request.setAttribute("user", sysUserService.load(SpringContextUtil.getUser().getResourceid()));
		request.setAttribute("organization", organization);
		return "base/user/myinfo";
	}

	/**
	 * 保存个人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveInfo.do")
	public void exeSaveInfo(SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(user.getResourceid())) {
				SysUser cUser = sysUserService.load(user.getResourceid());
				cUser.setEmail(user.getEmail());
				cUser.setQq(user.getQq());
				cUser.setMobile(user.getMobile());
				cUser.setOfficePhone(user.getOfficePhone());
				cUser.setDutyDesc(user.getDutyDesc());
				cUser.setRemark(user.getRemark());
				cUser.setHeadImage(user.getHeadImage());
				sysUserService.update(cUser);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 公共选择页面搜索、岗位查找用户
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/findUsers.do")
	public void exeFindUsers(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String username = request.getParameter("username");
		String postVal = request.getParameter("postVal"); // 岗位
		String hideState = request.getParameter("hideState");

		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(username)) {
			param.addLike("username", username);
		}
		if (StringUtils.isNotEmpty(postVal)) {
			param.addIn("post", "'" + postVal.replaceAll(",", "','") + "'");
		}

		if (StringUtils.isEmpty(hideState)) param.addEquals("showState", "y"); // 显示，状态为显示用户
		param.addEquals("isEnabled", "y"); // 激活
		param.addEquals("stateFlag", "1"); // 在职
		param.addEquals("deleteState", "n"); // 未删除
		param.setOrderBy("orderBy");
		List<SysUser> userList = sysUserService.findList(param);
		printWriterStringToJson(response, JSON.toJSONString(userList));
	}

	// 多个角色查询用户
	@RequestMapping("/findUsersByRole.do")
	public void exeFindUsersByRole(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysUser> userList = new ArrayList<SysUser>();
		String roleIds = request.getParameter("roleIds");
		String hideState = request.getParameter("hideState");

		if (StringUtils.isNotEmpty(roleIds)) {
			userList = sysUserService.findUsersByRoles(roleIds, hideState);
		}
		printWriterStringToJson(response, JSON.toJSONString(userList));
	}

	// 多个组织查询用户
	@RequestMapping("/findUsersByOrg.do")
	public void exeFindUsersByOrg(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysUser> userList = new ArrayList<SysUser>();
		String orgIds = request.getParameter("orgIds");
		String hideState = request.getParameter("hideState");

		if (StringUtils.isNotEmpty(orgIds)) {
			userList = sysUserService.findUsersByOrgs(orgIds, hideState);
		}
		printWriterStringToJson(response, JSON.toJSONString(userList));
	}

	/**
	 * 首页用户搜索
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/findUsersByName.do")
	public void exeFindUsersByName(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String username = request.getParameter("username");

		List<SysUser> userList = new ArrayList<SysUser>();
		if (StringUtils.isNotEmpty(username)) {
			userList = sysUserService.findUsersByName(username);

			// 在线判断
			if (CollectionUtils.isNotEmpty(userList)) {
				Set<String> onlineUserIds = new HashSet<String>(); // 在线用户ID集合，用于判断用户是否在线
				List<Object> userlist = getSessionRegistry().getAllPrincipals(); // 在线用户
				if (CollectionUtils.isNotEmpty(userlist)) {
					for (Object obj : userlist) {
						if (obj instanceof SysUser) {
							onlineUserIds.add(((SysUser) obj).getResourceid());
						}
					}
				}

				for (SysUser user : userList) {
					if (onlineUserIds.contains(user.getResourceid())) {
						user.setStateFlag("(在线)");
					} else {
						user.setStateFlag("");
					}
				}
			}
		}

		printWriterStringToJson(response, JSON.toJSONString(userList));
	}

	/**
	 * 通讯录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addressList.do")
	public String exeAddressList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("stateFlag", "y"); // 有效
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");
		List<SysOrganization> list = sysOrganizationService.findList(param);

		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>();

		// 组装ztree树对象
		if (CollectionUtils.isNotEmpty(list)) {
			String orgCode = PropertiesUtil.instance().get("ROOT_ORG_CODE");
			for (SysOrganization org : list) {
				ZtreeObject ztree = new ZtreeObject(org.getResourceid(), org.getParentId(), org.getOrgName());
				if (StringUtils.equals(org.getOrgCode(), orgCode))
					ztree.setOpen(true); // 打一个root节点打开
				ztreeList.add(ztree);
			}
		}

		String ztreeOrgJson = JSON.toJSONString(ztreeList); // 转化为ztree对象
		request.setAttribute("ztreeOrgJson", ztreeOrgJson);
		return "base/user/address_list";
	}

	/**
	 * 通讯录方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/adressDG.do")
	public void exeAdressDG(SysUser user, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(user.getUsername()))
			param.addLike("username", user.getUsername());
		if (StringUtils.isNotEmpty(user.getOrgId()))
			param.addHql("resourceid in (SELECT ou.userId FROM SysOrgUser ou where ou.orgId in (select o.resourceid from SysOrganization o where o.parentIds like '%" + user.getOrgId() + "%' or o.resourceid='" + user.getOrgId() + "') and ou.orgUserType='r')");

		param.addEquals("stateFlag", "1"); // 在职
		param.addEquals("isEnabled", "y"); // 激活
		param.addEquals("showState", "y"); // 显示
		param.addEquals("deleteState", "n"); // 未删除

		List<SysUser> pageList = sysUserService.findPageList(page, param);

		for (SysUser sysUser : pageList) {
			SysOrganization organization = sysOrganizationService.getOrgByUserid(sysUser.getResourceid());
			if (null != organization)
				sysUser.setOrgName(organization.getOrgName());
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 导出通讯录
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/addressExport.do")
	public void exeAddressExport(SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(user.getUsername()))
			param.addLike("username", user.getUsername());
		if (StringUtils.isNotEmpty(user.getOrgId()))
			param.addHql("resourceid in (SELECT ou.userId FROM SysOrgUser ou where ou.orgId in (select o.resourceid from SysOrganization o where o.parentIds like '%" + user.getOrgId() + "%' or o.resourceid='" + user.getOrgId() + "') and ou.orgUserType='r')");

		param.addEquals("stateFlag", "1"); // 在职
		param.addEquals("isEnabled", "y"); // 激活
		param.addEquals("showState", "y"); // 显示
		param.addEquals("deleteState", "n"); // 未删除

		List<SysUser> list = sysUserService.findList(param);

		for (SysUser sysUser : list) {
			sysUser.setPost(DictionaryELTag.getText("D_Post", sysUser.getPost()));
			sysUser.setDuty(DictionaryELTag.getText("D_Duty", sysUser.getDuty()));
			SysOrganization organization = sysOrganizationService.getOrgByUserid(sysUser.getResourceid());
			if (null != organization)
				sysUser.setOrgName(organization.getOrgName());
		}

		String fileName = "通讯录.xls";
		String filePath = this.getClass().getClassLoader().getResource("//templates//addressList.xls").getPath();

		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("userList", list);

		InputStream is = new BufferedInputStream(new FileInputStream(filePath));

		XLSTransformer transformer = new XLSTransformer();
		Workbook resultWorkbook = transformer.transformXLS(is, beans);
		is.close();

		super.responseDownloadWorkbook(request, response, fileName, resultWorkbook);
	}

	@Resource(name = "sysuserservice")
	SysUserService sysUserService;

	@Resource(name = "sysorganizationservice")
	SysOrganizationService sysOrganizationService;

	@Resource(name = "sysroleservice")
	SysRoleService sysRoleService;

}
