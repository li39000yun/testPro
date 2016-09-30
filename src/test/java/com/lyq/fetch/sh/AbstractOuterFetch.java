/**
 * @(#)AbstractOuterFetch.java     2012-4-5
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.fetch.sh;

import com.kingsoft.control.util.StringManage;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 该类为外部网站身份验证抽象类
 * 
 * 继承自com.kingsoft.nb.outer.Identity类
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-5
 * 
 * @since JDK 1.6
 * 
 */
public abstract class AbstractOuterFetch  {
	private static Logger S_Logger = Logger.getLogger(AbstractOuterFetch.class);
	public String encoding = "UTF-8";// 网页编码,默认为UTF-8
	public String host = null;// 主机域名
	public String cookie = null;// 网站Cookie值
	public String cookie2 = null;// SESSION_ID_IN_BIZ
	public boolean login = false;// 是否已登陆成功
	private static String S_Project_Path = "";// 项目根目录

	static {
		// 加载项目根目录
		String url = AbstractOuterFetch.class.getClassLoader().getResource("").getPath();
		S_Project_Path = url.replace("file:/", "").replace("%20", " ").replace("classes/", "").replace("WEB-INF/", "");
		// 判断是不是Windows操作系统
		String osName = System.getProperties().getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			S_Project_Path = S_Project_Path.substring(1);
		}
	}

	public int getImageCodeUrl(String wwwUrl) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer html = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(wwwUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			is = conn.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, encoding);
			br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("service_center - AbstractOuterFetch" + e.getMessage());
		} finally {
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
			if (buff != null) {
				buff.close();
			}
			if (r != null) {
				r.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		// 代表用得是模糊的验证码
		if (html.toString().indexOf("id=\"img\"") > -1) {
			return 1;
		}
		return 0;
	}

	public String getCookie(String wwwUrl) throws Exception {
		if (StringManage.isEmpty(host)) {// 传入的网站地址为空，返回null值.
			this.cookie = null;
			return cookie;
		}
		URL url = null;
		HttpURLConnection conn = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(wwwUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Host", host);
			conn.connect();
			// 获取Cookie值
			cookie = conn.getHeaderField("Set-Cookie");
		} catch (Exception e) {

		} finally {
			// 关闭与网站服务器的连接
			if (conn != null) {
				conn.disconnect();
			}
		}
		return cookie;
	}
	
	public String getValidateCode(String imageUrl) throws Exception {
		if (StringManage.isEmpty(imageUrl)) {// 传入的验证码图片生成地址为空，返回null值.
			return null;
		}
		URL url = null;
		HttpURLConnection conn = null;
		S_Logger.debug("fetch_center - [AbstractOuterFetch] ------in getValidateCode--------");
		// 解析数据集
		InputStream inStream = null;
		FileOutputStream outStream = null;
		File imageFile = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(imageUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Host", host);
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", cookie);
			conn.connect();

			inStream = conn.getInputStream();
			byte[] data = readInputStream(inStream);
			imageFile = new File(S_Project_Path + "vcode.jpg");
			outStream = new FileOutputStream(imageFile);
			outStream.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				outStream.close();
			}
			if (inStream != null) {
				inStream.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		S_Logger.debug("fetch_center - [AbstractOuterFetch] ------conn disconnect--------");
		// 解析验证码
		S_Logger.debug("fetch_center - [AbstractOuterFetch] -------" + imageFile.getPath() + "-------");
//		UUWiseHelper httpTest = new UUWiseHelper();
//		httpTest.login();
//		return httpTest.getResult(httpTest.upload(imageFile.getPath(), "1004", false));
		return "";
	}

	public String login(String loginUrl , String formData) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer html = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(loginUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", host);
			conn.setRequestProperty("Content-Length", formData.length() + "");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", cookie);

			conn.getOutputStream().write(formData.getBytes(encoding));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();

			conn.connect();
			cookie2 = conn.getHeaderField("Set-Cookie");
			is = conn.getInputStream();
			 buff = new BufferedInputStream(is);
			 r = new InputStreamReader(buff, encoding);
			br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				br.close();
			}
			if (is != null) {
				is.close();
			}
			if (buff != null) {
				buff.close();
			}
			if (r != null) {
				r.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
//			// 添加日志
//			if (html != null) {
//				LogHelper.addLog(loginUrl, formData, html.toString());
//			} else {
//				LogHelper.addLog(loginUrl, formData, "AbstractOuterFetch的login方法");
//			}
		}
		return html.toString();
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public String getCookie2() {
		return cookie2;
	}

	public void setCookie2(String cookie2) {
		this.cookie2 = cookie2;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
