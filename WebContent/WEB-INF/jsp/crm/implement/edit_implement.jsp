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
	  		$("#implementForm").validate({
				rules: {
					customerName: "required",
					projectName: "required",
					dutyPerson: {required:true,maxlength:20},
					dutyPersonPhone: {required:true,maxlength:20}
				}
			})
	  		
	  		//ajax提交
	  	    $("#implementForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchImplement(); //调list页面查询方法
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
		        formData:{"attachmentId":"${implement.attachmentId}"},
		        uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}',
		        buttonText:'上传附件',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	$("#attFileDiv").append("<div id='"+obj.fileid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.fileid+"')\">"+obj.original+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.fileid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer;'></div>");
		        }
			});
	  		
			UE.getEditor("implementContent"); //初始化,放到最后不影响附件初始化
			
	  	});
	  	
	  	function submitForm(){
	  		$("#implementForm").submit();
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
	  	
	  	function appendRow(){
	  		var index = $("#productTab > tbody").children().size();
	  		var rowhtml = "<tr><td><input type='hidden' id='proId' name='proId' value=''><input type='text' id='productName"+index+"' name='productName"+index+"' value='' required maxlength='40' style='width: 95%'></td>"+
	  			"<td><input type='text' id='productModel"+index+"' name='productModel"+index+"' value='' required maxlength='40' style='width: 95%'></td>"+
	  			"<td><input type='text' id='productUnit"+index+"' name='productUnit"+index+"' value='' required maxlength='40' style='width: 95%'></td>"+
	  			"<td><input type='text' id='productNumber"+index+"' name='productNumber"+index+"' value='' required digits='true' maxlength='20' style='width: 95%'></td>"+
	  			"<td><input type='text' id='productRemark"+index+"' name='productRemark"+index+"' value='' maxlength='100' style='width: 95%'></td>"+
	            "<td><input type='button' value='删除' onclick='deleteRow(\"\",this)'/></td></tr>";
			$("#productTab > tbody").append(rowhtml);
	  	}
	  	
	  	function deleteRow(id,btnObj){
	  		if(id!=""&&id.length>0){
	  			var url = "${pageContext.request.contextPath}/implement/delete.do?proId="+id;
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
	  	
	  	function clickRowlog(rowIndex, rowData){
	  		alert(rowDate.resourceid);
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<form id="implementForm" action="${pageContext.request.contextPath}/implement/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="实施信息">
				<input type="hidden" id="resourceid" name="resourceid" value="${implement.resourceid }">
			  	<input type="hidden" id="createId" name="createId" value="${implement.createId }">
			  	<input type="hidden" id="creator" name="creator" value="${implement.creator }">
			  	<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${implement.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  	<input type="hidden" id="deleteFlag" name="deleteFlag" value="${implement.deleteFlag }">
				<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<tbody>
					    <tr>  
					    	<td class="tdTitle" style="width: 15%;">项目名称：</td>
					        <td style="width: 35%;">
					        	<input type="hidden" id="projectId" name="projectId" value="${implement.projectId }">
					            <input type="text" id="projectName" name="projectName" value="${implement.projectName }">
					            <input type="button" value="选择" onclick="openProject()"/>
					            <input type="button" value="清除" onclick="cleanProject()"/>
					        </td>
					        <td class="tdTitle" style="width: 15%;">客户名称：</td>
					        <td>
					          	<input type="text" id="customerName" name="customerName" value="${implement.customerName }">
					        </td>
					    </tr>
					    <tr>
					    	<td class="tdTitle">责任人：</td>
					    	<td>
					    		<input type="text" id="dutyPerson" name="dutyPerson" value="${implement.dutyPerson }">
					    	</td>
					    	<td class="tdTitle">责任人电话：</td>
					    	<td>
					    		<input type="text" id="dutyPersonPhone" name="dutyPersonPhone" value="${implement.dutyPersonPhone }">
					    	</td>
					    </tr>
					    <tr>  
					        <td class="tdTitle">附件：</td>
					        <td colspan="3">
					           	<input type="hidden" id="attachmentId" name="attachmentId" value="${implement.attachmentId }">
					           	<input id="file_upload" name="file_upload" type="file"/>
					           	<div id="attFileDiv">
					           		<c:forEach var="att" items="${attList }" varStatus="vs">
					             		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font><img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer;"></div>
					           		</c:forEach>
					           	</div>
					        </td>
					     </tr>
					     <tr>  
					        <td class="tdTitle">实施内容：</td>
					        <td colspan="3">
					           	<textarea style="width: 820px;height: 300px;" id="implementContent" name="implementContent">${implement.implementContent}</textarea>
					        </td>
					     </tr>
					</tbody>
				</table>
			</div>
			<%-- 产品列表 --%>
			<div title="产品列表">
				<input type="button" value="新增" onclick="appendRow()"/>
				<table id="productTab" style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					<thead>
						<tr class="tdTitle">
							<td style="width: 20%;">物品名称</td>
							<td style="width: 18%;">规格</td>
							<td style="width: 15%;">单位</td>
							<td style="width: 15%;">数量</td>
							<td style="width: 25%;">备注</td>
							<td>&nbsp;</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${products }" varStatus="vs">
							<tr id="${p.resourceid}">
								<td>
							    	<input type="hidden" id="proId" name="proId" value="${p.resourceid }">
							      	<input type="text" id="productName${vs.index}" name="productName${vs.index}" value="${p.productName}" required maxlength="40"  style="width: 95%">
							    </td>
							    <td>
							    	<input type="text" id="productModel${vs.index}" name="productModel${vs.index}" value="${p.productModel}" required maxlength="40"  style="width: 95%">
							    </td>
							    <td>
							    	<input type="text" id="productUnit${vs.index}" name="productUnit${vs.index}" value="${p.productUnit}" required maxlength="20"  style="width: 95%">
							    </td>
							    <td>
							    	<input type="text" id="productNumber${vs.index}" name="productNumber${vs.index}" value="${p.productNumber}" required digits="true" maxlength="20"  style="width: 95%">
							    </td>
							    <td>
							    	<input type="text" id="productRemark${vs.index}" name="productRemark${vs.index}" value="${p.productRemark}" required maxlength="100"  style="width: 95%">
							    </td>
							    <td>
							    	<input type="button" value="删除" onclick="deleteRow('${p.resourceid}',this)"/>
							    </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<%-- 实施日志 --%>
			<c:if test="${not empty logList}">
			<div title="实施日志">
				<table class="easyui-datagrid" data-options="fit:true,onDblClickRow:clickRowlog">
					<thead>
						<tr>
							<th data-options="field:'implementItem',width:120">实施项目</th>
							<th data-options="field:'implementPerson',width:100">实施人</th>
							<th data-options="field:'customerSign',width:100">客户签字</th>
							<th data-options="field:'startTimeFmt',width:150">开始时间</th>
							<th data-options="field:'endTimeFmt',width:150">结束时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="log" items="${logList }" varStatus="vs">
							<tr>
								<td>${app:fmtDictText("D_Implement_Item",log.implementItem) } </td>
								<td>${log.implementPerson }</td>
								<td>${log.customerSign }</td>
								<td>${log.startTimeFmt }</td>
								<td>${log.endTimeFmt }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/implement/save.do">
			<a  onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	
	</form>
  </body>
</html>
