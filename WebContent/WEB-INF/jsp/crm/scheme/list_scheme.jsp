<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>方案</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addScheme(){
	    	var url = "${pageContext.request.contextPath}/scheme/add.do";
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	    //修改
	    function updateScheme(){
            var rows = $("#schemeTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/scheme/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	    //删除
	    function deleteScheme(){
            var rows = $("#schemeTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/scheme/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchScheme();
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
	    function searchScheme(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#schemeTab").datagrid("reload", dataParam);
	    }
	    
	    function resetScheme(){
	    	Common.clearForm("queryForm");
	    	searchScheme();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchScheme();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px;overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 50%">客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td>方案名称：<input type="text" id="schemeName" name="schemeName" value=""/></td>
					</tr>
					<tr>
						<td>项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
						<td>
							<a onclick="searchScheme()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetScheme()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="方案列表">
			<table id="schemeTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/scheme/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'schemeName',width:200">方案名称</th>
						<th data-options="field:'creator',width:120">创建人</th>
						<th data-options="field:'createTimeFmt',width:125">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/scheme/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addScheme()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/scheme/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateScheme()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/scheme/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteScheme()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>