/**
 * @(#)AbstractHttpClient.java    2015年12月7日
 *
 * Copyright 2015 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

import com.kingsoft.business.BusinessService;
import com.kingsoft.control.util.StringManage;

/**
 * HTTP请求服务抽象类
 * 
 * @author wangchao
 * 
 * @version 2015年12月7日
 * 
 * @since JDK 1.6
 * 
 */
public abstract class AbstractHttpClient extends BusinessService {
	private static Logger S_Logger = Logger.getLogger(AbstractHttpClient.class);
	protected String encoding = "UTF-8";// 网页编码,默认为UTF-8
	protected String cookie = null;// 网站Cookie值
	protected CloseableHttpClient httpClient = null;// http请求对象
	protected List<String> cookies = null;// 存储请求网站的cookie

	// post相应网址获取数据集
	protected String execute(HttpPost httpPost) throws Exception {
		try {
			// 解析返回数据集
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(),encoding);
		} catch (Exception e) {
			e.printStackTrace();
			S_Logger.debug("请求接口失败:" + e.getMessage());
		} finally {
			destroy();
		}
		return StringManage.FS_EMPTY;
	}

	// 根据传入的网址获取cookie
	protected String getCookie(String cookieUrl) throws Exception {
		String rvalue = StringManage.FS_EMPTY;
		try {
			// 设置HttpClient请求对象
			httpClient = HttpClients.createDefault();

			// 设置GET请求参数
			HttpGet httpGet = new HttpGet(cookieUrl);

			// 解析返回数据集
			HttpResponse response = httpClient.execute(httpGet);
			Header[] hs = response.getAllHeaders();
			for (Header h : hs) {
				if (h.getName().equals("Set-Cookie")) {
					cookies.add(h.getValue());
				}
			}
			rvalue = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			S_Logger.debug("获取网址Cookie失败:" + cookieUrl + e.getMessage());
		}
		return rvalue;
	}

	// 根据传入的网址获取验证码
	public HttpEntity getValidateCode(String imageUrl) throws Exception {
		try {
			// 设置GET请求参数
			HttpGet httpGet = new HttpGet(imageUrl);
			httpGet.setHeader("Cookie", cookie);

			// 解析返回数据集
			HttpResponse response = httpClient.execute(httpGet);
			return response.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
			S_Logger.debug("获取验证码失败:" + imageUrl + e.getMessage());
		}
		return null;
	}

	// 根据传入的图片地址存储图片
	protected boolean image(InputStream is, String imagePath) throws Exception {
		FileOutputStream os = null;
		File imageFile = null;
		try {
			byte[] data = readInputStream(is);
			imageFile = new File(imagePath);
			os = new FileOutputStream(imageFile);
			os.write(data);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			S_Logger.debug("存储图片失败:" + imagePath + e.getMessage());
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
		}
		return false;
	}

	// 根据传入输入流获取byte数组
	private byte[] readInputStream(InputStream is) throws Exception {
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			return os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("service_center - AbstractHttpClient" + "读取输入流失败:readInputStream" + e.getMessage());
		} finally {
			if (os != null) {
				os.close();
			}
			if(is!=null){
				is.close();
			}
		}
		return new byte[0];
	}

	// 解析返回的html数据集
	protected NodeList parserHtml(String html, String tagFilter,
			String filterKey, String filterValue) throws Exception {
		Parser parser = new Parser(html);
		parser.setEncoding(encoding);// 设置编码
		NodeFilter filter = new TagNameFilter(tagFilter);
		NodeFilter classfilter = new HasAttributeFilter(filterKey, filterValue);
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { filter, classfilter });
		return parser.parse(threadFilter);
	}

	// 销毁http请求对象
	protected void destroy() throws IOException {
		if (httpClient != null) {
			httpClient.close();
		}
	}

	// 获取系统根目录
	protected String getRootPath() {
		// 获取系统根目录
		String rootPath = AbstractHttpClient.class.getResource("/").getPath();
		rootPath = rootPath.replace("file:/", "").replace("%20", " ")
				.replace("classes/", "").replace("WEB-INF/", "");

		// 判断是不是Windows操作系统
		String osName = System.getProperties().getProperty("os.name");
		if (osName.indexOf("Windows") != -1) {
			rootPath = rootPath.substring(1);
		}
		return rootPath;
	}
}