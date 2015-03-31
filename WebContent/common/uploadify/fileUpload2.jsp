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
			$("#file_upload").uploadify({
				height: 17,
		        width: 80,
		        multi: false,
		        fileSizeLimit: '20MB',
		        fileTypeExts: '*.rar; *.zip; *.7z; *.pdf; *.txt; *.doc; *.docx; *.xlsx; *.xls; *.gif; *.png; *.jpg; *.jpeg;',
		        swf: '${pageContext.request.contextPath}/common/uploadify/uploadify.swf',
		        formData:{"attachmentId":"${param.attachmentId}"},
		        //uploader: '${pageContext.request.contextPath}/fileupload',
				uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}',//注意的是jsessionid前面那个是个分号而不是问号，写成问号就作为参数传递了
		        buttonText:'上传附件',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	window.parent.appendFile(obj);
		        	//$("#attFileDiv").append("<div id='"+obj.fileid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.fileid+"')\">"+obj.original+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.fileid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer; width: 11px; height: 11px;'></div>");
		        }
			});
	  	
  		});
	</script>
  </head>
  <body style="padding: 0px; margin: 0px">
    <input id="file_upload" name="file_upload" type="file"/>
    <div id="file_queue" class="uploadify-queue" style="display: none;"></div>
  </body>
</html>
