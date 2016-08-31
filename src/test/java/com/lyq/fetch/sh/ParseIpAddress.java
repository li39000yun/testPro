package com.lyq.fetch.sh;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析ip地址
 * @author lxm
 */
public class ParseIpAddress {
	/** 查询IP网址  调用360接口*/
//	private static String FS_IP_URL = "http://api.ip.360.cn/?src=onebox_ipquery&ip=";
//	private static String FS_IP_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=";
	private static String FS_IP_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";
	/**IP地址正则表达式*/
	private static String IP_PATTERN="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	
	//	private String testStr={"errno":0,"errmsg":"\u6210\u529f","data":{"country":"\u4e2d\u56fd","province":"\u6e56\u5317\u7701","city":"\u5b9c\u660c\u5e02","area":"","address":"","isp":"\u8054\u901a"},"ip":"58.19.176.200"}

	public static void main(String[] args) throws Exception {
		ParseIpAddress ip = new ParseIpAddress();
		System.out.println(ip.crawlIP("202.101.124.18"));
		System.out.println(ip.crawlIP("112.65.211.154"));
		System.out.println(ip.crawlIP("121.34.241.5"));
//		/service/getIpInfo.php?ip=
	}

	/** 在其它网站查询IP地址的详情 */
	public String crawlIP(String ip) throws Exception {
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(FS_IP_URL + ip);
		HttpResponse response = defaultHttpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity);
		EntityUtils.consume(entity);
		defaultHttpClient.getConnectionManager().shutdown();// 释放链接
		System.out.println(json);
		JSONObject obj = JSONObject.fromObject(json).getJSONObject("data");
		//测试
		System.out.println(obj.toString());
		if(obj.containsKey("city"))
			return obj.getString("city").replace("市", "");
		else
			return "深圳";
	}
	/**
	 * 获取浏览器IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null)
			return "";

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
