<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>竞争对手</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addCompetitor(){
	    	var url = "${pageContext.request.contextPath}/competitor/add.do";
			Common.dialog({"url":url,"width":800,"height":480});
	    }
	    
	    //修改
	    function updateCompetitor(){
            var rows = $("#competitorTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/competitor/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":480});
	    }
	    
	    //删除
	    function deleteCompetitor(){
            var rows = $("#competitorTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/competitor/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchCompetitor();
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
	    function searchCompetitor(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#competitorTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetCompetitor(){
	    	Common.clearForm("queryForm");
	    	searchCompetitor();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchCompetitor();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">竞争对手：<input type="text" id="competitorName" name="competitorName" value=""/></td>
						<td>
							<a onclick="searchCompetitor()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCompetitor()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="竞争对手列表">
			<table id="competitorTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/competitor/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'competitorName',width:200">竞争对手</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'companyScale',width:100">企业规模</th>
						<th data-options="field:'companyProperty',width:100">企业性质</th>
						<th data-options="field:'companyDesc',width:350">描述</th>
						<th data-options="field:'superiority',width:350">优势</th>
						<th data-options="field:'disadvantages',width:350">劣势</th>
						<th data-options="field:'createTimeFmt',width:120">创建时间</th>
						<th data-options="field:'creator',width:80">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/competitor/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCompetitor()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/competitor/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateCompetitor()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/competitor/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteCompetitor()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>