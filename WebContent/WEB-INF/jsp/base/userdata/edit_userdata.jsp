<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#useDataForm").validate({
				rules: {
					userId: "required",
					resourceId: "required",
					hqlCondition: "required",
					orderBy: {digits:true},
					remark: {maxlength:250}
				}
			})
	  		
	  		//ajax提交
	  	    $("#useDataForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchUserData();
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#useDataForm").submit();
	  	}
	  	
	  	//选择资源
	  	function openMenu(){
	  		var url = "${pageContext.request.contextPath}/sysresource/select.do";
			Common.dialog({"title":"系统资源选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedParentMenu(res){
			if(res){
		  		$("#resourceId").val(res.resourceid);
		  		$("#resourceName").val(res.menuName);
			}
	  	}
	  	function cleanMenu(){
	  		$("#resourceId").val("");
	  		$("#resourceName").val("");
	  	}
	  	
	  	//选择用户
	  	function selectUser(){
	  		Common.singleUserSelect({"userId":$("#userId").val(),"userName":$("#userName").val(),"hideState":"y"});
	  	}
	  	//回调选中的用户
	  	function selectedSingleUser(user){
	  		if(user){
	  			$("#userId").val(user.userid);
	  			$("#userName").val(user.username);
	  		}
	  	}
	  	function cleanUser(){
	  		$("#userId").val("");
	  		$("#userName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="系统用户数据权限">
			  	<form id="useDataForm" action="${pageContext.request.contextPath}/sysUserDataLimit/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${userdate.resourceid }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 20%;">用户名称：</td>
					                <td>
					                	<input type="text" id="userName" name="userName" value="${userdate.userName }" style="width: 200px;">
								  		<input type="hidden" id="userId" name="userId" value="${userdate.userId }">
					                	<input type="button" value="选择" onclick="selectUser()"/>
					                	<input type="button" value="清除" onclick="cleanUser()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">资源名称：</td>
					                <td>
					                	<input type="text" id="resourceName" name="resourceName" value="${userdate.resourceName }" style="width: 200px;">
								  		<input type="hidden" id="resourceId" name="resourceId" value="${userdate.resourceId }">
					                	<input type="button" value="选择" onclick="openMenu()"/>
					                	<input type="button" value="清除" onclick="cleanMenu()"/>
					                </td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">HQL条件：</td>
					                <td>
					                	<textarea rows="5" style="width: 80%" id="hqlCondition" name="hqlCondition">${userdate.hqlCondition}</textarea>
					                	<br>
					                	<font color="red">当HQL条件为1=1，则拥有全部查看权限</font>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td>
					                	<textarea rows="4" style="width: 80%" id="remark" name="remark">${userdate.remark}</textarea>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${userdate.orderBy }"></td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysUserDataLimit/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
