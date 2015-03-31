<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <body>
  	<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;">
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshCustomerInfo('all')" <c:if test="${empty typeflag or typeflag eq 'all'}">style="color: blue"</c:if>>全部</a>|
		<c:forEach var="type" items="${sourceType }">
			<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshCustomerInfo('${type.itemValue}')" <c:if test="${typeflag eq type.itemValue}">style="color: blue"</c:if>>${type.itemName }</a>|
		</c:forEach>
	</div>
	
	<c:if test="${not empty list}">
	<ul class="index_public">
	<c:forEach var="c" items="${list }">
		<li><span class="fleft index_case_hide ellipsis"><a href="#" style="color: black;" onclick="viewCustomerInfoDetail('${c.resourceid}')">【${app:fmtDictText("D_Customer_Source", c.customerSource)}】&nbsp;&nbsp;-&nbsp;&nbsp;${c.userName}(${app:fmtDictText("D_Sex", c.userSex)})&nbsp;&nbsp;-&nbsp;&nbsp;${c.customerName }&nbsp;[${c.mobile}]&nbsp;&nbsp;-&nbsp;&nbsp;${c.creator }</a></span><span class="fright">(<fmt:formatDate value="${c.createTime}" pattern="yyyy-MM-dd HH:mm"/>)</span></li>
	</c:forEach>
	</ul>
	</c:if>
	
	<c:if test="${empty list}">
		<ul class="index_public">
			<li>
				<span class="fleft index_case_hide ellipsis"><a href="#" style="color:#0000FF;">不存在客户信息......</a></span>
				<span class="fright blue">&nbsp;</span>
			</li>
		</ul>
	</c:if>
  </body>
</html>