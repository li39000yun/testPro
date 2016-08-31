/**
 * @(#)InGateInfoImpl.java     2012-4-12
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
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.common.WebservicesMonitor;
import com.kingsoft.nb.outer.VoyageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * NpEdi Ajax根据提单号查询集装箱进门信息数据处理实现类,
 * 
 * @author zhangxulong
 * 
 * @version 2012-4-12
 * 
 * @since JDK 1.6
 * 
 */
public class MessageByBookingNoImpl {
	/** 旧版EDI信息 **/
	private String www = "http://www1.npedi.com/edi/scoarriDischargeAction.do";// 根据提单号查询集装箱进门信息
	private String goodsContainers = "http://www1.npedi.com/edi/ediweb/ScoarriBl.jsp?";// 查询集装箱进门货物信息
	/** 新版EDI信息 **/
	private String newWww = "http://www.npedi.com/ediportal-web/getEdiScoarri.action";// 根据提单号查询集装箱进门信息
	private String newGoodsContainers = "http://www.npedi.com/ediportal-web/getEdiScoarriBl.action";// 查询集装箱进门货物信息
	private static Logger S_Logger = Logger.getLogger(MessageByBookingNoImpl.class);

	public List<VoyageInfo> fetch(String param) throws Exception {
		if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {// 先跑新版EDI信息
			ArrayList<VoyageInfo> lists = new ArrayList<VoyageInfo>();
			String html = getNewHtml(param);
			if (html.indexOf("result") > 0) {
				parseNewHtml(html, lists, param);
			}
			if (lists.size() > 0)
				return lists;
		}
		return null;
	}

	public String getHtml(String bookingNo) throws Exception {
		StringBuffer html = new StringBuffer();
		URL url = null;
		HttpURLConnection conn = null;

		String formData = "vesselvoyage=&ctnoperatorcode=&options=blno&blno=" + bookingNo + "&ctnno=&selectAll=on";
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
				S_Logger.debug("transit_nb - MessageByBookingNoImpl" + e.getMessage());
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
		return html.toString();
	}

	public void parseHtml(String html , List<VoyageInfo> lists , String bookingNo) throws Exception {
		Parser myParser;
		String filterStr = "table";// 节点名

		myParser = Parser.createParser(html, WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());
		myParser.setEncoding(WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());// 设置编码
		NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
		TableTag tabletag = (TableTag) nodeList.elementAt(5);// 提取第6个table节点
		TableRow[] rows = tabletag.getRows();// 数据行数
		int len = rows.length;
		String[] str = null;
		String s = StringManage.FS_EMPTY;
		// 除去标题行和底部的数据分页行
		if (len > 1) {
			VoyageInfo voyageInfo = null;
			TableColumn[] col;
			// 取第一行的数据 这行是最近的了
			for (int i = 1; i < len; i++) {
				col = rows[i].getColumns();
				if (col != null && col.length > 1) {
					voyageInfo = new VoyageInfo();
					str = col[1].getStringText().trim().split("/");
					if (str != null && str.length == 2) {
						voyageInfo.setEn_ship(str[0]);// 英文船名
						if (!"#".equals(str[1])) {// 航次没有时 是 一个 # 这里处理下
							if (str[1].length() > 1) {
								voyageInfo.setVoyage(str[1].substring(0, str[1].length() - 1));// 航次最后一位表示航向
																								// E
																								// /
																								// I
							}
						}
					}
					s = col[5].getStringText().trim();
					if (s.contains("(")) {
						voyageInfo.setDock(s.split("\\(")[0]);// 码头 也就是 还箱堆场
					} else {
						voyageInfo.setDock(s);// 码头 也就是 还箱堆场
					}
					// 箱号
					voyageInfo.setContainerNo(col[2].getStringText().trim());
					voyageInfo.setBookingNo(bookingNo);// 提单号
					voyageInfo.setLoadAnchor(col[6].getStringText().trim());// 装货港
					voyageInfo.setContainerOwner(col[7].getStringText().trim());// 箱主
					voyageInfo.setContainerType(col[8].getStringText().trim());// 尺寸类型
					voyageInfo.setContainerState(col[9].getStringText().trim());// 箱状态

					// 获取货物明细showModalDialog('ediweb/ScoarriBl.jsp?containerId=12062701290001'
					s = col[3].getStringText().trim().replaceAll("\r\n", "").replaceAll(" ", "");
					String containerIdMode = "'ediweb/ScoarriBl.jsp.*?containerId=.*?'";
					Pattern containerIdPattern = Pattern.compile(containerIdMode);
					Matcher containerIdMatcher = containerIdPattern.matcher(s);
					StringBuffer containerIdBuffer = new StringBuffer();
					while (containerIdMatcher.find()) {
						containerIdBuffer.append(containerIdMatcher.group().replace("'", "").replace("ediweb/ScoarriBl.jsp?containerId=", ""));
					}
					if (!StringManage.isEmpty(containerIdBuffer.toString())) {
						getGoodsContainer(voyageInfo, containerIdBuffer.toString());// 根据提单号，获取货物明细数据
					}
					// 添加数据
					lists.add(voyageInfo);
				}
			}
		}
	}

