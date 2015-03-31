package com.app.base.controller;

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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysDictionary;
import com.app.base.entity.SysOrganization;
import com.app.base.entity.SysUser;
import com.app.base.service.SysOrganizationService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.QueryParameter;
import com.app.core.tag.DictionaryELTag;
import com.app.utils.PropertiesUtil;
import com.app.utils.SpringContextUtil;
import com.app.utils.ZtreeObject;

/**
 * 
 * TODO：组织机构
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysorganization")
public class SysOrganizationController extends BaseController {

	private static Logger logger = Logger.getLogger(SysOrganizationController.class);

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
		return "base/org/list_organization";
	}

	/**
	 * 树列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/datagrid.do")
	public void exeDataGrid(SysOrganization organization, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String query = "";
		QueryParameter param = new QueryParameter();
		if (StringUtils.isNotEmpty(organization.getOrgName())) {
			param.addLike("orgName", organization.getOrgName());
			query = "y";
		}
		if (StringUtils.isNotEmpty(organization.getOrgCode())) {
			param.addLike("orgCode", organization.getOrgCode());
			query = "y";
		}
		if (StringUtils.isNotEmpty(organization.getOrgType())) {
			param.addEquals("orgType", organization.getOrgType());
			query = "y";
		}
		if (StringUtils.isNotEmpty(organization.getStateFlag())) {
			param.addEquals("stateFlag", organization.getStateFlag());
			query = "y";
		}
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");

		List<SysOrganization> pageList = sysOrganizationService.findList(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageList.size());
		map.put("rows", pageList);

		String jsonString = JSON.toJSONString(map);
		if (StringUtils.isEmpty(query))
			jsonString = jsonString.replaceAll("\"parentId\"", "\"_parentId\""); // treegrid，父ID定义
		printWriterStringToJson(response, jsonString);
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

		SysOrganization organization = new SysOrganization();
		organization.setOrgCode("ORG_");
		organization.setDeleteState("n");
		if (StringUtils.isNotEmpty(parentId))
			organization.setParentId(parentId);
		if (StringUtils.isNotEmpty(parentName))
			organization.setParentName(URLDecoder.decode(parentName, "UTF-8"));

		model.addAttribute("organization", organization);
		return "base/org/edit_organization";
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

		SysOrganization organization = null;

		if (StringUtils.isNotEmpty(resourceid)) {
			organization = sysOrganizationService.load(resourceid);
		}

		model.addAttribute("organization", organization);
		return "base/org/edit_organization";
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
	public void exeSave(SysOrganization organization, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 组合父id
			if (StringUtils.isNotEmpty(organization.getParentId())) {
				SysOrganization parentOrg = sysOrganizationService.load(organization.getParentId());
				if (StringUtils.isEmpty(parentOrg.getParentId())) {
					organization.setParentIds(parentOrg.getResourceid());
					organization.setParentNames(parentOrg.getOrgName());
				} else {
					organization.setParentIds(parentOrg.getParentIds() + "," + parentOrg.getResourceid());
					organization.setParentNames(parentOrg.getParentNames() + "," + parentOrg.getOrgName());
				}
			}else{
				organization.setParentIds("");
				organization.setParentNames("");
			}

			if (StringUtils.isEmpty(organization.getResourceid())) { // 新增
				sysOrganizationService.save(organization);
			} else {
				sysOrganizationService.update(organization); // 修改
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
				sysOrganizationService.deleteById(id);
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
		String orgCode = StringUtils.defaultString(request.getParameter("orgCode")).trim();

		String state = "false"; // 假设已经存在
		QueryParameter para = new QueryParameter();
		if (StringUtils.isEmpty(resourceid)) { // 新增操作
			para.addEquals("deleteState", "n"); // 删除标志,n：未删除
			para.addEquals("orgCode", orgCode);
			List<SysOrganization> lists = sysOrganizationService.findList(para);
			if (CollectionUtils.isEmpty(lists))
				state = "true";
		} else { // 修改操作
			para.addNotEquals("resourceid", resourceid);
			para.addEquals("deleteState", "n"); // 删除标志,n：未删除
			para.addEquals("orgCode", orgCode);
			List<SysOrganization> lists = sysOrganizationService.findList(para);
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
		return "base/org/select_organization";
	}

	/**
	 * select列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/treegrid.do")
	public void exeTreeGrid(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'y','h'"); //包含有效和隐藏
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");
		String orgName = request.getParameter("orgName");

		String query = "";
		if (StringUtils.isNotEmpty(orgName)) {
			param.addLike("orgName", orgName);
			query = "y";
		}

		List<SysOrganization> list = sysOrganizationService.findList(param);

		if (CollectionUtils.isNotEmpty(list) && StringUtils.isEmpty(query)) {
			Set<String> parentIdlSet = new HashSet<String>(); // 父文件夹

			for (SysOrganization org : list) {
				if (StringUtils.isNotEmpty(org.getParentId()) && !parentIdlSet.contains(org.getParentId()))
					parentIdlSet.add(org.getParentId());
			}

			for (SysOrganization org : list) {
				if (StringUtils.isNotEmpty(org.getParentId()) && parentIdlSet.contains(org.getResourceid())) { // 判断为父文件夹，状态为关闭
					org.setState("closed");
				}
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);

		String jsonString = JSON.toJSONString(map);
		if (StringUtils.isEmpty(query))
			jsonString = jsonString.replaceAll("\"parentId\"", "\"_parentId\""); // treegrid，父ID定义
		printWriterStringToJson(response, jsonString);
	}
	
	/**
	 * 组织用户treegrid页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/orgUser.do")
	public String exeOrgUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("select...........");
		return "base/org/user_organization";
	}

	/**
	 * 组织用户列表方法
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/orgUserTreegrid.do")
	public void exeOrgUserTreeGrid(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		// 查询所有组织
		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'y','h'"); //包含有效和隐藏
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");
		List<SysOrganization> orgList = sysOrganizationService.findList(param);

		if (CollectionUtils.isNotEmpty(orgList)) {
			Set<String> parentIdlSet = new HashSet<String>(); // 父文件夹
			for (SysOrganization org : orgList) {
				if (StringUtils.isNotEmpty(org.getParentId()) && !parentIdlSet.contains(org.getParentId()))
					parentIdlSet.add(org.getParentId());
			}

			for (SysOrganization org : orgList) {
				if (StringUtils.isNotEmpty(org.getParentId()) && parentIdlSet.contains(org.getResourceid())) { // 判断为父文件夹，状态为关闭
					org.setState("closed");
				}
			}
		}

		// 查询所有用户组装成ORG对象
		List<SysOrganization> userList = sysOrganizationService.getOrgUser();
		if (CollectionUtils.isNotEmpty(userList))
			orgList.addAll(userList); // 追加到组织集合

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", orgList.size());
		map.put("rows", orgList);
		String jsonString = JSON.toJSONString(map);

		jsonString = jsonString.replaceAll("\"parentId\"", "\"_parentId\""); // treegrid，父ID定义
		printWriterStringToJson(response, jsonString);
	}

	/**
	 * 公共用户选择页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userSelect.do")
	public String exeUserSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("select...........");
		String selectType = StringUtils.defaultString(request.getParameter("selectType"), "single"); // 选择类型,默认单选
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String hideState = request.getParameter("hideState");
		String limitSize = request.getParameter("limitSize");

		QueryParameter param = new QueryParameter();
		if(StringUtils.isNotEmpty(hideState)){
			param.addIn("stateFlag", "'y','h'"); //包含有效和隐藏
		}else{
			param.addEquals("stateFlag", "y"); // 默认有效状态
		}
		param.addEquals("deleteState", "n"); // 未删除
		param.setOrderBy("orderBy");
		List<SysOrganization> orgList = sysOrganizationService.findList(param);

		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>();
		if (CollectionUtils.isNotEmpty(orgList)) {
			String orgCode = PropertiesUtil.instance().get("ROOT_ORG_CODE");
			for (SysOrganization org : orgList) {
				ZtreeObject ztree = new ZtreeObject(org.getResourceid(), org.getParentId(), org.getOrgName());
				if (StringUtils.equals(org.getOrgCode(), orgCode)) ztree.setOpen(true); // 打一个root节点打开
				ztreeList.add(ztree);
			}
		}

		String ztreeString = JSON.toJSONString(ztreeList); // 转化为ztree对象
		request.setAttribute("ztreeString", ztreeString);
		request.setAttribute("hideState", hideState);
		request.setAttribute("limitSize", limitSize);
		if(StringUtils.isNotEmpty(userId)) request.setAttribute("userId", userId);
		if(StringUtils.isNotEmpty(userName)) request.setAttribute("userName", URLDecoder.decode(userName, "UTF-8"));

		String returnPath = "base/org/org_select_user_multi";
		if (StringUtils.equals(selectType, "single")) { // 单选
			returnPath = "base/org/org_select_user_single";
		}
		return returnPath;
	}
	
	/**
	 * 公共选择页面岗位
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/post.do")
	public void exePost(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		List<SysDictionary> postList = DictionaryELTag.getList(PropertiesUtil.instance().get("POST_DICTIONARY")); // 岗位
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", postList.size());
		map.put("rows", postList);
		String jsonString = JSON.toJSONString(map);
		printWriterStringToJson(response, jsonString);
	}
	
	/**
	 * 选择Ztree页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectZtree.do")
	public String exeSelectZtree(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("selectZtree...........");
		QueryParameter param = new QueryParameter();
		param.addIn("stateFlag", "'y','h'"); //包含有效和隐藏
		param.addEquals("deleteState", "n"); // 未删除
		param.setOrderBy("orderBy");
		List<SysOrganization> orgList = sysOrganizationService.findList(param);

		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>();
		if (CollectionUtils.isNotEmpty(orgList)) {
			String orgCode = PropertiesUtil.instance().get("ROOT_ORG_CODE");
			for (SysOrganization org : orgList) {
				ZtreeObject ztree = new ZtreeObject(org.getResourceid(), org.getParentId(), org.getOrgName());
				if (StringUtils.equals(org.getOrgCode(), orgCode)) ztree.setOpen(true); // 打一个root节点打开
				ztreeList.add(ztree);
			}
		}

		String ztreeString = JSON.toJSONString(ztreeList); // 转化为ztree对象
		request.setAttribute("ztreeString", ztreeString);
		return "base/org/select_ztree_organization";
	}

	/**
	 * 首页组织用户Ztree树
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/index.do")
	public String exeIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("首页组织用户Ztree...........");

		QueryParameter param = new QueryParameter();
		param.addEquals("stateFlag", "y"); // 有效状态
		param.addEquals("deleteState", "n"); // 删除标志,n：未删除
		param.setOrderBy("orderBy");
		
		SysUser currentUser = SpringContextUtil.getUser(); //当前用户
		
		List<SysOrganization> orgList = null;
		Object tempOrgList = WebUtils.getSessionAttribute(request, currentUser.getResourceid()+"_ORG"); //从session中获取组织
		if(null == tempOrgList){
			orgList = sysOrganizationService.findList(param); // 查询全部有效状态组织
			WebUtils.setSessionAttribute(request, currentUser.getResourceid()+"_ORG", orgList); //放入session中
		}else{
			orgList = (List<SysOrganization>) tempOrgList;
		}

		List<ZtreeObject> ztreeList = new ArrayList<ZtreeObject>(); //ztree对象

		Set<String> onlineUserIds = new HashSet<String>(); // 在线用户ID集合，用于判断用户是否在线
		List<Object> onlineUserlist = getSessionRegistry().getAllPrincipals(); // 在线用户
		if (CollectionUtils.isNotEmpty(onlineUserlist)) {
			for (Object obj : onlineUserlist) {
				if (obj instanceof SysUser) {
					onlineUserIds.add(((SysUser) obj).getResourceid());
				}
			}
		}
		
		List<SysUser> userList = null;
		Object tempUserList = WebUtils.getSessionAttribute(request, currentUser.getResourceid()+"_USERS"); //从session中获取用户
		if(null == tempUserList){
			userList = sysOrganizationService.findValidUserBySQL(); // 查询所有用户
			WebUtils.setSessionAttribute(request, currentUser.getResourceid()+"_USERS", userList); //放入session中
		}else{
			userList = (List<SysUser>) tempUserList;
		}
		
		if (CollectionUtils.isNotEmpty(userList)) {
			String ctxPath = request.getContextPath();
			String user_green = ctxPath + PropertiesUtil.instance().get("USER_GREEN");// 男-在线
			String user_black = ctxPath + PropertiesUtil.instance().get("USER_BLACK"); // 男-离线
			String user_green_female = ctxPath + PropertiesUtil.instance().get("USER_GREEN_FEMALE"); // 女-在线
			String user_black_female = ctxPath + PropertiesUtil.instance().get("USER_BLACK_FEMALE"); // 女-离线
			String user_question = ctxPath + PropertiesUtil.instance().get("USER_QUESTION"); // 未知

			for (SysUser user : userList) {
				String userid = user.getResourceid();
				String sex = user.getSex();
				ZtreeObject ztree = new ZtreeObject(userid, user.getOrgId(), user.getUsername());
				// 在线用户头像
				if (StringUtils.equals(sex, "1")) { // 男
					if (onlineUserIds.contains(userid)) {
						ztree.setIcon(user_green);
					} else {
						ztree.setIcon(user_black);
					}
				} else if (StringUtils.equals(sex, "2")) { // 女
					if (onlineUserIds.contains(userid)) {
						ztree.setIcon(user_green_female);
					} else {
						ztree.setIcon(user_black_female);
					}
				} else {
					ztree.setIcon(user_question);
				}
				ztree.setNodeType("user");
				ztreeList.add(ztree);
			}
		}

		// 后组装ztree树对象
		if (CollectionUtils.isNotEmpty(orgList)) {
			String orgCode = PropertiesUtil.instance().get("ROOT_ORG_CODE");
			for (SysOrganization org : orgList) {
				ZtreeObject ztree = new ZtreeObject(org.getResourceid(), org.getParentId(), org.getOrgName());
				ztree.setIsParent(true);
				ztree.setNodeType("org");
				if (StringUtils.equals(org.getOrgCode(), orgCode)) ztree.setOpen(true); // 打一个root节点打开
				ztreeList.add(ztree);
			}
		}

		String ztreeOrgUserJson = JSON.toJSONString(ztreeList); // 转化为ztree对象
		request.setAttribute("ztreeOrgUserJson", ztreeOrgUserJson);
		return "base/org/index_org_user";
	}

	@Resource(name = "sysorganizationservice")
	SysOrganizationService sysOrganizationService;

}
