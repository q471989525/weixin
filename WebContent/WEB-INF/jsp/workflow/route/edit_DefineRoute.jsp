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
	  		$("#routeForm").validate({
				rules: {
					nextActivityId: "required",
					orderBy: {required:true,digits:true},
					remark: {maxlength:150}
				}
			})
	  		
	  		//ajax提交
	  	    $("#routeForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {	
		                		opener.searchRoute();
		                	} catch (e) {} //刷新dataGrid页面
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  		switchFinishType($("#nextActivityId").val());
	  	});
	  	
	  	function submitForm(){
	  		$("#routeForm").submit();
	  	}
	  	
	  	//选择用户
	  	var operateType = 0;
	  	function selectUser(t){
	  		operateType = t;
	  		var userIds = "", userNames = "";
	  		if(t==1){ //主办人
	  			userIds = $("#candidateId").val();
	  			userNames = $("#candidateName").val();
	  		}
	  		if(t==2){ //经办人
	  			userIds = $("#personId").val();
	  			userNames = $("#personName").val();
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
	  			if(operateType == 1){
		  			$("#candidateId").val(id.toString());
		  			$("#candidateName").val(name.toString());
	  			}
	  			if(operateType == 2){
		  			$("#personId").val(id.toString());
		  			$("#personName").val(name.toString());
	  			}
	  		}
	  	}
	  	//清空用户
	  	function cleanUsers(t){
	  		if(t==1){
		  		$("#candidateId").val("");
		  		$("#candidateName").val("");
	  		}
	  		if(t==2){
		  		$("#personId").val("");
		  		$("#personName").val("");
	  		}
	  	}
	  	
	  	function switchFinishType(v){
	  		if($("option[value='"+v+"']").attr("id")=='E'){ //结束
	  			$("#finishType").addClass("required").show(); //添加验证并显示
	  		}else{
	  			$("#finishType").removeClass().hide(); //隐藏
	  		}
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="${activityName} - 路由定义">
			  	<form id="routeForm" action="${pageContext.request.contextPath}/wfDefineRoute/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${route.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${route.createId }">
			  		<input type="hidden" id="createName" name="createName" value="${route.createName }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${route.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="activityId" name="activityId" value="${route.activityId }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">下一个活动：</td>
					                <td style="width: 35%;">
					                	<select id="nextActivityId" name="nextActivityId" style="width: 80%" onchange="switchFinishType(this.value)">
					                		<option value="">请选择...</option>
					                		<c:forEach var="act" items="${list}">
							              		<option value="${act.resourceid}" id="${act.activityType}" <c:if test="${route.nextActivityId eq act.resourceid}">selected="selected"</c:if>>${act.activityName}</option>
						            		</c:forEach>
					                	</select>
					                </td>
						            <td class="tdTitle" style="width: 15%;">排序：</td>
						            <td><input type="text" id="orderBy" name="orderBy" value="${route.orderBy }"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">路由别名：</td>
					                <td><input type="text" id="routeAlias" name="routeAlias" value="${route.routeAlias }"></td>
					                <td class="tdTitle">结束类型：</td>
					                <td>
					                	<select id="finishType" name="finishType" style="display: none;">
					                		<option value="">请选择...</option>
					                		<option value="Y" <c:if test="${route.finishType eq 'Y'}">selected="selected"</c:if>>正常结束</option>
					                		<option value="N" <c:if test="${route.finishType eq 'N'}">selected="selected"</c:if>>异常结束</option>
					                	</select>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">条件表达式：</td>
					                <td colspan="3">
					                	<input type="text" id="conditionExpression" name="conditionExpression" value="${route.conditionExpression }" style="width:80%">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">条件表达式说明：</td>
					                <td colspan="3">
					                	<textarea rows="2" style="width: 80%" id="conditionExplain" name="conditionExplain">${route.conditionExplain}</textarea>
					                </td>
					            </tr>
					            <tr>
					            	<td class="tdTitle">主办人：</td>
						        	<td colspan="3">
						           		<input type="hidden" id="candidateId" name="candidateId" value="${route.candidateId }">
						        		<textarea rows="2" style="width: 80%" id="candidateName" name="candidateName" readonly="readonly">${route.candidateName}</textarea>
						        		<input type="button" value="选择" onclick="selectUser(1)"/>
						                <input type="button" value="清除" onclick="cleanUsers(1)"/>
						            </td>
						        </tr>
					            <tr>
					            	<td class="tdTitle">经办人：</td>
						        	<td colspan="3">
						           		<input type="hidden" id="personId" name="personId" value="${route.personId }">
						        		<textarea rows="2" style="width: 80%" id="personName" name="personName" readonly="readonly">${route.personName}</textarea>
						        		<input type="button" value="选择" onclick="selectUser(2)"/>
						                <input type="button" value="清除" onclick="cleanUsers(2)"/>
						            </td>
						        </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="remark" name="remark">${route.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/wfDefineRoute/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
