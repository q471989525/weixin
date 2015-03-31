<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html;charset=UTF-8");
    String path = request.getContextPath();
    request.setCharacterEncoding("utf-8");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">

        <title>列表</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width; initial-scale=1; maximum-scale=1" />
        <meta name="author" content="FamousThemes" />
        <meta name="description" content="My Mobile Page Version 3 Template" />
        <meta name="keywords" content="mobile templates, mobile wordpress themes, mobile themes, my mobile page, premium css templates, premium wordpress themes" />

        <!-- Main CSS file -->
        <link rel="stylesheet" href="common/css/wx/style_wdd.css" type="text/css" media="screen" />
        <script type="text/javascript">

            var json = [{name: 1, price: 265.00, msg: '苹果'}, {name: 2, price: 265.00, msg: '雪梨'}, {name: 3, price: 265.00, msg: '樱桃'}, {name: 4, price: 265.00, msg: '葡萄'}, {name: 5, price: 265.00, msg: '香蕉'}, {name: 6, price: 265.00, msg: '橘子'}]

            window.addEventListener("load", function() {
                // Set a timeout...
                setTimeout(function() {
                    // Hide the address bar!
                    window.scrollTo(0, 1);
                }, 0);
            });

            function init() {

            }

            function fun() {
                alert(11);
            }
        </script>
        <style type="text/css">
            .search_input{ 
                border-radius:10px;
                padding-right:20px; 
                width:40%;
                height:25px;
                margin-left: 10%;
                margin-top:3%
            }

            .search_img{
                background:url("common/images/wx/search.gif") no-repeat scroll right center transparent; 
            }
        </style>
    </head>
    <body id="page" style="background: #EBEBEB;">
        <div style='position:fixed; z-index:999; top:0;width: 100%;background: #EBEBEB;'>
            <table style="width: 100%">
                <tr>
                    <td style="width: 83%"><input  class="search_input" type="text"/></td>
                    <td style="width: 17%;margin-left:20px">
                        <img class="search_img" style="width: 27px;height: 27px"
                             onclick="fun()"
                             />
                    </td>
                </tr>
            </table>


        </div>
        <div id="pagecontainer" style="background: #EBEBEB;">

            <div id="pages_nav" style="display: none ">
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

            <div class="content" style="background: #EBEBEB;">
                <c:forEach items="${requestScope.list}" var="product" varStatus="i">
                    
                    <div style="width: 100%">
                        <a href="#${product.id}">
                        <div class="<c:if test="${(i.index+1)%2!=0}">post_left</c:if><c:if test="${(i.index+1)%2==0}">post_right</c:if>">
                            <div class="post_thumb">
                                <img src="${product.image_url}" alt="" title="" border="0" class="rounded-half" />
                            </div>
                            <div class="post_content">
                                <div style="font-size: 16px; height: 32px; overflow: hidden;"><span style="color: black;">${product.name}</span></div>
                                <p style="color: #FF8C00;font-size: 14px;margin-top: 10px">
                                    ￥<font>${product.price}</font>
                                </p>
                            </div>
                        </div>
                                </a>
                    </c:forEach>

                    <div class="clear"></div>
                </div>

            </div>

    </body>
</html>