<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>角色选择</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
	
  	<script type="text/javascript">
	    //确定
	    function selectRole(){
	    	var rows = $("#roleTab").datagrid("getSelections");
	    	if(rows){
	    		window.parent.selectedRoles(rows);
		    	colseWin();
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	    function dbclickRow(rowIndex, rowData){
	    	var rows = new Array();
    		rows.push(rowData);
	    	window.parent.selectedRoles(rows);
	    	colseWin();
	    }
	    
	    function colseWin(){
	    	var api = frameElement.api.close();
	    }
	    
	  	//搜索
	    function searchRole(){
	    	var roleName = $("#roleName").val();
	    	var roleCode = $("#roleCode").val();
	    	$("#roleTab").datagrid("clearSelections");
	    	$("#roleTab").datagrid("reload",{roleName:roleName,roleCode:roleCode});
	    }
	    
	  	//重置
	    function resetRole(){
	    	$("#roleName").val("");
	    	$("#roleCode").val("");
	    	searchRole();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchRole();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">角色名称：<input type="text" id="roleName" name="roleName" value=""/></td>
						<td style="width: 33%">角色编码：<input type="text" id="roleCode" name="roleCode" value=""/></td>
						<td>
							<a onclick="searchRole()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetRole()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="系统角色列表">
			<table id="roleTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:dbclickRow,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysrole/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'roleName',width:220">角色名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'roleCode',width:220">角色编码</th>
						<th data-options="field:'orderBy',width:80,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'remark',width:500">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="selectRole()">确定</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="colseWin()">关闭</a>
		</div>
  </body>
</html>