<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统角色</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatSex(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Sex")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    function formatPost(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Post")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    function formatDuty(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Duty")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatState(val,row){
	    	var state = "";
	    	if (val == "1") state = "在职";  
	    	if (val == "2") state = "<span style='color:red;'>离职</span>";
	    	if (val == "3") state = "<span style='color:blue;'>待定</span>";
	        return state;
	    }
	    
	    function formatShowState(val,row){
	    	if (val == "y"){  
	            return "显示";  
	        } else {  
	            return "<span style='color:red;'>隐藏</span>";  
	        } 
	    }
	    function formatIsEnabled(val,row){
	    	if (val == "y"){  
	            return "激活";  
	        } else {  
	            return "<span style='color:red;'>未激活</span>";  
	        } 
	    }
	
	  	//点击
	    function clickRow(rowIndex, rowData){
	    	$("#userTab").datagrid("load",{"roleId":rowData.resourceid});
	    }
	  	
	  	//双击用户
	    function clickRowUser(rowIndex, rowData){
	    	var url = "${pageContext.request.contextPath}/sysuser/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":500});
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
	  	
		//搜索
	    function searchUser(){
	    	var dataParam = Common.getFormJson("queryUserForm");
	    	
	    	var row = $("#roleTab").datagrid("getSelected"); //选中角色
	    	if(row) dataParam["roleId"] = row.resourceid;
	    	
	    	$("#userTab").datagrid("reload",dataParam);
	    }
	    
	  	//重置
	    function resetUser(){
	    	Common.clearForm("queryUserForm");
	    	searchUser();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPressUser(event){
			if (event.keyCode==13){
				searchUser();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
  	<%-- 角色 --%>
  	<div data-options="region:'center',title:'角色'">
  		<div id="roleDiv" class="easyui-layout" data-options="fit:true,border:false">
  			<div data-options="region:'north'" style="height:32px; overflow: hidden;">
				<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 35%">角色名称：<input type="text" id="roleName" name="roleName" value="" style="width: 60%"/></td>
							<td style="width: 35%">角色编码：<input type="text" id="roleCode" name="roleCode" value="" style="width: 60%"/></td>
							<td>
								<a onclick="searchRole()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
								<a onclick="resetRole()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
		    <div data-options="region:'center',border:false" title="系统角色列表">
				<table id="roleTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,onClickRow:clickRow,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysrole/datagrid.do'">
					<thead data-options="frozen:true"> 
						 <tr>
							<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
							<th data-options="field:'roleName',width:150">角色名称</th>
				         </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'roleCode',width:150">角色编码</th>
							<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
							<th data-options="field:'remark',width:450">备注</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
  	</div>
  	
  	<%-- 用户 --%>
	<div data-options="region:'east',title:'用户',split:true" style="width:650px;">
		<div id="userDiv" class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:true" style="height:32px; overflow: hidden;">
				<form id="queryUserForm" onkeypress="onFormKeyPressUser(event)" onsubmit="return false;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 50%">用户名称：<input type="text" id="username" name="username" value=""/></td>
							<td>
								<a onclick="searchUser()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
								<a onclick="resetUser()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div data-options="region:'center',border:false" title="系统用户列表">
				<table id="userTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRowUser,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysuser/datagrid.do'">
					<thead data-options="frozen:true"> 
						 <tr>
							<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
							<th data-options="field:'username',width:100">用户名称</th>
							<th data-options="field:'orgName',width:120">所属组织</th>
				         </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'loginId',width:90">登录账号</th>
							<th data-options="field:'employeeId',width:90,sortable:'true'">员工编号</th>
							<th data-options="field:'post',width:120,formatter:formatPost">岗位</th>
							<th data-options="field:'duty',width:120,formatter:formatDuty">职务</th>
							<th data-options="field:'mobile',width:120">手机号码</th>
							<th data-options="field:'email',width:150">邮箱地址</th>
							<th data-options="field:'sex',width:70,formatter:formatSex">性别</th>
							<th data-options="field:'birthdayFmt',width:100,sortable:'true'">出生日期</th>
							<th data-options="field:'joinDateFmt',width:100,sortable:'true'">入职日期</th>
							<th data-options="field:'stateFlag',width:70,align:'center',formatter:formatState">状态</th>
							<th data-options="field:'showState',width:70,align:'center',formatter:formatShowState">隐藏状态</th>
							<th data-options="field:'isEnabled',width:70,align:'center',formatter:formatIsEnabled">激活标志</th>
							<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
							<th data-options="field:'dutyDesc',width:300">职责描述</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
	</div>
  </body>
</html>