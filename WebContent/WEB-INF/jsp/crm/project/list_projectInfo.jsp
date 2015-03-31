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
	    //新增
	    function addProject(){
	    	var url = "${pageContext.request.contextPath}/projectInfo/add.do";
			Common.dialog({"url":url,"width":1000,"height":600});
	    }
	    
	    //修改
	    function updateProject(){
            var rows = $("#projectInfoTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/projectInfo/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":1000,"height":600});
	    }
	    
	    //删除
	    function delProject(){
            var rows = $("#projectInfoTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);
		                }
		                var url = "${pageContext.request.contextPath}/projectInfo/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchProject();
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
		<div data-options="region:'north'" title="查询条件" style="height:85px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 33%">客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td style="width: 33%">采购项目名称：<input type="text" id="projectName" name="projectName" value=""/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>招标文件编号：<input type="text" id="fileNo" name="fileNo" value=""/></td>
						<td>
							<a onclick="searchProject()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetProject()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="项目信息列表">
			<table id="projectInfoTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/projectInfo/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'projectName',width:200">采购项目名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'contactsName',width:150">联系人</th>
						<th data-options="field:'fileNo',width:120">招标文件编号</th>
						<th data-options="field:'procurementMethod',width:120">采购方式</th>
						<th data-options="field:'priceLimit',width:150,align:'right',formatter:formatPrice">最高限价(小写)</th>
						<th data-options="field:'timeLimit',width:200">工期要求</th>
						<th data-options="field:'openTimeFmt',width:120">投标截止/开标时间</th>
						<th data-options="field:'openAddress',width:200">开标地点</th>
						<th data-options="field:'creator',width:100">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/projectInfo/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProject()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/projectInfo/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateProject()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/projectInfo/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="delProject()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>