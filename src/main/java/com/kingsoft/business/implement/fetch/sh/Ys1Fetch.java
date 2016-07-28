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
 * [洋山港一期] 数据抓取服务类
 * 
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class Ys1Fetch extends AbstractFetch {
	private static Logger S_logger = Logger.getLogger(Ys1Fetch.class);
	private static final long serialVersionUID = 1L;
	// 洋山港一期
	private static final String FS_YS1_GOODS = "http://www.shsict.com/as/query/search/com_user.jsp";// 洋山港一期 箱货查询

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		FetchDataVO main = new FetchDataVO();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.Ys1Fetch] fetch ");
			}
			main.setContainerNo(search.getContainerNo());
			main.setFecthBeginTime(Console.FS_TIME.getNow());
			HttpGet httpget = new HttpGet(FS_YS1_GOODS);
			HttpReturn httpReturn=executeGet(httpclient, httpget, StringManage.FS_EMPTY);
			String html = httpReturn.getReturnHtml();
			// System.out.println(html);
			Header[] hs = httpReturn.getHead();
			String cookie = "";
			for (Header h : hs) {
				// System.out.println(h.getName()+"=="+h.getValue());
				if (h.getName().equals("Set-Cookie")) {
					cookie = h.getValue();
				}
			}

			// 货物详细信息
			HttpPost httpPost = new HttpPost("http://www.shsict.com/as/query/search/comm/contgoods.jsp");
			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.shsict.com");
			httpPost.setHeader("Referer", "http://www.shsict.com/as/query/search/com_user.jsp");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:20.0) Gecko/20100101 Firefox/20.0");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("cntrno", search.getContainerNo()));
			nvps.add(new BasicNameValuePair("choice", "1"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn=executePost(httpclient, httpPost, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			// System.out.println(html);
			parseYS1Goods(html, main);
			// 如果箱货信息里没查到记录时就返回
			if (main.getGoods().length == 0) {
				return "";
			}

			// 海关放行
			httpget = new HttpGet("http://edi.easipass.com/dataportal/query.do?entryid=&blno=" + main.getBookingNo() + "&ctnno=" + main.getContainerNo() + "&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb471004a");
			httpReturn=executeGet(httpclient, httpget, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			parseCustoms(html, main);
			main.setFecthEndTime(Console.FS_TIME.getNow());
			return objToJson(main);

		} catch (Exception e) {
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.Ys1Fetch] fetch error");
			}
			e.printStackTrace();
		} finally {
				httpclient.close();
		}
		return "";

	}

	// 解析 装箱单信息
	private void parseYS1Goods(String html, FetchDataVO main) throws Exception {
		String url = StringManage.FS_EMPTY;
		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		NodeFilter aFilter = new TagNameFilter("table");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length > 2) {
			TableTag tg = (TableTag) nodes[2];
			TableRow[] tr = tg.getRows();
			// System.out.println(tr.length);
			FetchData dt;
			String[] ss;
			String[] vs;
			List<String> values = new ArrayList<String>();
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
					url = td[7].toHtml().replace("('", "").replace("');", "").replace("\"", "'");
					Pattern p = Pattern.compile("(.*onclick='winopencontgoods_detail.jsp\\?(.+)','_blank','800','500'\\).*)");
					Matcher m = p.matcher(url);
					if (m.matches()) {
						url = m.group(2);
					}

					ss = url.split("&");
					for (String s : ss) {
						vs = s.split("=");
						if (vs[1].equals("null")) {
							values.add("");
						} else {
							values.add(vs[1]);
						}
					}
					List<FetchData> list = new ArrayList<FetchData>();
					dt = new FetchData();
					dt.setName("箱号");
					dt.setValue(values.get(0));
					list.add(dt);

					dt = new FetchData();
					dt.setName("尺寸");
					dt.setValue(values.get(7));
					list.add(dt);

					dt = new FetchData();
					dt.setName("箱型");
					dt.setValue(values.get(8));
					list.add(dt);

					dt = new FetchData();
					dt.setName("高度");
					dt.setValue(values.get(9));
					list.add(dt);

					dt = new FetchData();
					dt.setName("状态");
					dt.setValue(values.get(10));
					list.add(dt);

					dt = new FetchData();
					dt.setName("铅封号");
					dt.setValue(values.get(12));
					list.add(dt);

					dt = new FetchData();
					dt.setName("提单号<br>（重量/件数/体积/原因）");
					dt.setValue(values.get(28));
					list.add(dt);
					// 设置提单号
					if (!StringManage.isEmpty(dt.getValue())) {
						vs = dt.getValue().split("\\(");
						if (vs.length > 0) {
							main.setBookingNo(vs[0]);
						}
					}

					dt = new FetchData();
					dt.setName("重量");
					dt.setValue(values.get(29));
					list.add(dt);

					dt = new FetchData();
					dt.setName("冷藏箱温度");
					dt.setValue(values.get(26));
					list.add(dt);

					dt = new FetchData();
					dt.setName("危险品类型");
					dt.setValue(values.get(27));
					list.add(dt);

					dt = new FetchData();
					dt.setName("持箱人");
					dt.setValue(values.get(11));
					list.add(dt);

					dt = new FetchData();
					dt.setName("计划作业方式");
					dt.setValue(values.get(33));
					list.add(dt);

					dt = new FetchData();
					dt.setName("计划作业时间");
					dt.setValue(values.get(34));
					list.add(dt);

					dt = new FetchData();
					dt.setName("进场时间");
					dt.setValue(values.get(1));
					list.add(dt);

					dt = new FetchData();
					dt.setName("出场时间");
					dt.setValue(values.get(2));
					list.add(dt);

					dt = new FetchData();
					dt.setName("进场方式");
					dt.setValue(values.get(10));
					list.add(dt);

					dt = new FetchData();
					dt.setName("出场方式");
					dt.setValue(values.get(4));
					list.add(dt);

					dt = new FetchData();
					dt.setName("箱变空时间");
					dt.setValue(values.get(13));
					list.add(dt);

					dt = new FetchData();
					dt.setName("箱变空原因");
					dt.setValue(values.get(14));
					list.add(dt);

					dt = new FetchData();
					dt.setName("一程船船名");
					dt.setValue(values.get(15));
					list.add(dt);

					dt = new FetchData();
					dt.setName("一程船航次");
					dt.setValue(values.get(16));
					list.add(dt);

					dt = new FetchData();
					dt.setName("二程船船名");
					dt.setValue(values.get(17));
					list.add(dt);

					dt = new FetchData();
					dt.setName("二程船航次");
					dt.setValue(values.get(18));
					list.add(dt);

					dt = new FetchData();
					dt.setName("装货港");
					dt.setValue(values.get(30));
					list.add(dt);

					dt = new FetchData();
					dt.setName("卸货港");
					dt.setValue(values.get(31));
					list.add(dt);

					dt = new FetchData();
					dt.setName("目的港");
					dt.setValue(values.get(32));
					list.add(dt);

					dt = new FetchData();
					dt.setName("放关");
					dt.setValue(values.get(5));
					list.add(dt);

					dt = new FetchData();
					dt.setName("配船");
					dt.setValue(values.get(6));
					list.add(dt);

					dt = new FetchData();
					dt.setName("超限箱类型");
					dt.setValue(values.get(19));
					list.add(dt);

					dt = new FetchData();
					dt.setName("超重");
					dt.setValue(values.get(20));
					list.add(dt);

					dt = new FetchData();
					dt.setName("超高");
					dt.setValue(values.get(21));
					list.add(dt);

					dt = new FetchData();
					dt.setName("前超长");
					dt.setValue(values.get(22));
					list.add(dt);

					dt = new FetchData();
					dt.setName("后超长");
					dt.setValue(values.get(23));
					list.add(dt);

					dt = new FetchData();
					dt.setName("左超宽");
					dt.setValue(values.get(24));
					list.add(dt);

					dt = new FetchData();
					dt.setName("右超宽");
					dt.setValue(values.get(25));
					list.add(dt);

					main.setGoods(list.toArray(new FetchData[0]));
					break;
				}
			}
		}
	}
}
