<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>表单元素选择</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
	
  	<script type="text/javascript">
  	//格式化
  	function formatElementType(val,row){
	    	var text = "";
	    	if("hidden" == val) text = "隐藏";
	    	if("text" == val) text = "文本";
	    	if("button" == val) text = "按钮";
	    	if("select" == val) text = "下拉框";
	    	if("textarea" == val) text = "文本域";
	    	if("checkbox" == val) text = "多选框";
	    	if("radio" == val) text = "单选框";
	    	if("a" == val) text = "超链接";
	    	if("password" == val) text = "密码";
	        return text;
	}
  	function formatIsSub(val,row){
	    	var text = "";
	    	if("Y" == val) text = "是";
	    	if("N" == val) text = "<font color='blue'>否</font>";
	        return text;
	}
	    //确定
	    function selectForm(){
	    	var rows = $("#formTab").datagrid("getSelections");
	    	if(rows){
	    		window.parent.selectedForms(rows);
		    	colseWin();
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	    function dbclickRow(rowIndex, rowData){
	    	var rows = new Array();
    		rows.push(rowData);
	    	window.parent.selectedForms(rows);
	    	colseWin();
	    }
	    
	    function colseWin(){
	    	var api = frameElement.api.close();
	    }
	    
	  	//搜索
	    function searchForm(){
	    	var elementName = $("#elementName").val();
	    	var elementType = $("#elementType").val();
	    	$("#formTab").datagrid("clearSelections");
	    	$("#formTab").datagrid("reload",{elementName:elementName,elementType:elementType});
	    }
	    
	  	//重置
	    function resetForm(){
	    	$("#elementName").val("");
	    	$("#elementType").val("");
	    	searchForm();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchForm();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">
							元素名称：<input type="text" id="elementName" name="elementName" value=""/>
							<input type="hidden" id="processId" name="processId" value=""/>
						</td>
						<td style="width: 33%">
							元素类型：<select id="elementType" name="elementType">
								<option value="">请选择...</option>
								<option value="text">文本</option>
								<option value="textarea">文本域</option>
								<option value="Hidden">隐藏</option>
								<option value="select">下拉框</option>
								<option value="button">按钮</option>
								<option value="checkbox">多选框</option>
								<option value="radio">单选框</option>
								<option value="password">密码</option>
								<option value="a">超链接</option>
							</select>
						</td>
						<td>
							<a onclick="searchForm()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetForm()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="流程表单元素列表">
			<table id="formTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,onDblClickRow:dbclickRow,remoteSort:false,sortName:'orderBy',url:'${pageContext.request.contextPath}/wfDefineForm/datagrid.do?processId=${param.processId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th>
						<th data-options="field:'elementName',width:150">元素名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'elementId',width:150">元素ID</th>
						<th data-options="field:'elementType',width:150,formatter:formatElementType">元素类型</th>
						<th data-options="field:'isSub',width:100,formatter:formatIsSub">是否子表元素</th>
						<th data-options="field:'orderBy',width:80,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'remark',width:300">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="selectForm()">确定</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="colseWin()">关闭</a>
		</div>
  </body>
</html>