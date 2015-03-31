<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>组织选择</title>
    <script type="text/javascript">
	<!--
		var ctx = "${pageContext.request.contextPath}";
	//-->
	</script>
    <link href="${pageContext.request.contextPath}/common/zTree_v3/css/default.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/common/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/icon.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/common/easyui1.4/themes/public.css">

  	<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/easyui-lang-zh_CN.js"></script>
  	<script src="${pageContext.request.contextPath}/common/zTree_v3/js/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
  	<script type="text/javascript">
	//ztree 设置
	var setting = {
		data: {simpleData:{enable:true}},
		view: {expandSpeed: "fast", selectedMulti: false},
		check: {enable: true, chkboxType: {"Y":"", "N":""}}
	};
	
	// ztree json对象
	var zNodes = ${ztreeString};
	
	//初始化
	var treeObj;
	$(document).ready(function(){
		treeObj = $.fn.zTree.init($("#ztreeORG"), setting, zNodes);
	});
	
  	
  	//确定
  	function confirmSelect(){
  		var orgs = treeObj.getCheckedNodes(true);;
        if(orgs.length>0){
  			window.parent.selectedOrgs(orgs);
  		}else{
  			$.messager.alert("警告", "请选择组织！","warning");
  			return;
  		}
  		confirmColse();
  	}
  	
  	//关闭
  	function confirmColse(){
  		var api = frameElement.api.close();
  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center',border:false">
  		<div id="ztreeORG" class="ztree" style="height: 100%"></div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<a onclick="confirmSelect()" data-options="iconCls:'icon-ok'" class="easyui-linkbutton">确定</a>
		<a onclick="confirmColse()" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
