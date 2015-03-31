<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
	
  	<script type="text/javascript">
  		//得到父窗口的引用。showModalDialog   
		//var parentWin = window.dialogArguments;
  
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#userForm").validate({
				rules: {
					username: "required",
					loginId: {required:true, remote:"${pageContext.request.contextPath}/sysuser/uniqueLogin.do?resourceid=${user.resourceid}"},
					password: {required:true, minlength:4},
					employeeId: {required:true, remote:"${pageContext.request.contextPath}/sysuser/uniqueNumber.do?resourceid=${user.resourceid}"},
					orgId: "required",
					post: "required",
					duty: "required",
					email: {required:true, email:true},
					mobile: "required",
					sex: "required",
					birthday: "required",
					joinDate: "required",
					stateFlag: "required",
					isEnabled: "required",
					orderBy: {digits:true},
					remark: {maxlength:150}
				},messages: {
					loginId: {remote:"登陆账号已经存在！"},
					employeeId: {remote:"员工编号已经存在！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#userForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try { opener.searchUser(); } catch(e) {} //刷新dataGrid页面
			            	try { opener.reLoadTreeGD(); } catch(e) {} //刷新组织用户treeGrid页面
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#userForm").submit();
	  	}
	  	
	  	function openParentOrg(){
			var url = "${pageContext.request.contextPath}/sysorganization/select.do";
			Common.dialog({"title":"组织机构选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedParentOrg(org){
			if(org){
				$("#orgId").val(org.resourceid);
		  		$("#orgName").val(org.orgName);
			}
	  	}
	  	
	  	function cleanOrg(){
	  		$("#orgId").val("");
	  		$("#orgName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="系统用户">
			  	<form id="userForm" action="${pageContext.request.contextPath}/sysuser/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${user.resourceid }">
			  		<input type="hidden" id="deleteState" name="deleteState" value="${user.deleteState }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">用户名称：</td>
					                <td style="width: 28%;"><input type="text" id="username" name="username" value="${user.username }"></td>
					                <td class="tdTitle" style="width: 15%;">英文名/拼音：</td>
					                <td style="width: 25%;"><input type="text" id="enName" name="enName" value="${user.enName }"></td>
					                <td align="center">照片</td>
					            </tr>
					            <tr>
					                <td class="tdTitle">性别：</td>
					                <td>
					                	<app:select name="sex" dictionaryCode="D_Sex" value="${user.sex}"/>
					                </td>
					                <td class="tdTitle">出生日期：</td>
					                <td>
					                	<input type="text" id="birthday" name="birthday" value="${user.birthday }" class="Wdate" readonly="readonly" onclick="WdatePicker({})">
					                </td>
					                <td rowspan="7" align="center">
					                	<input type="hidden" id="headImage" name="headImage" value="${user.headImage }">
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
					  				<td class="tdTitle">登录账号<font color="red">(唯一)</font>：</td>
					                <td><input type="text" id="loginId" name="loginId" value="${user.loginId }"></td>
					                <td class="tdTitle">登录密码：</td>
					                <td><input type="password" id="password" name="password" value="${user.password }" readonly="readonly"></td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">员工编号<font color="red">(唯一)</font>：</td>
					                <td><input type="text" id="employeeId" name="employeeId" value="${user.employeeId }" <c:if test="${not empty user.resourceid}">readonly="readonly"</c:if>></td>
					             	<td class="tdTitle">入职日期：</td>
					                <td>
					                	<input type="text" id="joinDate" name="joinDate" value="${user.joinDate }" class="Wdate" readonly="readonly" onclick="WdatePicker({})">
					                </td>
					            </tr> 
					            <tr>
					                <td class="tdTitle">所属组织：</td>
					                <td colspan="3">
					                	<input type="text" id="orgName" name="orgName" value="${user.orgName }" readonly="readonly">
					                	<input type="hidden" id="orgId" name="orgId" value="${user.orgId }">
					                	<input type="button" value="选择" onclick="openParentOrg()"/>
					                	<input type="button" value="清除" onclick="cleanOrg()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">岗位：</td>
					                <td>
					                	<app:select name="post" dictionaryCode="D_Post" value="${user.post}"/>
					                </td>
					                <td class="tdTitle">职务：</td>
					                <td><app:select name="duty" dictionaryCode="D_Duty" value="${user.duty}"/></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">手机：</td>
					                <td><input type="text" id="mobile" name="mobile" value="${user.mobile }"></td>
					                <td class="tdTitle">办公电话：</td>
					                <td><input type="text" id="officePhone" name="officePhone" value="${user.officePhone }"></td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">邮箱：</td>
					                <td><input type="text" id="email" name="email" value="${user.email }"></td>
					                <td class="tdTitle">QQ号码：</td>
					                <td><input type="text" id="qq" name="qq" value="${user.qq }"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">状态：</td>
					                <td>
					                	<select id="stateFlag" name="stateFlag">
					                		<option value="1">在职</option>
					                		<option value="2" <c:if test="${user.stateFlag eq '2'}">selected="selected"</c:if>>离职</option>
					                		<option value="3" <c:if test="${user.stateFlag eq '3'}">selected="selected"</c:if>>待定</option>
					                	</select>
					                </td>
					                <td class="tdTitle">隐藏状态：</td>
					                <td>
					                	<select id="showState" name="showState">
					                		<option value="y">显示</option>
					                		<option value="h" <c:if test="${user.showState eq 'h'}">selected="selected"</c:if>>隐藏</option>
					                	</select>
					                </td>
					                <td rowspan="2">
					                	<iframe src="${pageContext.request.contextPath}/common/uploadify/headImageUpload.jsp" frameborder="0" style="width: 99%; height: 50px;" scrolling="no"></iframe>
					                </td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">激活标志：</td>
					                <td>
					                	<select id="isEnabled" name="isEnabled">
					                		<option value="y">激活</option>
					                		<option value="n" <c:if test="${user.isEnabled eq 'n'}">selected="selected"</c:if>>未激活</option>
					                	</select>
					                </td>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${user.orderBy }"></td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">职责描述：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="dutyDesc" name="dutyDesc">${user.dutyDesc}</textarea>
					                </td>
					                <td>&nbsp;</td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="remark" name="remark">${user.remark}</textarea>
					                </td>
					                <td>&nbsp;</td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysuser/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
