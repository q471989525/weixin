package com.app.utils;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.app.base.entity.PubAttachment;
import com.app.base.service.PubAttachmentService;

/**
 * 
 * TODO：文件下载
 * 
 * @author zhoufeng
 */
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileid = request.getParameter("fileid");
		String image = request.getParameter("image");
		try {
			// 文件下载
			if (StringUtils.isNotEmpty(fileid)) {
				PubAttachmentService pubAttachmentService = (PubAttachmentService) SpringContextUtil.getBean("pubAttachmentService");
				PubAttachment attachment = pubAttachmentService.get(fileid);

				if (null != attachment) {
					String filePath = PropertiesUtil.instance().get("FILE_PATH") + attachment.getFileUrl();
					String fileName = attachment.getFileName();

					// 读到流中
					InputStream inStream = new FileInputStream(filePath);// 文件的存放路径

					String agent = request.getHeader("User-Agent").toUpperCase();

					String encodedfileName = "";
					if (agent.indexOf("MSIE") != -1 || agent.indexOf("TRIDENT") != -1) { // IE
						encodedfileName = URLEncoder.encode(fileName, "utf-8");
					}else if(agent.indexOf("CHROME") != -1 || agent.indexOf("FIREFOX") != -1){ //谷歌或火狐
						encodedfileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
					}else{
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
			}

			// 图片下载
			if (StringUtils.isNotEmpty(image)) {
				// 获取图片绝对路径
				File file = new File(PropertiesUtil.instance().get("FILE_PATH") + image);
				InputStream is = new FileInputStream(file);
				Image img = ImageIO.read(is);// 读图片
				String imageType = image.substring(image.lastIndexOf(".") + 1);
				RenderedImage rendImg = (RenderedImage) img;
				OutputStream out = response.getOutputStream();
				ImageIO.write(rendImg, imageType, out);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace(); // 打印错误信息
			if (StringUtils.isNotEmpty(image)) { // 只针对图片
				PrintWriter toClient = response.getWriter(); // 得到向客户端输出文本的对象
				response.setContentType("text/html;charset=gb2312");
				toClient.write("无法打开图片!");
				toClient.close();
			}
		}
	}

}
