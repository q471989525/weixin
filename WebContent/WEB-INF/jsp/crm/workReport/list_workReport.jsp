<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>工作报告</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatType(val,row){
	    	if (val == "d"){  
	            return "日报";  
	        } else if (val == "w"){
	            return "周报";  
	        } else if (val == "m"){
	            return "月报";  
	        } else {  
	            return "";  
	        } 
	    }
	    function formatFlag(val,row){
	    	if (val == "y"){  
	            return "<span style='color:red'>已审核</span>";  
	        } else {  
	            return "";  
	        } 
	    }
	  
	    //新增
	    function addWorkReport(){
	    	var url = "${pageContext.request.contextPath}/workreport/add.do";
			Common.dialog({"url":url,"width":600,"height":400});
	    }
	    
	    //修改
	    function updateWorkReport(){
            var rows = $("#workReportTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/workreport/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":600,"height":400});
	    }
	  	
	  	//删除
	  	function deleteWorkReport(){
	  		var rows = $("#workReportTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		            		if(rows[i].verifyFlag=='y'){
		            			 Common.showBottomMsg("已审核，不能删除？");
		            			 return;
		            		}
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/workreport/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchWorkReport();
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
	    
	    //验证审核
	    function verifyWorkReport(){
            var rows = $("#workReportTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                var row = rows[0];  
                var url = "${pageContext.request.contextPath}/workreport/verify.do?resourceid="+row.resourceid;
    			Common.dialog({"url":url,"width":600,"height":500});
            }else{
                Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	    //搜索
	    function searchWorkReport(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#workReportTab").datagrid("reload", dataParam);
	    }
	    
	    function resetWorkReport(){
	    	Common.clearForm("queryForm");
	    	searchWorkReport();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchWorkReport();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">
							报告类型：<select id="reportType" name="reportType">
								<option value="">请选择...</option>
								<option value="d">日报</option>
								<option value="w">周报</option>
								<option value="m">月报</option>
							</select>
						</td>
						<td style="width: 33%">报&nbsp;告&nbsp;&nbsp;人：<input type="text" id="creator" name="creator" value=""/></td>
						<td><app:btnAuth btnUrl="/workreport/verify.do">
							状态：<select id="verifyFlag" name="verifyFlag">
								<option value="">请选择...</option>
								<option value="y">已审核</option>
							</select>
							</app:btnAuth>
						</td>
					</tr>
					<tr>
						<td style="width: 33%">开始日期：<input type="text" id="startDate" name="startDate" value="" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"/></td>
						<td style="width: 33%">结束日期：<input type="text" id="endDate" name="endDate" value="" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"/></td>
						<td>
							<a onclick="searchWorkReport()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>	
							<a onclick="resetWorkReport()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
							<input type="hidden" id="type" name="type" value="${type}">
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="工作报告列表" >
			<table id="workReportTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTimeFmt',sortOrder:'desc',toolbar:'#tb',url:'${pageContext.request.contextPath}/workreport/datagrid.do?menuId=${param.menuId}',queryParams:{type:'${type}'}">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'reportType',width:100,formatter:formatType">报告类型</th>
						<th data-options="field:'creator',width:100">报告人</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'startDateFmt',width:100">开始日期</th>
						<th data-options="field:'endDateFmt',width:100">结束日期</th>
						<th data-options="field:'workContent',width:550">工作内容</th>
						<th data-options="field:'verifyFlag',width:100,formatter:formatFlag">审核状态</th>
						<th data-options="field:'createTimeFmt',width:120,sortable:'true'">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/workreport/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addWorkReport()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/workreport/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateWorkReport()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/workreport/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteWorkReport()">删除</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/workreport/verify.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="verifyWorkReport()">审核</a>|
			</app:btnAuth>
		</div>
  </body>
</html>