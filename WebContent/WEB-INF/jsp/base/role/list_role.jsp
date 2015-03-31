<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统角色</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addRole(){
	    	var url = "${pageContext.request.contextPath}/sysrole/add.do";
			Common.dialog({"url":url,"width":800,"height":300});
	    }
	    
	    //修改
	    function modifyRole(){
            var rows = $("#roleTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/sysrole/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":300});
	    }
	    
	    //删除
	    function deleteRole(){
            var rows = $("#roleTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/sysrole/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
		                		Common.showBottomMsg("删除成功");
				                searchRole();
			            	}else{
			            		Common.showBottomMsg("删除失败");  
			            	}
		                },"json");
    				}
    			});
            }else{
            	Common.showBottomMsg("请选择至少一条记录");
            }
	    }
	    
	    //分配资源
	    function doResource(){
            var rows = $("#roleTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                var row = rows[0];  
                var url = "${pageContext.request.contextPath}/sysrole/resourceList.do?roleId="+row.resourceid;
    			Common.dialog({"url":url,"width":400});
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	    //刷新角色资源关系缓存
	    function refreshRoles(){
	    	$.messager.confirm("确认?", "确认刷新吗？", function(r){
	    		if (r){
		            var url = "${pageContext.request.contextPath}/sysrole/refresh.do";
		            $.post(url,function(data){
		              	if(data.state == "success"){
		              		Common.showBottomMsg("成功");
		            	}else{
		            		Common.showBottomMsg("失败");  
		            	}
		        	},"json");
	    		}
	    	});
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
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysrole/datagrid.do'">
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
		
		<%-- 按钮栏 --%>
		<div id="tb">
			<app:btnAuth btnUrl="/sysrole/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRole()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysrole/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyRole()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysrole/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRole()">删除</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysrole/resourceList.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-zone-share',plain:true" onclick="doResource()">分配系统资源</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysrole/refresh.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="refreshRoles()">刷新角色缓存</a>|
			</app:btnAuth>
		</div>
  </body>
</html>