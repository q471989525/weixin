package com.app.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.app.base.entity.SysUser;

/**
 * 
 * TODO：文件上传
 * 
 * @author zhoufeng
 */
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			String extPath = request.getParameter("extPath"); // 自定义扩展存储路径
			extPath = StringUtils.isNotEmpty(extPath) ? "/" + extPath : "";

			SysUser user = SpringContextUtil.getUser();

			FileUploader up = new FileUploader(request);
			up.setSavePath(PropertiesUtil.instance().get("FILE_PATH") + extPath); // 保存路径
			String[] fileType = { ".rar", ".zip", ".7z", ".pdf", ".txt", ".doc", ".docx", ".xlsx", ".xls", ".gif", ".png", ".jpg", ".jpeg", ".bmp" }; // 允许的文件类型
			up.setAllowFiles(fileType);
			up.setMaxSize(61440); // 允许的文件最大尺寸，单位kB 60MB
			up.uploadFile(user);
			response.setContentType("text/html;charset=utf-8");
			String json = "{\"fileid\":\"" + up.getFileId() + "\",\"url\":\"" + up.getUrl() + "\",\"fileType\":\"" + up.getType() + "\",\"state\":\"" + up.getState() + "\",\"original\":\"" + up.getOriginalName() + "\",\"size\":\"" + up.getSizeM() + "\",\"creator\":\""
					+ up.getCreator() + "\"}";
			response.getWriter().print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
