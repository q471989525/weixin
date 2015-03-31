<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#CustomerContactsForm").validate({
				rules: {
					userName: {required:true,maxlength:20},
					customerName: "required",
					userSex: "required",
					userDept: {required:true,maxlength:50},
					userPost: {required:true,maxlength:50},
					mobile: {required:true,maxlength:50},
					telphone: {maxlength:50},
					email: {maxlength:80},
					qq: {maxlength:20},
					postCode: {maxlength:20},
					address: {maxlength:80},
					remark: {maxlength:400}
				}
			})
	  		
	  		//ajax提交
	  	    $("#CustomerContactsForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchCustomerContacts();
							} catch (e) { }
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#CustomerContactsForm").submit();
	  	}
	  	//选择客户
	  	function openCustomerList(){
	  		var url = "${pageContext.request.contextPath}/customerinfo/select.do";
			var rv = Common.dialog({"url":url,"width":600,"height":800,modal:true});
			if(rv){
				var customer = rv.split("=");
		  		$("#customerId").val(customer[0]);
		  		$("#customerName").val(customer[1]);
			}
	  	}
	  	
	  	function cleanCustomer(){
	  		$("#customerId").val("");
	  		$("#customerName").val("");
	  	}
	  	
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="联系人信息">
			  	<form id="CustomerContactsForm" action="${pageContext.request.contextPath}/customerContacts/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${contacts.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${contacts.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${contacts.creator }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${contacts.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">姓名：</td>
					                <td style="width: 30%"><input type="text" id="userName" name="userName" value="${contacts.userName }"></td>
					                <td class="tdTitle" style="width: 15%;">客户名称：</td>
					                <td>
					                	<input type="hidden" id="customerId" name="customerId" value="${contacts.customerId }">
					                	<input type="text" id="customerName" name="customerName" value="${contacts.customerName }">
					                	<input type="button" value="选择" onclick="openCustomerList()"/>
					                	<input type="button" value="清除" onclick="cleanCustomer()"/>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">性别：</td>
					                <td style="width: 30%;">
					                	<app:select name="userSex" dictionaryCode="D_Sex" value="${contacts.userSex}"/>
					                </td>
					                <td class="tdTitle" style="width: 15%;">部门：</td>
					                <td>
					                	<input type="text" id="userDept" name="userDept" value="${contacts.userDept }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">职称：</td>
					                <td>
					                	<input type="text" id="userPost" name="userPost" value="${contacts.userPost }">
					                </td>
					                <td class="tdTitle">电话：</td>
					                <td>
					                	<input type="text" id="telphone" name="telphone" value="${contacts.telphone }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">手机：</td>
					                <td>
					                	<input type="text" id="mobile" name="mobile" value="${contacts.mobile }">
					                </td>
					                <td class="tdTitle">传真：</td>
					                <td>
					                	<input type="text" id="fax" name="fax" value="${contacts.fax }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">电子邮箱：</td>
					                <td>
					                	<input type="text" id="email" name="email" value="${contacts.email }">
					                </td>
					                <td class="tdTitle">QQ：</td>
					                <td>
					                	<input type="text" id="qq" name="qq" value="${contacts.qq }">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">邮编：</td>
					                <td colspan="3">
					                	<input type="text" id="postCode" name="postCode" value="${contacts.postCode }">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">联系地址：</td>
					                <td colspan="3">
					                	<input type="text" id="address" name="address" value="${contacts.address }" style="width: 80%">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="5" style="width: 80%" id="remark" name="remark">${contacts.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/customerContacts/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
