<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>商机</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Business_Type")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    function formatSource(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Business_Source")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    function formatStatus(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Business_Status")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    //新增
	    function addBusiness(){
	    	var url = "${pageContext.request.contextPath}/businessopportunity/add.do";
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	    //修改
	    function updateBusiness(){
            var rows = $("#businessTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/businessopportunity/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	    //删除
	    function deleteBusiness(){
            var rows = $("#businessTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/businessopportunity/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchBusiness();
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
	    function searchBusiness(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#businessTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetBusiness(){
	    	Common.clearForm("queryForm");
	    	searchBusiness();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchBusiness();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px;overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td style="width: 33%">商机名称：<input type="text" id="businessName" name="businessName" value=""/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
						<td>商机类型：<app:select name="businessType" dictionaryCode="D_Business_Type" value=""/></td>
						<td>
							<a onclick="searchBusiness()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetBusiness()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="商业机会列表">
			<table id="businessTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/businessopportunity/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'businessName',width:200">商机名称</th>
						<th data-options="field:'businessType',width:120,formatter:formatType">业务类型</th>
						<th data-options="field:'businessSource',width:120,formatter:formatSource">商机来源</th>
						<th data-options="field:'businessStatus',width:120,formatter:formatStatus">商机状态</th>
						<th data-options="field:'creator',width:120">创建人</th>
						<th data-options="field:'createTimeFmt',width:125">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/businessopportunity/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addBusiness()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/businessopportunity/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateBusiness()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/businessopportunity/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteBusiness()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>