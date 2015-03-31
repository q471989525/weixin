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
	  		$("#roleForm").validate({
				rules: {
					roleName: "required",
					roleCode: {required:true, remote:"${pageContext.request.contextPath}/sysrole/unique.do?resourceid=${role.resourceid}"},
					orderBy: {digits:true},
					remark: {maxlength:150}
				},messages: {
					roleCode: {remote:"角色编码已经存在！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#roleForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchRole();
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#roleForm").submit();
	  	}
	  	
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="系统角色">
			  	<form id="roleForm" action="${pageContext.request.contextPath}/sysrole/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${role.resourceid }">
					   	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 20%;">角色名称：</td>
					                <td><input type="text" id="roleName" name="roleName" value="${role.roleName }" style="width: 50%;"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">角色编码<font color="red">(唯一)</font>：</td>
					                <td><input type="text" id="roleCode" name="roleCode" value="${role.roleCode }" style="width: 50%;"></td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${role.orderBy }"></td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td>
					                	<textarea rows="4" style="width: 80%" id="remark" name="remark">${role.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysrole/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
