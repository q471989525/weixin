<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>实施日志</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatItem(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Implement_Item")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	  	
	    //新增
	    function addImplementLog(){
	    	var url = "${pageContext.request.contextPath}/implementLog/add.do";
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //修改
	    function updateImplementLog(){
            var rows = $("#implementLogTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/implementLog/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //删除
	    function deleteImplementLog(){
            var rows = $("#implementLogTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/implementLog/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchImplementLog();
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
	    function searchImplementLog(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#implementLogTab").datagrid("reload", dataParam);
	    }
	    
	    function resetImplementLog(){
	    	Common.clearForm("queryForm");
	    	searchImplementLog();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchImplementLog();
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
						<td>实施项目：<app:select name="implementItem" dictionaryCode="D_Implement_Item" value=""/></td>
						<td>
							<a onclick="searchImplementLog()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetImplementLog()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="实施日志列表">
			<table id="implementLogTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/implementLog/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'implementItem',width:120,formatter:formatItem">实施项目</th>
						<th data-options="field:'implementPerson',width:100">实施人</th>
						<th data-options="field:'customerSign',width:100">客户签字</th>
						<th data-options="field:'startTimeFmt',width:150">开始时间</th>
						<th data-options="field:'endTimeFmt',width:150">结束时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/implementLog/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addImplementLog()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/implementLog/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateImplementLog()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/implementLog/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteImplementLog()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>