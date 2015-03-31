<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <body>
  	<div style="right: 0px; top: 0px; border-bottom:1px dotted #B7B7B7;">
		<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshBusiness('all')" <c:if test="${empty typeflag or typeflag eq 'all'}">style="color: blue"</c:if>>全部</a>|
		<c:forEach var="type" items="${saleStep }">
			<a class="easyui-linkbutton" data-options="plain:true" onclick="refreshBusiness('${type.itemValue}')" <c:if test="${typeflag eq type.itemValue}">style="color: blue"</c:if>>${type.itemName }</a>|
		</c:forEach>
	</div>
	
	<c:if test="${not empty list}">
	<ul class="index_public">
	<c:forEach var="b" items="${list }">
		<li><span class="fleft index_case_hide ellipsis"><a href="#" style="color: black;" onclick="viewBusinessDetail('${b.resourceid}')">【${app:fmtDictText("Code_Sale_Step", b.saleStep)}】&nbsp;&nbsp;-&nbsp;&nbsp;${app:fmtDictText("D_Business_Type", b.businessType)}&nbsp;&nbsp;-&nbsp;&nbsp;${b.businessName }&nbsp;[${b.amount}]&nbsp;&nbsp;-&nbsp;&nbsp;${b.customerName }</a></span><span class="fright">(<fmt:formatDate value="${b.createTime}" pattern="yyyy-MM-dd HH:mm"/>)</span></li>
	</c:forEach>
	</ul>
	</c:if>
	
	<c:if test="${empty list}">
		<ul class="index_public">
			<li>
				<span class="fleft index_case_hide ellipsis"><a href="#" style="color:#0000FF;">不存在商机......</a></span>
				<span class="fright blue">&nbsp;</span>
			</li>
		</ul>
	</c:if>
  </body>
</html>