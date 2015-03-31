<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#resourceForm").validate({
				rules: {
					menuName: "required",
					menuCode: {required:true, remote:"${pageContext.request.contextPath}/sysresource/unique.do?resourceid=${resource.resourceid}"},
					menuUrl: "required",
					orderBy: {digits:true},
					remark: {maxlength:150}
				},messages: {
					menuCode: {remote:"菜单编码已经存在！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#resourceForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {	
		                		opener.searchResource();	
		                		opener.searchSub();	
		                	} catch (e) {} //刷新dataGrid页面
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#resourceForm").submit();
	  	}
	  	
	  	function openParentMenu(){
			var url = "${pageContext.request.contextPath}/sysresource/select.do";
			Common.dialog({"title":"系统资源选择","url":url,modal:true});
	  	}
	  	//回调函数
	  	function selectedParentMenu(res){
			if(res){
		  		$("#parentId").val(res.resourceid);
		  		$("#parentName").val(res.menuName);
			}
	  	}
	  	function cleanMenu(){
	  		$("#parentId").val("");
	  		$("#parentName").val("");
	  	}
	  	
	  	function appendRow(){
	  		var index = 0; //默认从0开始
	  		var rowIndexObj = $("input[id='rowIndex']");
	  		if(rowIndexObj.length>0){
		  		index =  parseInt(rowIndexObj.last().val()) + 1; //获取最后一个值+1
	  		}
	  		var rowhtml = "<tr><td><input type='hidden' id='rowIndex' name='rowIndex' value='"+index+"'><input type='hidden' id='btnId"+index+"' name='btnId"+index+"' value=''><input type='text' style='width: 98%' id='btnName"+index+"' name='btnName"+index+"' value='' required></td><td><input type='text' style='width: 98%' id='btnCode"+index+"' name='btnCode"+index+"' value='' required></td><td><input type='text' style='width: 98%' id='btnUrl"+index+"' name='btnUrl"+index+"' value='' required></td>"+
                "<td><input type='text' style='width: 95%' id='btnOrder"+index+"' name='btnOrder"+index+"' value='' required digits='true'></td><td><input type='text' style='width: 98%' id='btnRemark"+index+"' name='btnRemark"+index+"' value=''></td><td><input type='button' value='删除' onclick='deleteRow(\"\",this)'/></td></tr>";
			$("#childBtn > tbody").append(rowhtml);
	  	}
	  	
	  	function deleteRow(id,btnObj){
	  		if(id!=""&&id.length>0){
	  			var ids = $("#subDeleteIds").val();
	  			if(ids!=""){
	  				$("#subDeleteIds").val(ids+","+id);
	  			}else{
	  				$("#subDeleteIds").val(id);
	  			}
	  			$("#"+id).remove();
	  		}else{
	  			$(btnObj).parent().parent().remove();
	  		}
	  	}
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="系统资源">
			  	<form id="resourceForm" action="${pageContext.request.contextPath}/sysresource/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${resource.resourceid }">
			  		<input type="hidden" id="menuType" name="menuType" value="${resource.menuType }">
			  		<input type="hidden" id="subDeleteIds" name="subDeleteIds" value="">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">菜单名称：</td>
					                <td colspan="3"><input type="text" id="menuName" name="menuName" value="${resource.menuName }" style="width: 50%"></td>
					            </tr>
					            <tr>
					                <td class="tdTitle">URL地址：</td>
					                <td colspan="3"><input type="text" id="menuUrl" name="menuUrl" value="${resource.menuUrl }"  style="width: 50%"></td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">菜单编码<font color="red">(唯一)</font>：</td>
					                <td style="width: 30%;">
					                	<input type="text" id="menuCode" name="menuCode" value="${resource.menuCode }" style="width: 80%;">
					                </td>
					                <td class="tdTitle" style="width: 15%;">父菜单名称：</td>
					                <td>
								  		<input type="hidden" id="parentId" name="parentId" value="${resource.parentId }">
					                	<input type="text" id="parentName" name="parentName" value="${resource.parentName }">
					                	<input type="button" value="选择" onclick="openParentMenu()"/>
					                	<input type="button" value="清除" onclick="cleanMenu()"/>
					                </td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">状态：</td>
					                <td>
					                	<select id="stateFlag" name="stateFlag">
					                		<option value="1">有效</option>
					                		<option value="2" <c:if test="${resource.stateFlag eq '2'}">selected="selected"</c:if>>无效</option>
					                		<option value="3" <c:if test="${resource.stateFlag eq '3'}">selected="selected"</c:if>>隐藏</option>
					                	</select>
					                </td>
					                <td class="tdTitle">排序：</td>
					                <td><input type="text" id="orderBy" name="orderBy" value="${resource.orderBy }"></td>
					            </tr>  
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="3" style="width: 80%" id="remark" name="remark">${resource.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
					
					&nbsp;
					<input type="button" value="新增" onclick="appendRow()"/>
					
					<table id="childBtn" style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					      	<thead>
					      		<tr class="tdTitle">
					      			<td style="width: 18%;">按钮名称</td>
					      			<td style="width: 18%;">按钮编码<font color="red">(唯一)</font></td>
					      			<td style="width: 30%;">URL地址</td>
					      			<td style="width: 8%;">排序</td>
					      			<td>备注</td>
					      			<td>&nbsp;</td>
					      		</tr>
					      	</thead>
					        <tbody>
					        	<c:forEach var="b" items="${butList }" varStatus="vs">
					            <tr id="${b.resourceid }">  
					                <td>
					                	<input type="hidden" id="rowIndex" name="rowIndex" value="${vs.index}">
					                	<input type="hidden" id="btnId${vs.index}" name="btnId${vs.index}" value="${b.resourceid }">
					                	<input type="text" style="width: 98%" id="btnName${vs.index}" name="btnName${vs.index}" value="${b.menuName }" required>
					                </td>
					                <td><input type="text" style="width: 98%" id="btnCode${vs.index}" name="btnCode${vs.index}" value="${b.menuCode }" required></td>
					                <td><input type="text" style="width: 98%" id="btnUrl${vs.index}" name="btnUrl${vs.index}" value="${b.menuUrl }" required></td>
					                <td><input type="text" style="width: 95%" id="btnOrder${vs.index}" name="btnOrder${vs.index}" value="${b.orderBy }" required digits="true"></td>
					                <td><input type="text" style="width: 98%" id="btnRemark${vs.index}" name="btnRemark${vs.index}" value="${b.remark }"></td>
					                <td><input type="button" value="删除" onclick="deleteRow('${b.resourceid}',this)"/></td>
					            </tr>
					        	</c:forEach>
					        </tbody>
					</table>
					
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysresource/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
