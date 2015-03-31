<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>会议室预订</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addMeetOrder(){
	    	var roomName = encodeURI(encodeURI("${room.meetName}"));//两次编码,后台解码
	    	var url = "${pageContext.request.contextPath}/meetOrder/add.do?roomId=${room.resourceid}&roomName="+roomName+"&random="+Math.random();
	    	Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	    //修改
	    function updateMeetOrder(){
            var rows = $("#meetOrderTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
	  		var condition = "";
	    	if (rowData.stating == "ing" || rowData.stating == "end"){ condition = "view=flag&"; }
			var url = "${pageContext.request.contextPath}/meetOrder/edit.do?"+condition+"resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	  	
	  	function rowStyle(index,row){
	  		if (row.stating == "ing"){
				return "color:blue;";
			}
	  		if (row.stating == "end"){
				return "color:#878787;";
			}
	  	}
	    
	    //删除
	    function deleteMeetOrder(){
            var rows = $("#meetOrderTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		            		if (rows[i].stating == "ing"){
		            			Common.showBottomMsg("会议正在进行中,不能删除...");
		            			return;
		            		}else if (rows[i].stating == "end"){
		            			Common.showBottomMsg("会议已结束,不能删除...");
		            			return;
		            		}else{
			                    ids.push(rows[i].resourceid);  
		            		}
		                }
		                var url = "${pageContext.request.contextPath}/meetOrder/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchMeetOrder();
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
	    function searchMeetOrder(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#meetOrderTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetMeetOrder(){
	    	Common.clearForm("queryForm");
	    	searchMeetOrder();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchMeetOrder();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">会议主题：<input type="text" id="meetSubject" name="meetSubject" value=""/></td>
						<td style="width: 33%">会议时间：
							<input type="text" id="startTime" name="startTime" value="" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}'})">&nbsp;-&nbsp;
					        <input type="text" id="endTime" name="endTime" value="" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'startTime\')}'})">
						</td>
						<td>
							<a onclick="searchMeetOrder()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetMeetOrder()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="会议室预订列表">
			<table id="meetOrderTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,rowStyler:rowStyle,sortName:'startTimeFmt',sortOrder:'desc',toolbar:'#tb',url:'${pageContext.request.contextPath}/meetOrder/datagrid.do?menuId=${param.menuId}&meetId=${room.resourceid }'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'meetSubject',width:300">会议主题</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'startTimeFmt',width:125,sortable:'true'">开始时间</th>
						<th data-options="field:'endTimeFmt',width:125,sortable:'true'">结束时间</th>
						<th data-options="field:'compereName',width:100">主持人</th>
						<th data-options="field:'recorder',width:100">记录人</th>
						<th data-options="field:'creator',width:100">申请人</th>
						<th data-options="field:'createTimeFmt',width:125,sortable:'true'">申请时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/meetOrder/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addMeetOrder()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/meetOrder/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateMeetOrder()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/meetOrder/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteMeetOrder()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>