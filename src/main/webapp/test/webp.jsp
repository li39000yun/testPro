<%--
  Created by IntelliJ IDEA.
  User: lyq
  Date: 2016/5/17
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="<%=request.getContextPath()%>/js/plugins/webp/WebP.js"></script>
</head>
<body>
<% if (request.getParameter("webp") != null) {
%>
显示我的webp图片
<img src="<%=request.getParameter("webp") %>" title="这是一副WebP图片！">
<%
} else {
%>
显示webp图片
<img src="1.webp" title="这是一副WebP图片！">
<%
    } %>
</body>
</html>
