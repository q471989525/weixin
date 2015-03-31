<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>组织机构</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatStateFlag(val,row){
	    	var state = "";
	    	if (val == "y") state = "有效";
	    	if (val == "n") state = "<span style='color:red;'>无效</span>";
	    	if (val == "h") state = "<span style='color:blue;'>隐藏</span>";
            return state;  
	    }
	    
	    function formatOrgType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_OrgType")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
		
	    //新增
	    function addOrg(){
	    	var param = "";
	    	var row = $("#orgTab").treegrid("getSelected");
	    	if(row){
	    		var orgname = encodeURI(encodeURI(row.orgName));//两次编码,后台解码
	    		param = "?parentId="+row.resourceid+"&parentName="+orgname;
	    	}
	    	var url = "${pageContext.request.contextPath}/sysorganization/add.do"+param;
			Common.dialog({"url":url,"width":1000,"height":400});
	    }
	    
	    //修改
	    function modifyOrg(){
	    	var row = $("#orgTab").treegrid("getSelected");
	    	if(row){
    			clickRow(row);
	    	}else{
            	Common.showBottomMsg("请选择一条记录");
            }
	    }
	    
	  	//双击
	    function clickRow(row){
	    	var url = "${pageContext.request.contextPath}/sysorganization/edit.do?resourceid="+row.resourceid;
	    	Common.dialog({"url":url,"width":1000,"height":400});
	    }
	    
	    //删除
	    function deleteOrg(){
            var row = $("#orgTab").treegrid("getSelected");
	    	if(row){
            	$.messager.confirm("确认?", "确认删除吗，如果存在子节点将一起删除，请谨慎操作？", function(r){
    				if (r){
		                var url = "${pageContext.request.contextPath}/sysorganization/delete.do?id="+row.resourceid;
		                $.post(url,function(data){
		                	if(data.state == "success"){
		                		Common.showBottomMsg("删除成功");
				                searchOrg();
			            	}else{
			            		Common.showBottomMsg("删除失败");  
			            	}
		                },"json");
    				}
    			});
            }else{
            	Common.showBottomMsg("请选择一条记录");
            }
	    }
	    
	    //搜索
	    function searchOrg(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#orgTab").treegrid("clearSelections");
	    	$("#orgTab").treegrid("reload",dataParam);
	    }
	    
	  	//重置
	    function resetOrg(){
	    	Common.clearForm("queryForm");
	    	searchOrg();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchOrg();
			}
		}
	  	
	  	function expandAll(){
	  		$("#orgTab").treegrid("expandAll");
	  	}
	  	
	  	function collapseAll(){
	  		$("#orgTab").treegrid("collapseAll");
	  	}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">组织名称：<input type="text" id="orgName" name="orgName" value=""/></td>
						<td style="width: 33%">组织代码：<input type="text" id="orgCode" name="orgCode" value=""/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>组织类型：<app:select name="orgType" dictionaryCode="D_OrgType" value=""/></td>
						<td>状态：<select id="stateFlag" name="stateFlag"><option value="">请选择...</option><option value="y">有效</option><option value="n">无效</option><option value="h">隐藏</option></select></td>
						<td>
							<a onclick="searchOrg()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetOrg()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center',border:false" title="组织机构树">
			<table id="orgTab" class="easyui-treegrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'orgName',onDblClickRow:clickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/sysorganization/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<%-- <th data-options="field:'resourceid',checkbox:true"></th>--%> 
						<th data-options="field:'orgName',width:240">组织名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'orgCode',width:160">组织代码</th>
						<th data-options="field:'shortName',width:120">简称</th>
						<th data-options="field:'parentName',width:150">父组织名称</th>
						<th data-options="field:'orgType',width:80,formatter:formatOrgType">组织类型</th>
						<th data-options="field:'stateFlag',width:60,align:'center',formatter:formatStateFlag">状态</th>
						<th data-options="field:'orderBy',width:60,align:'right'">排序</th>
						<th data-options="field:'dutyDesc',width:300">职责描述</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/sysorganization/add.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addOrg()">新增</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/sysorganization/edit.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="modifyOrg()">修改</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/sysorganization/delete.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteOrg()">删除</a>|</app:btnAuth>
			<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
		</div>
  </body>
</html>