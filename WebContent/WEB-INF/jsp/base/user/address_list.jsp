<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统用户</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
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
	    
	    //搜索
	    function searchUserName(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#userTab").datagrid("reload",dataParam);
	    }
	    
	  	//重置
	    function resetUser(){
	    	Common.clearForm("queryForm");
	    	searchUserName();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchUserName();
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
		
		function exportExcel(){
			var username = $("#username").val();
			var orgId = "";
			var orgZtreeObj = $.fn.zTree.getZTreeObj("ztreeOrgJsonDiv");
			var nodes = orgZtreeObj.getSelectedNodes();
			if(nodes.length >0) orgId = nodes[0].id;
			//下载
			$("#excelframeForDownload").remove();
	 		var iframe = document.createElement("iframe");
	 		iframe.id = "excelframeForDownload";
	 		iframe.src = ctx+"/sysuser/addressExport.do?orgId="+orgId+"&username="+username;
	 		iframe.style.display = "none";
	 		document.body.appendChild(iframe); //创建完成之后，添加到body中
	 		
	 		$.messager.progress({
                title:'Please waiting',
                msg:'Loading data...'
            });
            setTimeout(function(){ $.messager.progress('close'); },5000);
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">用户名称：<input type="text" id="username" name="username" value=""/></td>
						<td>
							<a onclick="searchUserName()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
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
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysuser/adressDG.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'username',width:100">用户名称</th>
						<th data-options="field:'orgName',width:120">所属组织</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'post',width:120,formatter:formatPost">岗位</th>
						<th data-options="field:'duty',width:120,formatter:formatDuty">职务</th>
						<th data-options="field:'mobile',width:130">手机</th>
						<th data-options="field:'officePhone',width:130">办公电话</th>
						<th data-options="field:'email',width:160">邮箱地址</th>
						<th data-options="field:'orderBy',width:70,align:'right',sortable:'true'">排序</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/sysuser/addressExport.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-excel',plain:true" onclick="exportExcel()">导出</a>
			</app:btnAuth>
		</div>
  </body>
</html>