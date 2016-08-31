/**
 * @(#)VoyageInfoImpl.java     2012-4-5
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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.common.WebservicesMonitor;
import com.kingsoft.nb.outer.VoyageInfo;

/**
 * NpEdi 宁波港 船期数据处理实现类
 * --不再使用
 * @author zhangxulong
 * 
 * @version 2012-4-5
 * 
 * @since JDK 1.6
 * 
 */
public class VoyageInfoImpl {
	private static Logger S_Logger = Logger.getLogger(VoyageInfoImpl.class);
	private String www = "http://www1.npedi.com/edi/voyageInfoAction.do";// 船期数据查询地址
	private String[] docks = { "BLCT", "BLCT2", "BLCT3", "BLCTYD", "BLCTZS", "BLCTMS", "YZCT", "B2SCT", "BLCTCT", "ZHCT", "DXCTE", "ZIT" };// 宁波所有码头信息

	public VoyageInfo[] fetch() throws Exception {
		VoyageInfo[] voyageInfos = new VoyageInfo[0];

		if (WebservicesMonitor.FS_FETCH_LOGIN.isLogin()) { // 已登陆成功,抓取船期信息
			ArrayList<VoyageInfo> lists = new ArrayList<VoyageInfo>();
			String html = null;
			for (int i = 0; i < 12; i++) {
				html = getHtml(docks[i]);
				if (html.indexOf("voyageInfoAction.do") > 0)
					parseHtml(html, lists);
			}
			if (lists.size() > 0)
				voyageInfos = (VoyageInfo[]) lists.toArray(voyageInfos);
		}

		return voyageInfos;
	}

	public String getHtml(String dock) throws Exception {
		StringBuffer html = new StringBuffer();
		URL url = null;
		HttpURLConnection conn = null;
		String formData = "cpcode=" + dock + "&ename=&voyage=&pageIndex=1&selectAll=on";
		// String formData = "cpcode=" + dock + "&ename=&voyage=&pageIndex=10";

		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(www);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", WebservicesMonitor.FS_FETCH_LOGIN.getHost());
			conn.setRequestProperty("Content-Length", formData.length() + "");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", WebservicesMonitor.FS_FETCH_LOGIN.getCookie());
			conn.getOutputStream().write(formData.getBytes(WebservicesMonitor.FS_FETCH_LOGIN.getEncoding()));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();
			is = conn.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());
			br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("transit_nb - VoyageInfoImpl" + e.getMessage());
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
		conn.disconnect();
		return html.toString();
	}

	public void parseHtml(String html , List<VoyageInfo> lists) throws Exception {
		Parser myParser;
		String filterStr = "table";// 节点名
		myParser = Parser.createParser(html, WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());
		myParser.setEncoding(WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());// 设置编码
		NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
		TableTag tabletag = (TableTag) nodeList.elementAt(4);// 提取第5个table节点
		TableRow[] rows = tabletag.getRows();// 数据行数
		int len = rows.length;
		if (len > 1) {
			VoyageInfo voyageInfo = null;
			TableColumn[] col;
			String arrivalTime = null;// 预计抵港时间
			String inStartTime = null;// 进箱开始时间
			String inCutOffTime = null;// 进箱截止时间

			// 获取数据的时间范围,只取过去10天到未来20天内的船期信息
			String nowTime = Console.FS_TIME.getNow();// 当前时间
			long minute = 0;// 当前时间与预计抵港时间的天数差
			for (int i = 1; i < len - 1; i++) {
				col = rows[i].getColumns();
				arrivalTime = col[11].getStringText().trim();// 获取预计抵港时间
				if (!StringManage.isEmpty(arrivalTime)) {// 预计抵港时间不为空时对该记录做处理,为空表示无用数据
					arrivalTime += ":00";
					minute = Console.FS_TIME.compareDay(arrivalTime, nowTime);

					// 判断该条数据的 预计抵港时间 是否在可用范围之内,即当前时间往前10天往后20天范围内
					if (minute >= -10 && minute <= 20) {
						voyageInfo = new VoyageInfo();
						voyageInfo.setDock(col[1].getStringText().trim());// 码头
						voyageInfo.setEn_ship(col[2].getStringText().trim());// 英文船名
						voyageInfo.setCn_ship(col[4].getStringText().trim());// 中文船名
						voyageInfo.setVoyage(col[5].getStringText().trim());// 航次
						voyageInfo.setType(col[7].getStringText().trim());// 进出口方向
						voyageInfo.setUnCode(col[8].getStringText().trim());// 船舶UN代码

						inStartTime = col[9].getStringText().trim();
						if (!StringManage.isEmpty(inStartTime)) {
							voyageInfo.setInStartTime(inStartTime + ":00");// 进箱开始时间
						}

						inCutOffTime = col[10].getStringText().trim();
						if (!StringManage.isEmpty(inCutOffTime)) {
							voyageInfo.setInCutOffTime(inCutOffTime + ":00");// 进箱截止时间
						}

						lists.add(voyageInfo);
					}
				}
			}
		}
	}
}
