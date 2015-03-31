<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>客户管理</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatProvince(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Province")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatCity(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_City")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatCustomerNature(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Customer_Nature")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatCustomerSource(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Customer_Source")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatCustomerType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Customer_Type")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatCustomerIndustry(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Customer_Industry")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatTrustStatus(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Trust_Status")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatContactStrategy(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Contact_Strategy")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatIndustryStatus(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Industry_Status")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatClearingForm(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Clearing_Form")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
		
	    //新增
	    function addCustomerInfo(){
	    	var url = "${pageContext.request.contextPath}/customerinfo/add.do";
			Common.dialog({"url":url,"width":800,"height":450});
	    }
	    
	    //修改
	    function updateCustomerInfo(){
            var rows = $("#customerInfoTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
			var url = "${pageContext.request.contextPath}/customerinfo/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":800,"height":450});
	    }
	    
	    //删除
	    function delCustomerInfo(){
            var rows = $("#customerInfoTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/customerinfo/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
				                Common.showBottomMsg("删除成功");
			                	searchCustomerInfo();
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
	    function searchCustomerInfo(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#customerInfoTab").datagrid("reload", dataParam);
	    }
	    
	    function resetCustomerInfo(){
	    	Common.clearForm("queryForm");
	    	searchCustomerInfo();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchCustomerInfo();
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
						<td style="width: 33%">客户性质：<app:select name="customerNature" dictionaryCode="D_Customer_Nature" value=""/></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>信用状况：<app:select name="trustStatus" dictionaryCode="D_Trust_Status" value=""/></td>
						<td>
							<a onclick="searchCustomerInfo()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCustomerInfo()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="客户信息列表">
			<table id="customerInfoTab" class="easyui-datagrid" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/customerinfo/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'customerName',width:200">客户名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'provinceName',width:100,formatter:formatProvince">所属省</th>
						<th data-options="field:'cityName',width:100,formatter:formatCity">所属市</th>
						<th data-options="field:'customerNature',width:100,formatter:formatCustomerNature">客户性质</th>
						<th data-options="field:'customerSource',width:100,formatter:formatCustomerSource">客户来源</th>
						<th data-options="field:'customerType',width:100,formatter:formatCustomerType">客户类别</th>
						<th data-options="field:'customerIndustry',width:100,formatter:formatCustomerIndustry">所属行业</th>
						<th data-options="field:'trustStatus',width:100,formatter:formatTrustStatus">信用状况</th>
						<th data-options="field:'contactStrategy',width:100,formatter:formatContactStrategy">联系策略</th>
						<th data-options="field:'industryStatus',width:100,formatter:formatIndustryStatus">行业地位</th>
						<th data-options="field:'clearingForm',width:120,formatter:formatClearingForm">结算方式</th>
						<th data-options="field:'telphone',width:100">电话</th>
						<th data-options="field:'fax',width:100">传真</th>
						<th data-options="field:'email',width:150">电子邮箱</th>
						<th data-options="field:'www',width:150">单位网址</th>
						<th data-options="field:'postCode',width:100">邮政编码</th>
						<th data-options="field:'address',width:250">通讯地址</th>
						<th data-options="field:'remark',width:300">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/customerinfo/add.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCustomerInfo()">新增</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/customerinfo/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateCustomerInfo()">修改</a>|
			</app:btnAuth>
			<app:btnAuth btnUrl="/customerinfo/delete.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="delCustomerInfo()">删除</a>|
			</app:btnAuth>
		</div>
  </body>
</html>