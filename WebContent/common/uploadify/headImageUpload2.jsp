<%@ page language="java" contentType="text/html; charset=utf-8"%>

<!DOCTYPE>
<html>
  <head>
  	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/uploadify/uploadify.css">
  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript">
	  	$(document).ready(function(){
	  		
		  	//附件上传
			$("#headImage_upload").uploadify({
				height: 17,
		        width: 80,
		        multi: false,
		        fileSizeLimit: '2MB',
		        fileTypeExts: '*.gif; *.png; *.jpg; *.jpeg;',
		        swf: '${pageContext.request.contextPath}/common/uploadify/uploadify.swf',
				uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}?extPath=headImages',//注意的是jsessionid前面那个是个分号而不是问号，写成问号就作为参数传递了
		        buttonText:'上传头像',
		        queueID:'headImage_queue',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	if(obj.state=="SUCCESS"){
		        		window.parent.document.getElementById("headImage").value = obj.url;
		        		window.parent.document.getElementById("headImg").src = "${pageContext.request.contextPath}/filedownload?image="+obj.url;
		        	}
		        }
			});
	  	
  		});
	</script>
  </head>
  <body style="padding: 0px; margin: 0px">
  	<center>
    	<input id="headImage_upload" name="headImage_upload" type="file"/>
    	<div id="headImage_queue" class="uploadify-queue" style="display: none;"></div>
  	</center>
  </body>
</html>
