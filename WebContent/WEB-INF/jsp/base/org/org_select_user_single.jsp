<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>用户选择(单选)</title>
    <script type="text/javascript">
	<!--
		var ctx = "${pageContext.request.contextPath}";
	//-->
	</script>
    <link href="${pageContext.request.contextPath}/common/zTree_v3/css/default.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/common/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
  	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
  	<script src="${pageContext.request.contextPath}/common/zTree_v3/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
  	<script type="text/javascript">
  	//格式化岗位数据字典
  	function formatPost(val){
    	var text = "";
    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Post")}');
    	if(null == dictObj) return text;
    	for ( var int = 0; int < dictObj.length; int++) {
    		if(dictObj[int].value == val) text = dictObj[int].name;
		}
        return text;
    }
  	
  	function formatSex(val){
    	var text = "";
    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Sex")}');
    	if(null == dictObj) return text;
    	for ( var int = 0; int < dictObj.length; int++) {
    		if(dictObj[int].value == val) text = dictObj[int].name;
		}
        return text;
  	}
      	
  	//增加用户
  	function appedRows(url, param){
    	$("#userDg").datagrid({ data: [] }); //先清空数据
  		$.post(url, param, function(data){
        	if(data.length>0){
         		for ( var int = 0; int < data.length; int++) {
         			$("#userDg").datagrid("appendRow",{
         				userid:data[int].resourceid,
         				username:data[int].username,
         				employeeId:data[int].employeeId,
         				postname:formatPost(data[int].post),
         				post:data[int].post,
         				mobile:data[int].mobile,
         				officePhone:data[int].officePhone,
         				email:data[int].email,
         				sexname:formatSex(data[int].sex),
         				sex:data[int].sex
         			});
         		}
         	}
         },"json");
  	}
  	
    //姓名查询用户
    function searchUser(){
    	var username = $("#username").val();
    	if(username != ""){
    		var url = "${pageContext.request.contextPath}/sysuser/findUsers.do";
    		appedRows(url, {username:username, hideState:"${hideState}"});
    	}else{
    		$("#userDg").datagrid({ data: [] }); //先清空数据
    	}
    }
    
    function clearForm(){
    	$("#username").val("");
    }
    
  	//按下回车时，直接查询
	function onFormKeyPress(event){
		if (event.keyCode==13){
			searchUser();
		}
	}
  	
	//ztree 设置
	var setting = {
		data: {simpleData:{enable:true}},
		view: {expandSpeed: "fast", selectedMulti: false},
		check: {enable: true},
		callback: {onCheck: orgFindUser}
	};
	
	// ztree json对象
	var zNodes = ${ztreeString};
	
	//初始化
	var treeObj;
	$(document).ready(function(){
		treeObj = $.fn.zTree.init($("#ztreeORG"), setting, zNodes);
		//$("#ztreeORG").width($("#orgDiv").width()-2);
		//$("#ztreeORG").height($("#orgDiv").height()-2);
		
		//初始化选中用户
		var userId = "${userId}";
		var userName = "${userName}";
		if(userId.length>0 && userName.length>0){
			$("#selectUserDg").datagrid("appendRow",{
     			userid:userId,
     			username:userName
     		});
		}
	});
    
  	//ztree click回调方法,组织查询用户
	function orgFindUser(event, treeId, treeNode) {
		var nodes = treeObj.getCheckedNodes(true); //获取选中
		if(nodes.length>0){
			var orgIds = new Array();
    		$.each(nodes, function(i, n){
    			orgIds.push(n.id);
    		});
    		var url = "${pageContext.request.contextPath}/sysuser/findUsersByOrg.do";
    		appedRows(url, {orgIds:orgIds.toString(), hideState:"${hideState}"});
		}else{
			$("#userDg").datagrid({ data: [] }); //先清空数据
		}
	}
  	
    //角色查找用户
  	function clickRowRole(rowIndex, rowData){
  		var rows = $("#roleTab").datagrid("getSelections");
  		if(rows.length>0){
  			var roleIds = new Array();
    		for(var i=0; i<rows.length; i++){
    			roleIds.push(rows[i].resourceid);  
            }
    		var url = "${pageContext.request.contextPath}/sysuser/findUsersByRole.do";
    		appedRows(url, {roleIds:roleIds.toString(), hideState:"${hideState}"});
    	}else{
    		$("#userDg").datagrid({ data: [] }); //先清空数据
    	}
  	}
  	
  	//岗位查找用户
  	function clickRowPost(rowIndex, rowData){
  		var rows = $("#postTab").datagrid("getSelections");
    	if(rows.length>0){
    		var dicts = new Array();
    		for(var i=0; i<rows.length; i++){
    			dicts.push(rows[i].itemValue);  
            }
    		var url = "${pageContext.request.contextPath}/sysuser/findUsers.do";
    		appedRows(url, {postVal:dicts.toString(), hideState:"${hideState}"});
    	}else{
    		$("#userDg").datagrid({ data: [] }); //先清空数据
    	}
  	}
  	
  	//添加用户
  	function arrowRight(){
  		var users = $("#userDg").datagrid("getSelections"); //选中增加用户
  		if(users.length>0){
	  		var selectUsers = $("#selectUserDg").datagrid("getRows"); //获取已选用户
	  		if(selectUsers.length>0){ //过滤重复用户
		  		for (var i = 0; i < users.length; i++) {
		  			var addFlag = true;
	  				for (var j=0; j<selectUsers.length; j++){
		  				if(users[i].userid == selectUsers[j].userid){addFlag = false; break;}
		         	}
	  				
	  				if(addFlag){
		         		$("#selectUserDg").datagrid("appendRow",{
		         			userid:users[i].userid,
		         			username:users[i].username
		         		});
	  				}
	  			}
	  		}else{
	         	for ( var i = 0; i < users.length; i++) {
	         		$("#selectUserDg").datagrid("appendRow",{
	         			userid:users[i].userid,
	         			username:users[i].username
	         		});
	         	}
	  		}
  		}else{
  			$.messager.show({title:"提示",msg:"请选择一条记录",showType:"slide",timeout:3000,style:{right:"",top:"",bottom:-document.body.scrollTop-document.documentElement.scrollTop}});
  		}
  	}
  	
  	//删除选中用户
  	function deleteSelectedUser(){
  		var rows = $("#selectUserDg").datagrid("getSelections"); //选中用户
  		if(rows.length>0){
         	for ( var i = 0; i < rows.length; i++) {
         		var rowIndex = $("#selectUserDg").datagrid("getRowIndex", rows[i]);
         		$("#selectUserDg").datagrid("deleteRow", rowIndex);
         	}
  		}
  	}
  	
  	//确定
  	function confirmSelect(){
  		var users = $("#selectUserDg").datagrid("getRows"); //获取已选用户
  		if(users.length==1){
	  		//window.returnValue = users[0];
  			window.parent.selectedSingleUser(users[0]);
  		}else if(users.length>1){
  			$.messager.alert("警告", "只能选择一个用户！","warning");
  			return;
  		}else{
  			$.messager.alert("警告", "请选择用户！","warning");
  			return;
	  		//window.returnValue = false;
  		}
  		//window.close();
  		confirmColse();
  	}
  	
  	//双击选中
    function clickRow(rowIndex, rowData){
    	window.parent.selectedSingleUser(rowData);
    	confirmColse();
    }
  	
  	//关闭
  	function confirmColse(){
  		var api = frameElement.api.close();
  		//window.returnValue = false;
  		//window.close();
  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'north'" title="用户选择(单选)" style="height:60px;padding: 2px;">
  		<center>
	  		<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
		  		姓名：<input id="username" value="" type="text" style="width: 150px;">&nbsp;&nbsp;
		  		<a onclick="searchUser()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		  		<a onclick="clearForm();" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
	  		</form>
  		</center>
  	</div>
  	
  	<div data-options="region:'west',border:false" style="width:230px;">
  		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="组织" id="orgDiv">
				<div id="ztreeORG" class="ztree" style="height: 100%"></div>
			</div>
			<div title="角色">
				<table id="roleTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,showHeader:false,onClickRow:clickRowRole,url:'${pageContext.request.contextPath}/sysrole/roleAll.do'">
					<thead>
						<tr>
							<th data-options="field:'roleName',width:180">角色名称</th>
						</tr>
					</thead>
				</table>
			</div>
			<div title="岗位">
				<table id="postTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,showHeader:false,onClickRow:clickRowPost,url:'${pageContext.request.contextPath}/sysorganization/post.do'">
					<thead>
						<tr>
							<th data-options="field:'itemName',width:180">岗位名称</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
  	</div>
  	
  	<div data-options="region:'center',border:false">
  		<table id="userDg" class="easyui-datagrid" title="用户列表" data-options="rownumbers:true,fit:true,singleSelect:true,onDblClickRow:clickRow,toolbar:'#tb'">
  			<thead data-options="frozen:true"> 
				<tr>
					<th data-options="field:'username',width:100">姓名</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th data-options="field:'sexname',width:50">性别</th>
					<%--
					<th data-options="field:'employeeId',width:80">员工编号</th>
					<th data-options="field:'postname',width:100">岗位</th>
					<th data-options="field:'mobile',width:100">手机号码</th>
					<th data-options="field:'officePhone',width:100">办公电话</th>
					<th data-options="field:'email',width:150">邮箱</th>
					 --%>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-arrow',plain:true" onclick="arrowRight()">添加</a>
		</div>
	</div>
	
	<div data-options="region:'east'" style="width:160px;">
		<table id="selectUserDg" class="easyui-datagrid" title="已选用户" data-options="rownumbers:true,fit:true,toolbar:'#tb2',border:false">
			<thead>
				<tr>
					<th data-options="field:'username',width:100">姓名</th>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb2" style="height:auto">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cross-button',plain:true" onclick="deleteSelectedUser()">删除</a>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<a onclick="confirmSelect()" data-options="iconCls:'icon-ok'" class="easyui-linkbutton">确定</a>
		<a onclick="confirmColse()" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
