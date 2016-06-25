<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>json工具</title>
</head>
<%@ include file="/inc.jsp"%>
<script type="text/javascript" src="mytime.js?version="+<%=version %>></script>
<script type="text/javascript">
	<script type="text/javascript">
			$(function() {
				var div1 = $("#left");
				var div2 = $("#right");

				var h = GetMaxHeight(div1, div2);
				div1.css("height", h);
				div2.css("height", h);

			});


	function GetMaxHeight(div1, div2) {
		var h1 = $(div1).attr("offsetHeight");
		var h2 = $(div2).attr("offsetHeight");

		return Math.max(h1, h2);
	}

</script>
</script>
<body>
<div id="list" style="border: solid 1px #999;">
	<div id="left" style="border: 1px solid red; float: left; width: 100px; overflow: hidden;">
		这是左侧的内容这是左侧的内容这是左侧的内容
	</div>
	<div id="right" style="margin-left: 110px; border: 1px solid #000;">
		这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容这是右侧的内容

	</div>
	<div style="clear: both;" title="该标签用于清除浮动，使left和right的父标签能自适应高度！">
	</div>
</div>
<div id="p" class="easyui-panel" title="My Panel"
	 style="width:500px;height:150px;padding:10px;background:#fafafa;"
	 data-options="iconCls:'icon-save',closable:true,
                collapsible:true,minimizable:true,maximizable:true">
	<p>panel content.</p>
	<p>panel content.</p>
</div>
</body>
</html>
