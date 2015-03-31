<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>联系人管理</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatSex(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Sex")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    //新增
	    function addCustomerContacts(){
	    	var url = "${pageContext.request.contextPath}/customerContacts/add.do";
			Common.dialog({"url":url,"width":800,"height":350});
	    }
	    
	    //修改
	    function updateCustomerContacts(){
            var rows = $("#customerContactsTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/customerContacts/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":350});
	    }
	    
	    //删除
	    function delCustomerContacts(){
            var rows = $("#customerContactsTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/customerContacts/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
				                searchCustomerContacts();
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
	    function searchCustomerContacts(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#customerContactsTab").datagrid("reload", dataParam);
	    }
	    
	    function resetCustomerContacts(){
	    	Common.clearForm("queryForm");
	    	searchCustomerContacts();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchCustomerContacts();
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
						<td style="width: 33%">姓名：<input type="text" id="userName" name="userName" value=""/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>手机/电话：<input type="text" id="mobile" name="mobile" value=""/></td>
						<td>
							<a onclick="searchCustomerContacts()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCustomerContacts()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="联系人列表">
			<table id="customerContactsTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/customerContacts/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
						<th data-options="field:'userName',width:120">姓名</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'userSex',width:60,formatter:formatSex">性别</th>
						<th data-options="field:'userDept',width:120">部门</th>
						<th data-options="field:'userPost',width:120">职称</th>
						<th data-options="field:'mobile',width:120">手机</th>
						<th data-options="field:'telphone',width:120">电话</th>
						<th data-options="field:'fax',width:120">传真</th>
						<th data-options="field:'email',width:150">电子邮箱</th>
						<th data-options="field:'postCode',width:60">邮编</th>
						<th data-options="field:'address',width:250">联系地址</th>
						<th data-options="field:'qq',width:100">QQ</th>
						<th data-options="field:'remark',width:250">备注</th>
						<th data-options="field:'creator',width:100">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/customerContacts/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCustomerContacts()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/customerContacts/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateCustomerContacts()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/customerContacts/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="delCustomerContacts()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>