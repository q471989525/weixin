<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/uploadify/uploadify.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/uploadify/jquery.uploadify.min.js"></script>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#runRecordForm").validate({
				rules: {
					projectName: "required",
					customerName: "required",
					serviceItem: "required",
					startTime: "required",
					endTime: "required",
					servicePerson: {required:true,maxlength:20},
					customerSign: {maxlength:20}
				}
			})
	  		
	  		//ajax提交
	  	    $("#runRecordForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchRunrecord(); //调list页面查询方法
							} catch (e) { }
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  		//附件上传
			$("#file_upload").uploadify({
				height: 20,
		        width: 80,
		        multi: false,
		        fileSizeLimit: '20MB',
		        fileTypeExts: '*.rar; *.zip; *.7z; *.pdf; *.txt; *.doc; *.docx; *.xlsx; *.xls; *.gif; *.png; *.jpg; *.jpeg;',
		        swf: '${pageContext.request.contextPath}/common/uploadify/uploadify.swf',
		        formData:{"attachmentId":"${runRecord.attachmentId}"},
		        uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}',
		        buttonText:'上传附件',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	$("#attFileDiv").append("<div id='"+obj.fileid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.fileid+"')\">"+obj.original+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.fileid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer;'></div>");
		        }
			});
	  		
			UE.getEditor("runContent"); //初始化,放到最后不影响附件初始化
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#runRecordForm").submit();
	  	}
	  	
	  	//删除
	  	function deleteFile(id){
	  		var url = "${pageContext.request.contextPath}/pubattachment/delete.do";
	  		$.post(url, { "fileid": id }, function(){
	  			$("#"+id).remove();
	  		}, "json");
	  	}
	  	
	  	//选择项目
	  	function openProject(){
	  		var url = "${pageContext.request.contextPath}/projectInfo/select.do";
			var rv = Common.dialog({"url":url,"width":700,"height":700,modal:true});
			if(rv){
		  		$("#projectId").val(rv.resourceid);
		  		$("#projectName").val(rv.projectName);
		  		$("#customerName").val(rv.customerName);
			}
	  	}
	  	
	  	function cleanProject(){
	  		$("#projectId").val("");
	  		$("#projectName").val("");
	  		$("#customerName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<form id="runRecordForm" action="${pageContext.request.contextPath}/runrecord/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="维护信息">
				<input type="hidden" id="resourceid" name="resourceid" value="${runRecord.resourceid }">
			  	<input type="hidden" id="createId" name="createId" value="${runRecord.createId }">
			  	<input type="hidden" id="creator" name="creator" value="${runRecord.creator }">
			  	<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${runRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  	<input type="hidden" id="deleteFlag" name="deleteFlag" value="${runRecord.deleteFlag }">
				<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<tbody>
					    <tr>  
					    	<td class="tdTitle" style="width: 15%;">项目名称：</td>
					        <td style="width: 30%;">
					        	<input type="hidden" id="projectId" name="projectId" value="${runRecord.projectId }">
					            <input type="text" id="projectName" name="projectName" value="${runRecord.projectName }">
					            <input type="button" value="选择" onclick="openProject()"/>
					            <input type="button" value="清除" onclick="cleanProject()"/>
					        </td>
					        <td class="tdTitle" style="width: 15%;">客户名称：</td>
					        <td>
					          	<input type="text" id="customerName" name="customerName" value="${runRecord.customerName }">
					        </td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">服务项目：</td>
					    	<td>
					    		<app:select name="serviceItem" dictionaryCode="D_Service_Item" value="${runRecord.serviceItem}"/>
					    	</td>
					    	<td class="tdTitle">服务人员：</td>
					    	<td>
					    		<input type="text" id="servicePerson" name="servicePerson" value="${runRecord.servicePerson }">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">维护时间：</td>
					    	<td>
					    		<input type="text" id="startTime" name="startTime" value="<fmt:formatDate value="${runRecord.startTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\');}'})" class="Wdate"> - 
					    		<input type="text" id="endTime" name="endTime" value="<fmt:formatDate value="${runRecord.endTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\');}'})" class="Wdate">
					    	</td>
					    	<td class="tdTitle">客户签字：</td>
					    	<td>
					    		<input type="text" id="customerSign" name="customerSign" value="${runRecord.customerSign }">
					    	</td>
					    </tr>
					    <tr>  
					        <td class="tdTitle">附件：</td>
					        <td colspan="3">
					           	<input type="hidden" id="attachmentId" name="attachmentId" value="${runRecord.attachmentId }">
					           	<input id="file_upload" name="file_upload" type="file"/>
					           	<div id="attFileDiv">
					           		<c:forEach var="att" items="${attList }" varStatus="vs">
					             		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font><img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer;"></div>
					           		</c:forEach>
					           	</div>
					        </td>
					     </tr>
					     <tr>  
					        <td class="tdTitle">运行维护内容：</td>
					        <td colspan="3">
					           	<textarea rows="4" style="width: 650px;" id="runContent" name="runContent">${runRecord.runContent}</textarea>
					        </td>
					     </tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/runrecord/save.do">
			<a  onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	
	</form>
  </body>
</html>
