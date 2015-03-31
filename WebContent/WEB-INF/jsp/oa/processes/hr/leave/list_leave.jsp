<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>请假申请</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatLeaveType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Leave_Type")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text; 
	    }
	  
	    //新增
	    function addLeave(){
	    	var url = "${pageContext.request.contextPath}/leaveApply/add.do?menuId=${param.menuId}";
			Common.dialog({"url":url});
	    }
	    
	    //修改
	    function editLeave(){
            var rows = $("#leaveTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRowLeave(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRowLeave(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/leaveApply/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":400});
	    }
	  	
	  	//删除
	  	function deleteLeave(){
	  		var row = $("#leaveTab").datagrid("getSelected");
            if(row){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		                var url = "${pageContext.request.contextPath}/leaveApply/delete.do?id="+row.resourceid;
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchLeave();
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
	    function searchLeave(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#leaveTab").datagrid("reload", dataParam);
	    }
	    
	    function resetLeave(){
	    	Common.clearForm("queryForm");
	    	searchLeave();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchLeave();
			}
		}
  	</script>
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:65px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">
							请假类型：<app:select name="leaveType" dictionaryCode="D_Leave_Type"/>
						</td>
						<td style="width: 33%">请假时间：
							<input type="text" id="startTime" name="startTime" value="" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
							- <input type="text" id="endTime" name="endTime" value="" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
						</td>
						<td>
							<a onclick="searchLeave()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>	
							<a onclick="resetLeave()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="请假流程列表" >
			<table id="leaveTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,onDblClickRow:clickRowLeave,sortName:'createTimeFmt',sortOrder:'desc',toolbar:'#tb',url:'${pageContext.request.contextPath}/leaveApply/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'applyName',width:100">申请人</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'deptName',width:100">部门</th>
						<th data-options="field:'postName',width:100">岗位</th>
						<th data-options="field:'leaveType',width:100,formatter:formatLeaveType">假期类型</th>
						<th data-options="field:'startTimeFmt',width:120">开始时间</th>
						<th data-options="field:'endTimeFmt',width:120">结束时间</th>
						<th data-options="field:'sumDay',width:100">合计时间(天)</th>
						<th data-options="field:'applyReason',width:200">请假理由</th>
						<th data-options="field:'createTimeFmt',width:125,sortable:'true'">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/leaveApply/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addLeave()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/leaveApply/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editLeave()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/leaveApply/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteLeave()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>