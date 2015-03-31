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
  		//提交前的回调函数
  		function beforeMethod(formData, jqForm, options){
  			var resourceid = jqForm[0].resourceid.value;
  			var meetId = jqForm[0].meetId.value;
  			var startTime = jqForm[0].startTime.value;
  			var endTime = jqForm[0].endTime.value;
  			var url = "${pageContext.request.contextPath}/meetOrder/validMeetTime.do";
  			
  			var bool = false;
  			$.ajax({
  				type: "POST",
  				url: url,
  				async:false, //同步请求将锁住浏览器
  				data: "meetId="+meetId+"&startTime="+startTime+"&endTime="+endTime+"&resourceid="+resourceid,
  				success: function(data){
  					if(data.state == "success"){
  						bool = true;
  	  				}else{
  	  					$.messager.alert("警告", "会议时间冲突，请另选时间！","warning");
  	  				}
  				},
  				dataType:"json"
  			});
  			
  			if(bool){
  				return true;
  			}else{
  				return false;
  			}
  		}
  		
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#MeetOrderForm").validate({
				rules: {
					meetSubject: {required:true,maxlength:50},
					startTime: {required:true},
					endTime: {required:true},
					compereId: {required:true},
					recorderId: {required:true},
					userId: {required:true},
					meetContent: {required:true,maxlength:200}
				}
			})
	  		
	  		//ajax提交
	  	    $("#MeetOrderForm").ajaxForm({
	  	        dataType:"json",
	  	      	beforeSubmit: beforeMethod, //提交前的回调函数
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchMeetOrder(); //调list页面查询方法
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
	  		$("#MeetOrderForm").submit();
	  	}
	  	
	  	//选择用户
	  	function selectUser(){
	  		Common.multiUserSelect({userId:$("#userId").val(),userName:$("#userName").val()});
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
	  			$("#userId").val(id.toString());
	  			$("#userName").val(name.toString());
	  		}
	  	}
	  	function cleanUser(){
	  		$("#userId").val("");
	  		$("#userName").val("");
	  	}
	  	
	  	//单选用户
	  	var t = 0;
	  	function selectSingleUser(type){
	  		Common.singleUserSelect();
	  		t = type;
	  	}
	  	//回调选中的用户
	  	function selectedSingleUser(user){
	  		if(user){
	  			if(t==1){
			  		$("#compereId").val(user.userid);
			  		$("#compereName").val(user.username);
		  		}
		  		if(t==2){
			  		$("#recorderId").val(user.userid);
			  		$("#recorder").val(user.username);
		  		}
	  		}
	  	}
	  	function cleanSingleUser(type){
	  		if(type==1){
		  		$("#compereId").val("");
		  		$("#compereName").val("");
	  		}
	  		if(type==2){
		  		$("#recorderId").val("");
		  		$("#recorder").val("");
	  		}
	  	}
	  	
	  	//添加上传文件
	  	function appendFile(obj){
	  		$("#attFileDiv").append("<div id='"+obj.resourceid+"'><font style='font-size: 14px;'><a href='#' onclick=\"Common.downloadFile('"+obj.resourceid+"')\">．"+obj.fileName+"</a></font><img alt='删除' title='删除' onclick=\"deleteFile('"+obj.resourceid+"')\" src='${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png' style='cursor: pointer; width: 11px; height: 11px;'></div>");
	  	}
	  	
	  	//删除文件
	  	function deleteFile(id){
	  		var url = "${pageContext.request.contextPath}/pubattachment/delete.do";
	  		$.post(url, { "fileid": id }, function(){
	  			$("#"+id).remove();
	  		}, "json");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="会议室预订">
			  	<form id="MeetOrderForm" action="${pageContext.request.contextPath}/meetOrder/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${meetOrder.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${meetOrder.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${meetOrder.creator }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${meetOrder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
			  		<input type="hidden" id="attachmentId" name="attachmentId" value="${meetOrder.attachmentId }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">会议室：</td>
					                <td>
					                	${meetOrder.meetName }
					                	<input type="hidden" id="meetId" name="meetId" value="${meetOrder.meetId }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议主题：</td>
					                <td>
					                	<input type="text" id="meetSubject" name="meetSubject" value="${meetOrder.meetSubject }" style="width: 80%;">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议时间：</td>
					                <td>
					                	<input type="text" id="startTime" name="startTime" value="<fmt:formatDate value="${meetOrder.startTime}" pattern="yyyy-MM-dd HH:mm"/>" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d %H:%m',maxDate:'#F{$dp.$D(\'endTime\')}'})">&nbsp;-&nbsp;
					                	<input type="text" id="endTime" name="endTime" value="<fmt:formatDate value="${meetOrder.endTime}" pattern="yyyy-MM-dd HH:mm"/>" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})">
					                	<%-- <input type="text" id="endTime" name="endTime" value="<fmt:formatDate value="${meetOrder.endTime}" pattern="yyyy-MM-dd HH:mm"/>" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'%y-%M-%d 23:59',minDate:'#F{$dp.$D(\'startTime\')}'})"> --%>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">主持人：</td>
					                <td>
					                	<input type="text" id="compereName" name="compereName" value="${meetOrder.compereName }" readonly="readonly">
					                	<input type="hidden" id="compereId" name="compereId" value="${meetOrder.compereId }">
					                	<input type="button" value="选择" onclick="selectSingleUser(1)"/>
					                	<input type="button" value="清除" onclick="cleanSingleUser(1)"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">记录人：</td>
					                <td>
					                	<input type="text" id="recorder" name="recorder" value="${meetOrder.recorder }" readonly="readonly">
					                	<input type="hidden" id="recorderId" name="recorderId" value="${meetOrder.recorderId }">
					                	<input type="button" value="选择" onclick="selectSingleUser(2)"/>
					                	<input type="button" value="清除" onclick="cleanSingleUser(2)"/>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">会议内容：</td>
					                <td>
					                	<textarea rows="4" style="width: 80%" id="meetContent" name="meetContent">${meetOrder.meetContent}</textarea>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">参会人员：</td>
					                <td>
					                	<textarea rows="6" style="width: 80%" id="userName" name="userName" readonly="readonly">${userName }</textarea>
					                	<input type="hidden" id="userId" name="userId" value="${userId }">
					                	<input type="button" value="选择" onclick="selectUser()"/>
					                	<input type="button" value="清除" onclick="cleanUser()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">附件：</td>
					                <td>
					                	<iframe src="${pageContext.request.contextPath}/common/uploadify/fileUpload.jsp?attachmentId=${meetOrder.attachmentId}" frameborder="0" style="width: 99%; height: 23px;" scrolling="no"></iframe>
					                	<div id="attFileDiv">
					                		<c:forEach var="att" items="${attList }" varStatus="vs">
					                		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">．${att.fileName}</a></font>&nbsp;<img alt="删除" title="删除" onclick="deleteFile('${att.resourceid}')" src="${pageContext.request.contextPath}/common/uploadify/uploadify-cancel.png" style="cursor: pointer; width: 11px; height: 11px;"></div>
					                		</c:forEach>
					                	</div>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/meetOrder/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
