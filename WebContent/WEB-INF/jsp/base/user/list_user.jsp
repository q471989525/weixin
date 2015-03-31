<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统用户</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatSex(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Sex")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatPost(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Post")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatDuty(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Duty")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatState(val,row){
	    	var state = "";
	    	if (val == "1") state = "在职";  
	    	if (val == "2") state = "<span style='color:red;'>离职</span>";
	    	if (val == "3") state = "<span style='color:blue;'>待定</span>";
            return state;
	    }
	    
	    function formatShowState(val,row){
	    	if (val == "y"){  
	            return "显示";  
	        } else {  
	            return "<span style='color:red;'>隐藏</span>";  
	        } 
	    }
	    function formatIsEnabled(val,row){
	    	if (val == "y"){  
	            return "激活";  
	        } else {  
	            return "<span style='color:red;'>未激活</span>";  
	        } 
	    }
		
	    //新增
	    var orgId = "";
	    var orgName = "";
	    function addUser(){
	    	var param = "";
	    	if(orgId!=""){
	    		param = "?orgId="+orgId+"&orgName="+orgName;
	    	}
	    	var url = "${pageContext.request.contextPath}/sysuser/add.do"+param;
			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	    
	    //修改
	    function modifyUser(){
            var rows = $("#userTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录");
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
	    	var url = "${pageContext.request.contextPath}/sysuser/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	  	
	    //选择事件
	    function selectRow(rowIndex, rowData){
	    	$("#roleTab").datagrid("reload",{userid:rowData.resourceid});
	    	$("#roleTab").datagrid("getPanel").panel("setTitle","用户名称："+rowData.username);
	    }
	    
	    //重置密码
	    function reSetPwd(){
            var rows = $("#userTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	$.messager.confirm("确认?", "确认重置为默认密码？", function(r){
    				if (r){
		                var row = rows[0];  
		                var url = "${pageContext.request.contextPath}/sysuser/reSetPwd.do?resourceid="+row.resourceid;
		                $.post(url,function(data){
		                	if(data.state == "success"){
		                		Common.showBottomMsg("重置成功");
			            	}else{
			            		Common.showBottomMsg("重置失败");  
			            	}
		                },"json");
    				}
    			});
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	    //删除
	    function deleteUser(){
            var rows = $("#userTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/sysuser/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
		                		Common.showBottomMsg("删除成功");
			                	searchUser();
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
	    
	    //用户分配角色
	    function authorizedRole(){
	    	var rows = $("#userTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                var row = rows[0];  
                var url = "${pageContext.request.contextPath}/sysuser/userRoleAuth.do?userid="+row.resourceid;
    			Common.dialog({"url":url,"width":1000,"height":550});
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	    //刷新角色
	    function refreshRole(){
	    	$("#roleTab").datagrid("reload");
	    }
	    
	    //搜索
	    function searchUser(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	//if(orgId!="") dataParam["orgId"] = orgId;
	    	$("#userTab").datagrid("reload",dataParam);
	    }
	    
	  	//重置
	    function resetUser(){
	    	Common.clearForm("queryForm");
	    	searchUser();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchUser();
			}
		}
	  	
		//ztree 设置
		var orgZtreeSetting = {
			data: {simpleData:{enable:true}},
			view: {expandSpeed: "fast", selectedMulti: false},
			callback: {onClick: orgZTreeOnClick}
		};
		
		// ztree json对象
		var orgZNodes = ${ztreeOrgJson};
		
		//初始化
		$(document).ready(function(){
			$.fn.zTree.init($("#ztreeOrgJsonDiv"), orgZtreeSetting, orgZNodes);
		});
	    
		function orgZTreeOnClick(event, treeId, treeNode) {
			orgId = treeNode.id;
		    orgName = encodeURI(encodeURI(treeNode.name));//两次编码,后台解码
			$("#userTab").datagrid("load",{"orgId":treeNode.id});
		};
		
		function expandAllZt(bool){
			var orgZtreeObj = $.fn.zTree.getZTreeObj("ztreeOrgJsonDiv");
			if(bool){
				orgZtreeObj.expandAll(true);
			}else{
				orgZtreeObj.expandAll(false);
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">用户名称：<input type="text" id="username" name="username" value=""/></td>
						<td style="width: 33%">手机号码：<input type="text" id="mobile" name="mobile" value=""/></td>
						<td>状态：<select id="stateFlag" name="stateFlag"><option value="">请选择...</option><option value="1">在职</option><option value="2">离职</option><option value="3">待定</option></select></td>
					</tr>
					<tr>
						<td>隐藏状态：<select id="showState" name="showState"><option value="">请选择...</option><option value="y">显示</option><option value="h">隐藏</option></select></td>
						<td>激活标志：<select id="isEnabled" name="isEnabled"><option value="">请选择...</option><option value="y">激活</option><option value="n">未激活</option></select></td>
						<td>
							<a onclick="searchUser()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetUser()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'west',title:'组织树',split:true,tools:'#treeTools'" style="width:230px;">
			<div id="ztreeOrgJsonDiv" class="ztree" style="height: 100%"></div>
		</div> 
		
		<div id="treeTools">
			<a href="#" onclick="javascript:expandAllZt(true)" style="width: 30px;">展开</a>
			<a href="#"onclick="javascript:expandAllZt(false)" style="width: 30px;">折叠</a>
		</div>
		
		<div data-options="region:'center'" title="用户列表">
			<table id="userTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,onSelect:selectRow,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysuser/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'username',width:100">用户名称</th>
						<th data-options="field:'orgName',width:120">所属组织</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'loginId',width:90">登录账号</th>
						<th data-options="field:'employeeId',width:90,sortable:'true'">员工编号</th>
						<th data-options="field:'post',width:120,formatter:formatPost">岗位</th>
						<th data-options="field:'duty',width:120,formatter:formatDuty">职务</th>
						<th data-options="field:'mobile',width:120">手机号码</th>
						<th data-options="field:'email',width:150">邮箱地址</th>
						<th data-options="field:'sex',width:70,formatter:formatSex">性别</th>
						<th data-options="field:'birthdayFmt',width:100,sortable:'true'">出生日期</th>
						<th data-options="field:'joinDateFmt',width:100,sortable:'true'">入职日期</th>
						<th data-options="field:'stateFlag',width:70,align:'center',formatter:formatState">状态</th>
						<th data-options="field:'showState',width:70,align:'center',formatter:formatShowState">隐藏状态</th>
						<th data-options="field:'isEnabled',width:70,align:'center',formatter:formatIsEnabled">激活标志</th>
						<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'dutyDesc',width:300">职责描述</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div data-options="region:'east',title:'角色',split:true" style="width:300px;">
			<table id="roleTab" class="easyui-datagrid" title="用户名称："
				data-options="border:false,rownumbers:true,fit:true,striped:true,url:'${pageContext.request.contextPath}/sysuser/userRoleDG.do'">
				<thead>
					<tr>
						<th data-options="field:'roleName',width:120">角色名称</th>
						<th data-options="field:'roleCode',width:150">角色编码</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/sysuser/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addUser()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysuser/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyUser()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysuser/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteUser()">删除</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysuser/reSetPwd.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reSetPwd()">重置密码</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/sysuser/userRoleAuth.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-users',plain:true" onclick="authorizedRole()">分配角色</a>|
			</app:btnAuth>
		</div>
  </body>
</html>