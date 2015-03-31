<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <link href="${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/uploadify.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/swfobject.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/jquery.uploadify.v2.1.4.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function() {
	      $('#file_upload').uploadify({
	        'uploader'  : '${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/uploadify.swf',
	        'script'    : '${pageContext.request.contextPath}/bbtForum/upload.do',
	        'cancelImg' : '${pageContext.request.contextPath}/jscript/uploadify-v2.1.4/cancel.png',
	        'auto'      : true,
	        'multi'     : true,
	        'fileDesc'  : '支持格式:zip/rar', 
	        'fileExt'   : '*.zip;*.rar',
	        'sizeLimit' : 10485760,
	        onComplete: function (event, queueID, fileObj, response, data) {
			    alert("文件:" + fileObj.name + "上传成功");
			},  
			onError: function(event, queueID, fileObj) {  
			    alert("文件:" + fileObj.name + "上传失败");  
			},  
			onCancel: function(event, queueID, fileObj){
			 	alert("取消了" + fileObj.name);  
			} 
	      });
    });
    </script>
</head>
<body>
	<center><b>listBoard</b>${sessionScope.locale }</center>
	<br>
	<spring:message code="main.title" arguments="zf"/><br>
	<spring:message code="main.img" arguments="zf"/><br>
	<br>
	<a href="javascript:$('#file_upload').uploadifyUpload()">Upload Files</a>
	<input id="file_upload" name="file_upload" type="file" />
</body>
</html>