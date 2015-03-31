<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <body>
  	<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;">
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshWork('d')" <c:if test="${empty typeflag or typeflag eq 'd'}">style="color: blue"</c:if>>日报</a>|
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshWork('w')" <c:if test="${typeflag eq 'w'}">style="color: blue"</c:if>>周报</a>|
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshWork('m')" <c:if test="${typeflag eq 'm'}">style="color: blue"</c:if>>月报</a>|
	</div>
	
	<c:if test="${not empty list}">
	<ul class="index_public">
	<c:forEach var="w" items="${list }">
		<c:choose>
			<c:when test="${w.verifyFlag eq 'y'}">
				<li><span class="fleft index_case_hide ellipsis"><a href="#" style="color: black;" onclick="viewWorkDetail('${w.resourceid}','${viewFlag}')">【<c:choose><c:when test="${w.reportType eq 'd'}">日报</c:when><c:when test="${w.reportType eq 'w'}">周报</c:when><c:otherwise>月报</c:otherwise></c:choose>】&nbsp;&nbsp;-&nbsp;&nbsp;${w.workContent }&nbsp;&nbsp;-&nbsp;&nbsp;${w.creator }</a></span><span class="fright">(<fmt:formatDate value="${w.startDate}" pattern="yyyy-MM-dd"/>&nbsp;--&nbsp;<fmt:formatDate value="${w.endDate}" pattern="yyyy-MM-dd"/>)</span></li>
			</c:when>
			<c:otherwise>
				<li><span class="fleft index_case_hide ellipsis"><a href="#" style="color: blue;" onclick="viewWorkDetail('${w.resourceid}','${viewFlag}')">【<c:choose><c:when test="${w.reportType eq 'd'}">日报</c:when><c:when test="${w.reportType eq 'w'}">周报</c:when><c:otherwise>月报</c:otherwise></c:choose>】&nbsp;&nbsp;-&nbsp;&nbsp;${w.workContent }&nbsp;&nbsp;-&nbsp;&nbsp;${w.creator }</a></span><span class="fright blue">(<fmt:formatDate value="${w.startDate}" pattern="yyyy-MM-dd"/>&nbsp;--&nbsp;<fmt:formatDate value="${w.endDate}" pattern="yyyy-MM-dd"/>)</span></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	</ul>
	</c:if>
	
	<c:if test="${empty list}">
		<ul class="index_public">
			<li>
				<span class="fleft index_case_hide ellipsis"><a href="#" style="color:#0000FF;">不存在工作日志......</a></span>
				<span class="fright blue">&nbsp;</span>
			</li>
		</ul>	
	</c:if>
  </body>
</html>