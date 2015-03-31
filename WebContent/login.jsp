<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>app3</title>
  		 
		<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/common/login/screen.css">
  		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/common/easyui1.4/themes/default/easyui.css">
  		
  		<script type="text/javascript" src="${pageContext.request.contextPath}/common/jscript/jquery/jquery-1.8.3.min.js"></script>
  		<script type="text/javascript" src="${pageContext.request.contextPath}/common/easyui1.4/jquery.easyui.min.js"></script>
  		<script type="text/javascript" src="${pageContext.request.contextPath}/common/validation1.13/jquery.validate.min.js"></script> 
		
		<script type="text/javascript">
			function reloadKaptcha(){ //重新生成验证码
				var url = "${pageContext.request.contextPath}/j_captcha.do?" + Math.floor(Math.random()*100);
				jQuery("#kaptchaImage").attr("src", url); 
			}
			
			$(function() {
				$("#j_username").focus();
				
				// highlight 
				var elements = $("input[type!='submit'], textarea, select");
				elements.focus(function(){
					$(this).parents('li').addClass('highlight');
				});
				elements.blur(function(){
					$(this).parents('li').removeClass('highlight');
				});
				
				//验证
				$("#loginForm").validate({
					rules: {
						j_username: "required",
						j_password: {required:true,minlength:4},
						j_kaptcha: {
							required: true,
							digits:true,
							rangelength:[4,4]
						}
					},
					messages: {
						j_username: "账号不能为空！",
						j_password: {required:"密码不能为空！",minlength:"长度至少4位数！"},
						j_kaptcha: {required:"验证码不能为空！",digits:"验证码只能为数字！",rangelength:"长度只能为4位！"}
					},
					submitHandler:function() {
						$.messager.progress({ title:'Please waiting', text:'Login...' });
						document.getElementById("loginForm").submit();
					}
				})
				
			});
		</script>
	</head>

	<body style="overflow: hidden;">
		
		<div id="page">
			<div id="header" style="width: 600px;">
				<%-- <h1>OA办公平台</h1> 
				<h1>CRM客户关系管理平台</h1>--%>
			</div>
			
			<div id="content" >
				<p id="status"></p>
				<form id="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
					<fieldset>
						<legend>登陆</legend>
						<ul>
							<li>
								<label for="username"><span class="required">账&nbsp;&nbsp;&nbsp;号：</span></label>
								<input id="j_username" name="j_username" class="text" type="text" style="padding-left: 5px;">
							</li>
							
							<li>
								<label for="j_password"><span class="required">密&nbsp;&nbsp;&nbsp;码：</span></label>
								<input id="j_password" name="j_password" type="password" class="text" style="padding-left: 5px;"/>
							</li>
	
							<li>
								<label for="j_kaptcha"><span class="required">验证码：</span></label>
								<input id="j_kaptcha" name="j_kaptcha" type="text" class="text" style="padding-left: 5px;"/>
							</li>
	
							<li>
								<label class="centered info">
									<img src="${pageContext.request.contextPath}/j_captcha.do" style="cursor: pointer;width: 75px;height: 24px;" id="kaptchaImage" title="重新获取" onclick="reloadKaptcha()"/>
									<span style="font-size: 11px; cursor: pointer;" onclick="reloadKaptcha()">看不清</span>
								</label>
							</li>
							<li>
								<label class="centered info"><font color="red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</font></label>
							</li>
	
						</ul>
					</fieldset>
					<center>
						<input type="submit" value="登&nbsp;录" class="button">&nbsp;
						<input type="reset" value="重&nbsp;置" class="button"/>
					</center>
					<div class="clear"></div>
				</form>
			</div>
			
		</div>
	</body>
</html>
