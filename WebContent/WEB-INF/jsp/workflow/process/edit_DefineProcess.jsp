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
	  		$("#processForm").validate({
				rules: {
					menuId: "required",
					menuUrl: "required",
					orderBy: {required:true,digits:true},
					remark: {maxlength:150}
				}
			})
	  		
	  		//ajax提交
	  	    $("#processForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {	
		                		opener.searchProcess();	
		                	} catch (e) {} //刷新dataGrid页面
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#processForm").submit();
	  	}
	  	
	  	function openMenu(){
			var url = "${pageContext.request.contextPath}/sysresource/select.do";
			Common.dialog({"title":"系统菜单选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedParentMenu(res){
			if(res){
		  		$("#menuId").val(res.resourceid);
		  		$("#menuName").val(res.menuName);
			}
	  	}
	  	function cleanMenu(){
	  		$("#menuId").val("");
	  		$("#menuName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="流程定义">
			  	<form id="processForm" action="${pageContext.request.contextPath}/wfDefineProcess/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${process.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${process.createId }">
			  		<input type="hidden" id="createName" name="createName" value="${process.createName }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${process.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="deleteState" name="deleteState" value="${process.deleteState }">
			  		<input type="hidden" id="isPriority" name="isPriority" value="${process.isPriority }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">菜单名称：</td>
					                <td>
					                	<input type="text" id="menuName" name="menuName" value="${process.menuName }" style="width: 50%" readonly="readonly">
								  		<input type="hidden" id="menuId" name="menuId" value="${process.menuId }">
					                	<input type="button" value="选择" onclick="openMenu()"/>
					                	<input type="button" value="清除" onclick="cleanMenu()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">是否可用：</td>
					                <td>
					                	<select id="isValid" name="isValid">
					                		<option value="y">可用</option>
					                		<option value="n" <c:if test="${process.isValid eq 'n'}">selected="selected"</c:if>>禁用</option>
					                	</select>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${process.orderBy }"></td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="remark" name="remark">${process.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/wfDefineProcess/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
