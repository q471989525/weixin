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
	  		$("#workReportForm").validate({
				rules: {
					startDate: "required",
					endDate: "required",
					reportType: "required",
					workContent: "required"
				}
			})
	  		
	  		//ajax提交
	  	    $("#workReportForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchWorkReport(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#workReportForm").submit();
	  	}
	  	
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="工作报告">
			  	<form id="workReportForm" action="${pageContext.request.contextPath}/workreport/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${workReport.resourceid }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${workReport.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">报告人：</td>
					                <td style="width: 30%;">
								  		<input type="hidden" id="createId" name="createId" value="${workReport.createId }">
					                	<input type="text" id="creator" name="creator" value="${workReport.creator }" readonly="readonly">
					                </td>
					                <td class="tdTitle" style="width: 15%;">报告类型：</td>
					                <td>
					                	<select id="reportType" name="reportType">
											<option value="">请选择...</option>
											<option value="d" <c:if test="${workReport.reportType eq 'd'}">selected="selected"</c:if> >日报</option>
											<option value="w" <c:if test="${workReport.reportType eq 'w'}">selected="selected"</c:if> >周报</option>
											<option value="m" <c:if test="${workReport.reportType eq 'm'}">selected="selected"</c:if> >月报</option>
										</select>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">开始时间：</td>
					                <td style="width: 30%;">
					                	<input type="text" id="startDate" name="startDate" value="<fmt:formatDate value="${workReport.startDate}" pattern="yyyy-MM-dd"/>" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})">
					                </td>
					                <td class="tdTitle" style="width: 15%;">结束时间：</td>
					                <td>
					                	<input type="text" id="endDate" name="endDate" value="<fmt:formatDate value="${workReport.endDate}" pattern="yyyy-MM-dd"/>" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">工作内容：</td>
					                <td colspan="3">
					                	<textarea rows="15" style="width: 80%" id="workContent" name="workContent">${workReport.workContent}</textarea>
					                </td>
					            </tr>
					            <c:if test="${workReport.verifyFlag eq 'y'}">
					            <tr>
					            	<td colspan="4">审核内容：${workReport.verifyDesc }</td>
					            </tr>
					            </c:if>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<c:if test="${saveFlag eq 'new'}">
			<app:btnAuth btnUrl="/workreport/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
			</app:btnAuth>
		</c:if>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