	public void getGoodsContainer(VoyageInfo voyageInfo , String containerId) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		String formData = "containerId=" + containerId;

		StringBuffer html = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			url = new URL(goodsContainers);
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
				S_Logger.debug("transit_nb - MessageByBookingNoImpl" + e.getMessage());
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
		// 解析货物信息
		parseGoodsHtml(voyageInfo, html.toString());
	}

	public void parseGoodsHtml(VoyageInfo voyageInfo , String html) throws Exception {
		Parser myParser;
		String filterStr = "table";// 节点名
		myParser = Parser.createParser(html, WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());
		myParser.setEncoding(WebservicesMonitor.FS_FETCH_LOGIN.getEncoding());// 设置编码
		NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
		TableTag tabletag = (TableTag) nodeList.elementAt(0);// 提取第1个table节点
		TableRow[] rows = tabletag.getRows();// 数据行数
		int len = rows.length;
		if (len > 1) {
			for (int i = 1; i < len; i++) {
				TableColumn[] col;
				// 只取最近期的信息
				col = rows[i].getColumns();
				if (col[1].getStringText().trim().equals(// 提单号对应多条货物信息，按提单号提取
						voyageInfo.getBookingNo())) {
					voyageInfo.setPiece(Integer.parseInt(col[2].getStringText().trim()));// 件数
					voyageInfo.setGoodsWeight(Double.parseDouble(col[3].getStringText().trim()));// 货毛重
					voyageInfo.setCubage(Double.parseDouble(col[4].getStringText().trim()));// 货尺寸
					voyageInfo.setGoodsName(col[5].getStringText().trim());// 货名
					break;// 取到对应的货物信息就跳出
				}
			}
		}
	}

	public String getNewHtml(String bookingNo) throws Exception {
		StringBuffer html = new StringBuffer();
		URL url = null;
		HttpURLConnection conn = null;
		String formData = "billno=" + bookingNo + "&xzcode=&loadOrNot=0&ctnNo=&vesselcode=&voyage=&blno=" + bookingNo + "&gotoPage=1&pageCount=500&vesselvoyage=&ctnoperatorcode=&options=blno&blno=" + bookingNo + "&ctnno=&selectAll=on";
		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			// 打开与网站服务器的连接
			url = new URL(newWww);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", WebservicesMonitor.FS_FETCH_NEW_LOGIN.getHost());
			conn.setRequestProperty("Content-Length", "0");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + ";Hm_lvt_f5127c6793d40d199f68042b8a63e725=1441613572,1441673287; SESSION_ID_IN_BIZ=f5dbfa96f067c5e9e784565dd3b66bfa; Hm_lpvt_f5127c6793d40d199f68042b8a63e725=1441678137; TKPaoPao=true");
			conn.getOutputStream().write(formData.getBytes(WebservicesMonitor.FS_FETCH_NEW_LOGIN.getEncoding()));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();
			is = conn.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, WebservicesMonitor.FS_FETCH_NEW_LOGIN.getEncoding());
			br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("transit_nb - MessageByBookingNoImpl" + e.getMessage());
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
		return html.toString();
	}

	public void parseNewHtml(String html , List<VoyageInfo> lists , String bookingNo) throws Exception {
		VoyageInfo[] voyageInfos = new VoyageInfo[0];
		JSONObject json = new JSONObject();
		json = JSONObject.fromObject(html.toString());
		if (json.containsKey("result")) {
			voyageInfos = (VoyageInfo[]) JSONArray.toArray(JSONArray.fromObject(json.get("result")), VoyageInfo.class);
		}

		HashMap<String, String> dockMap = new HashMap<String, String>();// 码头信息
		dockMap.put("BLCT", "NBCT(二期)");
		dockMap.put("BLCT2", "北二集司(三期)");
		dockMap.put("BLCT3", "港吉(四期)");
		dockMap.put("BLCTYD", "远东(五期)");
		dockMap.put("BLCTZS", "大榭招商");
		dockMap.put("BLCTMS", "梅山码头");
		dockMap.put("ZHCT", "镇司(ZHCT)");
		dockMap.put("ZIT", "世航五洲(乍浦码头)");
		dockMap.put("YZCT", "甬舟码头");
		dockMap.put("DXCTE", "大榭E港区");
		dockMap.put("B2SCT", "北仑山码头");

		HashMap<String, String> ctnStatusMap = new HashMap<String, String>();// 箱状态
		// F：整箱；L:
		// 拼箱；E：空箱
		ctnStatusMap.put("F", "整箱");
		ctnStatusMap.put("L", "拼箱");
		ctnStatusMap.put("E", "空箱");

		if (voyageInfos != null && voyageInfos.length > 0) {
			for (VoyageInfo voyageInfo : voyageInfos) {
				voyageInfo.setEn_ship(voyageInfo.getVessel());// 英文船名
				voyageInfo.setVoyage(voyageInfo.getVoyage());// 航次最后一位表示航向 E / I
				if (dockMap.containsKey(voyageInfo.getSenderCode())) {
					voyageInfo.setDock(dockMap.get(voyageInfo.getSenderCode()));// 码头也就是还箱堆场
				}
				voyageInfo.setContainerNo(voyageInfo.getCtnNo());// 箱号
				voyageInfo.setBookingNo(voyageInfo.getBlNo());// 提单号
				voyageInfo.setLoadAnchor(voyageInfo.getLoadPortCode());// 装货港
				voyageInfo.setContainerOwner(voyageInfo.getCtnOperatorCode());// 箱主
				voyageInfo.setContainerType(voyageInfo.getCtnSizeType());// 尺寸类型
				if (ctnStatusMap.containsKey(voyageInfo.getCtnStatus())) {
					voyageInfo.setContainerState(ctnStatusMap.get(voyageInfo.getCtnStatus()));// 箱状态
				}
				getNewGoodsContainer(voyageInfo, voyageInfo.getId());
				lists.add(voyageInfo);
			}
		}
	}

	public void getNewGoodsContainer(VoyageInfo voyageInfo , String containerId) throws Exception {
		StringBuffer html = new StringBuffer();
		URL url = null;
		HttpURLConnection conn = null;
		String formData = "billno=" + containerId;

		InputStream is = null;
		BufferedReader br = null;
		BufferedInputStream buff = null;
		Reader r = null;
		try {
			url = new URL(newGoodsContainers);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", WebservicesMonitor.FS_FETCH_NEW_LOGIN.getHost());
			conn.setRequestProperty("Content-Length", "0");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Cookie", WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + ";Hm_lvt_f5127c6793d40d199f68042b8a63e725=1441613572,1441673287; SESSION_ID_IN_BIZ=f5dbfa96f067c5e9e784565dd3b66bfa; Hm_lpvt_f5127c6793d40d199f68042b8a63e725=1441678137; TKPaoPao=true");
			conn.getOutputStream().write(formData.getBytes(WebservicesMonitor.FS_FETCH_NEW_LOGIN.getEncoding()));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();
			is = conn.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, WebservicesMonitor.FS_FETCH_NEW_LOGIN.getEncoding());
			br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				html.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("transit_nb - MessageByBookingNoImpl" + e.getMessage());
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

		if (html.indexOf("blNo") > 0) {
			// 解析货物信息
			parseNewGoodsHtml(voyageInfo, html.toString());
		}
	}

	public void parseNewGoodsHtml(VoyageInfo voyageInfo , String html) throws Exception {
		VoyageInfo[] voyageInfos = (VoyageInfo[]) JSONArray.toArray(JSONArray.fromObject(html.toString()), VoyageInfo.class);
		if (voyageInfos != null && voyageInfos.length > 0) {
			for (VoyageInfo v : voyageInfos) {
				if (v.getBlNo().equals(voyageInfo.getBookingNo())) {// 提单号对应多条货物信息，按提单号提取
					if (!StringManage.isEmpty(v.getNumbersOfPackages())) {
						voyageInfo.setPiece(Integer.parseInt(v.getNumbersOfPackages()));// 件数
					}
					if (!StringManage.isEmpty(v.getCargoGrossWeight())) {
						voyageInfo.setGoodsWeight(Double.parseDouble(v.getCargoGrossWeight()));// 货毛重
					}
					if (!StringManage.isEmpty(v.getCargoMeasurement())) {
						voyageInfo.setCubage(Double.parseDouble(v.getCargoMeasurement()));// 货尺寸
					}
					voyageInfo.setGoodsName(v.getCargoTypeCode());// 货名
					break;// 取到对应的货物信息就跳出
				}
			}
		}
	}
}
