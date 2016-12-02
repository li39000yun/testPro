<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>我的桌面</title>
</head>
<%@ include file="/inc.jsp" %>
<%@ include file="/bootstrap.jsp" %>
<script type="text/javascript" src="test/mytime.js?version="+<%=version %>></script>
<script type="text/javascript">
    $(function () {
        var nowDate = lyq.timeUtils.getNowFormatDate();
        $('#markDay').text(lyq.timeUtils.compareDay(nowDate, $('#markDay').text()) + 1);
    });
</script>
<body>
欢迎！<br />
<table>
    <tr>
        <td><label>markDay:</label></td>
        <td><p id="markDay">2015-05-22</p></td>
    </tr>
</table>
<!-- 升级日志 -->
<div class="container">
    <h2>升级日志</h2>
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>#</th>
                <th>时间</th>
                <th>内容</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>2016-11-01</td>
                <td>添加发送邮件工具</td>
                <td><a href="javascript:void(0)" onclick="addTab('发送邮件','test/tool/mail.jsp','mail')"
                       class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
            </tr>
            <tr>
                <td>2</td>
                <td>2016-11-03</td>
                <td>添加freemarker模板测试功能</td>
                <td><a href="javascript:void(0)" onclick="addTab('freemarkerDemo','hello','freemarkerDemo')"
                       class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
            </tr>
            <tr>
                <td>3</td>
                <td>2016-11-05</td>
                <td>添加ip查询工具</td>
                <td><a href="javascript:void(0)" onclick="addTab('ip查询','servlet/iptool','iptool')"
                       class="easyui-linkbutton" data-options="iconCls:'icon-add'">测试</a></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>