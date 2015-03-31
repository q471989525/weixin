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
<title>My Mobile Page V3</title>

<!-- Main CSS file -->
<link rel="stylesheet" href="common/css/wx/style.css" type="text/css" media="screen" />

<!-- jQuery file -->
<script src="common/jscript/wx/jquery.min.js"></script>
<!-- FlexSlider -->
<script src="common/jscript/wx/jquery.flexslider.js"></script>
<!-- Main effects files -->
<script src="common/jscript/wx/effects.js"></script>
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
            <a href="index.html" class="back_button black_button">Home</a>
            <div class="page_title">Services</div>
            <a href="#" id="menu_open" class="black_button">Menu</a>
            <a href="#" id="menu_close" class="black_button">Close</a>
            <div class="clear"></div>
        </div>
        
    	<div id="pages_nav">
            <div class="icons_nav">
                <ul class="slides">
                    <li>
                        <a href="about.html" class="icon"><img src="common/images/wx/icons/icon_about.png" alt="" title="" border="0" /><span>About</span></a>
                        <a href="services.html" class="icon"><img src="common/images/wx/icons/icon_services.png" alt="" title="" border="0" /><span>Services</span></a>
                        <a href="blog.html" class="icon"><img src="common/images/wx/icons/icon_blog.png" alt="" title="" border="0" /><span>Blog</span></a>
                        <a href="portfolio.html" class="icon"><img src="common/images/wx/icons/icon_portfolio.png" alt="" title="" border="0" /><span>Portfolio</span></a>
                    </li>
                    <li>
                        <a href="photos.html" class="icon"><img src="common/images/wx/icons/icon_photos.png" alt="" title="" border="0" /><span>Photos</span></a>
                        <a href="videos.html" class="icon"><img src="common/images/wx/icons/icon_video.png" alt="" title="" border="0" /><span>Videos</span></a>
                        <a href="clients.html" class="icon"><img src="common/images/wx/icons/icon_clients.png" alt="" title="" border="0" /><span>Clients</span></a>
                        <a href="contact.html" class="icon"><img src="common/images/wx/icons/icon_contact.png" alt="" title="" border="0" /><span>Contact</span></a>
                    </li>
                </ul>
          </div>
      </div>
        
        
   	  <div class="content">
        <h1>About your services</h1>
        <span class="subtitle_descr">Consectetur adipisicing elit, sed do eiusmod tempor incididunt</span>
        
        <div class="post">
            <div class="post_thumb">
            <img src="common/images/wx/pic2.jpg" alt="" title="" border="0" class="rounded-half" />
            <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
            </div>
            <div class="post_content">
            <h3><a href="blog-single.html">Lorem ipsum dolor sit amet</a></h3>
            <p>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt
			</p>
            </div>
        </div>
        
        <div class="post">
            <div class="post_thumb">
            <img src="common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded-half" />
            <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
            </div>
            <div class="post_content">
            <h3><a href="blog-single.html">Sed do eiusmod tempor incididunt ut</a></h3>
            <p>
        Sed do eiusmod tempor incididunt ut adipisicing elit, sed do eiusmod tempor incididunt
			</p>
            </div>
        </div>
        
        <div class="post">
            <div class="post_thumb">
            <img src="common/images/wx/pic3.jpg" alt="" title="" border="0" class="rounded-half" />
            <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
            </div>
            <div class="post_content">
            <h3><a href="blog-single.html">Consectetur adipisicing elit</a></h3>
            <p>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt
			</p>
            </div>
        </div>
        
        
        <h2>Best <span class="tag">Solutions</span></h2>
        
        <ul class="lists">
        <li>Consectetur adipisicing elit sed</li>
        <li>Sed do eiusmod tempor incididunt ut labore</li>
        <li>Ut enim ad minim veniam</li>
        </ul>
        
        <div class="clear"></div>
		</div>
        
        
    	<div id="footer" class="black_gradient">
            <a href="index.html" class="back_button black_button">Home</a>
            <div class="page_title">My Mobile Page V3</div>
            <a onClick="jQuery('html, body').animate( { scrollTop: 0 }, 'slow' );"  href="javascript:void(0);" id="top" class="black_button">Top</a>
            <div class="clear"></div>
        </div>
        
        
</div>
   
</body>
</html>