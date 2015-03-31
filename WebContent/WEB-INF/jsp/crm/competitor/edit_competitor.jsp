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
	  		$("#CompetitorForm").validate({
				rules: {
					competitorName: {required:true,maxlength:80},
					companyScale: {required:true,maxlength:20},
					companyProperty: {required:true,maxlength:20},
					companyDesc: {required:true,maxlength:800},
					superiority: {required:true,maxlength:800},
					disadvantages: {required:true,maxlength:800}
				}
			})
	  		
	  		//ajax提交
	  	    $("#CompetitorForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchCompetitor(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#CompetitorForm").submit();
	  	}
	  	
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="竞争对手">
			  	<form id="CompetitorForm" action="${pageContext.request.contextPath}/competitor/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${competitor.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${competitor.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${competitor.creator }">
			  		<input type="hidden" id="deleteFlag" name="deleteFlag" value="${competitor.deleteFlag }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${competitor.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle">竞争对手名称：</td>
					                <td colspan="3">
					                	<input type="text" id="competitorName" name="competitorName" value="${competitor.competitorName }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">企业规模：</td>
					                <td style="width: 35%;">
					                	<input type="text" id="companyScale" name="companyScale" value="${competitor.companyScale }">
					                </td>
					                <td class="tdTitle" style="width: 15%;">企业性质：</td>
					                <td>
					                	<input type="text" id="companyProperty" name="companyProperty" value="${competitor.companyProperty }">
					                </td>
					            </tr> 
					            <tr>  
					                <td class="tdTitle">描述：</td>
					                <td colspan="3">
					                	<textarea rows="8" style="width: 80%" id="companyDesc" name="companyDesc">${competitor.companyDesc}</textarea>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">优势：</td>
					                <td colspan="3">
					                	<textarea rows="7" style="width: 80%" id="superiority" name="superiority">${competitor.superiority}</textarea>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">劣势：</td>
					                <td colspan="3">
					                	<textarea rows="7" style="width: 80%" id="disadvantages" name="disadvantages">${competitor.disadvantages}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/competitor/save.do">
			<a  onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
