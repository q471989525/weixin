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
	    
	  	//确定
	    function selectContacts(){
            var rows = $("#customerContactsTab").datagrid("getSelections");
            var size = rows.length;
            if(size>=1){
            	window.returnValue=rows;
    	    	window.close();
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	  	
	    function dbclickRow(ind,row){
	    	window.returnValue=[row];
	    	window.close();
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
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 50%">姓名：<input type="text" id="userName" name="userName" value=""/></td>
						<td>
							<a onclick="searchCustomerContacts()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCustomerContacts()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="联系人列表">
			<table id="customerContactsTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,sortName:'createTime',onDblClickRow:dbclickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/customerContacts/datagrid.do?customerId=${param.customerId}'">
				<thead data-options="frozen:true"> 
					 <tr>
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
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectContacts()">确定</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="window.close()">关闭</a>|
		</div>
  </body>
</html>