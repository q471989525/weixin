package com.app.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.base.service.PubAttachmentService;
import com.app.utils.SpringContextUtil;

/**
 * 
 * TODO：公共附件
 * 
 * @author zhoufeng
 */
@Controller
@Scope("prototype")
@RequestMapping("/pubattachment")
public class PubAttachmentController {

	private static Logger logger = Logger.getLogger(PubAttachmentController.class);

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public void ExtDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String fileid = request.getParameter("fileid");

		if (StringUtils.isNotEmpty(fileid)) {
			try {
				/*PubAttachment attachment = pubAttachmentService.get(fileid);
				
				String filePath = PropertiesUtil.instance().get("FILE_PATH") + attachment.getFileUrl();
				File file = new File(filePath);
				if (file.exists())
					file.delete();

				*/
				pubAttachmentService.deleteById(fileid);
				logger.info(SpringContextUtil.getUser().getUsername() + "    删除文件...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Autowired
	PubAttachmentService pubAttachmentService;

}
