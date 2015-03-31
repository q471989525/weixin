<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
    <style type="text/css"> 
	pre {
		margin-top: 5px; margin-bottom: 5px;
		white-space: pre-wrap; 
		white-space: -moz-pre-wrap; 
		white-space: -pre-wrap; 
		white-space: -o-pre-wrap; 
		word-wrap: break-word; 
	} 
	</style>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="会议室预订">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>
					                <td class="tdTitle" style="width: 15%;">会议室：</td>
					                <td>
					                	${meetOrder.meetName }&nbsp;&nbsp;
					                	<c:if test="${meetOrder.stating eq 'ing'}"><font style="color: blue;">(进行中...)</font></c:if>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议主题：</td>
					                <td>
					                	${meetOrder.meetSubject }
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">会议时间：</td>
					                <td>
					                	<fmt:formatDate value="${meetOrder.startTime}" pattern="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;至&nbsp;&nbsp;
					                	<fmt:formatDate value="${meetOrder.endTime}" pattern="yyyy-MM-dd HH:mm"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">主持人：</td>
					                <td>
					                	${meetOrder.compereName }
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">记录人：</td>
					                <td>
					                	${meetOrder.recorder }
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">会议内容：</td>
					                <td>
					                	<pre>${meetOrder.meetContent}</pre>
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">参会人员：</td>
					                <td>
					                	${userName }
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">附件：</td>
					                <td>
					                	<div id="attFileDiv">
					                		<c:forEach var="att" items="${attList }" varStatus="vs">
					                		<div id="${att.resourceid}"><font style="font-size: 14px;"><a href="#" onclick="Common.downloadFile('${att.resourceid}')">${att.fileName}</a></font>&nbsp;</div>
					                		</c:forEach>
					                	</div>
					                </td>
					            </tr>
					        </tbody>
					</table>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<a  onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
