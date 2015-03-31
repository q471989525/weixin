<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>审核</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#workReportForm").validate({
				rules: {
					verifyDesc: "required"
				}
			})
	  		
	  		//ajax提交
	  	    $("#workReportForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "审核成功", "info",function(){
			            	opener.searchWorkReport(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "审核失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  		/* 自动适应文本域大小 */
	  		$("#workReportForm").find("textarea").each(function(){
	  			if(this.value!=""){
	  				if(this.scrollHeight<50){
	  					this.style.height = '50px';
	  			 	}else{
	  				 	this.style.height = (this.scrollHeight+5) + 'px';
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
			<div title="审核工作报告">
			  	<form id="workReportForm" action="${pageContext.request.contextPath}/workreport/saveVerify.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${workReport.resourceid }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">报告人：</td>
					                <td style="width: 30%;">
					                	${workReport.creator }
					                </td>
					                <td class="tdTitle" style="width: 15%;">报告类型：</td>
					                <td>
										<c:if test="${workReport.reportType eq 'd'}">日报</c:if>
										<c:if test="${workReport.reportType eq 'w'}">周报</c:if>
										<c:if test="${workReport.reportType eq 'm'}">月报</c:if>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">开始时间：</td>
					                <td style="width: 30%;">
					                	<fmt:formatDate value="${workReport.startDate}" pattern="yyyy-MM-dd"/>
					                </td>
					                <td class="tdTitle" style="width: 15%;">结束时间：</td>
					                <td>
					                	<fmt:formatDate value="${workReport.endDate}" pattern="yyyy-MM-dd"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">工作内容：</td>
					                <td colspan="3">
					                	<textarea rows="15" style="width: 80%;border: 0px;overflow: hidden;" id="workContent" name="workContent">${workReport.workContent}</textarea>
					                </td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">审核：</td>
					                <td colspan="3">
					                	<textarea rows="5" style="width: 80%;" id="verifyDesc" name="verifyDesc">${workReport.verifyDesc}</textarea>
					                </td>
					            </tr>  
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<c:if test="${workReport.verifyFlag ne 'y'}">
			<a  onclick="submitForm();" data-options="iconCls:'icon-ok'" class="easyui-linkbutton">审核</a>
		</c:if>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
