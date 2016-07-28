/**
 * @(#)W1Fetch.java     2014-7-3
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchData;
import com.kingsoft.business.vo.fetch.FetchDataVO;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * [外二期] 数据抓取服务类
 * 
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class W4Fetch extends AbstractFetch {
	private static Logger S_logger = Logger.getLogger(W4Fetch.class);
	private static final long serialVersionUID = 1L;
	private static final String FS_W4_GOODS = "http://www.sect.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0003";// 外四箱货查询
	private static final String FS_W4_PACKING = "http://www.sect.com.cn/wat/controllerServlet.do?queryid=0007001";// 外四装箱单查询

	@Override
	public String preExecute(FetchSearch search) throws Exception {

		FetchDataVO main = new FetchDataVO();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W4Fetch] fetch ");
			}

			main.setContainerNo(search.getContainerNo());
			main.setFecthBeginTime(Console.FS_TIME.getNow());
			HttpGet httpget = new HttpGet(FS_W4_GOODS);
			HttpReturn httpReturn=executeGet(httpclient, httpget, StringManage.FS_EMPTY);
			Header[] hs = httpReturn.getHead();
			String cookie = "";
			for (Header h : hs) {
				if (h.getName().equals("Set-Cookie")) {
					cookie = h.getValue();
				}
			}

			HttpPost httpPost = new HttpPost("http://www.sect.com.cn/wat/controllerServlet.do?queryid=0003001");

			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.sect.com.cn");
			httpPost.setHeader("Referer", "http://www.sect.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0003");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:20.0) Gecko/20100101 Firefox/20.0");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("iyc_cntrno", search.getContainerNo()));
			nvps.add(new BasicNameValuePair("method", "doquery"));
			nvps.add(new BasicNameValuePair("querysid", "g0003"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn=executePost(httpclient, httpPost, StringManage.FS_EMPTY);
			String html = httpReturn.getReturnHtml();
			parseW4Goods(html, main);

			// 如果箱货信息里没查到记录时就返回
			if (main.getGoods().length == 0) {
				return "";
			}

			// 装箱单信息查询 响应头信息
			httpPost = new HttpPost(FS_W4_PACKING);
			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.sect.com.cn");
			httpPost.setHeader("Referer", "	http://www.sect.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0007");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:20.0) Gecko/20100101 Firefox/20.0");
			// 参数
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("billnoinput", main.getBookingNo()));
			nvps.add(new BasicNameValuePair("method", "doquery"));
			nvps.add(new BasicNameValuePair("querysid", "g0007"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn=executePost(httpclient, httpPost, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			parseW4Packing(html, main);

			// 海关放行
			httpget = new HttpGet("http://edi.easipass.com/dataportal/query.do?entryid=&blno=" + main.getBookingNo() + "&ctnno=" + main.getContainerNo() + "&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb471004a");
			httpReturn=executeGet(httpclient, httpget, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			parseCustoms(html, main);
			main.setFecthEndTime(Console.FS_TIME.getNow());
			return objToJson(main);

		} catch (Exception e) {
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W4Fetch] fetch error");
			}
			e.printStackTrace();
		} finally {
				httpclient.close();
		}
		return "";

	}

	// 解析货物信息
	private void parseW4Goods(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		NodeFilter aFilter = new TagNameFilter("table");
		NodeFilter classfilter_l = new HasAttributeFilter("id", "ctable");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter, classfilter_l });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length > 0) {
			List<FetchData> goods = new ArrayList<FetchData>();
			TableTag tg = (TableTag) nodes[0];
			TableRow[] tr = tg.getRows();
			String[] ss;
			String[] vs;
			FetchData dt;
			for (int i = 1; i < tr.length; i++) {
				// 取得某行的所有列
				TableColumn[] td = tr[i].getColumns();
				// System.out.println(td.length);
				main.setEnterTime(td[1].toPlainTextString().trim().replace("&nbsp;", ""));
				if (td[3].toPlainTextString().replace("&nbsp;", "").equals("出口重箱进场")) {
					String date = td[1].toPlainTextString().trim();
					if (!StringManage.isEmpty(date) && date.length() >= 8) {// 如果有进场时间 且 距当前日期超出20天的话就返回
						date = Console.FS_DATE.getNow().substring(0, 2) + date.substring(0, 8);
						if (Console.FS_DATE.compareDay(Console.FS_DATE.getNow(), date) > 20) {
							continue;
						}
					}

					String str = td[7].toHtml().replace("&acute;", "'").replace("\\", "").replace("(\"", "").replace("\");", "");
					Pattern p = Pattern.compile("(.*onclick='todetail(.+)'.*)");
					Matcher m = p.matcher(str);
					if (m.matches()) {
						str = m.group(2);
					}
					ss = str.split("##");
					for (String s : ss) {
						vs = s.split(":=");
						dt = new FetchData();
						dt.setName(vs[0]);
						if (vs.length > 1) {// 冷藏箱温度= 像这种split后长度为1
							dt.setValue(vs[1]);
						}
						goods.add(dt);
						// 设置提单号
						if (dt.getName().equals("提单号") && !StringManage.isEmpty(dt.getValue())) {
							vs = dt.getValue().split("\\(");
							if (vs.length > 0) {
								main.setBookingNo(vs[0]);
							}
						}
					}
					break;
				}
			}
		}
	}

	// 解析 装箱单信息
	private void parseW4Packing(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		NodeFilter aFilter = new TagNameFilter("table");
		NodeFilter classfilter_l = new HasAttributeFilter("id", "ctable");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter, classfilter_l });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length > 0) {
			List<FetchData> packings = new ArrayList<FetchData>();
			TableTag tg = (TableTag) nodes[0];
			TableRow[] tr = tg.getRows();
			// System.out.println(tr.length);
			String[] ss;
			String[] vs;
			FetchData dt;
			if (tr != null && tr.length > 1) {
				for (int i = 1; i < tr.length; i++) {
					// 取得某行的所有列
					TableColumn[] td = tr[i].getColumns();
					String str = td[3].toHtml().replace("\"", "'").replace("('", "").replace("');", "");
					if (str.indexOf(main.getContainerNo()) > -1) {
						Pattern p = Pattern.compile("(.*onclick='todetail(.+)'.*)");
						Matcher m = p.matcher(str);
						if (m.matches()) {
							str = m.group(2);
						}
						ss = str.split("##");
						for (String s : ss) {
							vs = s.split(":=");
							dt = new FetchData();
							dt.setName(vs[0]);
							if (vs.length > 1) {// 冷藏箱温度= 像这种split后长度为1
								dt.setValue(vs[1]);
							}
							packings.add(dt);
						}
						break;
					}
				}
			}

			if (packings.isEmpty())
				main.setPackings(packings.toArray(new FetchData[0]));
		}
	}

}
