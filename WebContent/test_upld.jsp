<%-- 
    Document   : test_upld
    Created on : 2014-12-23, 19:40:52
    Author     : fuq
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <h2>手机拍照上传演示</h2>
        <h3>pai/upload.do</h3>
        <form action="pai/upload.do" id="imageForm" enctype="multipart/form-data" method="post">
            <input id="fileInput" name="file" type="file" style="width: 200px;"/><br/>
            地点： <input name="location" type="text" value="广州xxxx地点" style="width: 200px;"/><br/>
            imei：<input name="imei" type="text" value="4635283434323443" style="width: 200px;"/><br/>
            latitude： <input name="latitude" type="text" value="23.84745" style="width: 200px;"/><br/>
            longitude：<input name="longitude" type="text" value="108.23434" style="width: 200px;"/><br/>
            邮编：<input name="postcode" type="text" value="010001" style="width: 200px;"/><br/>
            城市编码：<input name="citycode" type="text" value="23" style="width: 200px;"/><br/>
            描述：<input name="remark" type="text" value="发生严重的BUG" style="width: 200px;"/><br/>

            <input type="submit" value="上传" >
        </form>
    </body>
</html>
