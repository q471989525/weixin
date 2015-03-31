<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>组织机构选择</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
	
  	<script type="text/javascript">
	    //格式化
	    function formatState(val,row){
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
	    
	    //确定
	    function selectOrg(){
            var row = $("#orgTab").treegrid("getSelected");
	    	if(row){
	    		window.parent.selectedParentOrg(row);
    			//window.returnValue=row;
   	    		//window.close();
		    	colseWin();
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	    function dbclickRow(row){
	    	window.parent.selectedParentOrg(row);
	    	//window.returnValue=row;
	    	//window.close();
	    	colseWin();
	    }
	    
	    function colseWin(){
	    	var api = frameElement.api.close();
	    }
	    
	  	//搜索
	    function searchOrg(){
	    	//var dataParam = Common.getFormJson("queryForm");
	    	var orgName = $("#orgName").val();
	    	$("#orgTab").treegrid("clearSelections");
	    	$("#orgTab").treegrid("reload",{orgName:orgName});
	    }
	    
	  	//重置
	    function resetOrg(){
	    	$("#orgName").val("");
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
  		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">
							组织名称：<input type="text" id="orgName" name="orgName" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;
							<a onclick="searchOrg()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetOrg()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'">
			<table id="orgTab" class="easyui-treegrid" title="组织机构树" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'orgName',onDblClickRow:dbclickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/sysorganization/treegrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'orgName',width:240">组织名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'shortName',width:130">简称</th>
						<th data-options="field:'orgType',width:100,formatter:formatOrgType">组织类型</th>
						<th data-options="field:'stateFlag',width:70,align:'center',formatter:formatState">状态</th>
					</tr>
				</thead>
			</table>
			<%-- 按钮栏 --%>
			<div id="tb" style="height:auto">
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
			</div>
		</div>
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="selectOrg()">确定</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="colseWin()">关闭</a>
		</div>
  </body>
</html>