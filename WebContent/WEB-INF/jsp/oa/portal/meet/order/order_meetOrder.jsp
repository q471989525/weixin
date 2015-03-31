<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>会议室预订</title>
	<%@ include file="/common/jscript/include_list.jsp" %>
	
  	<script type="text/javascript">
	    //新增
	    function addMeetOrder(id,name){
	    	var roomName = encodeURI(encodeURI(name));//两次编码,后台解码
	    	var url = "${pageContext.request.contextPath}/meetOrder/add.do?roomId="+id+"&roomName="+roomName+"&random="+Math.random();
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	    
	  	//查看
	    function viewDetail(id){
			var url = "${pageContext.request.contextPath}/meetOrder/edit.do?view=flag&resourceid="+id;
			Common.dialog({"url":url,"width":800,"height":500});
	    }
	  	
	    //增加tab页
	  	function moreMeetDetail(id, title){
			var url = "/meetOrder/list.do?menuId=${param.menuId}&roomId="+id;
	  		parent.addPanelTabs(title, url);
	  	}
  	</script>
  	
  </head>
  
  <body style="padding: 2px;">
  		<c:forEach var="room" items="${meetRooms }" varStatus="vs">
	  		<div id="room_${vs.index}" style="width: 49%; float: left; padding: 4px;" align="center">
				<div class="easyui-panel" title="${room.meetName }" data-options="iconCls:'icon-ok',tools:'#roomTools${vs.index}'" style="width:100%;height:300px;padding:2px;background:#fafafa;">   
				    <p>容量：${room.meetCapacity }</p>   
				    <p>设备：${room.meetEquip }</p>   
					<p>位置：${room.meetLocation }</p>
					<app:btnAuth btnUrl="/meetOrder/add.do"> 
					<p><a onclick="addMeetOrder('${room.resourceid}','${room.meetName}');" class="easyui-linkbutton">&nbsp;预&nbsp;&nbsp;定&nbsp;</a></p>
					</app:btnAuth>
					<c:forEach var="order" items="${room.orders }">
						<p><a href="#" onclick="viewDetail('${order.resourceid}')" <c:if test="${order.stating eq 'ing'}">style="color: blue;"</c:if> >(${order.startTimeFmt}&nbsp;-&nbsp;${order.endTimeFmt})&nbsp;&nbsp;&nbsp;主题:${order.meetSubject }&nbsp;&nbsp;&nbsp;--&nbsp;&nbsp;${order.creator}</a></p>
					</c:forEach>
				</div>
		  		<div id="roomTools${vs.index}">
					<a href="#" onclick="javascript:moreMeetDetail('${room.resourceid}','${room.meetName}')" style="width: 30px;">更多</a>
				</div>
	  		</div>
  		</c:forEach>
  </body>
</html>