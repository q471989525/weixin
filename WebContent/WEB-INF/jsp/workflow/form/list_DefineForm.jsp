<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>流程表单元素</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
  	//格式化
  	function formatIsValid(val,row){
	    	var text = "";
	    	if("y" == val) text = "可用";
	    	if("n" == val) text = "<font color='blue'>禁用</font>";
	        return text;
	}
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
	    //新增
	    function addForm(){
	    	var row = $("#processTab").datagrid("getSelected");
	        if(row){
	        	var orderBy = $("#formTab").datagrid("getRows").length + 1;
	        	var processName = encodeURI(encodeURI(row.menuName));//两次编码,后台解码
		    	var url = "${pageContext.request.contextPath}/wfDefineForm/add.do?processId="+row.resourceid+"&processName="+processName+"&orderBy="+orderBy;
				Common.dialog({"url":url,"width":600,"height":300});
	        }else{
	        	Common.showBottomMsg("请选择一个流程定义");
	        }
	    }
	    
	    //修改
	    function editForm(){
            var rows = $("#formTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRowForm(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRowForm(rowIndex, rowData){
            var url = "${pageContext.request.contextPath}/wfDefineForm/edit.do?resourceid="+rowData.resourceid;
   			Common.dialog({"url":url,"width":600,"height":300});
	    }
	    
	    //删除
	    function deleteForm(){
            var rows = $("#formTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/wfDefineForm/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchForm();
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
	    function searchForm(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#formTab").datagrid("reload", dataParam);
	    }
	    
	  	//重置
	    function resetForm(){
	    	var processId = $("#processId").val();
	    	Common.clearForm("queryForm");
	    	$("#processId").val(processId);
			searchForm();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchForm();
			}
		}
	  	
	  	function selectRowToForm(rowIndex, rowData){
	     	$("#formTab").datagrid("reload",{processId:rowData.resourceid});
	     	$("#processId").val(rowData.resourceid);
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
		
		<div data-options="region:'west',title:'流程定义',split:true" style="width:420px;">
	    	<table id="processTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,onSelect:selectRowToForm,sortName:'orderBy',url:'${pageContext.request.contextPath}/wfDefineProcess/datagrid.do'">
				<thead>
					<tr>
						<th data-options="field:'menuName',width:180">资源名称</th>
						<th data-options="field:'isValid',width:80,formatter:formatIsValid">是否可用</th>
						<th data-options="field:'orderBy',width:80,align:'right',sortable:'true'">排序</th>
					</tr>
				</thead>
			</table>
	    </div>
		
		<div data-options="region:'center'" title="流程表单元素列表">
			<table id="formTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,onDblClickRow:clickRowForm,remoteSort:false,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/wfDefineForm/datagrid.do'">
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
		
		<%-- 按钮栏 --%>
		<div id="tb">
			<app:btnAuth btnUrl="/wfDefineForm/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addForm()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineForm/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editForm()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/wfDefineForm/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteForm()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>