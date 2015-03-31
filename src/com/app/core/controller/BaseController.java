package com.app.core.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.app.utils.Constants_App;
import com.app.utils.MemeryCacheManager;
import com.app.utils.SpringContextUtil;

/**
 * 基础Controller类，实现一些公共方法
 * 
 * @author ZF
 * @Apr 15, 2011
 */
public class BaseController {

	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 初始化日期类型
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initDateFormateBinder(WebDataBinder binder) {
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

	@Autowired
	SessionRegistry sessionRegistry; // 注入spring session注册跟踪

	// 统计当前在线人员
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	/**
	 * 输出json格式字符串
	 * 
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	public void printWriterObjectToJson(HttpServletResponse response, Object object) throws IOException {
		response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		String jsonString = JSON.toJSONString(object);
		pw.write(jsonString);
		pw.flush();
		if (logger.isDebugEnabled())
			logger.debug(jsonString);
	}

	/**
	 * 输出json格式字符串
	 * 
	 * @param response
	 * @param jsonString
	 * @throws IOException
	 */
	public void printWriterStringToJson(HttpServletResponse response, String jsonString) throws IOException {
		response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(jsonString);
		pw.flush();
		if (logger.isDebugEnabled())
			logger.debug(jsonString);
	}

	/**
	 * 输出文本
	 * 
	 * @param response
	 * @param textString
	 * @throws IOException
	 */
	public void printWriterString(HttpServletResponse response, String textString) throws IOException {
		response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(textString);
		pw.flush();
		if (logger.isDebugEnabled())
			logger.debug(textString);
	}

	/**
	 * 通用文件下载方法
	 * 
	 * @param response
	 * @param filePath
	 *            文件绝对路径
	 *            this.getClass().getClassLoader().getResource("file").getPath
	 *            ();
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public void responseDownloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) throws IOException {
		// 读到流中
		InputStream inStream = new FileInputStream(filePath + fileName);// 文件的存放路径

		String agent = request.getHeader("User-Agent").toUpperCase();

		String encodedfileName = "";
		if (agent.indexOf("MSIE") != -1 || agent.indexOf("TRIDENT") != -1) { // IE
			encodedfileName = URLEncoder.encode(fileName, "utf-8");
		} else if (agent.indexOf("CHROME") != -1 || agent.indexOf("FIREFOX") != -1) { // 谷歌或火狐
			encodedfileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
		} else {
			encodedfileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");

		// 循环取出流中的数据
		byte[] b = new byte[100];
		int len;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}

	/**
	 * workbook文件下载方法
	 * 
	 * @param response
	 * @param fileName
	 *            文件名
	 * @param workbook
	 *            文件
	 * @throws IOException
	 */
	public void responseDownloadWorkbook(HttpServletRequest request, HttpServletResponse response, String fileName, Workbook workbook) throws IOException {
		String agent = request.getHeader("User-Agent").toUpperCase();

		String encodedfileName = "";
		if (agent.indexOf("MSIE") != -1 || agent.indexOf("TRIDENT") != -1) { // IE
			encodedfileName = URLEncoder.encode(fileName, "utf-8");
		} else if (agent.indexOf("CHROME") != -1 || agent.indexOf("FIREFOX") != -1) { // 谷歌或火狐
			encodedfileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
		} else {
			encodedfileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
		}

		// 设置输出的格式
		response.reset();
		response.setContentType("bin");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");

		// 取出流中的数据
		ServletOutputStream os = response.getOutputStream();
		workbook.write(os);
		os.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });// UTF-8
		os.flush();
		os.close();
	}

	/**
	 * 重定向跳转
	 * 
	 * @param url
	 * @return
	 */
	public String redirect(String url) {
		return "redirect:" + url;
	}

	/**
	 * 内部跳转
	 * 
	 * @param url
	 * @return
	 */
	public String forward(String url) {
		return "forward:" + url;
	}

	/**
	 * 通过菜单ID获取用户权限数据
	 * 
	 * @param menuId
	 * @return
	 */
	public String getLimitData(String menuId) {
		Object hql = MemeryCacheManager.getCache(Constants_App.CACHE_SYS_USER_DATA).get(SpringContextUtil.getUser().getResourceid(), menuId);
		if (null != hql) {
			return hql.toString();
		}
		return null;
	}

}
