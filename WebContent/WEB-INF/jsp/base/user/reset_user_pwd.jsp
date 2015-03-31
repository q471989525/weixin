<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>修改密码</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#userForm").validate({
				rules: {
					password: {required:true, rangelength:[4,16], remote:"${pageContext.request.contextPath}/sysuser/validatePwd.do?resourceid=${user.resourceid}"},
					newpassword: {required:true, rangelength:[4,16]},
					newpassword2: {required:true, rangelength:[4,16], equalTo:"#newpassword"}
				},messages: {
					password: {remote:"密码不正确！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#userForm").ajaxForm({
	  	        dataType:"json",
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "修改成功", "info",function(){
			            	//window.returnValue = true;
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "修改失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#userForm").submit();
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="修改用户密码">
			  	<form id="userForm" action="${pageContext.request.contextPath}/sysuser/savePwd.do" method="post">
		  			<input type="hidden" id="resourceid" name="resourceid" value="${user.resourceid }">
				   	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
				        <tbody>
				            <tr>  
				                <td class="tdTitle" style="width: 30%;">用户名称：</td>
				                <td>${user.username }</td>
				            </tr>
				            <tr>
				                <td class="tdTitle">登录账号：</td>
				                <td>${user.loginId }</td>
				            </tr>
				            <tr>
				                <td class="tdTitle">旧密码：</td>
				                <td><input type="password" id="password" name="password" value=""></td>
				            </tr>  
				            <tr>
				                <td class="tdTitle">新密码：</td>
				                <td><input type="password" id="newpassword" name="newpassword" value=""></td>
				            </tr>  
				            <tr>
				                <td class="tdTitle">确认新密码：</td>
				                <td><input type="password" id="newpassword2" name="newpassword2" value=""></td>
				            </tr>  
				        </tbody>
				    </table>
				</form>
			</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
