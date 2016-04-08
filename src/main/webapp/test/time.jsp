<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>时间</title>
</head>
<%@ include file="/inc.jsp"%>
<script type="text/javascript" src="mytime.js?version="+<%=version %>></script>
<script type="text/javascript">
	$(function() {
		var nowDate = lyq.timeUtils.getNowFormatDate();
		$('#markDay').text(lyq.timeUtils.compareDay(nowDate, $('#markDay').text())+1);
	});
</script>
<body>
	<table>
		<tr>
			<td><label>markDay:</label></td>
			<td><p id="markDay">2015-05-22</p></td>
		</tr>
	</table>
</body>
</html>
