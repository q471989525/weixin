<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>合同收款</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//格式化
	    function formatPay(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Pay_Status")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
	        return text;
	    }
	    
	    //修改
	    function update(){
            var rows = $("#paymentTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
                clickRow(0, rows[0]);
            }else{
                Common.showBottomMsg("请选择一条记录"); 
            }
	    }
	    
	  	//双击
	    function clickRow(rowIndex, rowData){
   			var url = "${pageContext.request.contextPath}/contractPayment/edit.do?resourceid="+rowData.resourceid;
			Common.dialog({"url":url,"width":600,"height":250});
	    }
	    
	    //搜索
	    function searchPayment(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#paymentTab").datagrid("reload", dataParam);
	    }
	    
	    function resetPayment(){
	    	Common.clearForm("queryForm");
	    	searchPayment();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchPayment();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px;overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 30%">合同编号：<input type="text" id="contractNo" name="contractNo" value=""></td>
						<td>
							<a onclick="searchPayment()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetPayment()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="收款列表">
			<table id="paymentTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,onDblClickRow:clickRow,sortName:'createTime',toolbar:'#tb',url:'${pageContext.request.contextPath}/contractPayment/datagrid.do?menuId=${param.menuId}'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'resourceid',checkbox:true"></th> 
						<th data-options="field:'contractNo',width:100">合同编号</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'periods',width:100">期数</th>
						<th data-options="field:'amount',width:100,align:'right'">金额</th>
						<th data-options="field:'payDateFmt',width:100">预计付款时间</th>
						<th data-options="field:'payFlag',width:100,formatter:formatPay">支付状态</th>
						<th data-options="field:'actualAmount',width:100,align:'right'">实际支付金额</th>
						<th data-options="field:'updateName',width:100">收款人</th>
						<th data-options="field:'updateTimeFmt',width:150">支付时间</th>
						<th data-options="field:'remark',width:300">备注</th>
						<th data-options="field:'creator',width:100">创建人</th>
						<th data-options="field:'createTimeFmt',width:150">创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/contractPayment/edit.do">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="update()">修改</a>|
			</app:btnAuth>
		</div>
  </body>
</html>