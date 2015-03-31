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
	  		$("#formForm").validate({
				rules: {
					elementId: "required",
					elementName: "required",
					elementType: "required",
					orderBy: {required:true,digits:true},
					remark: {maxlength:250}
				}
			})
	  		
	  		//ajax提交
	  	    $("#formForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchForm();
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#formForm").submit();
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="${processName} - 表单元素">
			  	<form id="formForm" action="${pageContext.request.contextPath}/wfDefineForm/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${form.resourceid }">
			  		<input type="hidden" id="processId" name="processId" value="${form.processId }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 20%;">元素ID：</td>
					                <td>
					                	<input type="text" id="elementId" name="elementId" value="${form.elementId }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">元素名称：</td>
					                <td>
					                	<input type="text" id="elementName" name="elementName" value="${form.elementName }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">元素类型：</td>
					                <td>
					                	<select id="elementType" name="elementType">
											<option value="">请选择...</option>
											<option value="text" <c:if test="${form.elementType eq 'text'}">selected="selected"</c:if>>文本</option>
											<option value="textarea" <c:if test="${form.elementType eq 'textarea'}">selected="selected"</c:if>>文本域</option>
											<option value="hidden" <c:if test="${form.elementType eq 'hidden'}">selected="selected"</c:if>>隐藏</option>
											<option value="select" <c:if test="${form.elementType eq 'select'}">selected="selected"</c:if>>下拉框</option>
											<option value="button" <c:if test="${form.elementType eq 'button'}">selected="selected"</c:if>>按钮</option>
											<option value="checkbox" <c:if test="${form.elementType eq 'checkbox'}">selected="selected"</c:if>>多选框</option>
											<option value="radio" <c:if test="${form.elementType eq 'radio'}">selected="selected"</c:if>>单选框</option>
											<option value="a" <c:if test="${form.elementType eq 'a'}">selected="selected"</c:if>>超链接</option>
											<option value="password" <c:if test="${form.elementType eq 'password'}">selected="selected"</c:if>>密码</option>
										</select>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">子表元素：</td>
					                <td>
					                	<select id="isSub" name="isSub">
											<option value="N">否</option>
											<option value="Y" <c:if test="${form.isSub eq 'Y'}">selected="selected"</c:if>>是</option>
										</select>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${form.orderBy }"></td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td>
					                	<textarea rows="4" style="width: 80%" id="remark" name="remark">${form.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/wfDefineForm/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
