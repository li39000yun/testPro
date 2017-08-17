<%--
  Created by IntelliJ IDEA.
  User: 云强
  Date: 2017/8/17
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>jOrgChart异步加载</title>
    <link rel="stylesheet" href='jquery.jOrgChart.css'/>
    <script type='text/javascript' src='jquery.min.js'></script>
    <script type='text/javascript' src='jquery.jOrgChart.js'></script>
    <style>
        a {
            text-decoration: none;
            color: #fff;
            font-size: 12px;
        }
        .jOrgChart .node {
            width: 120px;
            height: 50px;
            line-height: 50px;
            border-radius: 4px;
            margin: 0 8px;
        }
    </style>
</head>
<body>
<!--显示组织架构图-->
<div id='jOrgChart'></div>


<script type='text/javascript'>
    $(function(){
        //数据返回
        $.ajax({
            url: "test.json",
            type: 'POST',
            dataType: 'JSON',
            data: {action: 'org_select'},
            success: function(result){
                var showlist = $("<ul id='org' style='display:none'></ul>");
                showall(result.data, showlist);
                $("#jOrgChart").append(showlist);
                $("#org").jOrgChart( {
                    chartElement : '#jOrgChart',//指定在某个dom生成jorgchart
                    dragAndDrop : false //设置是否可拖动
                });

            }
        });
    });

    function showall(menu_list, parent) {
        $.each(menu_list, function(index, val) {
            if(val.childrens.length > 0){

                var li = $("<li></li>");
                li.append("<a href='javascript:void(0)' onclick=getOrgId("+val.id+");>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                //递归显示
                showall(val.childrens, $(li).children().eq(1));
            }else{
                $("<li></li>").append("<a href='javascript:void(0)' onclick=getOrgId("+val.id+");>"+val.name+"</a>").appendTo(parent);
            }
        });

    }

</script>
</body>
</html>
