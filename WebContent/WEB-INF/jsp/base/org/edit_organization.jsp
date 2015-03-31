<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#orgForm").validate({
				rules: {
					orgName: "required",
					orgCode: {required:true, remote:"${pageContext.request.contextPath}/sysorganization/unique.do?resourceid=${organization.resourceid}"},
					shortName: "required",
					orgType: "required",
					stateFlag: "required",
					contactPeople: {maxlength:50},
					contactPhone: {maxlength:50},
					email: {email:true},
					orderBy: {digits:true},
					contactAddress: {maxlength:100},
					dutyDesc: {maxlength:250},
					remark: {maxlength:250}
				},messages: {
					orgCode: {remote:"组织代码已经存在！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#orgForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try { opener.searchOrg(); } catch(e) {} //刷新treeGrid页面
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
	  		$("#orgForm").submit();
	  	}
	  	
	  	function openParentOrg(){
	  		var url = "${pageContext.request.contextPath}/sysorganization/select.do";
			Common.dialog({"title":"组织机构选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedParentOrg(org){
			if(org){
		  		$("#parentId").val(org.resourceid);
		  		$("#parentName").val(org.orgName);
			}
	  	}
	  	
	  	function cleanOrg(){
	  		$("#parentId").val("");
	  		$("#parentName").val("");
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="组织机构">
			  	<form id="orgForm" action="${pageContext.request.contextPath}/sysorganization/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${organization.resourceid }">
			  		<input type="hidden" id="deleteState" name="deleteState" value="${organization.deleteState }">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">组织名称：</td>
					                <td style="width: 25%;"><input type="text" id="orgName" name="orgName" value="${organization.orgName }"></td>
					                <td class="tdTitle" style="width: 15%;">组织代码<font color="red">(唯一)</font>：</td>
					                <td><input type="text" id="orgCode" name="orgCode" value="${organization.orgCode }" style="width: 50%;"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">简称：</td>
					                <td><input type="text" id="shortName" name="shortName" value="${organization.shortName }"></td>
					                <td class="tdTitle">父组织名称：</td>
					                <td>
					                	<input type="hidden" id="parentId" name="parentId" value="${organization.parentId }">
					                	<input type="text" id="parentName" name="parentName" value="${organization.parentName }">
					                	<input type="hidden" id="parentIds" name="parentIds" value="${organization.parentIds }">
					                	<input type="hidden" id="parentNames" name="parentNames" value="${organization.parentNames }">
					                	<input type="button" value="选择" onclick="openParentOrg()"/>
					                	<input type="button" value="清除" onclick="cleanOrg()"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">组织类型：</td>
					                <td>
					               		<app:select name="orgType" dictionaryCode="D_OrgType" value="${organization.orgType }"/>
					                </td>
					                <td class="tdTitle">状态：</td>
					                <td>
					                	<select id="stateFlag" name="stateFlag">
					                		<option value="y">有效</option>
					                		<option value="n" <c:if test="${organization.stateFlag eq 'n'}">selected="selected"</c:if>>无效</option>
					                		<option value="h" <c:if test="${organization.stateFlag eq 'h'}">selected="selected"</c:if>>隐藏</option>
					                	</select>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">负责人：</td>
					                <td><input type="text" id="contactPeople" name="contactPeople" value="${organization.contactPeople }"></td>
					                <td class="tdTitle">联系电话：</td>
					                <td><input type="text" id="contactPhone" name="contactPhone" value="${organization.contactPhone }"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">电子邮箱：</td>
					                <td><input type="text" id="email" name="email" value="${organization.email }"></td>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${organization.orderBy }"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">办公地址：</td>
					                <td colspan="3"><input type="text" id="contactAddress" name="contactAddress" value="${organization.contactAddress }" style="width: 80%"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">职责描述：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="dutyDesc" name="dutyDesc">${organization.dutyDesc}</textarea>
					                </td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="remark" name="remark">${organization.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysorganization/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
