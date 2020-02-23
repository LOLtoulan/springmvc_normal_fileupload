<%--
  Created by IntelliJ IDEA.
  User: liyag
  Date: 2020/2/23
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>文件上传</h3>

<form action="fileupload/upload" enctype="multipart/form-data" method="post">
文件上传：<input type="file" name="upload"/><br>
<input type="submit" value="上传"/>
</form>
<hr>
<h3>SpringMVC文件上传</h3>
<form action="fileupload/mvcupload" enctype="multipart/form-data" method="post">
    文件上传：<input type="file" name="upload"/><br>
    <input type="submit" value="上传"/>
</form>
<hr>
<hr>
<h3>SpringMVC跨服务器文件上传</h3>
<form action="fileupload/springmvcupload" enctype="multipart/form-data" method="post">
    文件上传：<input type="file" name="upload"/><br>
    <input type="submit" value="上传"/>
</form>
</body>
</html>
