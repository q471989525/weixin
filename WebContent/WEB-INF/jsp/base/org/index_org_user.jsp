<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head></head>
  <body>
  	<script type="text/javascript">
  	
	//ztree 设置
	var indexOrgUsersetting = {
		data: {simpleData:{enable:true}},
		view: {expandSpeed: "fast", selectedMulti: false},
		callback: {onClick: indexOrgUserZTreeOnClick}
	};
	
	// ztree json对象
	var indexOrgUserzNodes = ${ztreeOrgUserJson};
	
	//初始化
	$(document).ready(function(){
		$.fn.zTree.init($("#ztreeOrgUserDiv"), indexOrgUsersetting, indexOrgUserzNodes);
		//$("#ztreeOrgUser").height($(window).height()-160);
	});
    
	function indexOrgUserZTreeOnClick(event, treeId, treeNode) {
		if(treeNode.nodeType=="user"){
		    var title = treeNode.name;
		    var url = "/sysuser/userInfo.do?userid="+treeNode.id;
		    parent.addPanelTabs(title, url);
		}
	};
    </script>
    <div id="ztreeOrgUserDiv" class="ztree" style="height: 100%"></div>
  </body>
</html>
