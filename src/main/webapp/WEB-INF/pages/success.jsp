<%--
  Created by IntelliJ IDEA.
  User: liyag
  Date: 2020/2/23
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<span id="address"></span>
<script type="text/javascript">
    var url = document.location;
    document.getElementById("address").innerHTML=url;
</script>
<br>
<h3>success页面</h3>
</body>
</html>
