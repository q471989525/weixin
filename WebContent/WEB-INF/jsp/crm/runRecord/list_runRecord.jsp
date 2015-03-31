<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>维护</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatItem(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Service_Item")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    //新增
	    function addRunRecord(){
	    	var url = "${pageContext.request.contextPath}/runrecord/add.do";
			Common.dialog({"url":url,"width":800,"height":600});
	    }
	    
	    //修改
	    function updateRunRecord(){
            var rows = $("#runRecordTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/runrecord/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":600});
	    }
	    
	    //删除
	    function deleteRunRecord(){
            var rows = $("#runRecordTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/runrecord/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchRunrecord();
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
	    function searchRunrecord(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#runRecordTab").datagrid("reload", dataParam);
	    }
	    
	    function resetRunrecord(){
	    	Common.clearForm("queryForm");
	    	searchRunrecord();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchRunrecord();
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
						<td>项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
					</tr>
					<tr>
						<td>服务项目：<app:select name="serviceItem" dictionaryCode="D_Service_Item"/></td>
						<td>
							<a onclick="searchRunrecord()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetRunrecord()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="维护列表">
			<table id="runRecordTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/runrecord/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'serviceItem',width:200,formatter:formatItem">服务项目</th>
						<th data-options="field:'servicePerson',width:120">服务人员</th>
						<th data-options="field:'startTimeFmt',width:125">开始时间</th>
						<th data-options="field:'endTimeFmt',width:125">结束时间</th>
						<th data-options="field:'customerSign',width:120">客户签字</th>
						<th data-options="field:'createTimeFmt',width:125">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/runrecord/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRunRecord()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/runrecord/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateRunRecord()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/runrecord/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRunRecord()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>