<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>通知管理</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  //格式化
	    function formatNotifyType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Notification_Type")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    function formatReleaseFlag(val,row){
	    	var text = "";
	    	if("y" == val) text = "已发布";
	    	if("n" == val) text = "<font color='blue'>未发布</font>";
	        return text;
	    }
	    function formatTopFlag(val,row){
	    	var text = "";
	    	if("y" == val) text = "<font color='red'>置顶</font>";
	        return text;
	    }
	    
	    //新增
	    function addNotification(){
	    	var url = "${pageContext.request.contextPath}/notification/add.do";
	    	var v_height = window.screen.availHeight-150;
			Common.dialog({"url":url,"width":1100,"height":v_height});
	    }
	    
	    //修改
	    function updateNotification(){
            var rows = $("#notificationTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/notification/edit.do?resourceid="+rowData.resourceid;
			var v_height = window.screen.availHeight-150;
			Common.dialog({"url":url,"width":1100,"height":v_height});
	    }
	    
	    //删除
	    function deleteNotification(){
            var rows = $("#notificationTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/notification/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchNotification();
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
	    function searchNotification(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#notificationTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetNotification(){
	    	Common.clearForm("queryForm");
	    	searchNotification();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchNotification();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">标题：<input type="text" id="notifyTitle" name="notifyTitle" value=""/></td>
						<td style="width: 33%">类型：<app:select name="notifyType" dictionaryCode="D_Notification_Type"/></td>
						<td>发布状态：<select id="releaseFlag" name="releaseFlag"><option value="">请选择...</option><option value="y">已发布</option><option value="n">未发布</option></select></td>
					</tr>
					<tr>
						<td>置顶状态：<select id="topFlag" name="topFlag"><option value="">请选择...</option><option value="y">置顶</option></select></td>
						<td>创建人：<input type="text" id="creator" name="creator" value=""/></td>
						<td>
							<a onclick="searchNotification()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetNotification()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'">
			<table id="notificationTab" class="easyui-datagrid" title="通知列表" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTimeFmt',toolbar:'#tb',url:'${pageContext.request.contextPath}/notification/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'notifyTitle',width:300">标题</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'notifyType',width:100,formatter:formatNotifyType">类型</th>
						<th data-options="field:'releaseFlag',width:100,formatter:formatReleaseFlag">发布状态</th>
						<th data-options="field:'releaseTimeFmt',width:150,sortable:'true'">发布时间</th>
						<th data-options="field:'creator',width:100">创建人</th>
						<th data-options="field:'createDept',width:150">创建部门</th>
						<th data-options="field:'createTimeFmt',width:150,sortable:'true'">创建时间</th>
						<th data-options="field:'topFlag',width:100,formatter:formatTopFlag">置顶标志</th>
						<th data-options="field:'topTimeFmt',width:150">置顶失效时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/notification/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addNotification()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/notification/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateNotification()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/notification/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteNotification()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>