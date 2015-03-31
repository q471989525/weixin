<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE HTML>
<html>
  <head>
    
    <title>编辑</title>
  	<%@ include file="/common/jscript/include_edit.jsp" %>
  	<script type="text/javascript">
	  	$(document).ready(function(){
	  		//表单验证
	  		$("#customerForm").validate({
				rules: {
					customerName: {required:true,maxlength:80},
					provinceName: "required",
					cityName: "required",
					customerNature: "required",
					customerSource: "required",
					customerType: "required",
					customerIndustry: "required",
					trustStatus: "required",
					contactStrategy: "required",
					industryStatus: "required",
					clearingForm: "required",
					telphone: {maxlength:30},
					fax: {maxlength:30},
					email: {maxlength:30},
					www: {maxlength:30},
					postCode: {maxlength:20},
					address: {required:true,maxlength:80},
					remark: {maxlength:800}
				}
			})
	  		
	  		//ajax提交
	  	    $("#customerForm").ajaxForm({
	  	        dataType:"json", 
	  	        success:function (data){
	            	if(data.state == "success"){
		                $.messager.alert("提示", "保存成功", "info",function(){
		                	try {
				            	opener.searchCustomerInfo();
							} catch (e) { }
			   	    		window.close();
		   	        	});
	            	}else{
		                $.messager.alert("错误", "保存失败", "error");  
	            	}
	  	        } 
	  	    });
	  		
	  	});
	  	
	  	function submitForm(){
	  		$("#customerForm").submit();
	  	}
	  	
    </script>
  </head>
  
  <body class="easyui-layout">
  	<div data-options="region:'center'">
  		<div class="easyui-tabs" data-options="fit:true,border:false">
			<div title="客户信息">
			  	<form id="customerForm" action="${pageContext.request.contextPath}/customerinfo/save.do" method="post">
			  		<input type="hidden" id="resourceid" name="resourceid" value="${customer.resourceid }">
			  		<input type="hidden" id="createId" name="createId" value="${customer.createId }">
			  		<input type="hidden" id="creator" name="creator" value="${customer.creator }">
			  		<input type="hidden" id="createTime" name="createTime" value="<fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<table style="width:100%;" border="0" cellspacing="0" cellpadding="0" class="formtable">
					        <tbody>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">客户名称：</td>
					                <td colspan="3"><input type="text" id="customerName" name="customerName" value="${customer.customerName }" style="width: 80%"></td>
					            </tr>
					            <tr>  
					                <td class="tdTitle" style="width: 15%;">所属区域：</td>
					                <td style="width: 30%;">
					                	<app:select name="provinceName" dictionaryCode="D_Province" value="${customer.provinceName}" onchange="getCityByPId(this.value, \"cityName\")" style="width: 80px;"/>
					                	<%-- 
					                	<select id="provinceName" name="provinceName" onchange="">
					                		<option value="">请选择...</option>
					                		<c:forEach var="p" items="${provinceList }">
						                		<option value="${p.resourceid }" <c:if test="${customer.provinceName eq p.resourceid}">selected="selected"</c:if>>${p.regionName }</option>
					                		</c:forEach>
					                	</select>
					                	--%>
					                	&nbsp;
					                	<app:select name="cityName" dictionaryCode="D_City" pvalue="${customer.provinceName}" value="${customer.cityName}" style="width: 100px;"/>
					                	<%-- 
					                	<select id="cityName" name="cityName">
					                		<option value="">请选择...</option>
					                		<c:forEach var="c" items="${cityList }">
						                		<option value="${c.resourceid }" <c:if test="${customer.cityName eq c.resourceid}">selected="selected"</c:if>>${c.regionName }</option>
					                		</c:forEach>
					                	</select>
					                	--%>
					                </td>
					                <td class="tdTitle" style="width: 15%;">客户性质：</td>
					                <td>
					                	<app:select name="customerNature" dictionaryCode="D_Customer_Nature" value="${customer.customerNature}"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">客户来源：</td>
					                <td>
					                	<app:select name="customerSource" dictionaryCode="D_Customer_Source" value="${customer.customerSource}"/>
					                </td>
					                <td class="tdTitle">客户类别：</td>
					                <td>
					                	<app:select name="customerType" dictionaryCode="D_Customer_Type" value="${customer.customerType}"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">所属行业：</td>
					                <td>
					                	<app:select name="customerIndustry" dictionaryCode="D_Customer_Industry" value="${customer.customerIndustry}"/>
					                </td>
					                <td class="tdTitle">信用状况：</td>
					                <td>
					                	<app:select name="trustStatus" dictionaryCode="D_Trust_Status" value="${customer.trustStatus}"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">联系策略：</td>
					                <td>
					                	<app:select name="contactStrategy" dictionaryCode="D_Contact_Strategy" value="${customer.contactStrategy}"/>
					                </td>
					                <td class="tdTitle">行业地位：</td>
					                <td>
					                	<app:select name="industryStatus" dictionaryCode="D_Industry_Status" value="${customer.industryStatus}"/>
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">结算方式：</td>
					                <td>
					                	<app:select name="clearingForm" dictionaryCode="D_Clearing_Form" value="${customer.clearingForm}"/>
					                </td>
					                <td class="tdTitle">单位网址：</td>
					                <td>
					                	<input type="text" id="www" name="www" value="${customer.www }">
					                </td>
					            </tr>
					            <tr>
					                <td class="tdTitle">电话：</td>
					                <td>
					                	<input type="text" id="telphone" name="telphone" value="${customer.telphone }">
					                </td>
					            	<td class="tdTitle">传真：</td>
					                <td>
					                	<input type="text" id="fax" name="fax" value="${customer.fax }">
					                </td>
					            </tr>  
					            <tr>
					                <td class="tdTitle">电子邮箱：</td>
					                <td>
					                	<input type="text" id="email" name="email" value="${customer.email }">
					                </td>
					                <td class="tdTitle">邮政编码：</td>
					                <td>
					                	<input type="text" id="postCode" name="postCode" value="${customer.postCode }">
					                </td>
					            </tr> 
					            <tr>  
					                <td class="tdTitle">通讯地址：</td>
					                <td colspan="3">
					                	<input type="text" id="address" name="address" value="${customer.address }" style="width: 80%">
					                </td>
					            </tr>
					            <tr>  
					                <td class="tdTitle">备注：</td>
					                <td colspan="3">
					                	<textarea rows="5" style="width: 80%" id="remark" name="remark">${customer.remark}</textarea>
					                </td>
					            </tr>
					        </tbody>
					</table>
				</form>
			</div>
			<c:if test="${not empty contacts}">
			<div title="联系人信息">
				<table class="easyui-datagrid" data-options="fit:true">
					<thead data-options="frozen:true"> 
						 <tr>
							<th data-options="field:'userName',width:120">姓名</th>
				         </tr>
					</thead>
					<thead>
						<tr>
							<th data-options="field:'userSex',width:60">性别</th>
							<th data-options="field:'userDept',width:120">部门</th>
							<th data-options="field:'userPost',width:120">职称</th>
							<th data-options="field:'telphone',width:120">电话</th>
							<th data-options="field:'mobile',width:120">手机</th>
							<th data-options="field:'fax',width:120">传真</th>
							<th data-options="field:'email',width:150">电子邮箱</th>
							<th data-options="field:'qq',width:120">QQ</th>
							<th data-options="field:'remark',width:300">备注</th>
							<%-- <th data-options="field:'creator',width:100">创建人</th> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${contacts }" varStatus="vs">
							<tr>
								<td>${c.userName }</td>
								<td>${app:fmtDictText("D_Sex",c.userSex) } </td>
								<td>${c.userDept }</td>
								<td>${c.userPost }</td>
								<td>${c.telphone }</td>
								<td>${c.mobile }</td>
								<td>${c.fax }</td>
								<td>${c.email }</td>
								<td>${c.qq }</td>
								<td>${c.remark }</td>
								<%-- <td>${c.creator }</td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
		</div>
	</div>
	
	<div data-options="region:'south'" style="height:30px; padding-top:1px; overflow: hidden; background-color: rgb(244, 244, 244)" align="center">
		<app:btnAuth btnUrl="/customerinfo/save.do">
			<a onclick="submitForm();" data-options="iconCls:'icon-save'" class="easyui-linkbutton">保存</a>
		</app:btnAuth>
		<a onclick="window.close();" data-options="iconCls:'icon-cancel'" class="easyui-linkbutton">关闭</a>
	</div>
  </body>
</html>
