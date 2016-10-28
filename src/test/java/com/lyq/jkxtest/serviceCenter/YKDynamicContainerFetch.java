package com.lyq.jkxtest.serviceCenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.util.StringManage;
import org.jsoup.Jsoup;

/**
 * 箱动态信息查询
 * 
 * @author kangweishui
 * 
 * @version 2015年9月8日
 * 
 * @since JDK 1.6
 * 
 */
public class YKDynamicContainerFetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	private static Logger S_Logger = Logger
			.getLogger(YKDynamicContainerFetch.class);

	public static void main(String[] args) {
		YKDynamicContainerFetch fetch = new YKDynamicContainerFetch();
		try {
//			System.out.println(fetch.searchTrackByJsoup("YKCOS","RFCU2141913"));
			System.out.println(fetch.searchTrack("YKCOS","HJLU1297778"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String searchTrackByJsoup(String queryType, String queryParam) throws IOException {

		return Jsoup.connect("http://www.portx.cn/ediplatform/cntrquery/service/list.do")
			.data("inyard","0")
			.data("iycCntrno",queryParam)
			.data("portCode",queryType)
		.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.header("Accept-Encoding","gzip, deflate")
		.header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
		.header("Connection","keep-alive")
//		.header("Cookie","dljc=success; Market=dljc*CEC3B4B2632F3DF140ADB2C17ADDCE38")
		.header("Cookie","jkxrj=success; Market=jkxrj*220E079F9A5869696246F316DFD922CD")
		.header("Host","www.portx.cn")
		.header("Referer","http://www.portx.cn/ctplatform/ship/date/list1.do")
		.timeout(5000).post().html()
		;

//		httpPost.setHeader("Connection", "keep-alive");
//		httpPost.setHeader("Cookie",
//				"dljc=success; Market=dljc*CEC3B4B2632F3DF140ADB2C17ADDCE38");
//		httpPost.setHeader("Host", "www.portx.cn");
//		httpPost.setHeader("Referer",
//				"http://www.portx.cn/ctplatform/ship/date/list1.do");
//
//		httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(
//				list));

//		return null;
	}


	public String searchTrack(String queryType, String queryParam)
			throws Exception {
		if (S_Logger.isDebugEnabled()) {
			S_Logger.debug("fetch_center - [com.kingsoft.business.interfaces.data.ykcd.DynamicContainerService.java] searchTrack");
		}
		String rvalue = StringManage.FS_EMPTY;
		CloseableHttpClient httpclient = createHttp();// 创建http链接
		HttpPost httpPost = new HttpPost(
				"http://www.portx.cn/ediplatform/cntrquery/service/list.do");
		try {
			// 设置提交参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			// 箱动态查询
			list.add(new BasicNameValuePair("inyard", "0"));
			list.add(new BasicNameValuePair("iycCntrno", queryParam));
			list.add(new BasicNameValuePair("portCode", queryType));

			httpPost.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language",
					"zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie",
//					"dljc=success; Market=dljc*CEC3B4B2632F3DF140ADB2C17ADDCE38");
					"jkxrj=success; Market=jkxrj*220E079F9A5869696246F316DFD922CD");
			httpPost.setHeader("Host", "www.portx.cn");
			httpPost.setHeader("Referer",
					"http://www.portx.cn/ctplatform/ship/date/list1.do");

			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(
					list));
			// 执行
			HttpReturn httpReturn = executePost(httpclient, httpPost, "utf-8");
			// 获取返回数据
			rvalue = httpReturn.getReturnHtml();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return rvalue;
	}

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
