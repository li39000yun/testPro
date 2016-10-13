/**
 * @(#)AbstractFetch.java     2014-6-26
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;

import com.kingsoft.business.vo.fetch.Cache;
import com.kingsoft.business.vo.fetch.FetchData;
import com.kingsoft.business.vo.fetch.FetchDataVO;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.CacheProcess;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * 数据抓取抽象类
 * 
 * @author wangchao
 * 
 * @version 2014-6-26
 * 
 * @since JDK 1.6
 * 
 */
public abstract class AbstractFetch implements DockService {
	private static Logger S_Logger = Logger.getLogger(AbstractFetch.class);
	private static final long serialVersionUID = 1L;
	protected List<NameValuePair> nvps = null;// 查询参数
	protected CloseableHttpClient client = null;
	protected String cookies = StringManage.FS_EMPTY;
	protected String encoding = StringManage.FS_EMPTY;// 编码
	protected HttpReturn httpReturn = new HttpReturn();// 返回信息,包含返回html和头信息

	public String conFetch(FetchSearch search) throws Exception {
		if (S_Logger.isDebugEnabled())
			S_Logger.debug("fetch-center - [AbstractFetch] ContainerNo : " + search.getContainerNo() + "-" + search.getBookingNo());

		// 读取缓存数据
		Cache cache = CacheProcess.get(search.getContainerNo() + "-" + search.getBookingNo());
		if (cache != null && !StringManage.isEmpty(cache.getDatas()) && !cache.getDatas().equals("[]") && Console.FS_TIME.compareHour(Console.FS_TIME.getNow(), cache.getCacheTime()) < 1) {
			return cache.getDatas();
		}

		// 设置缓存
		cache = new Cache();
		cache.setDatas(preExecute(search));
		cache.setCacheTime(Console.FS_TIME.getNow());
		CacheProcess.put(search.getContainerNo() + "-" + search.getBookingNo(), cache);
		return cache.getDatas();
	}

	/**
	 * 
	 * 创建一个 CloseableHttpClient链接 用于http请求
	 * 
	 * @return
	 */
	protected CloseableHttpClient createHttp() {
		return HttpClients.createDefault();

	}

	/**
	 * 创建一个 CloseableHttpClient链接 用于https证书加密网站请求
	 * 
	 * @return
	 */
	protected CloseableHttpClient createHttps() throws Exception {
		// Trust all certs
		SSLContext sslcontext = buildSSLContext();

		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		return HttpClients.custom().setSSLSocketFactory(sslsf).build();

	}

	/**
	 * get请求
	 * 
	 * @param httpclient
	 * @param httpget
	 * @param Encoding
	 * @return 返回 HttpReturn 对像
	 * @throws Exception
	 */
	protected HttpReturn executeGet(CloseableHttpClient httpclient, HttpGet httpget, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		CloseableHttpResponse response = null;
		try {
			RequestConfig  requestConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpget.setConfig(requestConfig);
			response = httpclient.execute(httpget);
			httpReturn = getHttpEntity(response, Encoding);
		} finally {
			if (response != null) {
				response.close();// 关闭资源
			}
		}
		return httpReturn;
	}

	/**
	 * get请求 返回Gzip压缩页面
	 * 
	 * @param httpclient
	 * @param httpget
	 * @param Encoding
	 * @return 返回 HttpReturn 对像
	 * @throws Exception
	 */
	protected HttpReturn executeGetGzip(CloseableHttpClient httpclient, HttpGet httpget, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		CloseableHttpResponse response = null;
		try {
			RequestConfig  requestConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpget.setConfig(requestConfig);
			response = httpclient.execute(httpget);
			httpReturn = getHttpEntityGzip(response, Encoding);
		} finally {
			if (response != null) {
				response.close();// 关闭资源
			}
		}
		return httpReturn;
	}

	/**
	 * port请求
	 * 
	 * @param httpclient
	 * @param httpPost
	 * @param Encoding
	 * @return 返回 HttpReturn 对像
	 * @throws Exception
	 */
	protected HttpReturn executePost(CloseableHttpClient httpclient, HttpPost httpPost, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		CloseableHttpResponse response = null;
		try {
			RequestConfig  requestConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpPost.setConfig(requestConfig);
			response = httpclient.execute(httpPost);
			httpReturn = getHttpEntity(response, Encoding);
		} finally {
			if (response != null) {
				response.close();// 关闭资源
			}
		}
		return httpReturn;

	}

	/**
	 * port请求 被Gzip压缩过的
	 * 
	 * @param httpclient
	 * @param httpPost
	 * @param Encoding
	 * @return
	 * @throws Exception
	 */
	protected HttpReturn executePostGzip(CloseableHttpClient httpclient, HttpPost httpPost, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		CloseableHttpResponse response = null;
		try {
			RequestConfig  requestConfig=RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpPost.setConfig(requestConfig);
			response = httpclient.execute(httpPost);
			httpReturn = getHttpEntityGzip(response, Encoding);
		} finally {
			if (response != null) {
				response.close();// 关闭资源
			}
		}
		return httpReturn;

	}

