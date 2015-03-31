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
	  		$("#MeetRoomForm").validate({
				rules: {
					meetName: {required:true,maxlength:50},
					meetCapacity: {required:true,maxlength:50},
					meetLocation: {required:true,maxlength:50},
					adminId: {required:true},
					validFlag: {required:true},
					orderBy: {required:true,maxlength:5,digits:true},
					meetEquip: {required:true,maxlength:300}
				}
			})
	  		
	  		//ajax提交
	  	    $("#MeetRoomForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	opener.searchMeetRoom(); //调list页面查询方法
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#MeetRoomForm").submit();
	  	}
	  	
	  	//选择用户
	  	function selectUser(){
	  		Common.singleUserSelect();
	  	}
	  	//回调选中的用户
	  	function selectedSingleUser(user){
	  		if(user){
	  			$("#adminId").val(user.userid);
	  			$("#adminName").val(user.username);
	  		}
	  	}
	  	//清空用户
	  	function cleanUser(){
	  		$("#adminId").val("");
	  		$("#adminName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="会议室">
			  	<form id="MeetRoomForm" action="${pageContext.request.contextPath}/meetRoom/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${meetRoom.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${meetRoom.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${meetRoom.creator }">
			  		<input type="hidden" id="deleteFlag" name="deleteFlag" value="${meetRoom.deleteFlag }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${meetRoom.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">会议室名称：</td>
					                <td>
					                	<input type="text" id="meetName" name="meetName" value="${meetRoom.meetName }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议室容量：</td>
					                <td>
					                	<input type="text" id="meetCapacity" name="meetCapacity" value="${meetRoom.meetCapacity }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议室位置：</td>
					                <td>
					                	<input type="text" id="meetLocation" name="meetLocation" value="${meetRoom.meetLocation }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">管理员：</td>
					                <td>
					                	<input type="text" id="adminName" name="adminName" value="${meetRoom.adminName }" readonly="readonly">
					                	<input type="hidden" id="adminId" name="adminId" value="${meetRoom.adminId }">
					                	<input type="button" value="选择" onclick="selectUser()"/>
					                	<input type="button" value="清除" onclick="cleanUser()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">状态：</td>
					                <td>
					                	<select id="validFlag" name="validFlag">
					                		<option value="y">有效</option>
					                		<option value="n" <c:if test="${meetRoom.validFlag eq 'n'}">selected="selected"</c:if> >无效</option>
					                	</select>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">会议室设备：</td>
					                <td>
					                	<textarea rows="4" style="width: 80%" id="meetEquip" name="meetEquip">${meetRoom.meetEquip}</textarea>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">排序：</td>
					                <td>
					                	<input type="text" id="orderBy" name="orderBy" value="${meetRoom.orderBy }">
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/meetRoom/save.do">
			<a  onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
