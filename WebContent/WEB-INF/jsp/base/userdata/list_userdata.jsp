<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统用户数据权限</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addUserData(){
	    	var url = "${pageContext.request.contextPath}/sysUserDataLimit/add.do";
			Common.dialog({"url":url,"width":800,"height":400});
	    }
	    
	    //修改
	    function modifyUserData(){
            var rows = $("#userDataTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
            var url = "${pageContext.request.contextPath}/sysUserDataLimit/edit.do?resourceid="+rowData.resourceid;
   			Common.dialog({"url":url,"width":800,"height":400});
	    }
	    
	    //删除
	    function deleteUserData(){
            var rows = $("#userDataTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/sysUserDataLimit/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchUserData();
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
	    
	    //刷新缓存
	    function refreshUserData(){
	    	$.messager.confirm("确认?", "确认刷新吗？", function(r){
	    		if (r){
		            var url = "${pageContext.request.contextPath}/sysUserDataLimit/refresh.do";
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
	    function searchUserData(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#userDataTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetUserData(){
	    	Common.clearForm("queryForm");
			searchUserData();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchUserData();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">用户名称：<input type="text" id="userName" name="userName" value=""/></td>
						<td style="width: 33%">资源名称：<input type="text" id="resourceName" name="resourceName" value=""/></td>
						<td>
							<a onclick="searchUserData()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetUserData()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="系统用户数据权限列表">
			<table id="userDataTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysUserDataLimit/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'userName',width:100">用户名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'resourceName',width:200">资源名称</th>
						<th data-options="field:'hqlCondition',width:400">HQL条件</th>
						<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'remark',width:400">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb">
			<app:btnAuth btnUrl="/sysUserDataLimit/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addUserData()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysUserDataLimit/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyUserData()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysUserDataLimit/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteUserData()">删除</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysUserDataLimit/refresh.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="refreshUserData()">刷新数据权限缓存</a>|
			</app:btnAuth>
		</div>
  </body>
</html>