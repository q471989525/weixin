<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://custom/jsp/tag" prefix="app"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1" />
<meta name="author" content="FamousThemes" />
<meta name="description" content="My Mobile Page Version 3 Template" />
<meta name="keywords" content="mobile templates, mobile wordpress themes, mobile themes, my mobile page, premium css templates, premium wordpress themes" />
<title>用户信息</title>

<!-- Main CSS file -->
<link rel="stylesheet" href="../common/css/wx/style.css" type="text/css" media="screen" />

<!-- jQuery file -->
<script src="../common/jscript/wx/jquery.min.js"></script>
<!-- FlexSlider -->
<script src="../common/jscript/wx/jquery.flexslider.js"></script>
<!-- Main effects files -->
<script src="../common/jscript/wx/effects.js"></script>
<!-- PrettyPhoto -->
<script src="../common/jscript/wx/jquery.prettyPhoto.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="../common/css/wx/prettyphoto/prettyPhoto.css" type="text/css" media="screen" title="prettyPhoto main stylesheet" charset="utf-8" />
<!-- jQuery Tabs -->
<script src="../common/jscript/wx/jquery.tabify.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var $ = jQuery.noConflict();
$(function() {
$('#tabsmenu').tabify();
$(".toggle_container").hide(); 
$(".trigger").click(function(){
	$(this).toggleClass("active").next().slideToggle("slow");
	return false;
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
</head>
<body id="page">

	<div id="pagecontainer">
    
    	<div id="header" class="black_gradient">
            <a href="index.html" class="back_button black_button">主页</a>
            <div class="page_title">用户信息</div>
            <a href="#" id="menu_open" class="black_button">菜单</a>
            <a href="#" id="menu_close" class="black_button">收起</a>
            <div class="clear"></div>
        </div>
        
    	<div id="pages_nav">
            <div class="icons_nav">
                <ul class="slides">
                    <li>
                        <a href="about.html" class="icon"><img src="../common/images/wx/icons/icon_about.png" alt="" title="" border="0" /><span>About</span></a>
                        <a href="services.html" class="icon"><img src="../common/images/wx/icons/icon_services.png" alt="" title="" border="0" /><span>Services</span></a>
                        <a href="blog.html" class="icon"><img src="../common/images/wx/icons/icon_blog.png" alt="" title="" border="0" /><span>Blog</span></a>
                        <a href="portfolio.html" class="icon"><img src="../common/images/wx/icons/icon_portfolio.png" alt="" title="" border="0" /><span>Portfolio</span></a>
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
        
        
   	  <div class="content">
        <h1>用户信息</h1>
              <div class="ibx-uc-utool">
                  <div class="ibx-uc-utool-title">用户名：</div>
                  <div  class="ibx-uc-utool-content">${nickname}</div>
                  <div class="ibx-uc-utool-title">城市：</div>
                  <div class="ibx-uc-utool-content">${city}</div>
                  <div class="ibx-uc-utool-title">性别：</div>
                  <div class="ibx-uc-utool-content">${sex}</div>
              </div>

        <%--<span class="subtitle_descr">Consectetur adipisicing elit, sed do eiusmod tempor incididunt</span>--%>
        <%--<a href="../common/images/wx/pic.jpg" rel="prettyPhoto[gallery]"><img src="../common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded" /></a>--%>
        <%--<img src="../common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />--%>
    	<%--<p class="main_text">--%>
        <%--Lorem ipsum dolor sit amet, consectetur <a href="#">adipisicing elit</a>, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.--%>
        <%--</p>--%>
        
        <%--<h2>Some <span class="tag">list</span> styles</h2>--%>
        <%--<ul class="lists">--%>
        <%--<li>Consectetur adipisicing elit sed</li>--%>
        <%--<li>Sed do eiusmod tempor incididunt ut labore</li>--%>
        <%--<li>Ut enim ad minim veniam</li>--%>
        <%--</ul>--%>
        
        <%--<h2>Some <span class="tag">blockquotes</span></h2>--%>
        <%--<blockquote>--%>
         <%--Lorem ipsum <a href="#">dolor sit amet</a>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.--%>
        <%--</blockquote>--%>
        <%--<blockquote>--%>
         <%--Ut enim ad minim veniam, quis nostrud <a href="#">exercitation ullamco</a> laboris nisi ut aliquip ex ea commodo consequat.--%>
        <%--</blockquote>--%>
        
        <%--<h2>Some <span class="tag">toggle</span> styles</h2>--%>
        <%----%>
        <%--<div class="toogle_wrap">--%>
            <%--<div class="trigger"><a href="#">Toggle with text</a></div>--%>

            <%--<div class="toggle_container">--%>
			<%--<p>--%>
        <%--Lorem ipsum <a href="#">dolor sit amet</a>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.--%>
			<%--</p>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="toogle_wrap">--%>
            <%--<div class="trigger"><a href="#">Toggle with lists</a></div>--%>

            <%--<div class="toggle_container">--%>
            <%--<ul class="lists">--%>
            <%--<li>Consectetur adipisicing elit sed</li>--%>
            <%--<li>Sed do eiusmod tempor incididunt</li>--%>
            <%--<li>Ut enim ad minim veniam</li>--%>
            <%--</ul>--%>
            <%--</div>--%>
        <%--</div> --%>



        <%--<h2>Some flexible <span class="tag">tabs</span></h2>--%>
        <%----%>
		<%--<ul id="tabsmenu" class="tabsmenu">--%>
			<%--<li class="active"><a href="#tab1">Tab one</a></li>--%>
			<%--<li><a href="#tab2">Tab two</a></li>--%>
			<%--<li><a href="#tab3">Tab three</a></li>--%>
		<%--</ul>--%>
		<%--<div id="tab1" class="tabcontent">--%>
			<%--<h3>Tab one title</h3>--%>
			<%--<p>--%>
        <%--Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.--%>
			<%--</p>--%>
		<%--</div>--%>
		<%--<div id="tab2" class="tabcontent">--%>
			<%--<h3>Tab two title</h3>--%>
			<%--<ul class="lists">--%>
            <%--<li>Consectetur adipisicing elit sed</li>--%>
            <%--<li>Sed do eiusmod tempor incididunt</li>--%>
            <%--<li>Ut enim ad minim veniam</li>--%>
            <%--</ul>--%>
		<%--</div>--%>

		<%--<div id="tab3" class="tabcontent">--%>
			<%--<h3>Tab three title</h3>--%>
			<%--<p>--%>
        <%--Lorem ipsum <a href="#">dolor sit amet</a>, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.--%>
			<%--</p>--%>
		<%--</div> --%>

        
        <div class="clear"></div>
		</div>
        
        
    	<div id="footer" class="black_gradient">
            <a href="index.html" class="back_button black_button">主页</a>
            <div class="page_title"></div>
            <a onClick="jQuery('html, body').animate( { scrollTop: 0 }, 'slow' );"  href="javascript:void(0);" id="top" class="black_button">回到顶部</a>
            <div class="clear"></div>
        </div>
        
        
</div>
   
</body>
</html>