<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>流程定义</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
  	//格式化
  	function formatIsValid(val,row){
	    	var text = "";
	    	if("y" == val) text = "可用";
	    	if("n" == val) text = "<font color='blue'>禁用</font>";
	        return text;
	}
  	function formatActivityType(val,row){
	    	var text = "";
	    	if("T" == val) text = "任务";
	    	if("S" == val) text = "<font color='blue'>开始</font>";
	    	if("E" == val) text = "<font color='blue'>结束</font>";
	        return text;
	}
  	function formatTaskFlag(val,row){
	    	var text = "";
	    	if(row.activityType == 'T'){
		    	if("Y" == val) text = "强制";
		    	if("N" == val) text = "非强制";
		    	if("M" == val) text = "多人";
	    	}
	        return text;
	}
  	function formatForkJoin(val,row){
	    	var text = "";
		    if("G" == val) text = "普通";
		    if("F" == val) text = "<font color='blue'>分支</font>";
		    if("J" == val) text = "<font color='blue'>聚合</font>";
		    if("B" == val) text = "<font color='blue'>聚合分支</font>";
	        return text;
	}
  	function formatGobackType(val,row){
	    	var text = "";
		    if("A" == val) text = "默认/直接回退";
		    if("D" == val) text = "默认回退";
		    if("R" == val) text = "直接回退";
	        return text;
	}
  	function formatYorN(val,row){
	    	var text = "";
		    if("Y" == val) text = "√";
	        return text;
	}
  	function formatCustom(val,row){
	    	var text = "";
		    if("S" == val) text = "显示";
		    if("H" == val) text = "隐藏";
		    if("C" == val) text = "自定义";
	        return text;
	}
  	function formatFinishType(val,row){
	    	var text = "";
		    if("Y" == val) text = "正常结束";
		    if("N" == val) text = "异常结束";
	        return text;
	}
  	
  	//新增流程
    function addProcess(){
    	var orderBy = $("#processTab").datagrid("getRows").length + 1;
    	var url = "${pageContext.request.contextPath}/wfDefineProcess/add.do?orderBy="+orderBy;
		Common.dialog({"url":url,"width":800,"height":400});
    }
    
    //修改流程
    function editProcess(){
        var row = $("#processTab").datagrid("getSelected");
        if(row){
        	clickRowProcess(0, row);
        }else{
        	Common.showBottomMsg("请选择一条记录");
        }
    }
    
    //双击流程
    function clickRowProcess(rowIndex, rowData){
    	var url = "${pageContext.request.contextPath}/wfDefineProcess/edit.do?resourceid="+rowData.resourceid;
		Common.dialog({"url":url,"width":800,"height":400});
    }
    
    //删除流程
    function deleteProcess(){
        var row = $("#processTab").datagrid("getSelected");
        if(row){
        	$.messager.confirm("确认?", "确认删除吗？", function(r){
				if (r){
	                var url = "${pageContext.request.contextPath}/wfDefineProcess/delete.do?id="+row.resourceid;
	                $.post(url,function(data){
	                	if(data.state == "success"){
		                	Common.showBottomMsg("删除成功");
		                	searchProcess();
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
    
  	//搜索流程
    function searchProcess(){
    	$("#processTab").datagrid("reload");
    }
    
    //活动子表明细
    function selectRowToActivity(rowIndex, rowData){
    	$("#activityTab").datagrid("reload",{processId:rowData.resourceid});
    	//$("#activityTab").datagrid("getPanel").panel("setTitle",rowData.menuName+"  -  活动定义");
    }
    
  	//新增活动
    function addActivity(){
    	var row = $("#processTab").datagrid("getSelected");
        if(row){
        	var orderBy = $("#activityTab").datagrid("getRows").length + 1;
        	var processName = encodeURI(encodeURI(row.menuName));//两次编码,后台解码
	    	var url = "${pageContext.request.contextPath}/wfDefineActivity/add.do?processId="+row.resourceid+"&processName="+processName+"&orderBy="+orderBy;
			Common.dialog({"url":url,"width":1000,"height":550});
        }else{
        	Common.showBottomMsg("请选择一个流程定义");
        }
    }
    
    //修改活动
    function editActivity(){
        var row = $("#activityTab").datagrid("getSelected");
        if(row){
        	clickRowActivity(0, row);
        }else{
        	Common.showBottomMsg("请选择一条记录");  
        }
    }
    
    //双击活动
    function clickRowActivity(rowIndex, rowData){
    	var url = "${pageContext.request.contextPath}/wfDefineActivity/edit.do?resourceid="+rowData.resourceid;
		Common.dialog({"url":url,"width":1000,"height":550});
    }
    
    //删除活动
    function deleteActivity(){
        var row = $("#activityTab").datagrid("getSelected");
        if(row){
        	$.messager.confirm("确认?", "确认删除吗？", function(r){
				if (r){
	                var url = "${pageContext.request.contextPath}/wfDefineActivity/delete.do?id="+row.resourceid;
	                $.post(url,function(data){
	                	if(data.state == "success"){
		                	Common.showBottomMsg("删除成功");
		                	searchActivity();
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
    
  	//搜索活动
    function searchActivity(){
    	$("#activityTab").datagrid("reload");
    }
  	
  	//路由子表明细
    function selectRowToRoute(rowIndex, rowData){
    	$("#routeTab").datagrid("reload",{activityId:rowData.resourceid});
    }
  	
  	//新增路由
    function addRoute(){
    	var row = $("#activityTab").datagrid("getSelected");
        if(row){
        	var orderBy = $("#routeTab").datagrid("getRows").length + 1;
	    	var url = "${pageContext.request.contextPath}/wfDefineRoute/add.do?activityId="+row.resourceid+"&orderBy="+orderBy;
			Common.dialog({"url":url,"width":800,"height":400});
        }else{
        	Common.showBottomMsg("请选择一个活动定义");
        }
    }
    
    //修改路由
    function editRoute(){
        var row = $("#routeTab").datagrid("getSelected");
        if(row){
        	clickRowRoute(0, row);
        }else{
        	Common.showBottomMsg("请选择一条记录");  
        }
    }
    
    //双击路由
    function clickRowRoute(rowIndex, rowData){
    	var url = "${pageContext.request.contextPath}/wfDefineRoute/edit.do?resourceid="+rowData.resourceid;
		Common.dialog({"url":url,"width":800,"height":400});
    }
    
    //删除路由
    function deleteRoute(){
        var row = $("#routeTab").datagrid("getSelected");
        if(row){
        	$.messager.confirm("确认?", "确认删除吗？", function(r){
				if (r){
	                var url = "${pageContext.request.contextPath}/wfDefineRoute/delete.do?id="+row.resourceid;
	                $.post(url,function(data){
	                	if(data.state == "success"){
		                	Common.showBottomMsg("删除成功");
		                	searchRoute();
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
    
  	//搜索路由
    function searchRoute(){
    	$("#routeTab").datagrid("reload");
    }
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
    <div data-options="region:'west',title:'流程定义',split:true" style="width:425px;">
    	<table id="processTab" class="easyui-datagrid" 
			data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,onDblClickRow:clickRowProcess,onSelect:selectRowToActivity,sortName:'orderBy',toolbar:'#tb1',url:'${pageContext.request.contextPath}/wfDefineProcess/datagrid.do'">
			<thead>
				<tr>
					<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%>
					<th data-options="field:'menuName',width:150">资源名称</th>
					<th data-options="field:'isValid',width:60,formatter:formatIsValid">是否可用</th>
					<th data-options="field:'orderBy',width:50,align:'right',sortable:'true'">排序</th>
					<th data-options="field:'remark',width:120">备注</th>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb1" style="height:auto">
			<app:btnAuth btnUrl="/wfDefineProcess/add.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProcess()">新增</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineProcess/edit.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editProcess()">修改</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineProcess/delete.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProcess()">删除</a>|</app:btnAuth>
		</div>
    </div>
    
    <div data-options="region:'center',title:'活动定义'">
    	<table id="activityTab" class="easyui-datagrid" 
			data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,onDblClickRow:clickRowActivity,onSelect:selectRowToRoute,sortName:'orderBy',toolbar:'#tb2',url:'${pageContext.request.contextPath}/wfDefineActivity/datagrid.do'">
			<thead data-options="frozen:true"> 
				<tr>
					<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%>
					<th data-options="field:'activityName',width:150">活动名称</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th data-options="field:'activityAlias',width:100">活动别名</th>
					<th data-options="field:'activityType',width:70,formatter:formatActivityType">活动类型</th>
					<th data-options="field:'taskFlag',width:70,formatter:formatTaskFlag">任务标志</th>
					<th data-options="field:'forkJoin',width:70,formatter:formatForkJoin">分支聚合</th>
					<th data-options="field:'joinActivityCount',width:70">聚合节点数</th>
					<th data-options="field:'orderBy',width:50,align:'right',sortable:'true'">排序</th>
					<th data-options="field:'swimActivityName',width:120">泳道活动</th>
					<th data-options="field:'timeLimit',width:70">时间限制</th>
					<th data-options="field:'gobackType',width:80,formatter:formatGobackType">回退类型</th>
					<th data-options="field:'queryHistory',width:70,align:'center',formatter:formatYorN">查询历史</th>
					<th data-options="field:'hideAgent',width:70,align:'center',formatter:formatYorN">隐藏经办人</th>
					<th data-options="field:'copyBtn',width:60,align:'center',formatter:formatYorN">抄送</th>
					<th data-options="field:'signBtn',width:60,align:'center',formatter:formatYorN">会签</th>
					<th data-options="field:'authorizeBtn',width:60,align:'center',formatter:formatYorN">委托</th>
					<th data-options="field:'takebackBtn',width:60,align:'center',formatter:formatYorN">收回</th>
					<th data-options="field:'emailRemind',width:70,align:'center',formatter:formatYorN">邮件提醒</th>
					<th data-options="field:'smsRemind',width:70,align:'center',formatter:formatYorN">短信提醒</th>
					<th data-options="field:'opinionCustomFlag',width:80,formatter:formatCustom">意见框</th>
					<th data-options="field:'remark',width:150">备注</th>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb2" style="height:auto">
			<app:btnAuth btnUrl="/wfDefineActivity/add.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addActivity()">新增</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineActivity/edit.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editActivity()">修改</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineActivity/delete.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteActivity()">删除</a>|</app:btnAuth>
		</div>
    </div>

    <div data-options="region:'east',title:'路由定义',split:true" style="width:360px;">
    	<table id="routeTab" class="easyui-datagrid" 
			data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,onDblClickRow:clickRowRoute,sortName:'orderBy',toolbar:'#tb3',url:'${pageContext.request.contextPath}/wfDefineRoute/datagrid.do'">
			<thead data-options="frozen:true"> 
				<tr>
					<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%>
					<th data-options="field:'nextActivityName',width:150">下一个活动</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th data-options="field:'routeAlias',width:100">路由别名</th>
					<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
					<th data-options="field:'finishType',width:80,formatter:formatFinishType">结束类型</th>
					<th data-options="field:'conditionExpression',width:100">条件表达式</th>
					<th data-options="field:'candidateName',width:100">主办人</th>
					<th data-options="field:'personName',width:100">经办人</th>
					<th data-options="field:'remark',width:120">备注</th>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb3" style="height:auto">
			<app:btnAuth btnUrl="/wfDefineRoute/add.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addRoute()">新增</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineRoute/edit.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editRoute()">修改</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineRoute/delete.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteRoute()">删除</a>|</app:btnAuth>
		</div>
    </div>
  </body>
</html>