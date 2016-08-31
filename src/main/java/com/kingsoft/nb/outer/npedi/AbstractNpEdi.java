/**
 * @(#)AbstractNpEdi.java     2012-4-12
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.outer.npedi;

import org.apache.log4j.Logger;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.outer.AbstractOuterFetch;
import com.kingsoft.nb.outer.VoyageInfo;

/**
 * 该类为npedi宁波港数据处理抽象类
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-12
 * 
 * @since JDK 1.6
 * 
 */
public abstract class AbstractNpEdi extends AbstractOuterFetch implements
		INpEdi {
	private static Logger S_Logger = Logger.getLogger(AbstractNpEdi.class);
	protected String encoding = "GBK";// 编码
	protected String user = "GUEST";// 用户名
	protected String password = "guest";// 用户名
	protected String host = "www1.npedi.com";// 主机域名地址
	protected String cookieUrl = "http://www1.npedi.com/edi/ediweb/index.jsp";// 获取cookie地址
	protected String imageUrl = "http://www1.npedi.com/edi/ediweb/image.jsp";// 验证码图片生成地址
	protected String jcaptchaUrl = "http://www1.npedi.com/edi/ediweb/jcaptcha.jpg";// 非正常验证码图片生成地址
	protected String loginUrl = "http://www1.npedi.com/edi/webLoginAction.do";// 登陆表单提交地址

	public AbstractNpEdi() {
		setHost(host);// 主机域名地址
		setEncoding(encoding);// 网站编码
	}

	public AbstractNpEdi(String encoding, String host, String cookie,
			boolean login) {
		this.encoding = encoding;
		this.host = host;
		this.cookie = cookie;
		this.login = login;
	}
	public boolean login() throws Exception {
		if (!login) {
			if (getCookie(cookieUrl) != null) {
				int errorCount = 0;

				int imageState = getImageCodeUrl(cookieUrl);
				String nowImageUrl = StringManage.FS_EMPTY;
				if (imageState == 0) {
					nowImageUrl = imageUrl;
				} else {
					nowImageUrl = jcaptchaUrl;
				}
				String validateCode = null;
				StringBuffer formData = null;
				int codeErrorCount = 0;
				while (!login) {
					validateCode = null;
					formData = new StringBuffer();
					codeErrorCount = 0;
					while (validateCode == null || validateCode.length() != 4) {
						validateCode = getValidateCode(nowImageUrl);
						if (codeErrorCount++ > 5) {
							break;
						}
					}
					formData.append("usercode=").append(user);
					formData.append("&password=").append(password);
					formData.append("&randcode=").append(validateCode);
					String html = login(loginUrl, formData.toString());
					if (html.indexOf("退出") > 0) {
						login = true;
						S_Logger.debug("Login to " + host + " is OK!");
					} else {
						login = false;
						S_Logger.debug("Login to " + host + " is faild!");
					}
					if (errorCount++ > 10) {
						break;
					}
				}
			}
		}
		return login;
	}
	
	
	public VoyageInfo[] fetch() throws Exception{
		return null;
	}
	
	public VoyageInfo[] fetch(String param) throws Exception{
		return null;
	}
}
