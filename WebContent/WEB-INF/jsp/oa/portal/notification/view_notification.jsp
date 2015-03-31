<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>查看</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'north'" style="height:60px;padding:2px;overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<h3 style="margin-top: 10px;">${notification.notifyTitle }</h3>
		<div style="position: absolute; right: 1px; bottom: 1px;">
		发布人：${notification.creator }&nbsp;&nbsp;&nbsp;&nbsp;发布部门：${notification.createDept }&nbsp;&nbsp;&nbsp;&nbsp;发布时间：<fmt:formatDate value="${notification.releaseTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;
		</div>
	</div>
  	<div data-options="region:'center',border:false" style="padding:2px;">
       	${clobList[0].notifyContent }
       	<c:if test="${not empty attList }">
       	附件：<br>
       	<div id="attFileDiv">
			<c:forEach var="att" items="${attList }" varStatus="vs">
			<div><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${vs.count}、${att.fileName}</a></div>
			</c:forEach>
		</div>
		</c:if>
	</div>
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