	/**
	 * 处理HttpEntity 解析HTML
	 * 
	 * @param response
	 * @param Encoding
	 * @return
	 * @throws Exception
	 */
	private HttpReturn getHttpEntity(CloseableHttpResponse response, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		HttpEntity entity = response.getEntity();

		httpReturn.setHead(response.getAllHeaders());// 设置获取响应表头
		if (StringManage.isEmpty(Encoding)) {// 设置解析得到的html
			httpReturn.setReturnHtml(EntityUtils.toString(entity));
		} else {
			httpReturn.setReturnHtml(EntityUtils.toString(entity, Encoding));
		}

		// 释放资源
		EntityUtils.consume(entity);
		return httpReturn;
	}

	/**
	 * 返回页面被Gzip压缩过的
	 * 
	 * @param response
	 * @param Encoding
	 * @return
	 * @throws Exception
	 */
	protected HttpReturn getHttpEntityGzip(CloseableHttpResponse response, String Encoding) throws Exception {
		HttpReturn httpReturn = new HttpReturn();
		HttpEntity entity = response.getEntity();

		httpReturn.setHead(response.getAllHeaders());// 设置获取响应表头
		// 是否为压缩的数据
		boolean isGzip = false;
		for (Header h : httpReturn.getHead()) {
			if (h.getName().equals("Content-Encoding")) {
				if (h.getValue().equals("gzip")) {
					isGzip = true;
				}
			}
		}
		if (isGzip) {// 如果是压缩过的需要解压缩gzip
			InputStream is = entity.getContent();
			try {
				httpReturn.setReturnHtml(GZipToString(is, Encoding));
			} finally {
				is.close();// 关闭流
			}
		} else {
			if (StringManage.isEmpty(Encoding)) {// 设置解析得到的html
				httpReturn.setReturnHtml(EntityUtils.toString(entity));
			} else {
				httpReturn.setReturnHtml(EntityUtils.toString(entity, Encoding));
			}
		}

		// 释放资源
		EntityUtils.consume(entity);
		return httpReturn;
	}

	/**
	 * 把GZIP转换成String
	 * 
	 * @param is
	 * @param Encoding
	 * @return
	 * @throws Exception
	 */
	protected static String GZipToString(InputStream is, String Encoding) throws Exception {
		String rvalue = null;
		GZIPInputStream gzip = null;
		ByteArrayOutputStream baos = null;
		try {
			gzip = new GZIPInputStream(is);
			byte[] buf = new byte[1024];
			int num = -1;
			baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			if (StringManage.isEmpty(Encoding)) {
				rvalue = new String(baos.toByteArray());
			} else {
				rvalue = new String(baos.toByteArray(), Encoding);
			}

			baos.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (gzip != null) {
				gzip.close();
			}
		}
		return rvalue;
	}

