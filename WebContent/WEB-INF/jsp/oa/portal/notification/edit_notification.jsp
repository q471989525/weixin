<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	
	
	<%-- 建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败 --%>
  	<script type="text/javascript">
  		var editor = null;
	  	$(document).ready(function(){

	  		//表单验证
	  		$("#notificationForm").validate({
				rules: {
					notifyTitle: "required",
					notifyType: "required"
				}
			})
	  		/*
			var options = {
	  			beforeSubmit: showRequest, //提交前的回调函数
		        dataType:"json",
		        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchNotification(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");
	            	}
	  	        } 
		    };
	  		$("#notificationForm").submit(function() { 
	  	        $(this).ajaxSubmit(options); 
	  	        return false;
	  	    });*/
	  		
	  		//ajax提交
	  	    $("#notificationForm").ajaxForm({
	  	        dataType:"json", 
	  	      	beforeSubmit: showRequest, //提交前的回调函数
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchNotification(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");
	            	}
	  	        } 
	  	    });
	  		
			editor = UE.getEditor("notifyContent"); //初始化,放到最后不影响附件初始化
	  		
	  	});
	  	
	  	//提交前的回调函数
		function showRequest(formData, jqForm, options){
			if(editor.hasContents()){
				return true;
	  		}else{
	  			$.messager.alert("警告", "内容不能为空！", "warning");
	  			return false;
	  		}
	  	}
	  	
	  	//发布
	  	function releaseForm(){
	  		var userids = $("#userids").val();
	  		var userNames = $("#userNames").val();
	  		if(userNames =="" || userids ==""){
	  			$.messager.alert("警告", "用户不能为空！", "warning");
	  			return;
	  		}
	  		$("#releaseFlag").val("y");
	  		$("#submitType").val("release");
	  		$("#notificationForm").submit();   //提交Form
	  	}
	  	
	  	//保存
	  	function submitForm(){
	  		$("#submitType").val("save");
	  		$("#notificationForm").submit();   //提交Form
	  	}
	  	
	  	//选择用户
	  	function selectUser(){
	  		Common.multiUserSelect({userId:$("#userids").val(),userName:$("#userNames").val(),limitSize:1000});
	  	}
	  	//回调选中的用户
	  	function selectedMultiUsers(users){
	  		if(users){
		  		var id = new Array();
	  			var name = new Array();
	  			for(var i=0; i<users.length; i++){
	  				id.push(users[i].userid);
	  				name.push(users[i].username);
	  			}
	  			$("#userids").val(id.toString());
	  			$("#userNames").val(name.toString());
	  		}
	  	}
	  	//清空用户
	  	function cleanUsers(){
	  		$("#userids").val("");
	  		$("#userNames").val("");
	  	}
	  	
	  	//添加上传文件
	  	function appendFile(obj){
	  		$("#attFileDiv").append("<div id='"+obj.resourceid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.resourceid+"')\">．"+obj.fileName+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.resourceid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer; width: 11px; height: 11px;'></div>");
	  	}
	  	
	  	//删除文件
	  	function deleteFile(id){
	  		var url = "${pageContext.request.contextPath}/pubattachment/delete.do";
	  		$.post(url, { "fileid": id }, function(){
	  			$("#"+id).remove();
	  		}, "json");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="通知管理">
			  	<form id="notificationForm" action="${pageContext.request.contextPath}/notification/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${notification.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${notification.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${notification.creator }">
			  		<input type="hidden" id="createDept" name="createDept" value="${notification.createDept }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${notification.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="releaseFlag" name="releaseFlag" value="${notification.releaseFlag }">
			  		<input type="hidden" id="releaseTime" name="releaseTime" value="<fmt:formatDate value="${notification.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="viewCount" name="viewCount" value="${notification.viewCount }">
			  		<input type="hidden" id="topFlag" name="topFlag" value="${notification.topFlag }">
			  		<input type="hidden" id="topTime" name="topTime" value="<fmt:formatDate value="${notification.topTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="attachmentId" name="attachmentId" value="${notification.attachmentId }">
			  		
			  		<input type="hidden" id="submitType" name="submitType" value="">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 10%;">标题：</td>
					                <td>
					                	<input type="text" id="notifyTitle" name="notifyTitle" value="${notification.notifyTitle }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">类型：</td>
					                <td>
					                	<app:select name="notifyType" dictionaryCode="D_Notification_Type" value="${notification.notifyType}"/>
					                </td>
					            </tr> 
					            <tr>
					                <td class="tdTitle">用户：</td>
					                <td>
					                	<input id="userids" name="userids" value="${userids }" type="hidden">
					                	<textarea id="userNames" name="userNames" rows="4" style="width: 80%;" readonly="readonly">${usernames }</textarea>
					                	<input type="button" value="选择" onclick="selectUser()"/>
					                	<input type="button" value="清除" onclick="cleanUsers()"/>
					                </td>
					            </tr> 
					            <tr>
					                <td class="tdTitle">内容：</td>
					                <td>
					                	<script id="notifyContent" name="notifyContent" type="text/plain" style="width:950px;height:300px;">${contentDesc}</script>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">附件：</td>
					                <td>
					                	<iframe src="${pageContext.request.contextPath}/common/uploadify/fileUpload.jsp?attachmentId=${notification.attachmentId}" frameborder="0" style="width: 99%; height: 23px;" scrolling="no"></iframe>
					                	<div id="attFileDiv">
					                		<c:forEach var="att" items="${attList }" varStatus="vs">
					                		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">．${att.fileName}</a></font>&nbsp;<img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer; width: 11px; height: 11px;"></div>
					                		</c:forEach>
					                	</div>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
				<%--
				<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<tbody>
						<tr>
							<td class="tdTitle" style="width: 10%;">附件：</td>
					        <td>
					            <input id="file_upload" name="file_upload" type="file"/>
					            <div id="attFileDiv">
					                <c:forEach var="att" items="${attList }" varStatus="vs">
					                <div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font><img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer;"></div>
					            	</c:forEach>
					        	</div>
					    	</td>
						</tr>
					</tbody>
				</table> --%>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/notification/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
			<a onclick="releaseForm();" data-options="iconCls:'icon-ok'" class="easyui-linkbutton">发布</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
