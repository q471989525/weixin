<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="apple-touch-icon" sizes="114x114" href="../common/images/wx/apple-touch-icon.png" />
<link rel="apple-touch-startup-image" href="../common/images/wx/apple-touch-startup-image.png" />
<meta name="author" content="FamousThemes" />
<meta name="description" content="My Mobile Page Version 3 Template" />
<meta name="keywords" content="mobile templates, mobile wordpress themes, mobile themes, my mobile page, premium css templates, premium wordpress themes" />
<title>主页</title>

<!-- Main CSS file -->
<link rel="stylesheet" href="../common/css/wx/style.css" type="text/css" media="screen" />

<!-- jQuery file -->
<script src="../common/jscript/wx/jquery.min.js"></script>
<!-- FlexSlider -->
<script src="../common/jscript/wx/jquery.flexslider.js"></script>
<script type="text/javascript">
var $ = jQuery.noConflict();
$(window).load(function() {
	$('.icons_nav').flexslider({
	animation: "slide",
	directionNav: false,
	animationLoop: false,
	controlNav: false, 
	slideshow: false,
	animationDuration: 300
	});
	$('.panels_slider').flexslider({
	animation: "slide",
	directionNav: false,
	controlNav: true, 
	animationLoop: false,
	slideToStart: 1,
	animationDuration: 300, 
	slideshow: false
	});
});
</script>
<!-- Hide Mobiles Browser Navigation Bar -->
<script type="text/javascript">
	window.addEventListener("load",function() {
	// Set a timeout...
	setTimeout(function(){
	// Hide the address bar!
	window.scrollTo(0, 1);
	}, 0);
	});
</script>
<!-- Hide on iphone top browser element | only on home page -->
<script type="text/javascript">
if((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i))) {
$(window).load(function() {
   $("body").removeClass("home");
   $("body").addClass("homeiphone");
});
}
</script>
</head>
<body class="home">
<div id="container">

	<div class="logo">水果展示</div>

    <div id="main_panels">
        <div class="panels_slider">
        <ul class="slides">
            <li>
                <img src="../common/images/wx/panel_2.jpg" alt="" title="" border="0" />
            </li>
            <li>
                <img src="../common/images/wx/panel_start.jpg" alt="" title="" border="0" />
            </li>
            <li>
                <img src="../common/images/wx/panel_1.jpg" alt="" title="" border="0" />
            </li>
        </ul>
        </div>
    </div>

    <div id="bottom_nav">
        <div class="icons_nav">
        <ul class="slides">
            <li>
                <a href="${pageContext.request.contextPath}/user/user.do" class="icon"><img src="../common/images/wx/icons/icon_about.png" alt="" title="" border="0" /><span>我</span></a>
                <a href="services.html" class="icon"><img src="../common/images/wx/icons/icon_services.png" alt="" title="" border="0" /><span>订单管理</span></a>
                <a href="blog.html" class="icon"><img src="../common/images/wx/icons/icon_blog.png" alt="" title="" border="0" /><span>产品</span></a>
                <a href="portfolio.html" class="icon"><img src="../common/images/wx/icons/icon_portfolio.png" alt="" title="" border="0" /><span>联系方式</span></a>
            </li>
            <li>
                <a href="photos.html" class="icon"><img src="../common/images/wx/icons/icon_photos.png" alt="" title="" border="0" /><span>Photos</span></a>
                <a href="videos.html" class="icon"><img src="../common/images/wx/icons/icon_video.png" alt="" title="" border="0" /><span>Videos</span></a>
                <a href="clients.html" class="icon"><img src="../common/images/wx/icons/icon_clients.png" alt="" title="" border="0" /><span>Clients</span></a>
                <a href="contact.html" class="icon"><img src="../common/images/wx/icons/icon_contact.png" alt="" title="" border="0" /><span>Contact</span></a>
            </li>
        </ul>
        </div>
    </div>
    
</div>
</body>
</html>