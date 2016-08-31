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

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.InputTag;
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
 * [外一期] 数据抓取服务类
 * 
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class W1Fetch extends AbstractFetch {
	private static Logger S_logger = Logger.getLogger(W1Fetch.class);
	private static final long serialVersionUID = 1L;
	private static final String FS_URL = "http://api.baid8.cn/fob001/sh/sh_new_api/wg1.php?txt=";
	private static final String FS_URL_DZZXD = "http://www.fob001.cn/guestbook/sh_new_api/dzzxd.php?txt=";

	@Override
	public String preExecute(FetchSearch search) throws Exception {

		FetchDataVO vo = new FetchDataVO();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W1Fetch] fetch ");
			}
			
			vo.setContainerNo(search.getContainerNo());
			vo.setFecthBeginTime(Console.FS_TIME.getNow());
			HttpGet httpget = new HttpGet(FS_URL + search.getContainerNo());
			
			HttpReturn httpReturn=executeGet(httpclient, httpget, "utf-8");
			String html = httpReturn.getReturnHtml();
			if (html.indexOf(search.getContainerNo()) == -1) {
				return "";
			}

			String url = parseW1GoodsUrl(html, vo);
			if (StringManage.isEmpty(url)) {
				return "";
			}

			httpget = new HttpGet(url);
			httpReturn=executeGet(httpclient, httpget, "utf-8");
			html = httpReturn.getReturnHtml();
			// 解析
			parseW1Goods(html, vo);

			// 电子装箱单
			httpget = new HttpGet(FS_URL_DZZXD + vo.getContainerNo());
			httpReturn=executeGet(httpclient, httpget, "utf-8");
			html = httpReturn.getReturnHtml();
			
			if (html.indexOf("查不到您所需要的电子装箱单") == -1) {
				parseW1Packing(html, vo);
			}

			// 海关放行
			httpget = new HttpGet("http://edi.easipass.com/dataportal/query.do?entryid=&blno=" + vo.getBookingNo() + "&ctnno=" + vo.getContainerNo() + "&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb471004a");
			httpReturn=executeGet(httpclient, httpget, "utf-8");
			html = httpReturn.getReturnHtml();
			// System.out.println(html);
			parseCustoms(html, vo);
			vo.setFecthEndTime(Console.FS_TIME.getNow());
			return objToJson(vo);
			
		} catch (Exception e) {
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W1Fetch] fetch error");
			}
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return "";
	}

	// 解析货物信息 得到 获取详细信息的地址
	private String parseW1GoodsUrl(String html, FetchDataVO main) throws Exception {
		String url = "";
		html = html.replace("﻿<html xmlns=\"http://www.w3.org/1999/xhtml\">", "").replace("</html>", "");
		Parser parser = new Parser(html);
		NodeFilter aFilter = new TagNameFilter("tr");
		NodeFilter classfilter_l = new HasAttributeFilter("class", "dxgvDataRow");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter, classfilter_l });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length > 0) {
			// 取得某行的所有列
			TableColumn[] td = ((TableRow) nodes[nodes.length - 1]).getColumns();
			// System.out.println(td.length);
			if (td[2].toPlainTextString().equals("出口重箱进场")) {
				String date = td[1].toPlainTextString().trim();
				if (!StringManage.isEmpty(date) && date.length() >= 10) {// 如果有进场时间 且 距当前日期超出20天的话就返回
					date = date.substring(0, 10);
					if (Console.FS_DATE.compareDay(Console.FS_DATE.getNow(), date) > 20) {
						return url;
					}
				}
				// 设置提单号
				main.setBookingNo(td[6].toPlainTextString());
				url = td[0].childAt(1).toHtml().replace("\"", "'");
				// System.out.println(url);
				Pattern p = Pattern.compile("<a href='(http://www.spict.com/portinfo/Normal/CTNInfoPage.aspx.+ctnno=.+)' target='_blank'>.*");
				Matcher m = p.matcher(url);
				if (m.find()) {
					// System.out.println( m.group(1));
					url = m.group(1);
				}
			}
		}
		return url;
	}

	// 解析货物信息 得到 获取详细信息的地址
	private String parseW1Goods(String html, FetchDataVO main) throws Exception {
		String url = "";
		html = html.replace("﻿<html xmlns=\"http://www.w3.org/1999/xhtml\">", "").replace("</html>", "");
		Parser parser = new Parser(html);
		NodeFilter aFilter = new TagNameFilter("label");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();

		parser = new Parser(html);
		aFilter = new TagNameFilter("input");
		NodeFilter classfilter_l = new HasAttributeFilter("class", "dxeEditArea dxeEditAreaSys");
		threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter, classfilter_l });
		Node[] nodes2 = parser.parse(threadFilter).toNodeArray();

		// System.out.println(nodes.length+"-----"+nodes2.length);
		List<FetchData> goods = new ArrayList<FetchData>();
		if (nodes != null && nodes2 != null && nodes.length == nodes2.length) {
			InputTag tag;
			FetchData dt;
			for (int i = 0; i < nodes.length; i++) {
				dt = new FetchData();
				dt.setName(nodes[i].toPlainTextString());
				tag = (InputTag) nodes2[i];
				if (!StringManage.isEmpty(tag.getAttribute("value"))) {
					dt.setValue(tag.getAttribute("value"));
				}
				goods.add(dt);
				// System.out.println(dt.getIdx()+"---"+dt.getName()+"="+dt.getValue());
			}
			if (!goods.isEmpty())
				main.setGoods(goods.toArray(new FetchData[0]));
		}
		return url;
	}

	// 解析装箱单信息
	private void parseW1Packing(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		NodeFilter aFilter = new TagNameFilter("table");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		List<FetchData> packings = new ArrayList<FetchData>();
		if (nodes != null && nodes.length >= 5) {
			TableTag tg = (TableTag) nodes[4];
			TableRow[] tr = tg.getRows();
			FetchData dt;
			String str = "";
			String[] v;
			for (int i = 1; i < tr.length - 1; i = i + 2) {
				TableColumn[] td = tr[i].getColumns();
				for (int j = 0; j < td.length; j++) {
					dt = new FetchData();
					str = td[j].toPlainTextString();
					v = str.split(":&nbsp;");
					if (v != null && v.length > 0) {
						dt.setName(v[0].trim());
						if (v.length > 1) {
							dt.setValue(v[1].trim());
						}
					}
					packings.add(dt);
					// System.out.println(dt.getIdx()+"---"+dt.getName()+"="+dt.getValue());
				}
			}
			if (nodes.length >= 7) {
				// 最后一行
				tg = (TableTag) nodes[6];
				tr = tg.getRows();
				if (tr != null && tr.length > 1) {
					TableColumn[] names = tg.getRow(0).getColumns();
					TableColumn[] values = tg.getRow(1).getColumns();
					for (int i = 0; i < names.length; i++) {
						dt = new FetchData();
						dt.setName(names[i].toPlainTextString());
						dt.setValue(values[i].toPlainTextString());
						packings.add(dt);
						// System.out.println(dt.getIdx()+"---"+dt.getName()+"="+dt.getValue());
					}
				}
			}
			if (packings.isEmpty())
				main.setPackings(packings.toArray(new FetchData[0]));

		}
	}

}
