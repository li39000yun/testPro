<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.lyq.common.vo.IpVO" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>mail</title>
</head>
<%@ include file="/inc.jsp" %>
<%@ include file="/bootstrap.jsp" %>
<script type="text/javascript">

    $(function () {
        $("#rvalueDiv").hide();// 隐藏结果
    });

    // 查询ip
    function saarchIp() {
        var searchIp = $('#searchIp').val();
        if (searchIp == "") {
            $.messager.alert('Warning', '请输入ip');
        } else {
            $.post(lyq.basePath + "servlet/iptool", {searchIp: searchIp}, function (data) {
                if (data.indexOf("ip错误") != -1) {
                    $.messager.alert('Warning', data);
                } else {
                    var obj = jQuery.parseJSON(data);
                    $("#rvaue_ip").text(obj.ip);
                    $("#rvaue_country").text(obj.country);
                    $("#rvaue_area").text(obj.area);
                    $("#rvaue_region").text(obj.region);
                    $("#rvaue_city").text(obj.city);
                    $("#rvaue_county").text(obj.county);
                    $("#rvaue_isp").text(obj.isp);
                    $("#rvalueDiv").show();// 显示
                }
            });
        }
    }
</script>
<%
    IpVO ipVO = (IpVO) request.getAttribute("ipVO");


    String path1 = request.getContextPath();
    String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>
<body>
项目路径:<%=basePath1%>
<form id="iptool">
    <div class="container">
        <blockquote>
            <p>IP查询工具</p>
        </blockquote>
        <div class="form-group">
            <div class="col-sm-5">
                <input type="text" class="form-control" id="searchIp" placeholder="请输入ip地址">
            </div>
            <button type="button" class="btn btn-primary" onclick="saarchIp()">查询</button>
        </div>
    </div>
    <div id="rvalueDiv" class="container">
        <div class="table-responsive">
            <h4>查询结果：</h4>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>IP</th>
                    <th>国家</th>
                    <th>地区</th>
                    <th>省份</th>
                    <th>城市</th>
                    <th>县</th>
                    <th>运营商</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><span id="rvaue_ip"></span></td>
                    <td><span id="rvaue_country"></span></td>
                    <td><span id="rvaue_area"></span></td>
                    <td><span id="rvaue_region"></span></td>
                    <td><span id="rvaue_city"></span></td>
                    <td><span id="rvaue_county"></span></td>
                    <td><span id="rvaue_isp"></span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <hr>
    <div class="container">
        <div class="table-responsive">
            <h4>你的IP的详细信息为：</h4>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>IP</th>
                    <th>国家</th>
                    <th>地区</th>
                    <th>省份</th>
                    <th>城市</th>
                    <th>县</th>
                    <th>运营商</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><%=ipVO.getIp()%>
                    </td>
                    <td><%=ipVO.getCountry()%>
                    </td>
                    <td><%=ipVO.getArea()%>
                    </td>
                    <td><%=ipVO.getRegion()%>
                    </td>
                    <td><%=ipVO.getCity()%>
                    </td>
                    <td><%=ipVO.getCounty()%>
                    </td>
                    <td><%=ipVO.getIsp()%>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
</html>
