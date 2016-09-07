<%@page import="com.kingsoft.control.util.StringManage"%>
<%@page import="com.kingsoft.nb.common.WebservicesMonitor"%>
<%@page import="com.kingsoft.nb.outer.npedi.LoginNewEdi"%>
<br>
请在链接后面把参数带上才会进行设置
<br>
<%
	String ediCookie = request.getParameter("ediCookie");
	String ediCookie2 = request.getParameter("ediCookie2");
%>
<%
	if (!StringManage.isEmpty(ediCookie) && !StringManage.isEmpty(ediCookie2)) {
		if (WebservicesMonitor.FS_FETCH_NEW_LOGIN  != null) {
			LoginNewEdi edi = WebservicesMonitor.FS_FETCH_NEW_LOGIN;
			edi.setCookie(ediCookie);
			edi.setCookie2(ediCookie2);
			edi.setLogin(true);
			%>
			设置成功
			<%
		} else {
			%>
			超过链接数
			<%
		}
%>
<br>
<%
	} else {
%>
<br>
没有输全，设置不成功!
<%
	}
%>
