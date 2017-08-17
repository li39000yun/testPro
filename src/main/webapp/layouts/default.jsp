<%--
  Created by IntelliJ IDEA.
  User: 云强
  Date: 2017/3/10
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <title>QuickStart示例:<sitemesh:title/></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
    <link href="${ctx}/sc/bootstrap/2.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/sc/jquery-validation/1.11.0/validate.css" type="text/css" rel="stylesheet" />
    <link href="${ctx}/css/base/default.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/sc/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/sc/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/sc/jquery-validation/1.11.0/messages_bs_zh.js" type="text/javascript"></script>

    <sitemesh:head/>
</head>

<body>
<div class="container">
    sitemesh添加的头部
    <div id="content">
        <sitemesh:body/>
    </div>
    sitemesh添加的底部
</div>
<script src="${ctx}/sc/bootstrap/2.3.0/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
