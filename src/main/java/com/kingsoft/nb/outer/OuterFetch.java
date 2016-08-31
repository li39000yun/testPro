/**
 * @(#)OuterFetch.java     2012-4-5
 *
 * Copyright 2012 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.outer;

/**
 * 该类为外部网站身份验证接口类
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-5
 * 
 * @since JDK 1.6
 * 
 */
public interface OuterFetch {
	/**
	 * 获取相关网站的Cookie值
	 * 
	 * @param url
	 *            服务器地址
	 * @return String Cookie值
	 * @throws Exception
	 */
	public String getCookie(String url) throws Exception;

	/**
	 * 获取网站登陆时的验证码
	 * 
	 * 先获取图片，然后对图片进行处理得到验证码
	 * 
	 * @param imageUrl
	 *            验证码图片生成地址
	 * @return String 图片验证码
	 * @throws Exception
	 */
	public String getValidateCode(String imageUrl) throws Exception;

	/**
	 * 登陆相关网站
	 * 
	 * @param loginUrl
	 *            表单提交地址
	 * @param formData
	 *            表单数据
	 * @return String 提交登陆表单后返回的页面HTML代码字符串
	 * @throws Exception
	 */
	public String login(String loginUrl, String formData) throws Exception;
	
	/**
	 * 获取相关网站的图片地址值
	 * 
	 * @param url
	 *            服务器地址
	 * @return String Cookie值
	 * @throws Exception
	 */
	public int getImageCodeUrl(String wwwUrl) throws Exception;
}
