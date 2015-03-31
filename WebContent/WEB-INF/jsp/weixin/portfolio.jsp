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
<script type="text/javascript">
	$(window).load(function() {
		$('.images_slider').flexslider({
		animation: "slide",
		directionNav: false,
		controlNav: true,
		animationLoop: true,
		animationDuration: 300, 
		slideshow: false
		});
	});
</script>
<!-- Main effects files -->
<script src="common/jscript/wx/effects.js"></script>
<!-- PrettyPhoto -->
<script src="common/jscript/wx/jquery.prettyPhoto.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="common/css/wx/prettyphoto/prettyPhoto.css" type="text/css" media="screen" title="prettyPhoto main stylesheet" charset="utf-8" />
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
            <div class="page_title">Portfolio</div>
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
        <h1>Portfolio touch slideshow</h1>
        <span class="subtitle_descr">Consectetur adipisicing elit, sed do eiusmod tempor incididunt</span>
        
        <div class="images_slider_container">
            <div class="images_slider">
                <ul class="slides">
                    <li>
                        <img src="common/images/wx/pic.jpg" alt="" title="" border="0" />
                    </li>
                    <li>
                        <img src="common/images/wx/pic2.jpg" alt="" title="" border="0" />
                    </li>
                    <li>
                        <img src="common/images/wx/pic3.jpg" alt="" title="" border="0" />
                    </li>
                </ul>
           </div>
        </div>
    	<p class="main_text">
        Lorem ipsum dolor sit amet, consectetur <a href="#">adipisicing elit</a>, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
        </p>
        
        <h2>Portfolio with prettyPhoto</h2>
        <ul class="porfolio">
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li class="right">
        <span>Portfolio title</span>
        <a href="common/images/wx/pic2.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic2.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic4.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic4.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li class="right">
        <span>Portfolio title</span>
        <a href="common/images/wx/pic3.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic3.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        </ul>
        
        <h2>Portfolio 3 on row</h2>
        <ul class="porfolio-third">
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic2.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic2.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li class="right">
        <span>Portfolio title</span>
        <a href="common/images/wx/pic3.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic3.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic2.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic2.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li>
        <span>Portfolio title</span>
        <a href="common/images/wx/pic4.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic4.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
        <li class="right">
        <span>Portfolio title</span>
        <a href="common/images/wx/pic.jpg" rel="prettyPhoto[gallery]"><img src="common/images/wx/pic.jpg" alt="" title="" border="0" class="rounded-half" /></a>
        <img src="common/images/wx/shadow.jpg" alt="" title="" border="0" class="shadow" />
        </li>
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