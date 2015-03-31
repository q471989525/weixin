package com.app.base.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.app.base.entity.PubAttachment;
import com.app.base.entity.SysUser;
import com.app.base.service.PubAttachmentService;
import com.app.utils.PropertiesUtil;
import com.app.utils.SpringContextUtil;

/**
 * ajax文件上传
 *
 * @author zf
 *
 */
@Controller
@Scope("prototype")
public class FileUploadController {

    private String[] fileType = {".rar", ".zip", ".7z", ".pdf", ".txt", ".doc", ".docx", ".xlsx", ".xls", ".gif", ".png", ".jpg", ".jpeg", ".bmp"}; // 允许的文件类型,小写

    private String filePath = PropertiesUtil.instance().get("FILE_PATH");

    private long sizeLimit = 104857600;// 100 * 1024 * 1024=104857600 默认100MB

    @RequestMapping("/fileUpload.do")
    public void exeFildUpload(@RequestParam(value = "fileInput", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String state = "";
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            if (null == file) {
                state = getMapMsg().get("NOFILE"); // 未包含文件上传域
                return;
            }

            String extPath = request.getParameter("extPath"); // 自定义扩展存储路径
            String fileTypeExts = request.getParameter("fileTypeExts"); // 文件扩展名限制
            String fileSizeLimit = request.getParameter("fileSizeLimit"); // 文件大小限制：单位M
            String attachmentId = request.getParameter("attachmentId"); // 附件外键

            String oriFileName = file.getOriginalFilename(); // 源文件名称
            long fileSize = file.getSize(); // 文件大小，单位字节
            byte[] fileBytes = file.getBytes(); // 文件字节

            if (null == fileBytes) {
                state = getMapMsg().get("FILE"); // 未找到上传文件
                return;
            }

            String ext = getFileExt(oriFileName); // 获取文件扩展名

            boolean allowFlag = false; // 判断文件是否允许上传类型,默认否
            if (StringUtils.isNotEmpty(fileTypeExts)) {
                List<String> extList = Arrays.asList(fileTypeExts.toLowerCase().split(","));
                if (extList.contains(ext)) {
                    allowFlag = true;
                }
            } else {
                List<String> extList = Arrays.asList(fileType);
                if (extList.contains(ext)) {
                    allowFlag = true;
                }
            }

            // 上传
            if (allowFlag) {

                if (NumberUtils.isNumber(fileSizeLimit)) {
                    sizeLimit = Long.valueOf(fileSizeLimit) * 1024 * 1024; // 字节
                }

                if (fileSize > sizeLimit) {
                    state = getMapMsg().get("SIZE"); // 文件大小超出限制
                } else {

                    SysUser user = SpringContextUtil.getUser(); // 当前用户

                    extPath = StringUtils.isNotEmpty(extPath) ? "/" + extPath : ""; // 扩展路径
                    filePath = filePath + extPath;
                    String savePath = this.getRootFolder(filePath); // 保存路径
                    String fileName = this.getName(oriFileName); // 新文件名
                    String url = savePath.replaceFirst(PropertiesUtil.instance().get("FILE_PATH"), "") + "/" + fileName;

                    // 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(savePath, fileName));

                    // 保存数据库中
                    if (StringUtils.isNotEmpty(attachmentId)) {
                        PubAttachmentService pubAttachmentService = (PubAttachmentService) SpringContextUtil.getBean("pubAttachmentService");
                        PubAttachment attachment = new PubAttachment(attachmentId, oriFileName, url, fileSize + "", user.getResourceid(), user.getUsername(), new Date());

                        pubAttachmentService.save(attachment);
                        jsonMap.put("attObj", attachment);
                    } else {
                        PubAttachment attachment = new PubAttachment(oriFileName, url, fileSize + "", user.getResourceid(), user.getUsername(), new Date());
                        jsonMap.put("attObj", attachment);
                    }
                    state = getMapMsg().get("SUCCESS");
                }

            } else {
                state = getMapMsg().get("TYPE"); // 不允许的文件格式
            }
        } catch (Exception e) {
            state = getMapMsg().get("REQUEST");
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");

        jsonMap.put("msg", state);
        String json = JSON.toJSONString(jsonMap);

        response.getWriter().print(json);
    }

    /**
     * 获取文件扩展名,小写
     *
     * @return string
     */
    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     *
     * @param path
     * @return d:/upload/ **** /20141010
     */
    private String getRootFolder(String path) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                // this.state = this.errorInfo.get("DIR");
                return "";
            }
        }
        return path;
    }

    /**
     * 依据原始文件名生成新文件名
     *
     * @return
     */
    private String getName(String fileName) {
        Random random = new Random();
        return random.nextInt(10000) + System.currentTimeMillis() + this.getFileExt(fileName);
    }

    /**
     * 提示信息
     *
     * @return
     */
    private Map<String, String> getMapMsg() {

        HashMap<String, String> tmp = new HashMap<String, String>();

        tmp.put("SUCCESS", "SUCCESS"); // 默认成功
        // 未包含文件上传域
        tmp.put("NOFILE", "未包含文件上传域");
		// tmp.put("NOFILE",
        // "\\u672a\\u5305\\u542b\\u6587\\u4ef6\\u4e0a\\u4f20\\u57df");
        // 未找到上传文件
        tmp.put("FILE", "未找到上传文件");
		// tmp.put("FILE", "\\u672a\\u627e\\u5230\\u4e0a\\u4f20\\u6587\\u4ef6");
        // 不允许的文件格式
        tmp.put("TYPE", "不允许的文件格式");
		// tmp.put("TYPE",
        // "\\u4e0d\\u5141\\u8bb8\\u7684\\u6587\\u4ef6\\u683c\\u5f0f");
        // 文件大小超出限制
        tmp.put("SIZE", "文件大小超出限制");
		// tmp.put("SIZE",
        // "\\u6587\\u4ef6\\u5927\\u5c0f\\u8d85\\u51fa\\u9650\\u5236");
        // 请求类型错误
        tmp.put("ENTYPE", "请求类型错误");
		// tmp.put("ENTYPE", "\\u8bf7\\u6c42\\u7c7b\\u578b\\u9519\\u8bef");
        // 上传请求异常
        tmp.put("REQUEST", "上传请求异常");
		// tmp.put("REQUEST", "\\u4e0a\\u4f20\\u8bf7\\u6c42\\u5f02\\u5e38");
        // IO异常
        tmp.put("IO", "IO异常");
		// tmp.put("IO", "IO\\u5f02\\u5e38");
        // 目录创建失败
        tmp.put("DIR", "目录创建失败");
		// tmp.put("DIR", "\\u76ee\\u5f55\\u521b\\u5efa\\u5931\\u8d25");
        // 未知错误
        tmp.put("UNKNOWN", "未知错误");
        // tmp.put("UNKNOWN", "\\u672a\\u77e5\\u9519\\u8bef");

        return tmp;
    }

}
