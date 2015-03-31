<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>竞争对手</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	  	//确定
	    function selectCompetitor(){
	        var rows = $("#competitorTab").datagrid("getSelections");
	        var size = rows.length;
	        if(size>=1){
	            //var row = rows[0];
				//window.returnValue=row.resourceid+"="+row.competitorName;
            	window.returnValue=rows;
		    	window.close();
	        }else{
	            $.messager.alert("警告", "请选择一条记录","warning");  
	        }
	    }
	    
	    function dbclickRow(ind,row){
	    	//window.returnValue=row.resourceid+"="+row.competitorName;
	    	window.returnValue=[row];
	    	window.close();
	    }
	    
	    //搜索
	    function searchCompetitor(){
	    	var competitorName = $("#competitorName").val();
	    	$("#competitorTab").datagrid("reload",{competitorName:competitorName});
	    }
	    
	  	//重置
	    function resetCompetitor(){
	    	Common.clearForm("queryForm");
	    	searchCompetitor();
	    }
	    
	  	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchCompetitor();
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'north'" title="查询条件" style="height:60px; overflow: hidden;">
			<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
				<table style="width: 100%;">
					<tr>
						<td style="width: 60%">竞争对手：<input type="text" id="competitorName" name="competitorName" value=""/></td>
						<td>
							<a onclick="searchCompetitor()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
							<a onclick="resetCompetitor()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center'" title="竞争对手列表">
			<table id="competitorTab" class="easyui-datagrid"
				data-options="border:false,rownumbers:true,fit:true,striped:true,pagination:true,pageSize:20,sortName:'createTime',onDblClickRow:dbclickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/competitor/datagrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'competitorName',width:200">竞争对手</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'companyScale',width:100">企业规模</th>
						<th data-options="field:'companyProperty',width:100">企业性质</th>
						<th data-options="field:'companyDesc',width:350">描述</th>
						<th data-options="field:'superiority',width:350">优势</th>
						<th data-options="field:'disadvantages',width:350">劣势</th>
						<th data-options="field:'createTimeFmt',width:120">创建时间</th>
						<th data-options="field:'creator',width:80">创建人</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="selectCompetitor()">确定</a>|
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="window.close()">关闭</a>|
		</div>
  </body>
</html>