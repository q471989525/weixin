<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
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
		
	    //新增
	    function addResource(){
	    	var param = "";
	    	var row = $("#resourceTab").treegrid("getSelected");
	    	if(row && row.stateFlag=="1"){
	    		var name = encodeURI(encodeURI(row.menuName));//两次编码,后台解码
	    		param = "?parentId="+row.resourceid+"&parentName="+name;
	    	}
	    	var url = "${pageContext.request.contextPath}/sysresource/add.do"+param;
			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	    
	    //修改
	    function modifyResource(){
	    	var row = $("#resourceTab").treegrid("getSelected");
	    	if(row){
	    		dbClickRow(row);
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	  	//双击
	    function dbClickRow(rowData){
			var url = "${pageContext.request.contextPath}/sysresource/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	  	
	  	//单选
	    function clickRow(row){
			$("#subTab").datagrid("reload",{parentId:row.resourceid});
			$("#subTab").datagrid("getPanel").panel("setTitle","菜单名称："+row.menuName);
	    }
	  	
	    //子表刷新
	    function searchSub(){
	    	$("#subTab").datagrid("reload");
	    }

	    //删除
	    function deleteResource(){
	    	var row = $("#resourceTab").treegrid("getSelected");
	    	if(row){
            	$.messager.confirm("确认?", "确认删除吗，如果存在子节点将一起删除，请谨慎操作？", function(r){
    				if (r){
		                var url = "${pageContext.request.contextPath}/sysresource/delete.do?id="+row.resourceid;
		                $.post(url,function(data){
		                	if(data.state == "success"){
		                		Common.showBottomMsg("删除成功");
				                searchResource();
				                searchSub();
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
		
		<div data-options="region:'center'" title="系统资源树">
			<table id="resourceTab" class="easyui-treegrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'menuName',onClickRow:clickRow,onDblClickRow:dbClickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/sysresource/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
						<th data-options="field:'menuName',width:180">菜单名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'menuCode',width:180">菜单编码</th>
						<th data-options="field:'menuUrl',width:240">链接地址</th>
						<th data-options="field:'stateFlag',width:50,align:'center',formatter:formatState">状态</th>
						<th data-options="field:'orderBy',width:50,align:'right'">排序</th>
						<th data-options="field:'parentName',width:180">父菜单名称</th>
						<th data-options="field:'remark',width:300">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div data-options="region:'east',title:'按钮',split:true" style="width:450px;">
			<table id="subTab" class="easyui-datagrid" title="菜单名称："
				data-options="border:false,rownumbers:true,fit:true,striped:true,remoteSort:false,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysresource/subgrid.do'">
				<thead data-options="frozen:true"> 
					<tr>
						<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
						<th data-options="field:'menuName',width:80">按钮名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'menuCode',width:80">按钮编码</th>
						<th data-options="field:'orderBy',width:50,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'menuUrl',width:200">URL地址</th>
						<th data-options="field:'remark',width:200">备注</th>
					</tr>
				</thead>
			</table>
		</div>
				
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/sysresource/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addResource()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysresource/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyResource()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysresource/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteResource()">删除</a>|
			</app:btnAuth>
			<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
		</div>
  </body>
</html>