/**
 * @(#)W1Fetch.java     2014-7-3
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch.sh;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchData;
import com.kingsoft.business.vo.fetch.FetchDataVO;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * [外五期] 数据抓取服务类
 * 
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class W5Fetch extends AbstractFetch {
	private static Logger S_logger = Logger.getLogger(W5Fetch.class);
	private static final long serialVersionUID = 1L;
	// 外五
	private static final String FS_W5_GOODS = "http://www.smct.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0003";// 外五箱货查询
	private static final String FS_W5_PACKING = "http://www.smct.com.cn/wat/controllerServlet.do?queryid=0006001";// 外五装箱单查询

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		FetchDataVO main = new FetchDataVO();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W5Fetch] fetch ");
			}

			main.setContainerNo(search.getContainerNo());
			main.setFecthBeginTime(Console.FS_TIME.getNow());

			HttpGet httpget = new HttpGet(FS_W5_GOODS);
			HttpReturn httpReturn = executeGet(httpclient, httpget, StringManage.FS_EMPTY);

			String html = httpReturn.getReturnHtml();
			Header[] hs = httpReturn.getHead();

			String cookie = "";
			for (Header h : hs) {
				if (h.getName().equals("Set-Cookie")) {
					cookie = h.getValue();
				}
			}

			// 响应头信息
			HttpPost httpPost = new HttpPost("http://www.smct.com.cn/wat/controllerServlet.do?queryid=0003001");
			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.smct.com.cn");
			httpPost.setHeader("Referer", "	http://www.smct.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0003");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:20.0) Gecko/20100101 Firefox/20.0");

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("iyc_cntrno", search.getContainerNo()));
			nvps.add(new BasicNameValuePair("method", "doquery"));
			nvps.add(new BasicNameValuePair("querysid", "g0003"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn = executePostGzip(httpclient, httpPost, StringManage.FS_EMPTY);

			html = httpReturn.getReturnHtml();

			// System.out.println(html);
			parseW5Goods(html, main);

			// 如果箱货信息里没查到记录时就返回
			if (main.getGoods().length == 0) {
				return "";
			}

			// 装箱单信息查询
			httpPost = new HttpPost(FS_W5_PACKING);
			httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Encoding", "gzip, deflate");
			httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("Cookie", cookie);
			httpPost.setHeader("Host", "www.smct.com.cn");
			httpPost.setHeader("Referer", "	http://www.smct.com.cn/wat/controllerServlet.do?method=getsubformsinput&querysid=g0006");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:20.0) Gecko/20100101 Firefox/20.0");
			// 查询参数
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("cntrnoinput", search.getContainerNo()));
			nvps.add(new BasicNameValuePair("method", "doquery"));
			nvps.add(new BasicNameValuePair("querysid", "g0006"));

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn = executePostGzip(httpclient, httpPost, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			parseW5Packing(html, main);

			// 海关放行
			httpget = new HttpGet("http://edi.easipass.com/dataportal/query.do?entryid=&blno=" + main.getBookingNo() + "&ctnno=" + main.getContainerNo() + "&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb471004a");
			httpReturn = executeGetGzip(httpclient, httpget, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			parseCustoms(html, main);

			main.setFecthEndTime(Console.FS_TIME.getNow());
			return objToJson(main);

		} catch (Exception e) {
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.W5Fetch] fetch error");
			}
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
		return "";
	}

	// 解析货物信息
	private void parseW5Goods(String html , FetchDataVO main) throws Exception {
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
				main.setEnterTime(td[1].toPlainTextString().trim().replace("&nbsp;", ""));
				if (td[3].toPlainTextString().replace("&nbsp;", "").equals("出口重箱进场")) {
					String date = td[1].toPlainTextString().trim();
					if (!StringManage.isEmpty(date) && date.length() >= 8) {// 如果有进场时间
																			// 且
																			// 距当前日期超出20天的话就返回
						date = Console.FS_DATE.getNow().substring(0, 2) + date.substring(0, 8);
						if (Console.FS_DATE.compareDay(Console.FS_DATE.getNow(), date) > 20) {
							continue;
						}
					}
					String str = td[10].toHtml().replace("&acute;", "'").replace("\\", "").replace("(\"", "").replace("\");", "");
					Pattern p = Pattern.compile("(.*onclick='todetail(.+)'.*)");
					Matcher m = p.matcher(str);
					if (m.matches()) {
						str = m.group(2);
					}
					ss = str.split("##");
					for (String s : ss) {
						vs = s.split(":=");
						if (vs.length == 0)
							continue;
						dt = new FetchData();
						dt.setName(vs[0]);
						if (vs.length > 1) {// 冷藏箱温度= 像这种split后长度为1
							dt.setValue(vs[1]);
						}
						goods.add(dt);
						// System.out.println(dt.getIdx()+"------"+dt.getName()+"="+dt.getValue());
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

	// 测试用例
	public static void main(String[] args) throws Exception {
		W5Fetch fetch = new W5Fetch();
		String html = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'> <HTML lang=en xml:lang='en' xmlns='http://www.w3.org/1999/xhtml'><HEAD><TITLE>通用查询系统-详细信息</TITLE> <META content='text/html; charset=utf-8' http-equiv=Content-Type><LINK rel=stylesheet type=text/css href='/wat/css/style.css'> <STYLE type=text/css></STYLE> </HEAD> <BODY style='BORDER-BOTTOM: 0px; BORDER-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; BORDER-TOP: 0px; BORDER-RIGHT: 0px; PADDING-TOP: 0px' onload=loadInfor()> <DIV id=main><LINK rel=stylesheet type=text/css href='style.css'> <SCRIPT type=text/javascript> function loadWidth() {var otable = document.getElementById('ctable'); var itable = document.getElementById('itable'); itable.width = otable.offsetWidth+20; } </SCRIPT> <TABLE id=itable border=0 cellSpacing=0 cellPadding=0 width=860 align=center> <TBODY> <TR> <TD height=18 width=648 colSpan=2 align=middle>&nbsp;</TD></TR> <TR> <TD height=18 vAlign=center><IMG src='/wat/images/loc.gif' width=252 height=73></TD> <TD height=18 align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<IMG src='/wat/images/1111.jpg' width=580 height=100></TD></TR> <TR> <TD height=30 width=648 colSpan=2 align=left>&nbsp;<SPAN class=lanmu_title><FONT color=#ff0000>|</FONT> 当前位置-&gt;[普通查询]</SPAN> 可切换到&lt; <A href='/wat/controllerServlet.do?method=getsubformsinput&amp;querysid=r1000'>注册用户</A> || <A href='/wat/application/getapplication.jsp'>旧版网上受理</A> &gt; </TD></TR></TBODY></TABLE><SPAN style='FONT-SIZE: 12px' class=f6>&nbsp;&nbsp;&nbsp;&nbsp;详细信息</SPAN> <DIV style='TEXT-ALIGN: center' id=cv> <TABLE style='MARGIN-LEFT: 15px' class=x3 border=0 cellSpacing=0 cellPadding=0 width=700> <TBODY> <TR> <TD class='x1 h2' width='20%'>箱号</TD> <TD class=x4 width='30%' align=middle>SNBU2226866&nbsp;</TD> <TD class='x1 h2' width='20%'>尺寸</TD> <TD class=x4 width='30%' align=middle>20´&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>箱型</TD> <TD class=x4 width='30%' align=middle>GP/普通&nbsp;</TD> <TD class='x1 h2' width='20%'>高度</TD> <TD class=x4 width='30%' align=middle>8´6´´&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>状态</TD> <TD class=x4 width='30%' align=middle>出口重箱&nbsp;</TD> <TD class='x1 h2' width='20%'>铅封号</TD> <TD class=x4 width='30%' align=middle>5492455&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>提单号</TD> <TD class=x4 width='30%' align=middle>SNL4SHJL11E9198(17126.3;1429;24.52;)&nbsp;</TD> <TD class='x1 h2' width='20%'>持箱人</TD> <TD class=x4 width='30%' align=middle>SNL/中外运集装箱运输有限公司&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>重量</TD> <TD class=x4 width='30%' align=middle>19326&nbsp;</TD> <TD class='x1 h2' width='20%'>冷藏箱温度</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>危险品类型</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>危品联合国编号</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>计划作业方式</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>计划作业</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>进场时间</TD> <TD class=x4 width='30%' align=middle>14-07-0114:21&nbsp;</TD> <TD class='x1 h2' width='20%'>出场时间</TD> <TD class=x4 width='30%' align=middle>07-0423:03&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>进场方式</TD> <TD class=x4 width='30%' align=middle>出口重箱进场&nbsp;</TD> <TD class='x1 h2' width='20%'>出场方式</TD> <TD class=x4 width='30%' align=middle>装船出场&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>箱变空</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>箱变空原因</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>一程船船名</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>一程船航次</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>二程船船名</TD> <TD class=x4 width='30%' align=middle>FORMOSACONTAINERN/泛亚东京&nbsp;</TD> <TD class='x1 h2' width='20%'>二程船航次</TD> <TD class=x4 width='30%' align=middle>129E&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>装货港</TD> <TD class=x4 width='30%' align=middle>CNSHA/上海&nbsp;</TD> <TD class='x1 h2' width='20%'>卸货港</TD> <TD class=x4 width='30%' align=middle>JPYOK/横滨&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>目的港</TD> <TD class=x4 width='30%' align=middle>JPYOK/横滨&nbsp;</TD> <TD class='x1 h2' width='20%'>放关</TD> <TD class=x4 width='30%' align=middle>Y&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>空箱放关</TD> <TD class=x4 width='30%' align=middle>N&nbsp;</TD> <TD class='x1 h2' width='20%'>配船</TD> <TD class=x4 width='30%' align=middle>--&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>场箱位</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>超限箱类型</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'></TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>超重</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>超高</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>前超长</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>后超长</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>左超宽</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>右超宽</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>调箱门</TD> <TD class=x4 width='30%' align=middle>N&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>发票号</TD> <TD class=x4 width='30%' align=middle>11000002499,11000002498&nbsp;</TD> <TD class='x1 h2' width='20%'>残损标记</TD> <TD class=x4 width='30%' align=middle>无残损&nbsp;</TD></TR> <TR> <TD class='x1 h2' width='20%'>装卸泊位</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD> <TD class='x1 h2' width='20%'>装卸码头</TD> <TD class=x4 width='30%' align=middle>&nbsp;</TD></TR></TBODY></TABLE></DIV><BR><SPAN style='MARGIN-LEFT: 400px'><INPUT class=Button onclick=window.close() value=关闭 type=button></SPAN> <DIV class=copyright>Copyright (C) 2007-2010 All Rights Reserved. <A href='http://www.hb56.com'>上海海勃物流软件有限公司</A> <BR>&nbsp; </DIV></DIV> <SCRIPT type=text/javascript> function loadInfor() {var infor = opener.infor; var sbf = '<table width=\'700px\' border=\'0\' cellpadding=\'0\' cellspacing=\'0\' class=\'x3\' style=\'margin-left: 15px\'>'; var array = infor.split('##'); for (var i=0; i<array.length ; i++ ) {var o = array[i]; var offset = o.indexOf(':='); var name = o.substring(0, offset); var value = o.substring(offset+2,o.length); if(i%2==0)sbf +='<tr><td width=\'20%\' class=\'x1 h2\'>' + name + '</td><td width=\'30%\' class=\'x4\' align='center'>' + value + '&nbsp;</td>'; if(i%2==1)sbf +='<td width=\'20%\' class=\'x1 h2\'>' + name + '</td><td width=\'30%\' class=\'x4\' align='center'>' + value + '&nbsp;</td></tr>'; //sbf +='<tr><td class=\'x1 h2\'>' + name + '</td><td class=\'x4\'>&nbsp;&nbsp;&nbsp;&nbsp;' + value + '&nbsp;</td></tr>'; } if(array.length%2==1)sbf +='<td width=\'20%\' class=\'x1 h2\'>&nbsp;</td><td width=\'30%\' class=\'x4\'>&nbsp;</td></tr>'; sbf += '</table>'; var cv = document.getElementById('cv'); cv.innerHTML = sbf; } </SCRIPT> </BODY></HTML>";
		FetchDataVO main = new FetchDataVO();
		fetch.parseW5Packing(html, main);
		System.out.println(JSONObject.fromObject(main));
	}

	// 解析 装箱单信息
	private void parseW5Packing(String html , FetchDataVO main) throws Exception {
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
			String[] ss;
			String[] vs;
			FetchData dt;
			if (tr != null && tr.length > 1) {
				for (int i = 1; i < tr.length; i++) {
					// 取得某行的所有列
					TableColumn[] td = tr[i].getColumns();
					String str = td[5].toHtml().replace("('", "").replace("');", "").replace("\"", "'").replaceAll("\\s", "");
					if (str.indexOf(main.getBookingNo()) > -1) {
						// System.out.println(str);
						Pattern p = Pattern.compile("(.*onclick='todetail(.+)'.*)");
						Matcher m = p.matcher(str);
						if (m.matches()) {
							// System.out.println(m.group(2));
							str = m.group(2);
						}
						ss = str.split("##");
						for (String s : ss) {
							// System.out.println(s);
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

	/**
	 * 解压缩数据gzip
	 * 
	 * @param response
	 * @param entity
	 * @return String 返回解压缩的数据
	 * @throws Exception
	 **/
	protected String GzipToString(HttpResponse response , HttpEntity entity) throws Exception {
		String html = "";
		// 压缩的数据，解压缩gzip
		boolean isGzip = false;
		Header[] hs = response.getAllHeaders();
		for (Header h : hs) {
			if (h.getName().equals("Content-Encoding")) {
				if (h.getValue().equals("gzip")) {
					isGzip = true;
				}
			}
		}
		InputStream is = null;
		GZIPInputStream gzipo = null;
		ByteArrayOutputStream outStream = null;

		try {
			if (isGzip) {
				is = entity.getContent();
				gzipo = new GZIPInputStream(is);
				outStream = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = gzipo.read(b)) != -1) {
					outStream.write(b, 0, len);
				}
				html = new String(outStream.toByteArray(), "utf-8");
			} else {
				html = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			if (S_logger.isDebugEnabled())
				S_logger.debug("service_center - W5Fetch" + e.getMessage());
		} finally {
			if (is != null)
				is.close();
			if (gzipo != null)
				gzipo.close();
			if (outStream != null) {
				outStream.flush();
				outStream.close();
			}
		}
		return html;
	}

}
