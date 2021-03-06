<%@ page language="java" contentType="text/html; charset=utf-8"%>

<!DOCTYPE>
<html>
  <head>
  	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/AjaxFileUploaderV2.1/ajaxfileupload.css">
  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
	<script type="text/javascript">
	function ajaxFileUpload(){
		var file = $("#fileInput").val();  
	    if(file==""){
	    	alert("请选择上传文件."); 
	        return;  
	    }
	    
	    var exts = new Array(".gif",".png",".jpg",".jpeg"); //允许上传的文件类型
	    var fileType = file.substring(file.lastIndexOf(".")).toLowerCase(); //扩展名
	    var allowFlag = "N";
	    for ( var int = 0; int < exts.length; int++) {
			if(exts[int] == fileType){ allowFlag = "Y"; break;}
		}
        if(allowFlag === "N"){
            alert("上传文件格式错误.");  
            return;
        }
        
		$("#loading").ajaxStart(function(){
			$(this).show();
		}).ajaxComplete(function(){
			$(this).hide();
		});
		
		$.ajaxFileUpload({
				url:"${pageContext.request.contextPath}/fileUpload.do",
				secureuri:false,
				fileElementId:"fileInput",
				dataType: "json",
				data:{extPath:"headImages",fileTypeExts:exts.toString(),fileSizeLimit: "2"}, //fileSizeLimit：单位M
				success: function (data, status) {
					if(data.msg == "SUCCESS"){
						var attObj = data.attObj;
						window.parent.document.getElementById("headImage").value = attObj.fileUrl;
		        		window.parent.document.getElementById("headImg").src = "${pageContext.request.contextPath}/filedownload?image="+attObj.fileUrl;
					}else{
						alert(data.msg);
					}
				},
				error: function (data, status, e) {
					alert("上传错误！");
				}
		})
		
		return false;
	}
	</script>
  </head>
  <body style="padding: 0px; margin: 0px">
  	<img id="loading" src="${pageContext.request.contextPath}/common/AjaxFileUploaderV2.1/loading.gif" style="display:none;">
  	<center>
  		<form action="" id="imageForm" enctype="multipart/form-data" method="post">
    		<input id="fileInput" name="fileInput" type="file" style="width: 150px;"/><br>
    		<input type="button" value="上传头像" onclick="return ajaxFileUpload();">
    	</form>
  	</center>
  </body>
</html>
