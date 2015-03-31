<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>系统角色资源</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //保存角色资源
	    function saveRoleResource(){
           	var treeObj = $.fn.zTree.getZTreeObj("ztreeResource");
           	var resources = treeObj.getCheckedNodes(true);;
            if(resources.length>0){
            	var rsids = new Array();
            	$.each(resources, function(i,e){
    	    		rsids.push(e.id);
    	    	});
                var url = "${pageContext.request.contextPath}/sysrole/saveRoleResource.do";
                $.post(url, {resourceids:rsids.toString(),roleid:"${role.resourceid}"},function(data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
			            	//window.returnValue = true;
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
                },"json");
            }else{
                $.messager.alert("警告", "请选择一条记录","warning");  
            }
	    }
	    
	  //ztree 设置
		var setting = {
			data: {simpleData:{enable:true}},
			check: {enable: true, chkboxType: { "Y": "ps", "N": "s" }},
			view: {expandSpeed: "fast", selectedMulti: false}
		};
		
		// ztree json对象
		var zNodes = ${ztreeString};
			
	  	//初始已经存在角色
		$(document).ready(function(){
			//初始化ztree
			$.fn.zTree.init($("#ztreeResource"), setting, zNodes);
			//$("#ztreeResource").width($("#centerDiv").width()-5);
			//$("#ztreeResource").height($("#centerDiv").height()-5);
		});
	  	
		function expandAllZt(bool){
			var ztreeObj = $.fn.zTree.getZTreeObj("ztreeResource");
			if(bool){
				ztreeObj.expandAll(true);
			}else{
				ztreeObj.expandAll(false);
			}
		}
  	</script>
  	
  </head>
  
  <body class="easyui-layout">
		<div id="centerDiv" data-options="region:'center',tools:'#treeTools'" title="角色名称：<font color='red'>${role.roleName}</font>" style="overflow-x: hidden;">
			<div id="ztreeResource" class="ztree" style="height: 100%"></div>
		</div>
		
		<div id="treeTools">
			<a href="#" onclick="javascript:expandAllZt(true)" style="width: 30px;">展开</a>
			<a href="#"onclick="javascript:expandAllZt(false)" style="width: 50px;">折叠</a>
		</div>
		
		<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
			<app:btnAuth btnUrl="/sysrole/saveRoleResource.do">
				<a onclick="saveRoleResource();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
			</app:btnAuth>
			<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
		</div>
  </body>
</html>