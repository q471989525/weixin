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
	  		$("#dictionaryForm").validate({
				rules: {
					dictName: "required",
					dictCode: {required:true, remote:"${pageContext.request.contextPath}/sysdictionary/unique.do?resourceid=${dictionary.resourceid}"},
					isValid: "required",
					orderBy: {digits:true},
					remark: {maxlength:150}
				},messages: {
					dictCode: {remote:"当前编码已经存在！"}
				}
			})
	  		
	  		//ajax提交
	  	    $("#dictionaryForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	opener.searchDictionary();
		                	opener.searchSubDictionary();
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        }
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#dictionaryForm").submit();
	  	}
	  	
	  	function appendRow(){
	  		var index = 0; //默认从0开始
	  		var rowIndexObj = $("input[id='rowIndex']");
	  		if(rowIndexObj.length>0){
		  		index =  parseInt(rowIndexObj.last().val()) + 1; //获取最后一个值+1
	  		}
	  		var rowhtml = "<tr><td><input type='hidden' id='rowIndex' name='rowIndex' value='"+index+"'><input type='hidden' id='childId"+index+"' name='childId"+index+"' value=''><input type='text' style='width: 98%' id='itemName"+index+"' name='itemName"+index+"' value='' required></td>"+
	  			"<td><input type='text' style='width: 98%' id='itemValue"+index+"' name='itemValue"+index+"' value='' required></td>"+
	  			"<td><select id='isDefault"+index+"' name='isDefault"+index+"' style='width: 99%'><option value=''>...</option><option value='y'>默认</option></select></td>"+
	            "<td><select id='isValid"+index+"' name='isValid"+index+"' style='width: 99%'><option value='y'>有效</option><option value='n'>无效</option></select></td>"+
	            "<td><input type='text' style='width: 95%' id='orderBy"+index+"' name='orderBy"+index+"' value='' required digits='true'></td><td><input type='text' style='width: 98%' id='remark"+index+"' name='remark"+index+"' value=''></td>"+
	            "<td><input type='button' value='删除' onclick='deleteRow(\"\",this)'/></td></tr>";
			$("#childDct > tbody").append(rowhtml);
	  	}
	  	
	  	function deleteRow(id,btnObj){
	  		if(id!=""&&id.length>0){
	  			var ids = $("#deleteIds").val();
	  			if(ids!=""){
	  				$("#deleteIds").val(ids+","+id);
	  			}else{
	  				$("#deleteIds").val(id);
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
			<div title="数据字典">
			  	<form id="dictionaryForm" action="${pageContext.request.contextPath}/sysdictionary/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${dictionary.resourceid }">
			  		<input type="hidden" id="deleteIds" name="deleteIds" value=""><%-- 子表删除隐藏域  --%>
				   	<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
				    	<tbody>
					    	<tr>  
					        	<td class="tdTitle" style="width: 15%;">字典名称：</td>
					            <td style="width: 25%;"><input type="text" id="dictName" name="dictName" value="${dictionary.dictName }"></td>
					            <td class="tdTitle" style="width: 15%;">字典编码<font color="red">(唯一)</font>：</td>
					            <td><input type="text" id="dictCode" name="dictCode" value="${dictionary.dictCode }" style="width: 50%;"></td>
					        </tr>
					        <tr>
					        	<td class="tdTitle">有效状态：</td>
					            <td>
					            	<select id="isValid" name="isValid">
					              		<option value="y">有效</option>
					               		<option value="n" <c:if test="${dictionary.isValid eq 'n'}">selected="selected"</c:if>>无效</option>
					               	</select>
					            </td>
					            <td class="tdTitle">排序：</td>
					            <td><input type="text" id="orderBy" name="orderBy" value="${dictionary.orderBy }"></td>
					        </tr>  
					        <tr>  
					           <td class="tdTitle">备注：</td>
					           <td colspan="3">
					        	<textarea rows="3" style="width: 80%" id="remark" name="remark">${dictionary.remark}</textarea>
					           </td>
					        </tr>
					      </tbody>
					   </table>
					   
					   &nbsp;
					   <input type="button" value="新增" onclick="appendRow()"/>
					      
					   <table id="childDct" style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					      	<thead>
					      		<tr class="tdTitle">
					      			<td style="width: 20%;">字典项名称</td>
					      			<td style="width: 20%;">字典项值<font color="red">(唯一)</font></td>
					      			<td style="width: 10%;">默认选项</td>
					      			<td style="width: 10%;">有效状态</td>
					      			<td style="width: 10%;">排序</td>
					      			<td>备注</td>
					      			<td>&nbsp;</td>
					      		</tr>
					      	</thead>
					        <tbody>
					        	<c:forEach var="d" items="${lists }" varStatus="vs">
					            <tr id="${d.resourceid}">  
					                <td>
					                	<input type="hidden" id="rowIndex" name="rowIndex" value="${vs.index}">
					                	<input type="hidden" id="childId${vs.index}" name="childId${vs.index}" value="${d.resourceid}">
					                	<input type="text" style="width: 98%" id="itemName${vs.index}" name="itemName${vs.index}" value="${d.itemName }" required>
					                </td>
					                <td><input type="text" style="width: 98%" id="itemValue${vs.index}" name="itemValue${vs.index}" value="${d.itemValue }" required></td>
					                <td>
					                	<select id="isDefault${vs.index}" name="isDefault${vs.index}" style="width: 99%">
					                		<option value="">...</option>
					                		<option value="y" <c:if test="${d.isDefault eq 'y'}">selected="selected"</c:if>>默认</option>
					                	</select>
					                </td>
					                <td>
					                	<select id="isValid${vs.index}" name="isValid${vs.index}" style="width: 99%">
					                		<option value="y">有效</option>
					                		<option value="n" <c:if test="${d.isValid eq 'n'}">selected="selected"</c:if>>无效</option>
					                	</select>
					                </td>
					                <td><input type="text" style="width: 95%" id="orderBy${vs.index}" name="orderBy${vs.index}" value="${d.orderBy }" required digits="true"></td>
					                <td><input type="text" style="width: 98%" id="remark${vs.index}" name="remark${vs.index}" value="${d.remark }"></td>
					                <td><input type="button" value="删除" onclick="deleteRow('${d.resourceid}',this)"/></td>
					            </tr>
					        	</c:forEach>
					        </tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/sysdictionary/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
