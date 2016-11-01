<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>mail</title>
</head>
<%@ include file="/inc.jsp" %>

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
    // 发送邮件
    function sendMail() {
        $.post(lyq.basePath+"servlet/Mail", $('#mailForm').serialize(), function (data) {
            alert(data);
        });
    }
</script>
<body>
<form id="mailForm">
    <div class="container">
        <h2>通过smtp服务器发送邮件</h2>
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Value</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>服务邮箱</td>
                    <td><input id="host" name="mail.host" value="smtp.exmail.qq.com"></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>邮箱账户</td>
                    <td><input id="user" name="mail.user" value="luke@jkxsoft.com"></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>邮箱密码</td>
                    <td><input id="pass" type="password" name="mail.pass"></td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>接收邮箱</td>
                    <td><input id="mailTo" name="mail.mailTo" value="li39000yun@qq.com"></td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>接收标题</td>
                    <td><input id="subject" name="mail.subject" value="测试标题"></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td>正文</td>
                    <td><textarea type="" id="body" name="mail.body">测试邮件正文</textarea></td>
                </tr>
                <tr>
                    <td>7</td>
                    <td>签名</td>
                    <td><input id="signature" name="mail.signature" value="Luke"></td>
                </tr>
                </tbody>
            </table>
            <!-- Indicates a successful or positive action -->
            <button type="button" class="btn btn-success" onclick="sendMail();">发送</button>
        </div>
    </div>
</form>
</body>
</html>
