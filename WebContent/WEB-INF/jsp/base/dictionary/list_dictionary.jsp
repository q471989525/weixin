<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>数据字典</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatState(val,row){
	    	if (val == "y"){  
	            return "有效";  
	        } else {  
	            return "<span style='color:red;'>无效</span>";  
	        } 
	    }
		
	    //新增
	    function addDictionary(){
	    	var url = "${pageContext.request.contextPath}/sysdictionary/add.do";
			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	    
	    //修改
	    function editDictionary(){
            var rows = $("#dctionaryTab").datagrid("getSelections");
            var size = rows.length;
            if(size==1){
            	clickRow(0, rows[0]);
            }else{
            	Common.showBottomMsg("请选择一条记录");  
            }
	    }
	    
	    //双击
	    function clickRow(rowIndex, rowData){
	    	var url = "${pageContext.request.contextPath}/sysdictionary/edit.do?resourceid="+rowData.resourceid;
   			Common.dialog({"url":url,"width":1000,"height":500});
	    }
	    
	    //删除
	    function deleteDictionary(){
            var rows = $("#dctionaryTab").datagrid("getSelections");
            var size = rows.length;
            if(size>0){
            	$.messager.confirm("确认?", "确认删除吗？", function(r){
    				if (r){
		           		var ids = [];
		            	for(var i=0; i<size; i++){
		                    ids.push(rows[i].resourceid);  
		                }
		                var url = "${pageContext.request.contextPath}/sysdictionary/delete.do?ids="+ids.toString();
		                $.post(url,function(data){
		                	if(data.state == "success"){
			                	Common.showBottomMsg("删除成功");
			                	searchDictionary();
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
	    
	    //子表明细
	    function selectRow(rowIndex, rowData){
	    	$("#dctionarySubTab").datagrid("reload",{parentId:rowData.resourceid});
	    	$("#dctionarySubTab").datagrid("getPanel").panel("setTitle","字典名称："+rowData.dictName);
	    }
	    //子表刷新
	    function searchSubDictionary(){
	    	$("#dctionarySubTab").datagrid("reload");
	    }
	    
	    //刷新
	    function refreshDictionary(){
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
	    
	    //搜索
	    function searchDictionary(){
	    	var dataParam = Common.getFormJson("queryForm");
	    	$("#dctionaryTab").datagrid("reload",dataParam);
	    }
	    
	    //重置
	    function resetDictionary(){
	    	Common.clearForm("queryForm");
			searchDictionary();
	    }
	    
	 	//按下回车时，直接查询
		function onFormKeyPress(event){
			if (event.keyCode==13){
				searchDictionary();
			}
		}
	 	
	 	//查看细表
	 	/*
		$(function(){
            $("#dctionaryTab").datagrid({
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div style="padding:2px"><table class="ddv"></table></div>';
                },
                onExpandRow: function(index,row){
                    var ddv = $(this).datagrid("getRowDetail",index).find("table.ddv");
                    ddv.datagrid({
                        url:"${pageContext.request.contextPath}/sysdictionary/subgrid.do?parentId="+row.resourceid,
                        fitColumns:true,
                        singleSelect:true,
                        rownumbers:true,
                        loadMsg:'加载中...',
                        height:'auto',
                        columns:[[
                            {field:'itemName',title:'字典项名称',width:100},
                            {field:'itemValue',title:'字典项值<font color="red">(唯一)</font>',width:100},
                            {field:'isDefault',title:'默认选项',width:100,formatter: function(value,row,index){
                				if (value=='y'){
                					return "默认";
                				} else {
                					return "";
                				}
                			}},
                            {field:'isValid',title:'有效状态',width:100,formatter: function(value,row,index){
                				if (value=='y'){
                					return "有效";
                				} else {
                					return "无效";
                				}
                			}},
                            {field:'orderBy',title:'排序',width:100},
                            {field:'remark',title:'备注',width:200}
                        ]],
                        onResize:function(){
                            $("#dctionaryTab").datagrid("fixDetailRowHeight",index);
                        },
                        onLoadSuccess:function(){
                            setTimeout(function(){
                                $("#dctionaryTab").datagrid("fixDetailRowHeight",index);
                            },0);
                        }
                    });
                    $("#dctionaryTab").datagrid("fixDetailRowHeight",index);
                }
            });
        });*/
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'north',border:true" title="查询条件" style="height:60px; overflow: hidden;">
		<form id="queryForm" onkeypress="onFormKeyPress(event)" onsubmit="return false;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 33%">
						字典名称：<input type="text" id="dictName" name="dictName" value=""/>&nbsp;&nbsp;
					</td>
					<td style="width: 33%">
						字典编码：<input type="text" id="dictCode" name="dictCode" value=""/>&nbsp;&nbsp;
					</td>
					<td>
						<a onclick="searchDictionary()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						<a onclick="resetDictionary()" class="easyui-linkbutton" iconCls="icon-broom">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div data-options="region:'center'" title="主表">
		<table id="dctionaryTab" class="easyui-datagrid" 
			data-options="border:false,rownumbers:true,fit:true,striped:true,singleSelect:true,pagination:true,pageSize:20,onDblClickRow:clickRow,onSelect:selectRow,sortName:'orderBy',toolbar:'#tb',url:'${pageContext.request.contextPath}/sysdictionary/datagrid.do'">
			<thead data-options="frozen:true"> 
				<tr>
					<th data-options="field:'resourceid',checkbox:true"></th> 
					<th data-options="field:'dictName',width:120">字典名称</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th data-options="field:'dictCode',width:160">字典编码</th>
					<th data-options="field:'isValid',width:70,align:'center',formatter:formatState">有效状态</th>
					<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
					<th data-options="field:'remark',width:200">备注</th>
				</tr>
			</thead>
		</table>
		<%-- 按钮栏 --%>
		<div id="tb" style="height:auto">
			<app:btnAuth btnUrl="/sysdictionary/add.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addDictionary()">新增</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/sysdictionary/edit.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editDictionary()">修改</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/sysdictionary/delete.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteDictionary()">删除</a>|</app:btnAuth>
			<app:btnAuth btnUrl="/sysdictionary/refresh.do"><a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="refreshDictionary()">刷新字典缓存</a>|</app:btnAuth>
		</div>
	</div>
	
	<div data-options="region:'east',title:'子表',split:true" style="width:550px;">
		<table id="dctionarySubTab" class="easyui-datagrid" title="字典名称："
			data-options="border:false,rownumbers:true,fit:true,striped:true,remoteSort:false,sortName:'orderBy',url:'${pageContext.request.contextPath}/sysdictionary/subgrid.do'">
			<thead data-options="frozen:true"> 
				<tr>
					<%-- <th data-options="field:'resourceid',checkbox:true"></th> --%> 
					<th data-options="field:'itemName',width:120">名称</th>
				</tr>
			</thead>
			<thead>
				<tr>
					<th data-options="field:'itemValue',width:100">值</th>
					<th data-options="field:'isDefault',width:70,align:'center',formatter: function(value,row){ if (value=='y'){ return '默认'; } else { return ''; } }">默认选项</th>
					<th data-options="field:'isValid',width:70,align:'center',formatter:formatState">状态</th>
					<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
					<th data-options="field:'remark',width:120">备注</th>
				</tr>
			</thead>
		</table>
	</div>
		 
  </body>
</html>