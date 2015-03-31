<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>实施</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	function select(){
	  		var rows = $("#implementTab").datagrid("getSelections");
	        var size = rows.length;
	        if(size==1){
		    	clickRow(0, rows[0])
	        }else{
	            $.messager.alert("警告", "请选择一条记录","warning");  
	        }
	  	}
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
	    	window.returnValue = rowData;
	    	window.close();
	    }
	    
	    //搜索
	    function searchImplement(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#implementTab").datagrid("reload", dataParam);
	    }
	    
	    function resetImplement(){
	    	Common.clearForm("queryForm");
	    	searchImplement();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchImplement();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:85px;overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 50%">客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td>项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
					</tr>
					<tr>
						<td>责任人：<input type="text" id="dutyPerson" name="dutyPerson" value=""/></td>
						<td>
							<a onclick="searchImplement()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetImplement()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="实施列表">
			<table id="implementTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/implement/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'dutyPerson',width:100">责任人</th>
						<th data-options="field:'dutyPersonPhone',width:200">责任人电话</th>
						<th data-options="field:'createTimeFmt',width:150">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="select()">确定</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="window.close()">关闭</a>|
		</div>
  </body>
</html>