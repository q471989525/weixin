<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>合同</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addContract(){
	    	var url = "${pageContext.request.contextPath}/contract/add.do";
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //修改
	    function updateContract(){
            var rows = $("#contractTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/contract/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //删除
	    function deleteContract(){
            var rows = $("#contractTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/contract/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchContract();
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
	    function searchContract(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#contractTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetContract(){
	    	Common.clearForm("queryForm");
	    	searchContract();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchContract();
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
						<td>合同编号：<input type="text" id="contractNo" name="contractNo" value=""/></td>
						<td>
							<a onclick="searchContract()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetContract()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="合同列表">
			<table id="contractTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/contract/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'contractNo',width:100">合同编号</th>
						<th data-options="field:'contractName',width:200">合同名称</th>
						<th data-options="field:'contractDateFmt',width:100">签订日期</th>
						<th data-options="field:'contractAmount',width:100,align:'right'">合同金额</th>
						<th data-options="field:'valideFmt',width:160">合同有效期</th>
						<th data-options="field:'contractCopis',width:100">合同份数</th>
						<th data-options="field:'marginAmount',width:100,align:'right'">保证金</th>
						<th data-options="field:'marginReceiveFmt',width:100">保证金收缴时间</th>
						<th data-options="field:'contractRemark',width:260">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/contract/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addContract()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/contract/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateContract()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/contract/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteContract()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>