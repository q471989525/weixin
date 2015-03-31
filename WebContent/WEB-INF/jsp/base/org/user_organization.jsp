<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>组织用户树</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //格式化
	    function formatState(val,row){
	    	var state = "";
	    	//组织状态
	    	if (val == "y") state = "有效";
	    	if (val == "h") state = "<span style='color:blue;'>隐藏</span>";
	    	//人员状态
	    	if (val == "1") state = "<span style='color:gray;'>在职</span>";  
	    	if (val == "2") state = "<span style='color:red;'>离职</span>";
	    	if (val == "3") state = "<span style='color:blue;'>待定</span>";
            return state;
	    }
	    
	    function formatOrgType(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_OrgType")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatPost(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Post")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function formatSex(val,row){
	    	var text = "";
	    	var dictObj = jQuery.parseJSON('${app:fmtDict("D_Sex")}');
	    	if(null == dictObj) return text;
	    	for ( var int = 0; int < dictObj.length; int++) {
	    		if(dictObj[int].value == val) text = dictObj[int].name;
			}
            return text;
	    }
	    
	    function clickRow(row){
	    	if(row.iconCls){
	    		var url = "${pageContext.request.contextPath}/sysuser/edit.do?resourceid="+row.resourceid;
    			Common.dialog({"url":url,"width":1000,"height":500});
	    	}else{
	    		var url = "${pageContext.request.contextPath}/sysorganization/edit.do?resourceid="+row.resourceid;
    			Common.dialog({"url":url,"width":1000,"height":400});
	    	}
	    }
	    
	    function reLoadTreeGD(){
	    	$("#orgUserTG").treegrid("reload");
	    }
	    
	  	function expandAll(){
	  		$("#orgUserTG").treegrid("expandAll");
	  	}
	  	
	  	function collapseAll(){
	  		$("#orgUserTG").treegrid("collapseAll");
	  	}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div data-options="region:'center',border:false">
			<table id="orgUserTG" class="easyui-treegrid" title="组织用户树" 
				data-options="border:false,rownumbers:true,fit:true,striped:true,idField:'resourceid',treeField:'orgName',onDblClickRow:clickRow,toolbar:'#tb',url:'${pageContext.request.contextPath}/sysorganization/orgUserTreegrid.do'">
				<thead data-options="frozen:true"> 
					 <tr>
						<th data-options="field:'orgName',width:240">组织名称 / 用户名称 </th>
			         </tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field:'orgType',width:120,formatter:formatOrgType">组织类型</th>
						<th data-options="field:'userLogin',width:100">登录账号 </th>
						<th data-options="field:'userPost',width:100,formatter:formatPost">岗位名称</th>
						<th data-options="field:'userMobile',width:100">手机号码</th>
						<th data-options="field:'userEmail',width:180">邮箱地址</th>
						<th data-options="field:'userSex',width:70,formatter:formatSex">性别 </th>
						<th data-options="field:'stateFlag',width:70,align:'center',formatter:formatState">状态</th>
						<th data-options="field:'orderBy',width:60,align:'right',sortable:'true'">排序</th>
						<th data-options="field:'dutyDesc',width:300">职责描述</th>
					</tr>
				</thead>
			</table>
			<%-- 按钮栏 --%>
			<div id="tb" style="height:auto">
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder-open',plain:true" onclick="expandAll()">展开全部</a>|
				<a class="easyui-linkbutton" data-options="iconCls:'tree-folder',plain:true" onclick="collapseAll()">折叠全部</a>|
			</div>
		
		</div>
  </body>
</html>