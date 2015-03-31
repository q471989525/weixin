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
	  		$("#paymentForm").validate({
				rules: {
					actualAmount: {required:true,number:true}
				}
			})
	  		
	  		//ajax提交
	  	    $("#paymentForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchPayment(); //调list页面查询方法
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
	  		var amount = $("#amount").val();
	  		var actualAmount = $("#actualAmount").val();
	  		if(parseFloat(amount) === parseFloat(actualAmount)){
	  			$("#paymentForm").submit();
	  		}else{
	  			$.messager.confirm("确认?", "实际支付金额与待付金额不相等，结余将转到下期支付。", function(r){
	  				if (r){
				  		$("#paymentForm").submit();
	                }
	            });
	  		}
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<form id="paymentForm" action="${pageContext.request.contextPath}/contractPayment/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="付款信息">
				<input type="hidden" id="resourceid" name="resourceid" value="${payment.resourceid }">
			  	<input type="hidden" id="createId" name="createId" value="${payment.createId }">
			  	<input type="hidden" id="deleteFlag" name="deleteFlag" value="${payment.deleteFlag }">
				<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<tbody>
					    <tr>  
					    	<td class="tdTitle" style="width: 15%;">合同编号：</td>
					        <td style="width: 35%;">
					        	<input type="hidden" id="contractId" name="contractId" value="${payment.contractId }">
					            <input type="text" id="contractNo" name="contractNo" value="${payment.contractNo }" readonly="readonly">
					        </td>
					        <td class="tdTitle" style="width: 15%;">期数：</td>
					        <td>
					          	<input type="text" id="periods" name="periods" value="${payment.periods }" readonly="readonly">
					        </td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">金额：</td>
					    	<td>
					    		<input type="text" id="amount" name="amount" value="${payment.amount }" readonly="readonly">元
					    	</td>
					    	<td class="tdTitle">预计付款时间：</td>
					    	<td>
					    		<input type="text" id="payDate" name="payDate" value="<fmt:formatDate value="${payment.payDate}" pattern="yyyy-MM-dd"/>" class="Wdate" readonly="readonly">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">实际支付金额：</td>
					    	<td>
					    		<input type="text" id="actualAmount" name="actualAmount" value="${payment.actualAmount }">元
					    	</td>
					    	<td class="tdTitle">创建人：</td>
					    	<td>
					    		<input type="text" id="creator" name="creator" value="${payment.creator }" readonly="readonly">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">创建时间：</td>
					    	<td colspan="3">
					    		<input type="text" id="createTime" name="createTime" value="<fmt:formatDate value="${payment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
					    	</td>
					    </tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/contractPayment/save.do">
			<c:if test="${payment.payFlag eq 'n'}"><a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a></c:if>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	
	</form>
  </body>
</html>
