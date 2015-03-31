<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>用户角色授权</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //保存用户角色
	    function saveUserRole(){
           	var roles = new Array();
	    	$("input[id='roleId']").each(function(){
            	roles.push(this.value);
	    	});
            if(roles.length>=1){
                var url = "${pageContext.request.contextPath}/sysuser/saveUserRoleAuth.do";
                $.post(url, {roleIds:roles.toString(),userid:"${user.resourceid}"},function(data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try { opener.refreshRole(); } catch(e) {}
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
                },"json");
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	  	function selectRoles(){
	  		var rows = $("#roleTab").datagrid("getSelections");
	  		if(rows.length>0){
	  			for(var i=0; i<rows.length; i++){
	  				if($("#"+rows[i].resourceid).length==0){
	  					$("#authRoleDiv").append("<div id='"+rows[i].resourceid+"' class='userRole' onmouseover='this.style.backgroundColor=\"#f4f4f4\"' onmouseout='this.style.backgroundColor=\"\"'>"+rows[i].roleName+"&nbsp;<img src='${pageContext.request.contextPath}/common/easyui1.4/themes/icons/cross-button.png' style='width: 13px; height: 13px;' onclick='deleteAuthRole(\""+rows[i].resourceid+"\")'><input type='hidden' id='roleId' name='roleId' value='"+rows[i].resourceid+"'/></div>");
	  				}else{
		  				Common.showBottomMsg("【"+rows[i].roleName+"】已经存在！");
	  				}
	  			}
	  		}else{
	  			Common.showBottomMsg("请选择一条记录");
	  		}
	  	}
	  	
	  	function deleteAuthRole(roleId){
	  		$("#"+roleId).remove();
	  	}
	  	
		//搜索
	    function searchRole(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#roleTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetRole(){
	    	Common.clearForm("queryForm");
	    	searchRole();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchRole();
			}
		}
  	</script>
  	<style type="text/css"> 
		.userRole {
			width:200px; height:20px; margin:2px; padding: 2px 0px 0px; float:left; border:1px solid #A3C0E8; text-align: center; cursor: pointer;
		} 
	</style> 
  </head>
  
  <body class="easyui-layout">
		<div id="authRoleDiv" data-options="region:'west',split:true" title="${user.username }" style="width:220px;">
			<c:forEach var="ur" items="${userRoles }">
				<div id="${ur.resourceid}" class="userRole" onmouseover="this.style.backgroundColor='#f4f4f4'" onmouseout="this.style.backgroundColor=''">${ur.roleName}&nbsp;<img src="${pageContext.request.contextPath}/common/easyui1.4/themes/icons/cross-button.png" style="width: 13px; height: 13px;" onclick="deleteAuthRole('${ur.resourceid}')"><input type="hidden" id="roleId" name="roleId" value="${ur.resourceid}"/></div>
			</c:forEach>
		</div>
		
		<div data-options="region:'center'">
			<div class="easyui-layout" data-options="fit:true">   
			    <div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden; padding: 2px;">
					<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
						角色名称：<input type="text" id="roleName" name="roleName" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
						角色编码：<input type="text" id="roleCode" name="roleCode" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
						<a onclick="searchRole()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						<a onclick="resetRole()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
					</form>
				</div>  
			    <div data-options="region:'center',border:false" title="系统角色列表">
					<table id="roleTab" class="easyui-datagrid"
						data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysrole/datagrid.do'">
						<thead data-options="frozen:true"> 
							 <tr>
								<th data-options="field:'resourceid',checkbox:true"></th> 
								<th data-options="field:'roleName',width:200">角色名称</th>
					         </tr>
						</thead>
						<thead>
							<tr>
								<th data-options="field:'roleCode',width:200">角色编码</th>
								<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
								<th data-options="field:'remark',width:450">备注</th>
							</tr>
						</thead>
					</table>
				</div>
				<%-- 按钮栏 --%>
				<div id="tb">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectRoles()">选择</a>|
				</div>
			</div>
		</div>
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<app:btnAuth btnUrl="/sysuser/saveUserRoleAuth.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="saveUserRole()">保存</a>
			</app:btnAuth>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="window.close();">关闭</a>
		</div>
  </body>
</html>