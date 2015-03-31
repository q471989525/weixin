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
        <title></title>

        <!-- Main CSS file -->
        <link rel="stylesheet" href="../common/css/wx/style.css" type="text/css" media="screen" />
        <link rel="stylesheet" href="../common/css/wx/orderList.css" type="text/css" media="screen" />
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
                $(".trigger").click(function() {
                    $(this).toggleClass("active").next().slideToggle("slow");
                    return false;
                });
            });
          
        </script>
        <!-- Hide Mobiles Browser Navigation Bar -->
        <script type="text/javascript">
            window.addEventListener("load", function() {
                // Set a timeout...
                setTimeout(function() {
                    // Hide the address bar!
                    window.scrollTo(0, 1);
                }, 0);
            });
        </script>
    </head>
    <body id="page">



        <div id="pagecontainer">

            <!--            <div id="header" class="black_gradient">
                            <a href="index.html" class="back_button black_button">主页</a>
                            <div class="page_title">${sessionScope.wxin_user.nickname}的订单</div>
                            <a href="#" id="menu_open" class="black_button">菜单</a>
                            <a href="#" id="menu_close" class="black_button">收回</a>
                            <div class="clear"></div>
                        </div>-->

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
                //TODO 待完成


            </div>


<!--            <div id="footer" class="black_gradient">
                <a href="index.html" class="back_button black_button">Home</a>
                <div class="page_title"></div>
                <a onClick="jQuery('html, body').animate({scrollTop: 0}, 'slow');"  href="javascript:void(0);" id="top" class="black_button">Top</a>
                <div class="clear"></div>
            </div>-->


        </div>


    </body>
</html>