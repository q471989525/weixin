<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
  		/* 自动适应文本域大小
		$("#userForm").find("textarea").each(function(){
			if(this.value!=""){
				if(this.scrollHeight<50){
					this.style.height = '50px';
			 	}else{
				 	this.style.height = (this.scrollHeight+5) + 'px';
			 	}
			}
		}); */
  	</script>
  	 
	<style type="text/css"> 
	pre {
		margin-top: 5px; margin-bottom: 5px;
		white-space: pre-wrap; 
		white-space: -moz-pre-wrap; 
		white-space: -pre-wrap; 
		white-space: -o-pre-wrap; 
		word-wrap: break-word; 
	} 
	</style> 
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="用户信息">
			  	<form id="userForm" action="${pageContext.request.contextPath}/sysuser/saveInfo.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${user.resourceid }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">用户名称：</td>
					                <td style="width: 20%;">${user.username }</td>
					                <td class="tdTitle" style="width: 15%;">英文名/拼音：</td>
					                <td style="width: 20%;">${user.enName }</td>
					                <td align="center">照片</td>
					            </tr>
					            <tr>
					                <td class="tdTitle">性别：</td>
					                <td>
					                	${app:fmtDictText("D_Sex", user.sex)}
					                	<%-- <app:select name="sex" dictionaryCode="D_Sex" value="${user.sex}" disabled="true" style="border: 0px;"/> --%>
					                </td>
					                <td class="tdTitle">出生日期：</td>
					                <td>${user.birthday }</td>
					                <td rowspan="8" align="center">
					                	<c:choose>
					                		<c:when test="${empty user.headImage}">
							                	<img id="headImg" alt="头像" src="${pageContext.request.contextPath}/common/images/head.jpg" style="width: 146px; height: 182px;">
					                		</c:when>
					                		<c:otherwise>
							                	<img id="headImg" alt="头像" src="${pageContext.request.contextPath}/filedownload?image=${user.headImage}" style="width: 146px; height: 182px;">
					                		</c:otherwise>
					                	</c:choose>
					                </td>
					            </tr> 
					            <tr>
					                <td class="tdTitle">所属组织：</td>
					                <td>${organization.orgName }</td>
					  				<td class="tdTitle">入职日期：</td>
					                <td>${user.joinDate }</td>
					            </tr>
					            <tr>
					  				<td class="tdTitle">登录账号：</td>
					                <td>${user.loginId }</td>
					                <td class="tdTitle">岗位：</td>
					                <td>
					                	${app:fmtDictText("D_Post", user.post)}
					                	<%-- <app:select name="post" dictionaryCode="D_Post" value="${user.post}" disabled="true" style="border: 0px;"/> --%>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">员工编号：</td>
					                <td>${user.employeeId }</td>
					                <td class="tdTitle">职务：</td>
					                <td>${app:fmtDictText("D_Duty", user.duty)}</td>
					            </tr> 
					            <tr>
					                <td class="tdTitle">手机：</td>
					                <td>${user.mobile }</td>
					                <td class="tdTitle">办公电话：</td>
					                <td>${user.officePhone }</td>
					            </tr>
					            <tr>
					                <td class="tdTitle">邮箱：</td>
					                <td>${user.email }</td>
					                <td class="tdTitle">QQ号码：</td>
					                <td>${user.qq }</td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">职责描述：</td>
					                <td colspan="3"><pre>${user.dutyDesc}</pre></td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3"><pre>${user.remark}</pre></td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	<%--
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		&nbsp;
	</div>
	 --%>
  </body>
</html>
