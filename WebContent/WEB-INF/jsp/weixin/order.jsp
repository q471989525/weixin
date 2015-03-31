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
<title>下订单</title>

<!-- Main CSS file -->
<link rel="stylesheet" href="../common/css/wx/style.css" type="text/css" media="screen" />

<!-- jQuery file -->
<script src="../common/jscript/wx/jquery.min.js"></script>
<!-- FlexSlider -->
<script src="../common/jscript/wx/jquery.flexslider.js"></script>
<!-- Main effects files -->
<script src="../common/jscript/wx/effects.js"></script>
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
            <div class="page_title">下订单</div>
            <a href="#" id="menu_open" class="black_button">菜单</a>
            <a href="#" id="menu_close" class="black_button">收回</a>
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

        <form action="${pageContext.request.contextPath}/wx/ordersubmit.do">
   	  <div class="content">
        <h1>请填写订单信息</h1>
          <input type="hidden" name="id" value="value">
          <input type="hidden" name="product_id" value="value">
          <input type="hidden" name="user_id" value="value">
          <input type="hidden" name="createtime" value="value">



          <h2>收货地址：</h2>
        <div class="search">
            <input type="text" name="address" class="search_input" value="" onClick="this.value=''" />
            <%--<input type="image" src="../common/images/wx/search.gif" class="search_submit" />--%>
            <div class="clear"></div>
        </div>

          <h2>联系人手机：</h2>
          <div class="search">
              <input type="text" name="phone" class="search_input" value="" onClick="this.value=''" />
              <%--<input type="image" src="../common/images/wx/search.gif" class="search_submit" />--%>
              <div class="clear"></div>
          </div>

          <h2>收货时间：</h2>
          <div class="search">
              <input type="text" name="deliverytime" class="search_input" value="" onClick="this.value=''" />
              <%--<input type="image" src="../common/images/wx/search.gif" class="search_submit" />--%>
              <div class="clear"></div>
          </div>
          <h2>数量：</h2>
          <div class="search">
              <input type="text" name="number" class="search_input" value="" onClick="this.value=''" />
              <%--<input type="image" src="../common/images/wx/search.gif" class="search_submit" />--%>
              <div class="clear"></div>
          </div>
          <h2>价格：</h2>
          <div class="search">
              <input type="text" name="price" readonly="true" class="search_input" value="" onClick="this.value=''" />
              <%--<input type="image" src="../common/images/wx/search.gif" class="search_submit" />--%>
              <div class="clear"></div>
          </div>


        
        <%--<div class="toogle_wrap">--%>
            <%--<div class="trigger"><a href="#">Browse Categories</a></div>--%>

            <%--<div class="toggle_container">--%>
                <%--<ul class="lists">--%>
                <%--<li><a href="#">Consectetur adipisicing</a></li>--%>
                <%--<li><a href="#">Sed do eiusmod incididunt</a></li>--%>
                <%--<li><a href="#">Ut enim ad veniam</a></li>--%>
                <%--<li><a href="#">Consectetur eiusmod</a></li>--%>
                <%--<li><a href="#">Sed do incididunt</a></li>--%>
                <%--</ul>--%>
            <%--</div>--%>
        <%--</div> --%>
        
        
        <%--<h2>Latest Posts</h2>--%>
        <%----%>
        <%--<div class="post">--%>
            <%--<div class="post_thumb">--%>
            <%--<img src="../common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded-half" />--%>
            <%--<img src="../common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />--%>
            <%--</div>--%>
            <%--<div class="post_content">--%>
            <%--<h3><a href="blog-single.html">Lorem ipsum dolor sit amet</a></h3>--%>
            <%--<p>--%>
        <%--Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt--%>
			<%--</p>--%>
            <%--</div>--%>
            <%--<div class="clear"></div>--%>
            <%--<span class="post_detail date">09.12</span>--%>
            <%--<span class="post_detail category"><a href="#">category</a></span>--%>
            <%--<span class="post_detail comments"><a href="#">12</a></span>--%>
            <%----%>
        <%--</div>--%>
        
        
        <%--<div class="post">--%>
            <%--<div class="post_thumb">--%>
            <%--<img src="../common/images/wx/pic2.jpg" alt="" title="" border="0" class="rounded-half" />--%>
            <%--<img src="../common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />--%>
            <%--</div>--%>
            <%--<div class="post_content">--%>
            <%--<h3><a href="blog-single.html">Sed do eiusmod tempor incididunt</a></h3>--%>
            <%--<p>--%>
        <%--Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor--%>
			<%--</p>--%>
            <%--</div>--%>
            <%--<div class="clear"></div>--%>
            <%--<span class="post_detail date">10.12</span>--%>
            <%--<span class="post_detail category"><a href="#">category</a></span>--%>
            <%--<span class="post_detail comments"><a href="#">07</a></span>--%>
        <%--</div>--%>
        
        <%--<div class="post">--%>
            <%--<div class="post_thumb">--%>
            <%--<img src="../common/images/wx/pic3.jpg" alt="" title="" border="0" class="rounded-half" />--%>
            <%--<img src="../common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />--%>
            <%--</div>--%>
            <%--<div class="post_content">--%>
            <%--<h3><a href="blog-single.html">Consectetur adipisicing elit</a></h3>--%>
            <%--<p>--%>
        <%--Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore--%>
			<%--</p>--%>
            <%--</div>--%>
            <%--<div class="clear"></div>--%>
            <%--<span class="post_detail date">10.12</span>--%>
            <%--<span class="post_detail category"><a href="#">category</a></span>--%>
            <%--<span class="post_detail comments"><a href="#">07</a></span>--%>
        <%--</div>--%>
        
        <div class="blog_nav">
        <%--<a href="#" class="prev">prev posts</a>--%>
        <%--<a href="#" class="next">提交订单</a>--%>
            <input id="submit" type="submit" class="next" value="提交订单" />
        </div>

        <div class="clear"></div>
        

		</div>
        </form>
        
        
    	<div id="footer" class="black_gradient">
            <a href="index.html" class="back_button black_button">主页</a>
            <div class="page_title"></div>
            <a onClick="jQuery('html, body').animate( { scrollTop: 0 }, 'slow' );"  href="javascript:void(0);" id="top" class="black_button">回到顶部</a>
            <div class="clear"></div>
        </div>
        
        
</div>
    

</body>
</html>