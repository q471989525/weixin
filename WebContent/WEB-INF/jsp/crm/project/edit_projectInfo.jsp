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
  		var editor = null;
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#projectInfoForm").validate({
				rules: {
					projectName: {required:true,maxlength:80},
					customerName: "required",
					procurementMethod: {required:true,maxlength:20},
					fileNo: {required:true,maxlength:50},
					priceLimit: {required:true, number:true, maxlength:10},
					priceLimit2: "required",
					timeLimit: {required:true,maxlength:50},
					openTime: "required",
					openAddress: {required:true,maxlength:80}
					//competitorName: "required",
					//contactsName: "required"
				}
			})
	  		
	  		//ajax提交
	  	    $("#projectInfoForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchProject();
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
		        formData:{"attachmentId":"${projectInfo.attachmentId}"},
		        uploader: '${pageContext.request.contextPath}/fileupload;jsessionid=${pageContext.session.id}',
		        buttonText:'上传附件',
		        onUploadSuccess : function(file, data, response) {
		        	var obj = jQuery.parseJSON(data);
		        	$("#attFileDiv").append("<div id='"+obj.fileid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.fileid+"')\">"+obj.original+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.fileid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer;'></div>");
		        }
			});
	  		
			UE.getEditor("projectRequire"); //初始化,放到最后不影响附件初始化
	  	
	  	});
	  	
	  	function submitForm(){
	  		$("#projectInfoForm").submit();
	  	}
	  	
	 	//删除
	  	function deleteFile(id){
	  		var url = "${pageContext.request.contextPath}/pubattachment/delete.do";
	  		$.post(url, { "fileid": id }, function(){
	  			$("#"+id).remove();
	  		}, "json");
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
	  	
	  	//选择客户联系人
	  	function openContactsList(){
	  		var customerId = $("#customerId").val();
	  		if(customerId === ""){
	  			$.messager.alert("警告", "请先选择客户", "warning");
	  			return;
	  		}
	  		var url = "${pageContext.request.contextPath}/customerContacts/select.do?customerId="+customerId;
			var rv = Common.dialog({"url":url,"width":600,"height":800,modal:true});
			if(rv){
				var id = []; var name = [];
				for(var i=0; i<rv.length; i++){
					id.push(rv[i].resourceid);
					name.push(rv[i].userName);
				}
				if(id.length>0){
			  		$("#contactsIds").val(id.join(","));
			  		$("#contactsName").val(name.join(","));
				}
			}
	  	}
	  	
	  	function cleanContacts(){
	  		$("#contactsIds").val("");
	  		$("#contactsName").val("");
	  	}
	  	
	  	//选择竞争对手
	  	function openCompetitor(){
	  		var url = "${pageContext.request.contextPath}/competitor/select.do";
			var rv = Common.dialog({"url":url,"width":600,"height":800,modal:true});
			if(rv){
				var id = []; var name = [];
				for(var i=0; i<rv.length; i++){
					id.push(rv[i].resourceid);
					name.push(rv[i].competitorName);
				}
				if(id.length>0){
			  		$("#competitorIds").val(id.join(","));
			  		$("#competitorName").val(name.join(","));
				}
			}
	  	}
	  	
	  	function cleanCompetitor(){
	  		$("#competitorIds").val("");
	  		$("#competitorName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="项目信息">
			  	<form id="projectInfoForm" action="${pageContext.request.contextPath}/projectInfo/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${projectInfo.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${projectInfo.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${projectInfo.creator }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${projectInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">采购项目名称：</td>
					                <td colspan="3"><input type="text" id="projectName" name="projectName" value="${projectInfo.projectName }" style="width: 80%"></td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">客户名称：</td>
					                <td style="width: 30%">
					                	<input type="hidden" id="customerId" name="customerId" value="${projectInfo.customerId }">
					                	<input type="text" id="customerName" name="customerName" value="${projectInfo.customerName }">
					                	<input type="button" value="选择" onclick="openCustomerList()"/>
					                	<input type="button" value="清除" onclick="cleanCustomer()"/>
					                </td>
					                <td class="tdTitle" style="width: 15%;">采购方式：</td>
					                <td>
					                	<input type="text" id="procurementMethod" name="procurementMethod" value="${projectInfo.procurementMethod }">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">招标文件编号：</td>
					                <td>
					                	<input type="text" id="fileNo" name="fileNo" value="${projectInfo.fileNo }">
					                </td>
					                <td class="tdTitle">工期要求：</td>
					                <td>
					                	<input type="text" id="timeLimit" name="timeLimit" value="${projectInfo.timeLimit }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">最高限价(小写)：</td>
					                <td>
					                	<input type="text" id="priceLimit" name="priceLimit" value="${projectInfo.priceLimit }" onblur="numberToUpper(this,'priceLimit2')">元
					                </td>
					                <td class="tdTitle">最高限价(大写)：</td>
					                <td>
					                	<input type="text" id="priceLimit2" name="priceLimit2" value="${projectInfo.priceLimit2 }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">投标截止/开标时间：</td>
					                <td>
					                	<input type="text" id="openTime" name="openTime" value="<fmt:formatDate value="${projectInfo.openTime}" pattern="yyyy-MM-dd HH:mm"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate">
					                </td>
					                <td class="tdTitle">开标地点：</td>
					                <td>
					                	<input type="text" id="openAddress" name="openAddress" value="${projectInfo.openAddress }">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">联系人：</td>
					                <td colspan="3">
					                	<input type="hidden" id="contactsIds" name="contactsIds" value="${projectInfo.contactsIds }">
					                	<input type="text" id="contactsName" name="contactsName" value="${projectInfo.contactsName }" style="width: 80%">
					                	<input type="button" value="选择" onclick="openContactsList()"/>
					                	<input type="button" value="清除" onclick="cleanContacts()"/>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">竞争对手：</td>
					                <td colspan="3">
					                	<input type="hidden" id="competitorIds" name="competitorIds" value="${projectInfo.competitorIds }">
					                	<input type="text" id="competitorName" name="competitorName" value="${projectInfo.competitorName }" style="width: 80%">
					                	<input type="button" value="选择" onclick="openCompetitor()"/>
					                	<input type="button" value="清除" onclick="cleanCompetitor()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">招标文件：</td>
					                <td colspan="3">
					                	<input type="hidden" id="attachmentId" name="attachmentId" value="${projectInfo.attachmentId }">
					                	<input id="file_upload" name="file_upload" type="file"/>
					                	<div id="attFileDiv">
					                		<c:forEach var="att" items="${attList }" varStatus="vs">
					                		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font><img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer;"></div>
					                		</c:forEach>
					                	</div>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">项目内容及需求：</td>
					                <td colspan="3">
					                	<textarea rows="5" style="width: 820px;" id="projectRequire" name="projectRequire">${projectInfo.projectRequire}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
			<c:if test="${not empty contactlist}">
			<div title="联系人信息">
				<table class="easyui-datagrid" data-options="fit:true">
					<thead data-options="frozen:true"> 
						 <tr>
							<th data-options="field:'userName',width:120">姓名</th>
				         </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'userSex',width:60">性别</th>
							<th data-options="field:'userDept',width:120">部门</th>
							<th data-options="field:'userPost',width:120">职称</th>
							<th data-options="field:'telphone',width:120">电话</th>
							<th data-options="field:'mobile',width:120">手机</th>
							<th data-options="field:'fax',width:120">传真</th>
							<th data-options="field:'email',width:150">电子邮箱</th>
							<th data-options="field:'qq',width:120">QQ</th>
							<th data-options="field:'remark',width:300">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${contactlist }" varStatus="vs">
							<tr>
								<td>${c.userName }</td>
								<td>${app:fmtDictText("D_Sex",c.userSex) }</td>
								<td>${c.userDept }</td>
								<td>${c.userPost }</td>
								<td>${c.telphone }</td>
								<td>${c.mobile }</td>
								<td>${c.fax }</td>
								<td>${c.email }</td>
								<td>${c.qq }</td>
								<td>${c.remark }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
			
			<c:if test="${not empty competitors}">
			<div title="竞争对手信息">
				<table class="easyui-datagrid" data-options="fit:true">
					<thead data-options="frozen:true"> 
						<tr>
							<th data-options="field:'competitorName',width:200">竞争对手</th>
				        </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'companyScale',width:100">企业规模</th>
							<th data-options="field:'companyProperty',width:100">企业性质</th>
							<th data-options="field:'companyDesc',width:350">描述</th>
							<th data-options="field:'superiority',width:350">优势</th>
							<th data-options="field:'disadvantages',width:350">劣势</th>
							<th data-options="field:'createTimeFmt',width:120">创建时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${competitors }" varStatus="vs">
							<tr>
								<td>${c.competitorName }</td>
								<td>${c.companyScale }</td>
								<td>${c.companyProperty }</td>
								<td>${c.companyDesc }</td>
								<td>${c.superiority }</td>
								<td>${c.disadvantages }</td>
								<td>${c.createTimeFmt }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
			
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
