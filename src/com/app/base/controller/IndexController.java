package com.app.base.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.app.base.service.SysResourceService;
import com.app.core.controller.BaseController;
import com.app.utils.DateTimeUtil;
import com.app.utils.SpringContextUtil;
import com.app.utils.ZtreeObject;

@Controller
@Scope("prototype")
@RequestMapping("/index")
public class IndexController extends BaseController {

	private static Logger logger = Logger.getLogger(IndexController.class);

	/**
	 * 登录成功跳转
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authenticated.do")
	public String authenticated(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info(SpringContextUtil.getUser().getUsername() + " 进入首页...");
		List<ZtreeObject> rootMenuList = sysResourceService.findListByUser(null, SpringContextUtil.getUser()); // 第一级节点菜单

		String ztreeString = "";
		if (CollectionUtils.isNotEmpty(rootMenuList)) {
			// 取第一个主菜单节点子菜单
			List<ZtreeObject> ztreeList = sysResourceService.findListByUser(rootMenuList.get(0).getId(), SpringContextUtil.getUser());
			if (CollectionUtils.isNotEmpty(ztreeList)){
				WebUtils.setSessionAttribute(request, rootMenuList.get(0).getId(), ztreeList); //放入session中
				ztreeString = JSON.toJSONString(ztreeList); // 转化为ztree对象
			}
		}

		model.addAttribute("user", SpringContextUtil.getUser());
		model.addAttribute("rootMenuList", rootMenuList);
		model.addAttribute("ztreeString", ztreeString);
		model.addAttribute("AMPM", DateTimeUtil.getAMPM());
		model.addAttribute("currentDate", DateTimeUtil.getNowTime("yyyy年MM月dd日") + "&nbsp;&nbsp;" + DateTimeUtil.getWeekOfDate(new Date()));
		return "index";
	}

	@Resource(name = "sysresourceservice")
	SysResourceService sysResourceService;

}
