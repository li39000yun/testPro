<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>catch akw</title>
</head>
<body>
	地址:
	<input id="url">
	<button onclick="catchAKW();">获取</button>
</body>
<script type="text/javascript">
	function catchAKW() {
		var url = document.getElementById("url").value;
		if (url.indexOf("jsp") == -1) {
			alert("请输入正确的网址!");
		} else {
			window.location.href = 'catchAKW.jsp?sturl=' + url;
		}
	}
</script>
</html>