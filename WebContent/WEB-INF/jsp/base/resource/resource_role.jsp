<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统资源</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatState(val,row){
	    	var state = "";
	    	if (val == "1") state = "有效";  
	    	if (val == "2") state = "<span style='color:red;'>无效</span>";  
	    	if (val == "3") state = "<span style='color:blue;'>隐藏</span>";  
	        return state;
	    }
	    
	    //搜索
	    function searchResource(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#resourceTab").treegrid("clearSelections");
	    	$("#resourceTab").treegrid("reload",dataParam);
	    }
	    
	  	//重置
	    function resetResource(){
	    	Common.clearForm("queryForm");
	    	searchResource();
	    }
	  	
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchResource();
			}
		}
	  	
	  	function expandAll(){
	  		$("#resourceTab").treegrid("expandAll");
	  	}
	  	
	  	function collapseAll(){
	  		$("#resourceTab").treegrid("collapseAll");
	  	}
		
	    function clickRow(row){
	    	$("#roleTab").datagrid("reload",{resourceid:row.resourceid});
			$("#roleTab").datagrid("getPanel").panel("setTitle","菜单名称："+row.menuName);
	    }
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
  		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">菜单名称 / 链接地址：<input type="text" id="menuName" name="menuName" value=""/></td>
						<td style="width: 33%">菜单编码：<input type="text" id="menuCode" name="menuCode" value=""/></td>
						<td>
							<a onclick="searchResource()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetResource()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'">
			<table id="resourceTab" class="easyui-treegrid" title="系统资源树" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'menuName',onClickRow:clickRow,toolbar:'#tb3',url:'${pageContext.request.contextPath}/sysresource/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'menuName',width:170">菜单名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'menuCode',width:180">菜单编码</th>
						<th data-options="field:'menuUrl',width:270">链接地址</th>
						<th data-options="field:'stateFlag',width:50,align:'center',formatter:formatState">状态</th>
						<th data-options="field:'orderBy',width:50,align:'right'">排序</th>
						<th data-options="field:'remark',width:350">备注</th>
					</tr>
				</thead>
				<%-- 按钮栏 --%>
				<div id="tb3">
					<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
					<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
				</div>
			</table>
		</div>
		
		<div data-options="region:'east',title:'角色',split:true" style="width:450px;">
			<table id="roleTab" class="easyui-datagrid" title="菜单名称："
				data-options="border:false,rownumbers:true,fit:true,striped:true,remoteSort:false,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysresource/roleGrid.do'">
				<thead data-options="frozen:true"> 
					<tr>
						<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
						<th data-options="field:'roleName',width:120">角色名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'roleCode',width:150">角色编码</th>
						<th data-options="field:'orderBy',width:50,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'remark',width:300">备注</th>
					</tr>
				</thead>
			</table>
		</div>
  </body>
</html>