<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE HTML>
<html>
  <head>
    	<title>app3</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="spring,security3,jquery">
		<meta http-equiv="description" content="app3">
		
		<%@ include file="/common/jscript/include_index.jsp" %>
  		<script type="text/javascript">
			//ztree click回调方法
			function zTreeOnClick(event, treeId, treeNode) {
			   	var title = treeNode.name;
			   	var url = treeNode.value;
			   	if(url!=null && url.indexOf(".do")>0){
			   		if(url.indexOf("?")>0){
			   			url = url+"&menuId="+treeNode.id;
			   		}else{
			   			url = url+"?menuId="+treeNode.id;
			   		}
			   		addPanelTabs(title, url);
			   	}
			};
			
			//ztree 设置
			var setting = {
				data: {simpleData:{enable:true}},
				view: {expandSpeed: "fast", selectedMulti: false},
				callback: {onClick: zTreeOnClick}
			};
			
			// ztree json对象
			var zNodes = ${ztreeString};
			
			//初始化
			$(document).ready(function(){
				$.fn.zTree.init($("#ztreeMenu"), setting, zNodes); //ztree菜单树
				//$("#ztreeMenu").height($(window).height()-160);
					
				//绑定tabs的右键菜单
	            $("#mainTabs").tabs({
				       onContextMenu : function (e, title) {
				    	   //首页右键单击时不显示关闭菜单
				    	   if(title=='首页') return ;
				           e.preventDefault();
				           $("#tabsMenu").menu("show", {
				                left : e.pageX,
				                top : e.pageY
				            }).data("tabTitle", title);
				        }
				});

	          	//实例化menu的onClick事件
			    $("#tabsMenu").menu({
			        onClick : function (item) {
			            CloseTab(this, item.id);
			        }
			    });
	          
			});
			
			//几个关闭事件的实现
		    function CloseTab(menu, type) {
		        var curTabTitle = $(menu).data("tabTitle");
		        var tabs = $("#mainTabs");
		         
		        if (type === "close") {
		            tabs.tabs("close", curTabTitle);
		            return;
		        }
		         
		        var allTabs = tabs.tabs("tabs");
		        var closeTabsTitle = [];
		         
		        $.each(allTabs, function () {
		            var opt = $(this).panel("options");
		            if (opt.closable && opt.title != curTabTitle && type === "Other") {
		                closeTabsTitle.push(opt.title);
		            } else if (opt.closable && type === "All") {
		                closeTabsTitle.push(opt.title);
		            }
		        });
		         
		        for (var i = 0; i < closeTabsTitle.length; i++) {
		            tabs.tabs("close", closeTabsTitle[i]);
		        }
                if(type === "Other") tabs.tabs("select", curTabTitle); //选中当前tab
		    }
			
			//关闭当前tab窗口
			function closeCurrentTab(){
				var tab = $("#mainTabs").tabs("getSelected"); // 获取选中的panel
				if (tab){
					var index = $("#mainTabs").tabs("getTabIndex", tab);
					$("#mainTabs").tabs("close", index);
				}
			}
			
			//增加tab页签
			function addPanelTabs(title, url){
				var tabspanel=$("#mainTabs");
				//该菜单已打开，更新。
				if(tabspanel.tabs("exists",title)){
					tabspanel.tabs("select", title);  //选中
					var selTab = tabspanel.tabs("getSelected"); // 获取选中的panel
					tabspanel.tabs("update", {
						tab: selTab,
						options: {
							content:"<iframe src='${pageContext.request.contextPath}"+url+"' style='width:100%;height:100%;' frameborder='0'></iframe>"
						}
					});
				}else{
					tabspanel.tabs("add",{
						title:title,
						content:"<iframe src='${pageContext.request.contextPath}"+url+"' style='width:100%;height:100%;' frameborder='0'></iframe>",
						closable:true,
						bodyCls:"panel-body-overflow"
					});	
				}
			}
			
			//菜单切换
			function toggleMenuTree(id){
				$("#menuTabs").tabs("select", "菜单"); //选中
				
				//ajax加载菜单
				if(id!="" && id.length>0){
					var url = "${pageContext.request.contextPath}/sysresource/findChildMenuById.do";
	                $.post(url, {parentId:id},function(data){
		            	if(data.state == "success"){
		            		var zJsons = jQuery.parseJSON(data.ztreeJson);
							$.fn.zTree.init($("#ztreeMenu"), setting, zJsons);
		            	}else{
			            	$.messager.alert("错误", "加载菜单失败", "error");  
		            	}
	                },"json");
				}
			}

			//搜索
			function doSearch(value){
				$("#menuTabs").tabs("select", "搜索"); //选中
				if(value!=""&&value.length>0){
					var url = "${pageContext.request.contextPath}/sysuser/findUsersByName.do";
	                $.post(url, {username:value},function(data){
		            	if(data.length>0){
		            		$("#searchDiv").html(""); //先清空
		                 	var size = data.length;
		                 	for ( var int = 0; int < size; int++) {
		                 		var username = data[int].username;
		                 		var url = "/sysuser/userInfo.do?userid="+data[int].resourceid;
		                 		var state = data[int].stateFlag;
		                 		var contact = data[int].mobile;
		                 		if(data[int].officePhone.length>0) contact = contact+"/"+data[int].officePhone;
		                 		$("#searchDiv").append("<div>姓名：<a href='javascript:void(0)' onclick=\"javascript:addPanelTabs('"+username+"','"+url+"')\"  style='color: blue;'>"+username+state+"</a>&nbsp;&nbsp;&nbsp;&nbsp;性别："+data[int].sex+"<br>账号："+data[int].loginId+"<br>电话："+contact+"<br>邮箱："+data[int].email+"<br>岗位："+data[int].post+"<br>组织："+data[int].orgName+"<br>职责："+data[int].dutyDesc+"<br><hr></div>");
		                 	}
		            	}else{
		            		$("#searchDiv").html("<div><font color='red'>没有用户</font></div>");
		            	}
	                },"json");
				}
			}
			
			//修改密码
		    function modifyPwd(){
                var url = "${pageContext.request.contextPath}/sysuser/modifyPwd.do";
    			Common.dialog({"url":url,"width":400,"height":200});
		    }
			
			//退出
			function logout(){
				window.location.href = "${pageContext.request.contextPath}/j_spring_security_logout";
			}
			
			//--------------------------------------------------------------------------------------------------------------------------------------
			//查看通知详细
			function viewNotifyDetail(notid){
				var url = "${pageContext.request.contextPath}/notification/view.do?notid="+notid;
				var width=window.screen.availWidth-20;
				var height=window.screen.availHeight-65;
    			Common.dialog({"url":url,"width":width,"height":height,"iTop":5});
			}
			//刷新通知列表
			function refreshNotify(type){
				$("#panel_3").panel("refresh","${pageContext.request.contextPath}/notification/index.do?type="+type);
			}
			//查看工作详细
			function viewWorkDetail(workid,flag){
				var url = "${pageContext.request.contextPath}/workreport/verifyIndex.do?resourceid="+workid+"&viewFlag="+flag;
    			Common.dialog({"url":url,"width":600,"height":400});
			}
			//刷新工作日志
			function refreshWork(type){
				$("#panel_1").panel("refresh","${pageContext.request.contextPath}/workreport/index.do?type="+type);
			}
			//查看商机详细
			function viewBusinessDetail(bizid){
				var url = "${pageContext.request.contextPath}/businessopportunity/edit.do?resourceid="+bizid;
    			Common.dialog({"url":url,"width":800,"height":500});
			}
			//刷新商机
			function refreshBusiness(type){
				$("#panel_2").panel("refresh","${pageContext.request.contextPath}/businessopportunity/index.do?type="+type);
			}
			//查看客户信息详细
			function viewCustomerInfoDetail(cid){
				var url = "${pageContext.request.contextPath}/customerinfo/edit.do?resourceid="+cid;
    			Common.dialog({"url":url,"width":800,"height":500});
			}
			//刷新客户信息
			function refreshCustomerInfo(type){
				$("#panel_4").panel("refresh","${pageContext.request.contextPath}//customerinfo/index.do?type="+type);
			}
		</script>
  </head>
  
  <body class="easyui-layout">
  	<%-- 头部 --%>
	<div data-options="region:'north',border:false" style="height:60px;padding:0px;overflow: hidden;">
		<div class="easyui-panel" data-options="border:false,fit:true" style="padding:0px; overflow: hidden; background-image: url('${pageContext.request.contextPath}/common/images/asf-logo.jpg');background-repeat: no-repeat;" align="center">
		 	<a class="easyui-linkbutton" data-options="plain:true">${AMPM}：【${user.username}】&nbsp;${currentDate}</a>
			<div style="position: absolute; right: 0px; top: 0px; border:1px solid #ddd;">
				<%-- 
				<a class="easyui-linkbutton" data-options="plain:true">&nbsp;个性设置&nbsp;</a>|
				<a class="easyui-linkbutton" data-options="plain:true">&nbsp;日志查询&nbsp;</a>|
				--%>
				<a class="easyui-linkbutton" data-options="plain:true" onclick="modifyPwd()">&nbsp;修改密码&nbsp;</a>|
				<a class="easyui-linkbutton" data-options="plain:true" onclick="logout()">&nbsp;退&nbsp;出&nbsp;</a>
				<%-- 
				<select id="skinChange" name="skinChange" class="easyui-combobox" data-options="onSelect:selectSkin" style="width: 100px;">
					<option value="default">默认主题</option>
					<option value="gray">灰色主题</option>
					<option value="metro-blue">metro-蓝主题</option>
					<option value="metro-gray">metro-灰主题</option>
					<option value="metro-green">metro-绿主题</option>
					<option value="metro-orange">metro-橙主题</option>
					<option value="metro-red">metro-红主题</option>
					<option value="ui-cupertino">时髦主题</option>
					<option value="ui-pepper-grinder">胡椒主题</option>
					<option value="ui-sunny">快活主题</option>
				</select>
				--%>
			</div>
			<%-- 菜单 --%>
			<div style="position: absolute; left: 232px; bottom: 0px; border:1px solid #ddd; padding: 2px;">
				<c:forEach var="m" items="${rootMenuList }" varStatus="vs">
					<a class="easyui-linkbutton" data-options="toggle:true,group:'g1',plain:true<c:if test="${vs.index eq 0}">,selected:true</c:if>" onclick="javascript:toggleMenuTree('${m.id}')">&nbsp;${m.name}&nbsp;</a>
				</c:forEach>
			</div>
		</div>
	</div>
	
	<%-- 左边导航 --%>
	<div id="westDivLayout" data-options="region:'west',title:'功能导航'" style="width:200px;padding:0px;">
		<div class="easyui-layout" data-options="border:false,fit:true">
			<div data-options="region:'north',border:false" style="height:22px; overflow: hidden;">
				<input class="easyui-searchbox" data-options="prompt:' 搜索用户...',searcher:doSearch" style="width:198px;">
			</div>
			<div data-options="region:'center',border:false">
				<div id="menuTabs" class="easyui-tabs" data-options="border:false,fit:true">
					<div title="菜单">
						<div id="ztreeMenu" class="ztree" style="height: 100%"></div>
					</div>
					<div title="组织"  cache="false"  href="${pageContext.request.contextPath}/sysorganization/index.do">
					</div>
					<div title="搜索" style="padding:1px" id="searchDiv">
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%--主要工作区 --%>
	<div data-options="region:'center'">
		<div id="mainTabs" class="easyui-tabs" data-options="border:false,fit:true">
			<div id="firstDiv" title="首页" style="padding:2px; overflow: auto;">
				<div style="width: 49%; float: left; padding: 4px;" align="center">
					<div id="panel_1" class="easyui-panel" title="协同工作（待办、已办、全部）" style="width:100%;height:300px;" href="${pageContext.request.contextPath}/workreport/index.do">
					</div>
				</div>
						
				<div style="width: 49%; float: left; padding: 4px;" align="center">
					<div id="panel_2" class="easyui-panel" title="待办事项（会议、日程、任务、内部邮件）" style="width:100%;height:300px;" href="${pageContext.request.contextPath}/businessopportunity/index.do">
					</div>
				</div>
								
				<div style="width: 49%; float: left; padding: 4px;" align="center">
					<div id="panel_3" class="easyui-panel" title="公共信息（公告、新闻、调查）" style="width:100%;height:300px;" href="${pageContext.request.contextPath}/notification/index.do">
					</div>
				</div>
								
				<div style="width: 49%; float: left; padding: 4px;" align="center">
					<div id="panel_4" class="easyui-panel" title="员工社区" style="width:100%;height:300px;" href="${pageContext.request.contextPath}//customerinfo/index.do">
					</div>
				</div>
			</div>			
		</div>
		<!-- end tab -->
	</div>
	
	<%-- 版权声明 --%>
	<div data-options="region:'south',border:false" style="height:18px;padding:0px; overflow: hidden;">
		<div class="easyui-panel" data-options="border:false,fit:true" style="height:18px; padding:0px; overflow: hidden; background-image: linear-gradient(rgb(239, 245, 255) 0px, rgb(224, 236, 255) 100%)" align="center">
			<div style="float: left; padding: 2px;">当前在线人员：${numUsers}</div>版权所有：丰 <span style="padding-left: 25px;">技术支持与报障：zf_1017@163.com</span>
		</div>
	</div>
	
	<%-- 右键菜单 --%>
	<div id="tabsMenu" class="easyui-menu" style="width:120px;"> 
    	<div id="close">关闭</div> 
    	<div id="Other">关闭其他</div> 
    	<div id="All">关闭所有</div>
  	</div>
  </body>
</html>
