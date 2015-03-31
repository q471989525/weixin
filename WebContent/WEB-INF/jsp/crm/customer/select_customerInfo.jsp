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
		
	  	//确定
	    function selectCustomer(){
            var rows = $("#customerInfoTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	dbclickRow(0, rows[0]);
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	    function dbclickRow(ind,row){
	    	window.returnValue=row.resourceid+"="+row.customerName;
	    	window.close();
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
						<td style="width: 50%">客户名称：<input type="text" id="customerName" name="customerName" value=""/></td>
						<td style="width: 50%">客户性质：<app:select name="customerNature" dictionaryCode="D_Customer_Nature" value=""/></td>
					</tr>
					<tr>
						<td>信用状况：<app:select name="trustStatus" dictionaryCode="D_Trust_Status" value=""/></td>
						<td>
							<a onclick="searchCustomerInfo()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCustomerInfo()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="客户信息列表">
			<table id="customerInfoTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,sortName:'createTime',onDblClickRow:dbclickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/customerinfo/datagrid.do'">
				<thead data-options="frozen:true">
					 <tr>
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
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectCustomer()">确定</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="window.close()">关闭</a>|
		</div>
  </body>
</html>