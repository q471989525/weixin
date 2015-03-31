<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>实施</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addImplement(){
	    	var url = "${pageContext.request.contextPath}/implement/add.do";
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //修改
	    function updateImplement(){
            var rows = $("#implementTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/implement/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":650});
	    }
	    
	    //删除
	    function deleteImplement(){
            var rows = $("#implementTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/implement/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchImplement();
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
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/implement/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
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
			<app:btnAuth btnUrl="/implement/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addImplement()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/implement/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateImplement()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/implement/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteImplement()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>