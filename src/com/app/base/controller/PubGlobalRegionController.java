package com.app.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.SysGlobalRegion;
import com.app.base.service.SysGlobalRegionService;
import com.app.core.controller.BaseController;
import com.app.core.service.util.PageUtil;
import com.app.core.service.util.QueryParameter;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.PropertiesUtil;

/**
 * 
 * TODO：省市区域
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/pubglobalregion")
public class PubGlobalRegionController extends BaseController {

	private static Logger logger = Logger.getLogger(PubGlobalRegionController.class);

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
		return "base/region/list_region";
	}

	/**
	 * 省
	 * 
	 * @param region
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/provinceDatagrid.do")
	public void exeProvinceDataGrid(SysGlobalRegion region, PageUtil page, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();
		param.addEquals("deleteState", "n");
		param.addEquals("regionType", "1");
		param.addEquals("parentId", "1");
		if (StringUtils.isNotEmpty(region.getRegionName()))
			param.addLike("regionName", region.getRegionName());

		List<SysGlobalRegion> pageList = sysGlobalRegionService.findPageList(page, param);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalRows());
		map.put("rows", pageList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 市
	 * 
	 * @param region
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/cityDatagrid.do")
	public void exeCityDataGrid(SysGlobalRegion region, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		QueryParameter param = new QueryParameter();

		List<SysGlobalRegion> list = new ArrayList<SysGlobalRegion>();
		param.setOrderBy("orderBy");
		if (StringUtils.isNotEmpty(region.getRegionName()))
			param.addLike("regionName", region.getRegionName());
		
		if (StringUtils.isNotEmpty(region.getParentId())) {
			param.addEquals("deleteState", "n");
			param.addEquals("regionType", "2");
			param.addEquals("parentId", region.getParentId());
			list = sysGlobalRegionService.findList(param);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("rows", list);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * 新增省页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addProvince.do")
	public String exeAddProvince(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("add...........");
		SysGlobalRegion region = new SysGlobalRegion();
		region.setParentId("1");
		region.setRegionType("1");
		region.setDeleteState("n");
		model.addAttribute("region", region);
		return "base/region/edit_region";
	}

	/**
	 * 新增市
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addCity.do")
	public String exeAddCity(String parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("addCity...........");
		SysGlobalRegion region = new SysGlobalRegion();
		region.setParentId(parentId);
		region.setRegionType("2");
		region.setDeleteState("n");
		model.addAttribute("region", region);
		return "base/region/edit_region";
	}
	
	/**
	 * 编辑省页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editProvince.do")
	public String exeEditProvince(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("edit...........");
		String resourceid = request.getParameter("resourceid");
		
		SysGlobalRegion region = new SysGlobalRegion();
		
		if (StringUtils.isNotEmpty(resourceid)) {
			region = sysGlobalRegionService.load(resourceid);
		}
		
		model.addAttribute("region", region);
		return "base/region/edit_region";
	}

	/**
	 * 编辑市
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editCity.do")
	public String exeEditCity(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("edit...........");
		String resourceid = request.getParameter("resourceid");

		SysGlobalRegion region = new SysGlobalRegion();

		if (StringUtils.isNotEmpty(resourceid)) {
			region = sysGlobalRegionService.load(resourceid);
		}

		model.addAttribute("region", region);
		return "base/region/edit_region";
	}

	/**
	 * 保存省
	 * 
	 * @param region
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveProvince.do")
	public void exeSaveProvince(SysGlobalRegion region, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("saveProvince...........");
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isEmpty(region.getResourceid())) { // 新增
				sysGlobalRegionService.save(region);
			} else {
				sysGlobalRegionService.update(region); // 修改
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}
	
	/**
	 * 保存市
	 * 
	 * @param region
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCity.do")
	public void exeSaveCity(SysGlobalRegion region, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("save...........");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isNotEmpty(region.getParentId())){
				if (StringUtils.isEmpty(region.getResourceid())) { // 新增
					sysGlobalRegionService.save(region);
				} else {
					sysGlobalRegionService.update(region); // 修改
				}
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}
	
	/**
	 * 删除省
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteProvince.do")
	public void exeDeleteProvince(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				sysGlobalRegionService.deleteById(id);
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}
	
	/**
	 * 删除市
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteCity.do")
	public void exeDeleteCity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ids");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(id)) {
				sysGlobalRegionService.deleteStateByIds(id.split(","));
			}
			map.put("state", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "failed");
		}
		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	/**
	 * ajax查找市
	 * 
	 * @param provinceId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findCity.do")
	public void exeSave(String provinceId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("查找市...........省ID：" + provinceId);

		Map<String, Object> map = new HashMap<String, Object>();
		List<SysGlobalRegion> cityList = new ArrayList<SysGlobalRegion>();
		if (StringUtils.isNotEmpty(provinceId)) {
			// 从缓存中获取，获取失败才查询数据库
			cityList = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_DICTIONS).getList(PropertiesUtil.instance().get("D_PROVINCE") + "_" + provinceId, SysGlobalRegion.class);

			if (CollectionUtils.isEmpty(cityList))
				cityList = sysGlobalRegionService.findCity(provinceId);

			map.put("state", "success");
		} else {
			map.put("state", "failed");
		}
		map.put("cityData", cityList);

		printWriterStringToJson(response, JSON.toJSONString(map));
	}

	@Autowired
	SysGlobalRegionService sysGlobalRegionService;

}
