<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>项目管理</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatPrice(val,row){
	    	var text = "";
	    	if(val>=0) text = val+"元";
	        return text;
	    }
	  	
	  	function selectProject(){
	  		var rows = $("#projectInfoTab").datagrid("getSelections");
	        var size = rows.length;
	        if(size==1){
		    	clickRow(0, rows[0])
	        }else{
	            $.messager.alert("警告", "请选择一条记录","warning");  
	        }
	  	}
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
	  		//alert(rowData.resourceid+"-"+rowData.projectName+"-"+rowData.customerName)
	    	window.returnValue = rowData;
	    	window.close();
	    }
	    
	    //搜索
	    function searchProject(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#projectInfoTab").datagrid("reload", dataParam);
	    }
	    
	    function resetProject(){
	    	Common.clearForm("queryForm");
	    	searchProject();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchProject();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td>客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td>采购项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
						<td>
							<a onclick="searchProject()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetProject()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="项目信息列表">
			<table id="projectInfoTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/projectInfo/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'projectName',width:200">项目名称</th>
						<th data-options="field:'customerName',width:200">客户名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'contactsName',width:150">联系人</th>
						<th data-options="field:'fileNo',width:120">招标文件编号</th>
						<th data-options="field:'procurementMethod',width:120">采购方式</th>
						<th data-options="field:'priceLimit',width:150,align:'right',formatter:formatPrice">最高限价(小写)</th>
						<th data-options="field:'timeLimit',width:200">工期要求</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectProject()">确定</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="window.close()">关闭</a>|
		</div>
  </body>
</html>