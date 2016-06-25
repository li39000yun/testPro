<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="java.util.Map"%>
<%@page import="org.jsoup.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.jsoup.nodes.FormElement"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>catch akw</title>
</head>
<body>
	<%
		//response.setHeader("Content-disposition", "attachment;filename=data.txt");
		String loginurl = "http://www.aikao100.com/logina.jsp";
		Document doc = Jsoup.connect(loginurl).timeout(5000).get();
		FormElement form = (FormElement) doc.select("form").get(1);
		String loginAction = "http://www.aikao100.com/" + form.attr("action");
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("usernamex", "li39000yun@qq.com");
		maps.put("passwordx", "li39000yun");
		maps.put("prepage", "http://www.aikao100.com/logina.jsp?");
		maps.put("x", "56");
		maps.put("y", "16");
		Connection.Response responses = Jsoup.connect(loginAction).data(maps).method(Connection.Method.POST).execute();
		Map<String, String> cookies = responses.cookies();
		String sturl = request.getParameter("sturl");
		StringBuilder sb = new StringBuilder("");
		while (true) {
			doc = Jsoup.connect(sturl).cookies(cookies).get();
			response.getWriter().println(doc.html());
			break;
			/**
			// 类型
			Elements elements = doc.select(".shiti_con_left_1");
			sb.append(elements.get(0).text()).append("\r\n");
			// 问题
			elements = doc.select(".shiti_con_left_2");
			sb.append(elements.get(0).text()).append("\r\n");
			// 选项
			elements = doc.select("[name=xuanxiang]");
			for (Element element : elements) {
				sb.append(element.val()).append(" ").append(element.nextSibling().outerHtml()).append("\r\n");
			}
			// 解答
			elements = doc.select(".shiti_con_right");
			sb.append(elements.get(0).text()).append("\r\n");
			elements = doc.select(".stnext");
			String next = elements.get(0).attr("onclick");
			if (next.contains("jsp")) {
				int begin = next.indexOf("if('") + 4;
				int end = next.indexOf("==") - 1;
				sturl = "http://www.aikao100.com/" + next.substring(begin, end);
			} else {
				break;
			}
			sb.append("=======================" + "\r\n");*/
		}

		/*ServletOutputStream sos = response.getOutputStream();
		sos.write(sb.toString().getBytes());
		sos.flush();
		sos.close();*/
	%>
</body>
</html>