/**
 * @(#)AbstractNpEdi.java     2012-4-12
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved.
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.outer.npedi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.kingsoft.nb.outer.AbstractOuterFetch;
import com.kingsoft.nb.outer.VoyageInfo;

/**
 * 该类为新版npedi宁波港登录类
 *
 * @author 谢辉
 *
 * @version 2015-9-9
 *
 * @since JDK 1.6
 *
 */
public class LoginNewEdi extends AbstractOuterFetch {
	private static Logger S_Logger = Logger.getLogger(LoginNewEdi.class);
	protected String encoding = "UTF-8";// 编码
	protected String user = "GUEST";// 用户名
	protected String password = "guest";// 用户名
	// TODO: 2016/8/30
//	protected String user = "3801237032";// 用户名
//	protected String password = "223339";// 密码


	protected String host = "www.npedi.com";// 主机域名地址
	protected String cookieUrl = "http://www.npedi.com/ediportal-web/ediweb/index.jsp";// 获取cookie地址
	protected String imageUrl = "http://www.npedi.com/ediportal-web/createCheckImages.action";// 验证码图片生成地址
	protected String loginUrl = "http://www.npedi.com/ediportal-web/loginSystem.action";// 登陆表单提交地址
	protected String isLoginUrl = "http://www.npedi.com/ediportal-web/userLoginStatus.action";// 判断是否处于登陆状态


//	protected String cookieUrl = "http://localhost:8080/transit_nb/ediLoginState.jsp";// 获取cookie地址
//	protected String loginUrl = "http://localhost:8080/transit_nb/ediLoginState.jsp";// 登陆表单提交地址


	public LoginNewEdi() {
		setHost(host);// 主机域名地址
		setEncoding(encoding);// 网站编码
	}

	public LoginNewEdi(String encoding, String host, String cookie, boolean login) {
		this.encoding = encoding;
		this.host = host;
		this.cookie = cookie;
		this.login = login;
	}

	public boolean login() throws Exception {
		if (!login) {
			if (getCookie(cookieUrl) != null) {
				int errorCount = 0;
				String validateCode = null;
				StringBuffer formData = null;
				int codeErrorCount = 0;
				while (!login) {
					validateCode = null;
					formData = new StringBuffer();
					codeErrorCount = 0;
					while (validateCode == null || validateCode.length() != 4) {
						validateCode = getValidateCode(imageUrl);
//						validateCode = "1234";
						if (codeErrorCount++ > 5) {
							break;
						}
					}
					formData.append("userId=").append(user);
					formData.append("&password=").append(password);
					formData.append("&checkCode=").append(validateCode);
					String html = login(loginUrl, formData.toString());

					// TODO: 2016/8/30
					System.out.println(html);


					if (html.indexOf("userId") > 0) {
						login = true;
						S_Logger.debug("Image Login to " + host + " is OK!");
					} else {
						login = false;
						S_Logger.debug("Image Login to" + host + " is faild!");
					}
					if (errorCount++ > 10) {
						break;
					}
				}
			}
		}
		return login;
	}

	/**
	 * 判断session是否失效 并重新设置 login
	 *
	 * @return
	 * @throws Exception
	 */
	public void isKeepConnection() throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer html = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(isLoginUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", host);
			conn.setRequestProperty("Cache-Control", "no-cache");
			if (cookie != null) {
				conn.setRequestProperty("Cookie", cookie+";"+cookie2);
			} else {
				conn.setRequestProperty("Cookie", "");
			}
			conn.connect();
			is = conn.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, encoding);
			br = new BufferedReader(r);
			html = new StringBuffer();
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
            System.out.println("isKeepConnection:" + html);
        } catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("transit_nb - LoginNewEdi" + e.getMessage());
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
		if (html.indexOf("\"status\":\"y\"") > 0) {
			login = true;
		} else {
			login = false;
		}
        System.out.println("isKeepConnection:" + login);
        if (S_Logger.isDebugEnabled())
			S_Logger.debug("transit_nb - LoginNewEdi isKeepConnection result : "+ login +"  result:"+html);
	}

	public VoyageInfo[] fetch() throws Exception {
		return null;
	}

	public VoyageInfo[] fetch(String param) throws Exception {
		return null;
	}
}
