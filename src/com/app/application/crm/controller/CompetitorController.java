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
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.SpringContextUtil;
import com.app.application.crm.entity.Competitor;
import com.app.application.crm.service.CompetitorService;

/**
 * 
 * TODO：竞争对手
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/competitor")
public class CompetitorController extends BaseController {

	private static Logger logger = Logger.getLogger(CompetitorController.class);

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
		return "crm/competitor/list_competitor";
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
	public void exeDataGrid(String menuId, Competitor competitor, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		if (StringUtils.isNotEmpty(competitor.getCompetitorName()))
			param.addLike("competitorName", competitor.getCompetitorName());
		if (StringUtils.isNotEmpty(menuId)) {
			String hql = super.getLimitData(menuId);
			if (null != hql) {
				param.addHql(hql);
			} else {
				param.addEquals("createId", SpringContextUtil.getUser().getResourceid());
			}
		}
		param.addEquals("deleteFlag", "n");

		List<Competitor> pageList = competitorService.findPageList(page, param);

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

		Competitor competitor = new Competitor();
		competitor.setCreateId(SpringContextUtil.getUser().getResourceid());
		competitor.setCreator(SpringContextUtil.getUser().getUsername());
		competitor.setCreateTime(new Date());
		competitor.setDeleteFlag("n");

		model.addAttribute("competitor", competitor);
		return "crm/competitor/edit_competitor";
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

		Competitor competitor = null;
		if (StringUtils.isNotEmpty(resourceid)) {
			competitor = competitorService.load(resourceid);
		}

		model.addAttribute("competitor", competitor);
		return "crm/competitor/edit_competitor";
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
	public void exeSave(Competitor competitor, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(competitor.getResourceid())) { // 新增
				competitorService.save(competitor);
			} else {
				competitorService.update(competitor); // 修改
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
				competitorService.deleteByIds(id.split(","));
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
		return "crm/competitor/select_competitor";
	}

	@Resource(name = "competitorservice")
	CompetitorService competitorService;

}
