<%@ page import="java.io.File" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.IOException" %><%--
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
</head>
<body>
<%
    File file1 = new File("C:\\Users\\lyq\\Desktop\\1.webp");
    File file2 = new File("C:\\Users\\lyq\\Desktop\\1.png");
    BufferedImage im = ImageIO.read(file1);
    ImageIO.write(im, "png", file2);
%>
</body>
</html>
