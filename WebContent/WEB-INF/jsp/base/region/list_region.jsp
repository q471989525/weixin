<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>省市</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
  	//新增省
    function addProvince(){
    	var url = "${pageContext.request.contextPath}/pubglobalregion/addProvince.do";
		Common.dialog({"url":url,"width":400,"height":200});
    }
    
    //修改省
    function editProvince(){
        var row = $("#provinceTab").datagrid("getSelected");
        if(row){
        	dbclickProvince(0, row);
        }else{
            Common.showBottomMsg("请选择一条记录"); 
        }
    }
    
    //双击
    function dbclickProvince(rowIndex, row){
    	var url = "${pageContext.request.contextPath}/pubglobalregion/editProvince.do?resourceid="+row.resourceid;
 		Common.dialog({"url":url,"width":400,"height":200});
    }
    
  	//删除省
    function deleteProvince(){
        var row = $("#provinceTab").datagrid("getSelected");
        if(row){
        	$.messager.confirm("确认?", "确认删除吗？", function(r){
				if (r){
	                var url = "${pageContext.request.contextPath}/pubglobalregion/deleteProvince.do?id="+row.resourceid;
	                $.post(url,function(data){
	                	if(data.state == "success"){
			                Common.showBottomMsg("删除成功");
			                searchProvince();
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
    
    //刷新数据字典缓存
    function refreshCache(){
    	$.messager.confirm("确认?", "确认刷新吗？", function(r){
    		if (r){
	            var url = "${pageContext.request.contextPath}/sysdictionary/refresh.do";
	            $.post(url,function(data){
	              	if(data.state == "success"){
		                Common.showBottomMsg("成功");
	            	}else{
		                Common.showBottomMsg("失败");  
	            	}
	        	},"json");
    		}
    	});
    }
    
  	//新增市
    function addCity(){
    	var row = $("#provinceTab").datagrid("getSelected");
        if(row){
	    	var url = "${pageContext.request.contextPath}/pubglobalregion/addCity.do?parentId="+row.resourceid;
			Common.dialog({"url":url,"width":400,"height":200});
        }else{
        	Common.showBottomMsg("请选择一个省份");
        }
    }
    
    //修改市
    function editCity(){
        var rows = $("#cityTab").datagrid("getSelections");
        var size = rows.length;
        if(size==1){
        	dbclickCity(0, rows[0]);
        }else{
            Common.showBottomMsg("请选择一条记录"); 
        }
    }
    
    //双击
    function dbclickCity(rowIndex, row){
    	var url = "${pageContext.request.contextPath}/pubglobalregion/editCity.do?resourceid="+row.resourceid;
 		Common.dialog({"url":url,"width":400,"height":200});
    }
    
  	//删除市
    function deleteCity(){
        var rows = $("#cityTab").datagrid("getSelections");
        var size = rows.length;
        if(size>0){
        	$.messager.confirm("确认?", "确认删除吗？", function(r){
				if (r){
					var ids = [];
	            	for(var i=0; i<size; i++){
	                    ids.push(rows[i].resourceid);  
	                }
	                var url = "${pageContext.request.contextPath}/pubglobalregion/deleteCity.do?ids="+ids.toString();
	                $.post(url,function(data){
	                	if(data.state == "success"){
			                Common.showBottomMsg("删除成功");
			                searchCity();
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
    
  	//点击省
    function clickRow(rowIndex, rowData){
    	$("#cityTab").datagrid("load",{"parentId":rowData.resourceid});
    }
  	
    //搜索省
    function searchProvince(){
    	var dataParam = Common.getFormJson("queryForm");
    	$("#provinceTab").datagrid("reload", dataParam);
    }
    
  	//重置省
    function resetProvince(){
    	$("#regionName").val("");
    	searchProvince();
    }
    
  	//按下回车时，直接查询省
	function onFormKeyPressProvince(event){
		if (event.keyCode==13){
			searchProvince();
		}
	}
  	
	//搜索市
    function searchCity(){
    	var cityName = $("#cityName").val();
    	var row = $("#provinceTab").datagrid("getSelected"); //选中省
    	var parentId = "";
    	if(row) parentId = row.resourceid;
    	$("#cityTab").datagrid("reload",{"parentId":parentId,"regionName":cityName});
    }
    
  	//重置市
    function resetCity(){
    	$("#cityName").val("");
    	searchCity();
    }
    
  	//按下回车时，直接查询市
	function onFormKeyPressCity(event){
		if (event.keyCode==13){
			searchCity();
		}
	}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
  	<%-- 省 --%>
  	<div data-options="region:'center',title:'省'">
  		<div class="easyui-layout" data-options="fit:true,border:false">
  			<div data-options="region:'north'" style="height:32px; overflow: hidden;">
				<form id="queryForm" onkeypress="onFormKeyPressProvince(event)">
					<table style="width: 100%;">
						<tr>
							<td style="width: 35%">省：<input type="text" id="regionName" name="regionName" value="" style="width: 60%"/></td>
							<td style="display: none;">隐藏条件：<input type="text" id="deleteState" name="deleteState" value="n"/></td>
							<td>
								<a onclick="searchProvince()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
								<a onclick="resetProvince()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
		    <div data-options="region:'center',border:false" title="省列表">
				<table id="provinceTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:50,onClickRow:clickRow,onDblClickRow:dbclickProvince,sortName:'orderBy',toolbar:'#tb1',url:'${pageContext.request.contextPath}/pubglobalregion/provinceDatagrid.do'">
					<thead>
						<tr>
							<th data-options="field:'resourceid',checkbox:true"></th>
							<th data-options="field:'regionName',width:150">省</th>
							<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
						</tr>
					</thead>
				</table>
				<%-- 按钮栏 --%>
				<div id="tb1" style="height:auto">
					<app:btnAuth btnUrl="/pubglobalregion/addProvince.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addProvince()">新增</a>|</app:btnAuth>
					<app:btnAuth btnUrl="/pubglobalregion/editProvince.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editProvince()">修改</a>|</app:btnAuth>
					<app:btnAuth btnUrl="/pubglobalregion/deleteProvince.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteProvince()">删除</a>|</app:btnAuth>
					<app:btnAuth btnUrl="/pubglobalregion/refreshCache.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="refreshCache()">刷新缓存</a>|</app:btnAuth>
				</div>
			</div>
			
		</div>
  	</div>
  	
  	<%-- 市 --%>
	<div data-options="region:'east',title:'市',split:true" style="width:600px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:true" style="height:32px; overflow: hidden;">
				<form id="queryCityForm" onkeypress="onFormKeyPressCity(event)" onsubmit="return false;">
					<table style="width: 100%;">
						<tr>
							<td style="width: 50%">市：<input type="text" id="cityName" name="cityName" value=""/></td>
							<td>
								<a onclick="searchCity()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
								<a onclick="resetCity()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<div data-options="region:'center',border:false" title="市列表">
				<table id="cityTab" class="easyui-datagrid"
					data-options="border:false,rownumbers:true,fit:true,striped:true,onDblClickRow:dbclickCity,remoteSort:false,sortName:'orderBy',toolbar:'#tb2',url:'${pageContext.request.contextPath}/pubglobalregion/cityDatagrid.do'">
					<thead>
						<tr>
							<th data-options="field:'resourceid',checkbox:true"></th>
							<th data-options="field:'regionName',width:150">市</th>
							<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
						</tr>
					</thead>
				</table>
				<%-- 按钮栏 --%>
				<div id="tb2" style="height:auto">
					<app:btnAuth btnUrl="/pubglobalregion/addCity.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCity()">新增</a>|</app:btnAuth>
					<app:btnAuth btnUrl="/pubglobalregion/editCity.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editCity()">修改</a>|</app:btnAuth>
					<app:btnAuth btnUrl="/pubglobalregion/deleteCity.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteCity()">删除</a>|</app:btnAuth>
				</div>
			</div>
			
		</div>
	</div>
  </body>
</html>