	// Trust all certs(https访问信用所有证书)
	protected SSLContext buildSSLContext() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
		SSLContext sslcontext = SSLContexts.custom().setSecureRandom(new SecureRandom()).loadTrustMaterial(null, new TrustStrategy() {

			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();
		return sslcontext;
	}

	/**
	 * post访问,访问一次后关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @return
	 * @throws Exception
	 */
	protected String execute(String wwwUrl) throws Exception {
		return execute(wwwUrl, StringManage.FS_EMPTY);
	}

	/**
	 * post访问,访问一次后关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	protected String execute(String wwwUrl, String encoding) throws Exception {
		// 获取最新记录
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String html = StringManage.FS_EMPTY;
		try {
			// 设置请求参数
			HttpPost httpPost = new HttpPost(wwwUrl);
			if (StringManage.isEmpty(encoding)) {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
			}

			httpclient = createHttp();
			response = httpclient.execute(httpPost);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled()) {
				S_Logger.debug("fetch - [com.kingsoft.business.implement.fetch.AbstractFetch] fetch error " + wwwUrl + e.getMessage());
			}
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return html;
	}

	/**
	 * post访问,使用公共的不会断开httpclient链接，记得最后要关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	protected String executeCommonClient(String wwwUrl, String encoding) throws Exception {
		this.encoding = encoding;
		return executeCommonClient(wwwUrl);
	}

	/**
	 * post访问,使用公共的不会断开httpclient链接，记得最后要关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @return
	 * @throws Exception
	 */
	protected String executeCommonClient(String wwwUrl) throws Exception {
		// 获取最新记录
		String html = StringManage.FS_EMPTY;
		CloseableHttpResponse response = null;
		try {
			// 设置请求参数
			HttpPost httpPost = new HttpPost(wwwUrl);
			if (!StringManage.isEmpty(cookies)) {
				httpPost.setHeader("Cookie", cookies);
			}
			if (StringManage.isEmpty(encoding)) {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
			}

			// 获取数据集
			if (client == null) {
				client = createHttp();
			}
			response = client.execute(httpPost);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled()) {
				S_Logger.debug("fetch - [com.kingsoft.business.implement.fetch.AbstractFetch] fetch error " + wwwUrl + e.getMessage());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return html;
	}

	private String getEntityString(HttpResponse response, String encoding) throws Exception {
		String html = StringManage.FS_EMPTY;
		HttpEntity entity = response.getEntity();
		if (StringManage.isEmpty(encoding)) {
			html = EntityUtils.toString(entity);
		} else {
			html = EntityUtils.toString(entity, encoding);
		}
		// 释放资源
		EntityUtils.consume(entity);
		// 设置返回值
		httpReturn = new HttpReturn();
		httpReturn.setReturnHtml(html);
		httpReturn.setHead(response.getAllHeaders());
		return html;
	}

	/**
	 * get访问,访问一次后关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @return
	 * @throws Exception
	 */
	protected String executeGet(String wwwUrl) throws Exception {
		return executeGet(wwwUrl, StringManage.FS_EMPTY);
	}

	/**
	 * get访问,访问一次后关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	protected String executeGet(String wwwUrl, String encoding) throws Exception {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		String html = StringManage.FS_EMPTY;
		try {
			HttpGet httpGet = new HttpGet(wwwUrl);
			httpclient = createHttp();
			response = httpclient.execute(httpGet);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			if (S_Logger.isDebugEnabled()) {
				S_Logger.debug("fetch - [com.kingsoft.business.implement.fetch.AbstractFetch] fetch error " + wwwUrl + e.getMessage());
			}
		} finally {
			if (response != null) {
				response.close();
			}
			if (httpclient != null) {
				httpclient.close();
			}
		}
		return html;
	}

	protected String executeGetCommonClient(String wwwUrl, String encoding) throws Exception {
		this.encoding = encoding;
		return executeGetCommonClient(wwwUrl);
	}

	/**
	 * get访问,使用公共的不会断开httpclient链接，记得最后要关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @return
	 * @throws Exception
	 */
	protected String executeGetCommonClient(String wwwUrl) throws Exception {
		String html = StringManage.FS_EMPTY;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(wwwUrl);
			if (!StringManage.isEmpty(cookies)) {
				httpGet.setHeader("Cookie", cookies);
			}
			if (client == null) {
				client = createHttp();
			}
			response = client.execute(httpGet);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			if (S_Logger.isDebugEnabled()) {
				S_Logger.debug("fetch - [com.kingsoft.business.implement.fetch.AbstractFetch] fetch error " + wwwUrl + e.getMessage());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return html;
	}

	/**
	 * 关闭client的链接
	 * @throws Exception 
	 */
	protected void shutdownClient() throws Exception {
		if (client != null) {
			client.close();
			client = null;
		}
	}

	/** 解析 放行信息 */
	protected void parseCustoms(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		// 过滤
		NodeFilter aFilter = new TagNameFilter("table");
		NodeFilter classfilter_l = new HasAttributeFilter("bgcolor", "#FFFFFF");
		NodeFilter classfilter_2 = new HasAttributeFilter("bordercolor", "#7996c1");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter, classfilter_l, classfilter_2 });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();

		if (nodes != null && nodes.length > 0) {
			TableTag tg = (TableTag) nodes[0];
			TableRow[] tr = tg.getRows();
			// System.out.println(tr.length);
			if (tr != null && tr.length > 1) {
				// 取得某行的所有列
				TableColumn[] td = tr[1].getColumns();
				main.setPort(td[5].toPlainTextString());// 港区
				main.setCustomsNo(td[4].toPlainTextString());// 报关单号
				main.setCustomsPass(td[6].toPlainTextString());// 海关放行状态
				main.setReceiveTime(td[7].toPlainTextString());// EDI中心接收时间
				main.setReceiptState(td[8].toPlainTextString());// 码头回执状态
				main.setBookingNo(td[2].toPlainTextString());// 提单号
			}
		}
	}

	protected String toString(Node paramObject) {
		return paramObject.toPlainTextString().trim().replace("&nbsp;", "");
	}

	/*** 数组转 json字符串 */
	protected String arrayToJson(FetchData[] datas) throws Exception {
		return JSONArray.fromObject(datas).toString();
	}

	/*** 对象 转 json字符串 */
	protected String objToJson(FetchDataVO vo) throws Exception {
		return JSONObject.fromObject(vo).toString();
	}

	/*** Node对象转成文本 */
	protected String toPlainTextString(Node node) {
		return node.toPlainTextString().trim().replaceAll("\t", "").replaceAll("&nbsp;", "").replaceAll("\r\n", "");
	}

	public abstract String preExecute(FetchSearch search) throws Exception;

	// 判断传入字符串是否是数字
	public boolean isNumeric(String $) {
		Pattern pattern = Pattern.compile("-?\\d+\\.?\\d*");
		return pattern.matcher($).matches();
	}

}
