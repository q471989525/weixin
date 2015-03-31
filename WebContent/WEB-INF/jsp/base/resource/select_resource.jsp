<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统资源</title>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
	
  	<script type="text/javascript">
	    //格式化
	    function formatState(val,row){
	    	var state = "";
	    	if (val == "1") state = "有效";  
	    	if (val == "2") state = "<span style='color:red;'>无效</span>";  
	    	if (val == "3") state = "<span style='color:blue;'>隐藏</span>";  
	        return state;
	    }
	    
	  	function expandAll(){
	  		$("#resourceTab").treegrid("expandAll");
	  	}
	  	
	  	function collapseAll(){
	  		$("#resourceTab").treegrid("collapseAll");
	  	}
		
	    //确定
	    function selectResource(){
            var row = $("#resourceTab").treegrid("getSelected");
            if(row){
            	window.parent.selectedParentMenu(row);
                //window.returnValue=row;
   	    		//window.close();
		    	closeWin();
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	    function dbclickRow(row){
	    	//window.returnValue=row.resourceid+"="+row.menuName;
	    	//window.returnValue=row;
	    	//window.close();
	    	window.parent.selectedParentMenu(row);
	    	closeWin();
	    }
	    
	    function closeWin(){
	    	//window.returnValue=false;
	    	//window.close();
	    	var api = frameElement.api.close();
	    }
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'center'">
			<table id="resourceTab" class="easyui-treegrid" title="系统资源列表" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'menuName',onDblClickRow:dbclickRow,toolbar:'#tb3',url:'${pageContext.request.contextPath}/sysresource/treegrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'menuName',width:220">菜单名称</th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'menuUrl',width:280">链接地址</th>
						<%-- <th data-options="field:'stateFlag',width:50,align:'center',formatter:formatState">状态</th> --%>
						<th data-options="field:'orderBy',width:50,align:'right'">排序</th>
					</tr>
				</thead>
			</table>
			<%-- 按钮栏 --%>
			<div id="tb3">
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
			</div>
		</div>
		
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="selectResource()">确定</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin();">关闭</a>
		</div>
  </body>
</html>