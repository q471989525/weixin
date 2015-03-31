<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/uploadify/uploadify.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.config.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/ueditor.all.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/uploadify/jquery.uploadify.min.js"></script>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#contractForm").validate({
				rules: {
					contractName: {required:true,maxlength:80},
					projectName: "required",
					customerName: "required",
					contractNo: {required:true,maxlength:20},
					contractDate: "required",
					contractAmount: {required:true,number:true},
					contractAmountUpper: "required",
					contractStart: "required",
					contractEnd: "required",
					contractCopis: {required:true,digits:true},
					cooperator: {maxlength:80},
					contractRemark: {maxlength:600},
					marginFlag: "required",
					marginRatio: {required:true,maxlength:20},
					marginAmount: {required:true,number:true},
					marginReceive: "required"
				}
			})
	  		
	  		//ajax提交
	  	    $("#contractForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchContract(); //调list页面查询方法
							} catch (e) { }
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  		//附件上传
			$("#file_upload").uploadify({
				height: 20,
		        width: 80,
		        multi: false,
		        fileSizeLimit: '20MB',
		        fileTypeExts: '*.rar; *.zip; *.7z; *.pdf; *.txt; *.doc; *.docx; *.xlsx; *.xls; *.gif; *.png; *.jpg; *.jpeg;',
		        swf: '${pageContext.request.contextPath}/common/uploadify/uploadify.swf',
		        formData:{"attachmentId":"${contract.attachmentId}"},
		        uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}',
		        buttonText:'上传附件',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	$("#attFileDiv").append("<div id='"+obj.fileid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.fileid+"')\">"+obj.original+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.fileid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer;'></div>");
		        }
			});
	  		
			UE.getEditor("contractContent"); //初始化,放到最后不影响附件初始化
			
			marginView('${contract.marginFlag}');
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#contractForm").submit();
	  	}
	  	
	  	//删除
	  	function deleteFile(id){
	  		var url = "${pageContext.request.contextPath}/pubattachment/delete.do";
	  		$.post(url, { "fileid": id }, function(){
	  			$("#"+id).remove();
	  		}, "json");
	  	}
	  	
	  	//选择项目
	  	function openProject(){
	  		var url = "${pageContext.request.contextPath}/projectInfo/select.do";
			var rv = Common.dialog({"url":url,"width":700,"height":700,modal:true});
			if(rv){
		  		$("#projectId").val(rv.resourceid);
		  		$("#projectName").val(rv.projectName);
		  		$("#customerName").val(rv.customerName);
			}
	  	}
	  	
	  	function cleanProject(){
	  		$("#projectId").val("");
	  		$("#projectName").val("");
	  		$("#customerName").val("");
	  	}
	  	
	  	function marginView(v){
	  		if(v==='y'){
	  			$("#marginSpan").show();
	  			$("#marginRatio").show();
	  			$("#marginTR").show();
	  		}else{
	  			$("#marginSpan").hide();
	  			$("#marginRatio").hide();
	  			$("#marginTR").hide();
	  		}
	  	}
	  	
	  	
	  	function appendRow(){
	  		var index = $("#payTab > tbody").children().size();
	  		var rowhtml = "<tr><td><input type='hidden' id='payId' name='payId' value=''><input type='text' id='periods"+index+"' name='periods"+index+"' value='' required maxlength='20'></td>"+
	  			"<td><input type='text' id='amount"+index+"' name='amount"+index+"' value='' required number='true' maxlength='10'></td>"+
	  			"<td><input type='text' id='payDate"+index+"' name='payDate"+index+"' value='' onclick='WdatePicker()' class='Wdate' required></td>"+
	            "<td><input type='button' value='删除' onclick='deleteRow(\"\",this)'/></td></tr>";
			$("#payTab > tbody").append(rowhtml);
	  	}
	  	
	  	function deleteRow(id,btnObj){
	  		if(id!=""&&id.length>0){
	  			var url = "${pageContext.request.contextPath}/contract/delete.do?payId="+id;
                $.post(url,function(data){
                	if(data.state == "success"){
			  			$("#"+id).remove();
	            	}else{
		                $.messager.alert("错误", "删除失败", "error");
	            	}
                },"json");
	  		}else{
	  			$(btnObj).parent().parent().remove();
	  		}
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<form id="contractForm" action="${pageContext.request.contextPath}/contract/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="合同信息">
				<input type="hidden" id="resourceid" name="resourceid" value="${contract.resourceid }">
			  	<input type="hidden" id="createId" name="createId" value="${contract.createId }">
			  	<input type="hidden" id="creator" name="creator" value="${contract.creator }">
			  	<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${contract.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  	<input type="hidden" id="deleteFlag" name="deleteFlag" value="${contract.deleteFlag }">
				<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<tbody>
					    <tr>
					    	<td class="tdTitle" style="width: 15%;">合同名称：</td>
					    	<td colspan="3">
					    		<input type="text" id="contractName" name="contractName" value="${contract.contractName }" style="width: 80%">
					    	</td>
					    </tr>
					    <tr>  
					    	<td class="tdTitle" style="width: 15%;">项目名称：</td>
					        <td style="width: 35%;">
					        	<input type="hidden" id="projectId" name="projectId" value="${contract.projectId }">
					            <input type="text" id="projectName" name="projectName" value="${contract.projectName }">
					            <input type="button" value="选择" onclick="openProject()"/>
					            <input type="button" value="清除" onclick="cleanProject()"/>
					        </td>
					        <td class="tdTitle" style="width: 15%;">客户名称：</td>
					        <td>
					          	<input type="text" id="customerName" name="customerName" value="${contract.customerName }">
					        </td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">合同编号：</td>
					    	<td>
					    		<input type="text" id="contractNo" name="contractNo" value="${contract.contractNo }">
					    	</td>
					    	<td class="tdTitle">签订日期：</td>
					    	<td>
					    		<input type="text" id="contractDate" name="contractDate" value="<fmt:formatDate value="${contract.contractDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({})" class="Wdate">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">合同金额（小写）：</td>
					    	<td>
					    		<input type="text" id="contractAmount" name="contractAmount" value="${contract.contractAmount }" onblur="numberToUpper(this,'contractAmountUpper')">元
					    	</td>
					    	<td class="tdTitle">合同金额（大写）：</td>
					    	<td>
					    		<input type="text" id="contractAmountUpper" name="contractAmountUpper" value="${contract.contractAmountUpper }">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">合同有效期：</td>
					    	<td colspan="3">
					    		<input type="text" id="contractStart" name="contractStart" value="<fmt:formatDate value="${contract.contractStart}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'contractEnd\');}'})" class="Wdate"> - 
					    		<input type="text" id="contractEnd" name="contractEnd" value="<fmt:formatDate value="${contract.contractEnd}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({minDate:'#F{$dp.$D(\'contractStart\');}'})" class="Wdate">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">合同份数：</td>
					    	<td>
					    		<input type="text" id="contractCopis" name="contractCopis" value="${contract.contractCopis }">
					    	</td>
					    	<td class="tdTitle">合作单位：</td>
					    	<td>
					    		<input type="text" id="cooperator" name="cooperator" value="${contract.cooperator }" style="width: 80%;">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">是否存在保证金：</td>
					    	<td>
					    		<app:select name="marginFlag" dictionaryCode="D_True_False" value="${contract.marginFlag}" onchange="marginView(this.value)"/>
					    	</td>
					    	<td class="tdTitle"><span id="marginSpan" style="display: none;">保证金比例：</span></td>
					    	<td>
					    		<input type="text" id="marginRatio" name="marginRatio" value="${contract.marginRatio }" style="display: none;">&nbsp;
					    	</td>
					    </tr>
					    <tr id="marginTR" style="display: none;">
					    	<td class="tdTitle">保证金金额：</td>
					    	<td>
					    		<input type="text" id="marginAmount" name="marginAmount" value="${contract.marginAmount }">
					    	</td>
					    	<td class="tdTitle">保证收缴时间：</td>
					    	<td>
					    		<input type="text" id="marginReceive" name="marginReceive" value="<fmt:formatDate value="${contract.marginReceive}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({})" class="Wdate">
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">备注：</td>
					    	<td colspan="3">
					           	<textarea rows="4" style="width: 80%;" id="contractRemark" name="contractRemark">${contract.contractRemark}</textarea>
					    	</td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">合同付款：</td>
					    	<td colspan="3">
					        	&nbsp;<input type="button" value="新增" onclick="appendRow()"/>
					        	<table id="payTab" style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
							      	<thead>
							      		<tr class="tdTitle">
							      			<td style="width: 30%;">期数</td>
							      			<td style="width: 30%;">金额</td>
							      			<td style="width: 30%;">预计付款时间</td>
							      			<td>&nbsp;</td>
							      		</tr>
							      	</thead>
							      	<tbody>
							      	<c:forEach var="p" items="${payments }" varStatus="vs">
							      		<tr id="${p.resourceid}">
							      			<td>
							      				<input type="hidden" id="payId" name="payId" value="${p.resourceid }">
							      				<input type="text" id="periods${vs.index}" name="periods${vs.index}" value="${p.periods}" <c:if test="${p.payFlag eq 'y'}">readonly="readonly"</c:if> required maxlength="20">
							      			</td>
							      			<td>
							      				<input type="text" id="amount${vs.index}" name="amount${vs.index}" value="${p.amount}" <c:if test="${p.payFlag eq 'y'}">readonly="readonly"</c:if> required number="true" maxlength="10">
							      			</td>
							      			<td>
							      				<input type="text" id="payDate${vs.index}" name="payDate${vs.index}" value="${p.payDate}" <c:if test="${p.payFlag ne 'y'}">onclick="WdatePicker()"</c:if> class="Wdate" <c:if test="${p.payFlag eq 'y'}">readonly="readonly"</c:if> required>
							      			</td>
							      			<td>
							      				<c:if test="${p.payFlag ne 'y'}"><input type="button" value="删除" onclick="deleteRow('${p.resourceid}',this)"/></c:if>
							      			</td>
							      		</tr>
							      	</c:forEach>
							      	</tbody>
								</table>
					    	</td>
					    </tr>
					    <tr>  
					        <td class="tdTitle">附件：</td>
					        <td colspan="3">
					           	<input type="hidden" id="attachmentId" name="attachmentId" value="${contract.attachmentId }">
					           	<input id="file_upload" name="file_upload" type="file"/>
					           	<div id="attFileDiv">
					           		<c:forEach var="att" items="${attList }" varStatus="vs">
					             		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font><img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer;"></div>
					           		</c:forEach>
					           	</div>
					        </td>
					     </tr>
					     <tr>  
					        <td class="tdTitle">合同内容：</td>
					        <td colspan="3">
					           	<textarea rows="4" style="width: 820px;" id="contractContent" name="contractContent">${contract.contractContent}</textarea>
					        </td>
					     </tr>
					</tbody>
				</table>
			</div>
			<%-- 合同回款 --%>
			<c:if test="${not empty payments}">
			<div title="合同回款">
				<table class="easyui-datagrid" data-options="fit:true">
					<thead data-options="frozen:true"> 
						 <tr>
							<th data-options="field:'periods',width:100">期数</th>
				         </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'amount',width:120,align:'right'">金额</th>
							<th data-options="field:'payDate',width:120">预计付款时间</th>
							<th data-options="field:'payFlag',width:120">支付状态</th>
							<th data-options="field:'actualAmount',width:120,align:'right'">实际支付金额</th>
							<th data-options="field:'updateName',width:100">收款人</th>
							<th data-options="field:'updateTime',width:120">支付时间</th>
							<th data-options="field:'remark',width:260">备注</th>
							<th data-options="field:'creator',width:100">创建人</th>
							<th data-options="field:'createTime',width:120">创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${payments }" varStatus="vs">
							<tr>
								<td>${p.periods }</td>
								<td>
									<fmt:formatNumber value="${p.amount}" type="currency"/>
								</td>
								<td>
									<fmt:formatDate value="${p.payDate}"/>
								</td>
								<td>${app:fmtDictText("D_Pay_Status",p.payFlag) } </td>
								<td>
									<fmt:formatNumber value="${p.actualAmount}" type="currency"/>
								</td>
								<td>${p.updateName }</td>
								<td><fmt:formatDate value="${p.updateTime}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td>${p.remark }</td>
								<td>${p.creator }</td>
								<td><fmt:formatDate value="${p.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/contract/save.do">
			<a  onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	
	</form>
  </body>
</html>
