<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <body>
  	<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;">
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshNotify('all')" <c:if test="${empty typeflag or typeflag eq 'all'}">style="color: blue"</c:if>>全部</a>|
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshNotify('unread')" <c:if test="${typeflag eq 'unread'}">style="color: blue"</c:if>>未阅</a>|
		<c:forEach var="type" items="${notType }">
			<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshNotify('${type.itemValue}')" <c:if test="${typeflag eq type.itemValue}">style="color: blue"</c:if>>${type.itemName }</a>|
		</c:forEach>
	</div>
	
	<c:if test="${not empty list}">
	<c:forEach var="n" items="${list }">
		<c:if test="${n.viewFlag eq 'y'}">
			<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;"><a class="easyui-linkbutton" onclick="viewNotifyDetail('${n.resourceid}')" data-options="plain:true,iconCls:'icon-new'" style="color:black;">【${app:fmtDictText("D_Notification_Type", n.notifyType)}】&nbsp;&nbsp;-&nbsp;&nbsp;${n.notifyTitle }&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${n.releaseTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;&nbsp;&nbsp;(${n.viewCount})</a></div>
		</c:if>
		<c:if test="${n.viewFlag eq 'n'}">
			<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;"><a class="easyui-linkbutton" onclick="viewNotifyDetail('${n.resourceid}')" data-options="plain:true,iconCls:'icon-new'" style="color:blue;">【${app:fmtDictText("D_Notification_Type", n.notifyType)}】&nbsp;&nbsp;-&nbsp;&nbsp;${n.notifyTitle }&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${n.releaseTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;&nbsp;&nbsp;(${n.viewCount})</a></div>
		</c:if>
	</c:forEach>
	</c:if>
	
	<c:if test="${empty list}">
		<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;"><a class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-new'" style="color:#0000FF;">不存在通知</a></div>
	</c:if>
  </body>
</html>