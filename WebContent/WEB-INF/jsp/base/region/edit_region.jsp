<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑<c:if test="${region.regionType eq '1'}">省</c:if><c:if test="${region.regionType eq '2'}">市</c:if></title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#regionForm").validate({
				rules: {
					regionName: {required:true,maxlength:20},
					orderBy: {required:true,digits:true}
				}
			})
	  		
	  		//ajax提交
	  	    $("#regionForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	if(${region.regionType eq '1'}) opener.searchProvince();
		                	if(${region.regionType eq '2'}) opener.searchCity();
		                	window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#regionForm").submit();
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="<c:if test="${region.regionType eq '1'}">省</c:if><c:if test="${region.regionType eq '2'}">市</c:if>">
			  	<form id="regionForm" action="${pageContext.request.contextPath}/pubglobalregion/<c:if test="${region.regionType eq '1'}">saveProvince.do</c:if><c:if test="${region.regionType eq '2'}">saveCity.do</c:if>" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${region.resourceid }">
			  		<input type="hidden" id="parentId" name="parentId" value="${region.parentId }">
			  		<input type="hidden" id="regionType" name="regionType" value="${region.regionType }">
			  		<input type="hidden" id="deleteState" name="deleteState" value="${region.deleteState }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 20%;">
					                	<c:if test="${region.regionType eq '1'}">省</c:if>
					                	<c:if test="${region.regionType eq '2'}">市</c:if>：
					                </td>
					                <td>
					                	<input type="text" id="regionName" name="regionName" value="${region.regionName }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${region.orderBy }"></td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<c:if test="${region.regionType eq '1'}"><%-- 省 --%>
			<app:btnAuth btnUrl="/pubglobalregion/saveProvince.do">
				<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
			</app:btnAuth>
		</c:if>
		<c:if test="${region.regionType eq '2'}"><%-- 市 --%>
			<app:btnAuth btnUrl="/pubglobalregion/saveCity.do">
				<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
			</app:btnAuth>
		</c:if>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
