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
	  		$("#leaveForm").validate({
				rules: {
					leaveType: "required",
					startTime: "required",
					endTime: "required",
					sumDay: {required:true,number:true},
					applyReason: "required"
				}
			})
	  		
	  		//ajax提交
	  	    $("#leaveForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchLeave(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(t){
	  		if(t=="submit"){
	  			$("input[id^='nextActCandidateId_']").addClass("required");
	  		}else{
	  			$("input[id^='nextActCandidateId_']").removeClass();
	  		}
	  		$("#submitType").val(t);
	  		$("#leaveForm").submit();
	  	}
	  	
	  	//选择用户
	  	var actPersonId = "";
	  	var actPersonName = "";
	  	function selectActUser(id,name){
	  		actPersonId = id;
	  		actPersonName = name;
	  		var ids = $("#"+id).val();
	  		var names = $("#"+name).val();
	  		Common.multiUserSelect({userId:ids,userName:names});
	  	}
	  	//回调选中的用户
	  	function selectedMultiUsers(users){
	  		if(users){
		  		var id = new Array();
	  			var name = new Array();
	  			for(var i=0; i<users.length; i++){
	  				id.push(users[i].userid);
	  				name.push(users[i].username);
	  			}
	  			$("#"+actPersonId).val(id.toString());
	  			$("#"+actPersonName).val(name.toString());
	  		}
	  	}
	  	//清空用户
	  	function cleanActUsers(id,name){
	  		$("#"+id).val("");
	  		$("#"+name).val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
			  	<form id="leaveForm" action="${pageContext.request.contextPath}/leaveApply/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="请假申请">
			
			  		<input type="hidden" id="defineProcessId" name="defineProcessId" value="${defineProcess.resourceid }">
			  		<input type="hidden" id="defineActivityId" name="defineActivityId" value="${defineActivity.resourceid }">
			  		<input type="hidden" id="submitType" name="submitType" value="">
			  		
			  		<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					    <tbody>
					    	<tr>
					        	<td class="tdTitle" style="width: 15%;">流程标题：</td>
					            <td>
							  		<input type="text" id="processTitle" name="processTitle" value="${requestScope.processTitle }" style="width: 80%" class="required" maxlength="80">
					            </td>
					       </tr>
						</tbody>
					</table>
			  		
			  		<input type="hidden" id="resourceid" name="resourceid" value="${leave.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${leave.createId }">
                	<input type="hidden" id="createName" name="createName" value="${leave.createName }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${leave.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">申请人：</td>
					                <td style="width: 30%;">
					                	<input type="hidden" id="applyId" name="applyId" value="${leave.applyId }">
                						<input type="text" id="applyName" name="applyName" value="${leave.applyName }" readonly="readonly">
					                </td>
					                <td class="tdTitle" style="width: 15%;">部门：</td>
					                <td>
                						<input type="text" id="deptName" name="deptName" value="${leave.deptName }" readonly="readonly">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">岗位：</td>
					                <td>
					                	<input type="text" id="postName" name="postName" value="${leave.postName }" readonly="readonly">
					                </td>
					                <td class="tdTitle">假期类型：</td>
					                <td>
					                	<app:select name="leaveType" dictionaryCode="D_Leave_Type" value="${leave.leaveType}"/>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">开始时间：</td>
					                <td colspan="3">
					                	<input type="text" id="startTime" name="startTime" value="<fmt:formatDate value="${leave.startTime}" pattern="yyyy-MM-dd HH:mm"/>" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})">&nbsp; - &nbsp;
					                	<input type="text" id="endTime" name="endTime" value="<fmt:formatDate value="${leave.endTime}" pattern="yyyy-MM-dd HH:mm"/>" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})">；&nbsp;&nbsp;&nbsp;&nbsp;
					                	共<input type="text" id="sumDay" name="sumDay" value="${leave.sumDay }" style="width: 50px;">天
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">请假原因：</td>
					                <td colspan="3">
					                	<textarea rows="5" style="width: 80%" id="applyReason" name="applyReason">${leave.applyReason}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				
			</div>
		</div>
	</div>
	
	<div data-options="region:'east',title:'审批信息',split:true" style="width:300px;">
		<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
			<tbody>
				<c:if test="${defineActivity.activityType eq 'T'}">
				<tr>
					<td>
						<input type="radio" id="activityOpinion" name="activityOpinion" value="Y" checked="checked">同意&nbsp;&nbsp;
						<input type="radio" id="activityOpinion" name="activityOpinion" value="N">不同意
					</td>
				</tr>
				<tr>
					<td>
						<select>
							<option value="">请选择...</option>
							<option value="Y">同意</option>
						</select>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						<textarea id="" rows="5" style="width: 90%"><c:choose><c:when test="${defineActivity.activityType eq 'T'}">同意</c:when><c:otherwise>请审批.</c:otherwise></c:choose></textarea>
					</td>
				</tr>
				<c:forEach var="route" items="${defineRoutes }" varStatus="vsrt">
				<tr>
					<td>
						<input type="radio" id="nextActivityId" name="nextActivityId" <c:if test="${vsrt.index eq 0}">checked="checked"</c:if> value="${route.nextActivityId }"><a class="easyui-linkbutton" data-options="plain:true" style="font-weight: bold;">${route.nextActivityName }</a><br>
						<c:if test="${route.activityType ne 'E'}">
						主办人：<br>
						<input type="text" id="nextActCandidateName_${route.nextActivityId}" name="nextActCandidateName_${route.nextActivityId}" value="${route.candidateName }" style="width: 60%" readonly="readonly">
						<input type="hidden" id="nextActCandidateId_${route.nextActivityId}" name="nextActCandidateId_${route.nextActivityId}" value="${route.candidateId }" class="required">
						<input type="button" value="选择" onclick="selectActUser('nextActCandidateId_${route.nextActivityId}','nextActCandidateName_${route.nextActivityId}')"/>
					    <input type="button" value="清除" onclick="cleanActUsers('nextActCandidateId_${route.nextActivityId}','nextActCandidateName_${route.nextActivityId}')"/><br>
						经办人：<br>
						<input type="hidden" id="nextActPersonId_${route.nextActivityId}" name="nextActPersonId_${route.nextActivityId}" value="${route.personId }">
						<input type="text" id="nextActPersonName_${route.nextActivityId}" name="nextActPersonName_${route.nextActivityId}" value="${route.personName }" style="width: 60%" readonly="readonly">
						<input type="button" value="选择" onclick="selectActUser('nextActPersonId_${route.nextActivityId}','nextActPersonName_${route.nextActivityId}')"/>
					    <input type="button" value="清除" onclick="cleanActUsers('nextActPersonId_${route.nextActivityId}','nextActPersonName_${route.nextActivityId}')"/>
						</c:if>
					</td>
				</tr>
				</c:forEach>
				<tr>
					<td align="center">
						<a onclick="submitForm('submit');" data-options="iconCls:'icon-arrow'" class="easyui-linkbutton">提交</a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/leaveApply/save.do">
			<a onclick="submitForm('save');" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	</form>
  </body>
</html>
