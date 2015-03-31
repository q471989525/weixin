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
  	//格式化
  	function formatElementType(val,row){
	    	var text = "";
	    	if("hidden" == val) text = "隐藏";
	    	if("text" == val) text = "文本";
	    	if("button" == val) text = "按钮";
	    	if("select" == val) text = "下拉框";
	    	if("textarea" == val) text = "文本域";
	    	if("checkbox" == val) text = "多选框";
	    	if("radio" == val) text = "单选框";
	    	if("a" == val) text = "超链接";
	    	if("password" == val) text = "密码";
	        return text;
	}
  	function formatIsSub(val,row){
	    	var text = "";
	    	if("Y" == val) text = "是";
	    	if("N" == val) text = "<font color='blue'>否</font>";
	        return text;
	}
  	
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#activityForm").validate({
				rules: {
					activityName: "required",
					joinActivityCount: {digits:true},
					timeLimit: {digits:true},
					orderBy: {required:true,digits:true},
					remark: {maxlength:150}
				}
			})
	  		
	  		//ajax提交
	  	    $("#activityForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	opener.searchActivity();
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  		switchTaskFlag($("#activityType").val());
	  		switchJoinCount($("#forkJoin").val());
	  		switchCustomDesc($("#opinionCustomFlag").val());
	  		//初始化回退活动
	  		var backActivityId = "${activity.backActivityId}";
	  		if(backActivityId != ""){
	  			$("input[id='backActivityId']").each(function(i){
	  			   if(backActivityId.indexOf(this.value)>-1) this.checked=true;
	  			});
	  		}
	  		
	  	});
	  	
	  	function submitForm(){
	  		var selectdElems = $("#selectdFormTab").datagrid("getRows"); //获取已选元素
	  		if(selectdElems.length>0){
	  			var ids = new Array();
	  			for (var j=0; j<selectdElems.length; j++){
		  			ids.push(selectdElems[j].resourceid);
		        }
		  		$("#selectdFormElemIds").val(ids.toString());
	  		}
	  		$("#activityForm").submit();
	  	}
	  	
	  	function switchTaskFlag(v){
	  		if(v == 'T'){
	  			$("#taskFlag").show();
	  		}else{
	  			$("#taskFlag").hide();
	  		}
	  	}
	  	
	  	function switchJoinCount(v){
	  		if(v == 'J' || v == 'B'){
	  			$("#joinActivityCount").show();
	  		}else{
	  			$("#joinActivityCount").hide();
	  		}
	  	}
	  	
	  	function switchCustomDesc(v){
	  		if(v == 'C'){
	  			$("#opinionCustomDesc").show();
	  		}else{
	  			$("#opinionCustomDesc").hide();
	  		}
	  	}
	  	
	  	//选择用户
	  	var operateType = 0;
	  	function selectUser(t){
	  		operateType = t;
	  		var userIds = "", userNames = "";
	  		if(t==1){ //提醒用户
	  			userIds = $("#remindUserId").val();
	  			userNames = $("#remindUserName").val();
	  		}
	  		if(t==2){ //主办人
	  			userIds = $("#candidateId").val();
	  			userNames = $("#candidateName").val();
	  		}
	  		if(t==3){ //经办人
	  			userIds = $("#personId").val();
	  			userNames = $("#personName").val();
	  		}
	  		if(t==4){ //人员范围
	  			userIds = $("#limitPersonId").val();
	  			userNames = $("#limitPersonName").val();
	  		}
	  		Common.multiUserSelect({userId:userIds,userName:userNames,limitSize:100});
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
	  			if(operateType == 1){ //提醒用户
		  			$("#remindUserId").val(id.toString());
		  			$("#remindUserName").val(name.toString());
	  			}
	  			if(operateType == 2){ //主办人
		  			$("#candidateId").val(id.toString());
		  			$("#candidateName").val(name.toString());
	  			}
	  			if(operateType == 3){ //经办人
		  			$("#personId").val(id.toString());
		  			$("#personName").val(name.toString());
	  			}
	  			if(operateType == 4){ //人员范围
		  			$("#limitPersonId").val(id.toString());
		  			$("#limitPersonName").val(name.toString());
	  			}
	  		}
	  	}
	  	//选择角色
	  	var roleType = 0;
	  	function selectRole(t){
	  		roleType = t;
	  		var url = "${pageContext.request.contextPath}/sysrole/select.do";
			Common.dialog({"title":"角色选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedRoles(roles){
	  		if(roles){
		  		var id = new Array();
	  			var name = new Array();
	  			for(var i=0; i<roles.length; i++){
	  				id.push(roles[i].resourceid);
	  				name.push(roles[i].roleName);
	  			}
	  			if(roleType == 1){ //关联角色
		  			$("#relateRoleId").val(id.toString());
			  		$("#relateRoleName").val(name.toString());
	  			}
	  			if(roleType == 2){ //角色范围
		  			$("#limitRoleId").val(id.toString());
		  			$("#limitRoleName").val(name.toString());
	  			}
	  		}
	  	}
	  	//表单元素选择
	  	var elemType = 0;
	  	function selectFormElem(t){
	  		elemType = t;
	  		var url = "${pageContext.request.contextPath}/wfDefineForm/select.do?processId=${activity.processId}";
			Common.dialog({"title":"表单元素选择","url":url,modal:true});
	  	}
	  	//回调函数 
	  	function selectedForms(elems){
	  		if(elems){
		  		var id = new Array();
	  			var name = new Array();
	  			for(var i=0; i<elems.length; i++){
	  				id.push(elems[i].elementId);
	  				name.push(elems[i].elementName);
	  			}
	  			if(elemType == 1){ //对象主办人ID
		  			$("#objCandidateIdValue").val(id.toString());
			  		$("#objCandidateId").val(name.toString());
	  			}
	  			if(elemType == 2){ //对象主办人值
		  			$("#objCandidateNameValue").val(id.toString());
		  			$("#objCandidateName").val(name.toString());
	  			}
	  			if(elemType == 3){ //对象经办人ID
		  			$("#objPersonIdValue").val(id.toString());
			  		$("#objPersonId").val(name.toString());
	  			}
	  			if(elemType == 4){ //对象经办人
		  			$("#objPersonNameValue").val(id.toString());
		  			$("#objPersonName").val(name.toString());
	  			}
	  			if(elemType == 5){ //关联变量
		  			$("#relateVariableValue").val(id.toString());
		  			$("#relateVariable").val(name.toString());
	  			}
	  		}
	  	}
	  	//组织选择
	  	function selectOrg(){
	  		var url = "${pageContext.request.contextPath}/sysorganization/selectZtree.do";
			Common.dialog({"title":"组织选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedOrgs(orgs){
	  		if(orgs){
	        	var id = new Array();
	  			var name = new Array();
	  			for(var i=0; i<orgs.length; i++){
	  				id.push(orgs[i].id);
	  				name.push(orgs[i].name);
	  			}
	  			$("#limitOrgId").val(id.toString());
	  			$("#limitOrgName").val(name.toString());
	  		}
	  	}
	  	//清空值
	  	function cleanValue(id,name){
  			$("#"+id).val("");
  			$("#"+name).val("");
	  	}
	  	
	  	//全部选中
	  	function selectAllFormElem(){
	  		$("#formTab").datagrid("selectAll");
	  	}
	  	
	  	//全部返选
	  	function clearAllFormElem(){
	  		$("#formTab").datagrid("unselectAll");
	  	}
	  	
	  	//添加用户
	  	function arrowFormElemToRight(){
	  		var elems = $("#formTab").datagrid("getSelections"); //选中增加元素
	  		if(elems.length>0){
		  		var selectdElems = $("#selectdFormTab").datagrid("getRows"); //获取已选元素
		  		if(selectdElems.length>0){ //过滤重复元素
			  		for (var i = 0; i < elems.length; i++) {
			  			var addFlag = true;
		  				for (var j=0; j<selectdElems.length; j++){
			  				if(elems[i].elementId == selectdElems[j].elementId){addFlag = false; break;}
			         	}
		  				
		  				if(addFlag){
			         		$("#selectdFormTab").datagrid("appendRow",{
			         			resourceid:elems[i].resourceid,
			         			elementName:elems[i].elementName,
			         			elementId:elems[i].elementId,
								elementType:elems[i].elementType,
								isSub:elems[i].isSub
			         		});
		  				}
		  			}
		  		}else{
		         	for ( var i = 0; i < elems.length; i++) {
		         		$("#selectdFormTab").datagrid("appendRow",{
		         			resourceid:elems[i].resourceid,
		         			elementName:elems[i].elementName,
			         		elementId:elems[i].elementId,
							elementType:elems[i].elementType,
							isSub:elems[i].isSub
		         		});
		         	}
		  		}
	  		}else{
	  			Common.showBottomMsg("请选择一条记录");
	  		}
	  	}
	  	
	  	//删除选中元素
	  	function deleteSelectedElem(){
	  		var rows = $("#selectdFormTab").datagrid("getSelections"); //选中用户
	  		if(rows.length>0){
	         	for ( var i = 0; i < rows.length; i++) {
	         		var rowIndex = $("#selectdFormTab").datagrid("getRowIndex", rows[i]);
	         		$("#selectdFormTab").datagrid("deleteRow", rowIndex);
	         	}
	  		}
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
			  	<form id="activityForm" action="${pageContext.request.contextPath}/wfDefineActivity/save.do" method="post">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="${processName} - 活动定义">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${activity.resourceid }">
			  		<input type="hidden" id="processId" name="processId" value="${activity.processId }">
			  		<input type="hidden" id="createId" name="createId" value="${activity.createId }">
			  		<input type="hidden" id="createName" name="createName" value="${activity.createName }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${activity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
				   	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
				    	<tbody>
					    	<tr>  
					        	<td class="tdTitle" style="width: 15%;">活动名称：</td>
					            <td style="width: 25%;"><input type="text" id="activityName" name="activityName" value="${activity.activityName }" style="width: 70%;"></td>
					            <td class="tdTitle" style="width: 15%;">活动别名：</td>
					            <td><input type="text" id="activityAlias" name="activityAlias" value="${activity.activityAlias }"></td>
					        </tr>
					        <tr>
					        	<td class="tdTitle">活动类型：</td>
					            <td>
					            	<select id="activityType" name="activityType" onchange="switchTaskFlag(this.value)">
					              		<option value="T">任务</option>
					               		<option value="S" <c:if test="${activity.activityType eq 'S'}">selected="selected"</c:if>>开始</option>
					               		<option value="E" <c:if test="${activity.activityType eq 'E'}">selected="selected"</c:if>>结束</option>
					               	</select>
					            </td>
					            <td class="tdTitle">任务标志：</td>
					            <td>
					            	<select id="taskFlag" name="taskFlag">
					              		<option value="Y">强制</option>
					               		<option value="N" <c:if test="${activity.taskFlag eq 'N'}">selected="selected"</c:if>>非强制</option>
					               		<option value="M" <c:if test="${activity.taskFlag eq 'M'}">selected="selected"</c:if>>多人</option>
					               	</select>
					            </td>
					        </tr>  
					        <tr>
					        	<td class="tdTitle">分支聚合：</td>
					            <td>
					            	<select id="forkJoin" name="forkJoin" onchange="switchJoinCount(this.value)">
					              		<option value="G">普通</option>
					               		<option value="F" <c:if test="${activity.forkJoin eq 'F'}">selected="selected"</c:if>>分支</option>
					               		<option value="J" <c:if test="${activity.forkJoin eq 'J'}">selected="selected"</c:if>>聚合</option>
					               		<option value="B" <c:if test="${activity.forkJoin eq 'B'}">selected="selected"</c:if>>聚合分支</option>
					               	</select>
					            </td>
					            <td class="tdTitle">聚合节点数：</td>
					            <td>
					            	<input type="text" id="joinActivityCount" name="joinActivityCount" value="${activity.joinActivityCount }" style="display: none;">
					            </td>
					        </tr>
					        <tr>
					        	<td class="tdTitle">时间限制：</td>
					            <td>
					            	<input type="text" id="timeLimit" name="timeLimit" value="${activity.timeLimit }">小时
					            </td>
					            <td class="tdTitle">泳道活动：</td>
					            <td>
					            	<c:if test="${not empty list}">
					            	<select id="swimActivityId" name="swimActivityId">
					              		<option value="">请选择...</option>
					            		<c:forEach var="act" items="${list}">
						              		<option value="${act.resourceid}" <c:if test="${activity.swimActivityId eq act.resourceid}">selected="selected"</c:if>>${act.activityName}</option>
					            		</c:forEach>
					               	</select>
					            	</c:if>
					            </td>
					        </tr>
					        <tr>
					        	<td class="tdTitle" rowspan="2">回退：</td>
					            <td colspan="3">
					            	<select id="gobackType" name="gobackType">
					              		<option value="">请选择...</option>
					              		<option value="A" <c:if test="${activity.gobackType eq 'A'}">selected="selected"</c:if>>默认/直接回退</option>
					               		<option value="D" <c:if test="${activity.gobackType eq 'D'}">selected="selected"</c:if>>默认回退</option>
					               		<option value="R" <c:if test="${activity.gobackType eq 'R'}">selected="selected"</c:if>>直接回退</option>
					               	</select>
					            </td>
					        </tr>
					        <tr>
					            <td colspan="3">
					            	<c:forEach var="act" items="${list}">
						            	<input type="checkbox" id="backActivityId" name="backActivityId" value="${act.resourceid }">${act.activityName}；&nbsp;&nbsp;
					            	</c:forEach>
					            </td>
					        </tr>
					        <tr>
					            <td class="tdTitle">按钮：</td>
					        	<td colspan="3">
					            	<input type="checkbox" id="queryHistory" name="queryHistory" value="Y" <c:if test="${activity.queryHistory eq 'Y'}">checked="checked"</c:if>>查询历史办理人；&nbsp;&nbsp;
					            	<input type="checkbox" id="hideAgent" name="hideAgent" value="Y" <c:if test="${activity.hideAgent eq 'Y'}">checked="checked"</c:if>>隐藏经办人；&nbsp;&nbsp;
					            	<input type="checkbox" id="copyBtn" name="copyBtn" value="Y" <c:if test="${activity.copyBtn eq 'Y'}">checked="checked"</c:if>>抄送；&nbsp;&nbsp;
					            	<input type="checkbox" id="signBtn" name="signBtn" value="Y" <c:if test="${activity.signBtn eq 'Y'}">checked="checked"</c:if>>会签；&nbsp;&nbsp;
					            	<input type="checkbox" id="authorizeBtn" name="authorizeBtn" value="Y" <c:if test="${activity.authorizeBtn eq 'Y'}">checked="checked"</c:if>>委托；&nbsp;&nbsp;
					            	<input type="checkbox" id="takebackBtn" name="takebackBtn" value="Y" <c:if test="${activity.takebackBtn eq 'Y'}">checked="checked"</c:if>>收回；&nbsp;&nbsp;
					            	<input type="checkbox" id="stopBtn" name="stopBtn" value="Y" <c:if test="${activity.stopBtn eq 'Y'}">checked="checked"</c:if>>终止；&nbsp;&nbsp;
					            </td>
					        </tr>
					        <tr>
					            <td class="tdTitle" rowspan="2">提醒用户：</td>
					        	<td colspan="3">
					            	<input type="checkbox" id="emailRemind" name="emailRemind" value="Y" <c:if test="${activity.emailRemind eq 'Y'}">checked="checked"</c:if>>邮件提醒；&nbsp;&nbsp;
					            	<input type="checkbox" id="smsRemind" name="smsRemind" value="Y" <c:if test="${activity.smsRemind eq 'Y'}">checked="checked"</c:if>>短信提醒；
					            </td>
					        </tr>
					        <tr>
					           <td colspan="3">
					           		<input type="hidden" id="remindUserId" name="remindUserId" value="${activity.remindUserId }">
					        		<textarea rows="2" style="width: 80%" id="remindUserName" name="remindUserName" readonly="readonly">${activity.remindUserName}</textarea>
					        		<input type="button" value="选择" onclick="selectUser(1)"/>
					                <input type="button" value="清除" onclick="cleanValue('remindUserId','remindUserName')"/>
					           </td>
					        </tr>
					        <tr>
					            <td class="tdTitle" rowspan="2">意见框：</td>
					        	<td colspan="3">
					            	<select id="opinionCustomFlag" name="opinionCustomFlag" onchange="switchCustomDesc(this.value)">
					              		<option value="S">显示</option>
					               		<option value="H" <c:if test="${activity.opinionCustomFlag eq 'H'}">selected="selected"</c:if>>隐藏</option>
					               		<option value="C" <c:if test="${activity.opinionCustomFlag eq 'C'}">selected="selected"</c:if>>自定义</option>
					               	</select>
					            </td>
					        </tr>
					        <tr>
					           <td colspan="3">
					        		<textarea rows="2" style="width: 80%; display: none;" id="opinionCustomDesc" name="opinionCustomDesc">${activity.opinionCustomDesc}</textarea><font style="color: red">多个意见以“#”拆分</font>
					           </td>
					        </tr>
					        <tr>
					            <td class="tdTitle">排序：</td>
					            <td colspan="3"><input type="text" id="orderBy" name="orderBy" value="${activity.orderBy }"></td>
					        </tr>
					        <tr>
					           <td class="tdTitle">备注：</td>
					           <td colspan="3">
					        		<textarea rows="2" style="width: 80%" id="remark" name="remark">${activity.remark}</textarea>
					           </td>
					        </tr>
					      </tbody>
					   </table>
				
			</div>
			<div title="活动候选人">
			  		<input type="hidden" id="candidateResourceid" name="candidateResourceid" value="${candidate.resourceid }">
			  		<input type="hidden" id="activityId" name="activityId" value="${candidate.activityId }">
				   	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
				    	<tbody>
					        <tr>
					        	<td class="tdTitle" style="width: 15%;">主办人：</td>
						        <td colspan="3">
						        	<input type="hidden" id="candidateId" name="candidateId" value="${candidate.candidateId }">
						        	<input type="text" id="candidateName" name="candidateName" value="${candidate.candidateName }" readonly="readonly" style="width: 80%">
						        	<input type="button" value="选择" onclick="selectUser(2)"/>
						            <input type="button" value="清除" onclick="cleanValue('candidateId','candidateName')"/>
						        </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle">经办人：</td>
						       	<td colspan="3">
						       		<input type="hidden" id="personId" name="personId" value="${candidate.personId }">
						        	<input type="text" id="personName" name="personName" value="${candidate.personName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectUser(3)"/>
						            <input type="button" value="清除" onclick="cleanValue('personId','personName')"/>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" rowspan="2">对象主办人：</td>
						       	<td colspan="3">
						       		&nbsp;&nbsp;ID：&nbsp;&nbsp;<input type="hidden" id="objCandidateIdValue" name="objCandidateIdValue" value="${candidate.objCandidateIdValue }">
						        	<input type="text" id="objCandidateId" name="objCandidateId" value="${candidate.objCandidateId }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectFormElem(1)"/>
						            <input type="button" value="清除" onclick="cleanValue('objCandidateIdValue','objCandidateId')"/>
						         </td>
						    </tr>
					        <tr>
						       	<td colspan="3">
						       		名称：<input type="hidden" id="objCandidateNameValue" name="objCandidateNameValue" value="${candidate.objCandidateNameValue }">
						        	<input type="text" id="objCandidateName" name="objCandidateName" value="${candidate.objCandidateName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectFormElem(2)"/>
						            <input type="button" value="清除" onclick="cleanValue('objCandidateNameValue','objCandidateName')"/>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" rowspan="2">对象经办人：</td>
						       	<td colspan="3">
						       		&nbsp;&nbsp;ID：&nbsp;&nbsp;<input type="hidden" id="objPersonIdValue" name="objPersonIdValue" value="${candidate.objPersonIdValue }">
						        	<input type="text" id="objPersonId" name="objPersonId" value="${candidate.objPersonId }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectFormElem(3)"/>
						            <input type="button" value="清除" onclick="cleanValue('objPersonIdValue','objPersonId')"/>
						         </td>
						    </tr>
					        <tr>
						       	<td colspan="3">
						       		名称：<input type="hidden" id="objPersonNameValue" name="objPersonNameValue" value="${candidate.objPersonNameValue }">
						        	<input type="text" id="objPersonName" name="objPersonName" value="${candidate.objPersonName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectFormElem(4)"/>
						            <input type="button" value="清除" onclick="cleanValue('objPersonNameValue','objPersonName')"/>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" style="width: 15%; background-color: #EBEBEB;">关联条件：</td>
						       	<td style="width: 35%; background-color: #EBEBEB;">
						       		<select id="relateCondition" name="relateCondition">
					              		<option value="">请选择...</option>
					              		<option value="S" <c:if test="${candidate.relateCondition eq 'S'}">selected="selected"</c:if>>发起人</option>
					               		<option value="C" <c:if test="${candidate.relateCondition eq 'C'}">selected="selected"</c:if>>当前人</option>
					               		<option value="P" <c:if test="${candidate.relateCondition eq 'P'}">selected="selected"</c:if>>人员变量</option>
					               		<option value="D" <c:if test="${candidate.relateCondition eq 'D'}">selected="selected"</c:if>>部门变量</option>
					               	</select>
						         </td>
					          	<td class="tdTitle" style="width: 15%">是否允许弹框：</td>
						       	<td>
						       		<select id="isSelect" name="isSelect">
					              		<option value="Y">是</option>
					               		<option value="N" <c:if test="${candidate.isSelect eq 'N'}">selected="selected"</c:if>>否</option>
					               	</select>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" style="background-color: #EBEBEB;">关联角色：</td>
						       	<td colspan="3" style="background-color: #EBEBEB;">
						       		<input type="hidden" id="relateRoleId" name="relateRoleId" value="${candidate.relateRoleId }">
						        	<input type="text" id="relateRoleName" name="relateRoleName" value="${candidate.relateRoleName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectRole(1)"/>
						            <input type="button" value="清除" onclick="cleanValue('relateRoleId','relateRoleName')"/>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" style="background-color: #EBEBEB;">关联变量：</td>
						       	<td colspan="3" style="background-color: #EBEBEB;">
						       		<input type="hidden" id="relateVariableValue" name="relateVariableValue" value="${candidate.relateVariableValue }">
						       		<input type="text" id="relateVariable" name="relateVariable" value="${candidate.relateVariable }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectFormElem(5)"/>
						            <input type="button" value="清除" onclick="cleanValue('relateVariableValue','relateVariable')"/>
						         </td>
						    </tr>
					        <tr>
					          	<td class="tdTitle" style="background-color: #EBEBEB;">关联说明：</td>
						       	<td colspan="3" style="background-color: #EBEBEB;">
						      		<textarea rows="2" style="width: 80%" id="relateExplain" name="relateExplain">${candidate.relateExplain}</textarea>
						         </td>
						    </tr>
						    <tr>
					          	<td class="tdTitle">人员范围：</td>
						       	<td colspan="3">
						       		<input type="hidden" id="limitPersonId" name="limitPersonId" value="${candidate.limitPersonId }">
						      		<textarea rows="2" style="width: 80%" id="limitPersonName" name="limitPersonName" readonly="readonly">${candidate.limitPersonName}</textarea>
						       		<input type="button" value="选择" onclick="selectUser(4)"/>
						            <input type="button" value="清除" onclick="cleanValue('limitPersonId','limitPersonName')"/>
						         </td>
						    </tr>
						    <tr>
					          	<td class="tdTitle">角色范围：</td>
						       	<td colspan="3">
						       		<input type="hidden" id="limitRoleId" name="limitRoleId" value="${candidate.limitRoleId }">
						       		<input type="text" id="limitRoleName" name="limitRoleName" value="${candidate.limitRoleName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectRole(2)"/>
						            <input type="button" value="清除" onclick="cleanValue('limitRoleId','limitRoleName')"/>
						         </td>
						    </tr>
						    <tr>
					          	<td class="tdTitle">组织范围：</td>
						       	<td colspan="3">
						       		<input type="hidden" id="limitOrgId" name="limitOrgId" value="${candidate.limitOrgId }">
						       		<input type="text" id="limitOrgName" name="limitOrgName" value="${candidate.limitOrgName }" readonly="readonly" style="width: 80%">
						       		<input type="button" value="选择" onclick="selectOrg()"/>
						            <input type="button" value="清除" onclick="cleanValue('limitOrgId','limitOrgName')"/>
						         </td>
						    </tr>
					        <tr>
					           <td class="tdTitle">备注：</td>
					           <td colspan="3">
					        		<textarea rows="2" style="width: 80%" id="candidateRemark" name="candidateRemark">${candidate.candidateRemark}</textarea>
					           </td>
					        </tr>
					      </tbody>
					   </table>
			</div>
			<div title="活动表单">
				<input type="hidden" id="selectdFormElemIds" name="selectdFormElemIds" value="">
				<div class="easyui-layout" data-options="fit:true,border:false">   
				    <div data-options="region:'center'">
				    	<table id="formTab" class="easyui-datagrid" title="表单元素"
							data-options="border:false,rownumbers:true,fit:true,striped:true,remoteSort:false,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/wfDefineForm/datagrid.do?processId=${activity.processId}'">
							<thead data-options="frozen:true"> 
								 <tr>
									<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%>
									<th data-options="field:'elementName',width:120">元素名称</th>
						         </tr>
							</thead>
							<thead>
								<tr>
									<th data-options="field:'elementId',width:120">元素ID</th>
									<th data-options="field:'elementType',width:100,formatter:formatElementType">元素类型</th>
									<th data-options="field:'isSub',width:80,formatter:formatIsSub">是否子表元素</th>
									<th data-options="field:'orderBy',width:80,align:'right',sortable:'true'">排序</th>
									<th data-options="field:'remark',width:300">备注</th>
								</tr>
							</thead>
						</table>
						<%-- 按钮栏 --%>
						<div id="tb" style="height:auto">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-tick-button',plain:true" onclick="selectAllFormElem()">全选</a>|
							<a class="easyui-linkbutton" data-options="iconCls:'icon-cross-button',plain:true" onclick="clearAllFormElem()">反选</a>|
							<a class="easyui-linkbutton" data-options="iconCls:'icon-arrow',plain:true" onclick="arrowFormElemToRight()">添加</a>|
						</div>
				    </div>   
				    <div data-options="region:'east',split:true" style="width:50%;">
				    	<table id="selectdFormTab" class="easyui-datagrid" title="已选表单元素" 
				    		data-options="border:false,rownumbers:true,fit:true,striped:true,toolbar:'#tb2',url:'${pageContext.request.contextPath}/wfDefineForm/activityFormDg.do?activityId=${activity.resourceid}'">
							<thead>
								<tr>
									<th data-options="field:'elementName',width:120">元素名称</th>
									<th data-options="field:'elementId',width:120">元素ID</th>
									<th data-options="field:'elementType',width:100,formatter:formatElementType">元素类型</th>
									<th data-options="field:'isSub',width:80,formatter:formatIsSub">是否子表元素</th>
								</tr>
							</thead>
						</table>
						<%-- 按钮栏 --%>
						<div id="tb2" style="height:auto">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-cross-button',plain:true" onclick="deleteSelectedElem()">删除</a>|
						</div>
				    </div>   
				</div>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/wfDefineActivity/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
	</form>
  </body>
</html>
