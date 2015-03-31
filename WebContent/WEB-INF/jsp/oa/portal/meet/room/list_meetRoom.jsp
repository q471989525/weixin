<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>会议室管理</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
  		//格式化
  		function formatValidFlag(val,row){
	    	var text = "";
	    	if("y" == val) text = "有效";
	    	if("n" == val) text = "<font color='blue'>无效</font>";
	        return text;
	    }
	    //新增
	    function addMeetRoom(){
	    	var url = "${pageContext.request.contextPath}/meetRoom/add.do";
			Common.dialog({"url":url,"width":800,"height":400});
	    }
	    
	    //修改
	    function updateMeetRoom(){
            var rows = $("#meetRoomTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/meetRoom/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":400});
	    }
	    
	    //删除
	    function deleteMeetRoom(){
            var rows = $("#meetRoomTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/meetRoom/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchMeetRoom();
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
	    function searchMeetRoom(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#meetRoomTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetMeetRoom(){
	    	Common.clearForm("queryForm");
	    	searchMeetRoom();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchMeetRoom();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">会议室名称：<input type="text" id="meetName" name="meetName" value=""/></td>
						<td>
							<a onclick="searchMeetRoom()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetMeetRoom()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="会议室列表">
			<table id="meetRoomTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTimeFmt',toolbar:'#tb',url:'${pageContext.request.contextPath}/meetRoom/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'meetName',width:180">会议室名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'adminName',width:80">管理员</th>
						<th data-options="field:'validFlag',width:70,align:'center',formatter:formatValidFlag">状态</th>
						<th data-options="field:'meetCapacity',width:180">会议室容量</th>
						<th data-options="field:'meetLocation',width:200">会议室位置</th>
						<th data-options="field:'meetEquip',width:200">会议室设备</th>
						<th data-options="field:'createTimeFmt',width:125,sortable:'true'">创建时间</th>
						<th data-options="field:'orderBy',width:65,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'creator',width:100">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/meetRoom/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addMeetRoom()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/meetRoom/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateMeetRoom()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/meetRoom/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteMeetRoom()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>