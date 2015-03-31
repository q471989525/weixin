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
<!-- jQuery Gallery -->
<script type="text/javascript" src="common/jscript/wx/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="common/jscript/wx/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="common/jscript/wx/jquery.elastislide.js"></script>
<script type="text/javascript" src="common/jscript/wx/gallery.js"></script>
<noscript>
	<style>
		.es-carousel ul{
			display:block;
		}
	</style>
</noscript>
<script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">	
	<div class="rg-image-wrapper">
		{{if itemsCount > 1}}
			<div class="rg-image-nav">
				<a href="#" class="rg-image-nav-prev">Previous Image</a>
				<a href="#" class="rg-image-nav-next">Next Image</a>
			</div>
		{{/if}}
		<div class="rg-image"></div>
		<div class="rg-loading"></div>
	</div>
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
            <a href="index.html" class="back_button black_button">Home</a>
            <div class="page_title">Photo Gallery</div>
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
        <h1>Photo Gallery</h1>
        
				<div id="rg-gallery" class="rg-gallery">
					<div class="rg-thumbs">
						<!-- Elastislide Carousel Thumbnail Viewer -->
						<div class="es-carousel-wrapper">
							<div class="es-nav">
								<span class="es-nav-prev">Previous</span>
								<span class="es-nav-next">Next</span>
							</div>
							<div class="es-carousel">
								<ul>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/1.jpg" data-large="common/images/wx/gallery/1.jpg" alt="image01"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/2.jpg" data-large="common/images/wx/gallery/2.jpg" alt="image02"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/3.jpg" data-large="common/images/wx/gallery/3.jpg" alt="image03"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/4.jpg" data-large="common/images/wx/gallery/4.jpg" alt="image04"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/5.jpg" data-large="common/images/wx/gallery/5.jpg" alt="image05"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/1.jpg" data-large="common/images/wx/gallery/1.jpg" alt="image06"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/2.jpg" data-large="common/images/wx/gallery/2.jpg" alt="image07" /></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/3.jpg" data-large="common/images/wx/gallery/3.jpg" alt="image08"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/4.jpg" data-large="common/images/wx/gallery/4.jpg" alt="image09"/></a></li>
									<li><a href="#"><img src="common/images/wx/gallery/thumbs/5.jpg" data-large="common/images/wx/gallery/5.jpg" alt="image10"/></a></li>
								</ul>
							</div>
						</div>
						<!-- End Elastislide Carousel Thumbnail Viewer -->
					</div><!-- rg-thumbs -->
				</div><!-- rg-gallery -->
                
            <h2>Gallery <span class="tag">info</span></h2>   
            <p class="main_text">
             Lorem ipsum dolor sit amet, <a href="#">consectetur adipisicing</a> elit, sed do eiusmod tempor incididunt. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua <br /><br />
             Sed do eiusmod tempor incididunt ut labore et <a href="#">dolore magna</a> aliqua
			</p>
                 
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