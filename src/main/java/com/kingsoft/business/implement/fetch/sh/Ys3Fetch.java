/**
 * @(#)W1Fetch.java     2014-7-3
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
 * [洋山港三期] 数据抓取服务类
 * 
 * @author LXM
 * @version 2014-7-3
 * @since JDK 1.6
 */
public class Ys3Fetch extends AbstractFetch {
	private static Logger S_logger = Logger.getLogger(Ys3Fetch.class);
	private static final long serialVersionUID = 1L;

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		FetchDataVO main = new FetchDataVO();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.Ys3Fetch] fetch ");
			}
			main.setContainerNo(search.getContainerNo());
			main.setFecthBeginTime(Console.FS_TIME.getNow());
			HttpGet httpget = new HttpGet("http://www.fob001.cn/guestbook/sh_new_api/ys3.php?txt=" + search.getContainerNo());
			HttpReturn httpReturn=executeGet(httpclient, httpget, "gb2312");
			String html = httpReturn.getReturnHtml();
			// System.out.println(html);
			if (html.indexOf("没有查到任何纪录") > -1) {
				return "";
			}

			// 解析
			parseYS3Goods(html, main);

			// 电子装箱单
			httpget = new HttpGet("http://www.fob001.cn/guestbook/sh_new_api/dzzxd.php?txt=" + main.getContainerNo());
			httpReturn=executeGet(httpclient, httpget, "utf-8");
			html = httpReturn.getReturnHtml();
			// System.out.println(html);
			if (html.indexOf("查不到您所需要的电子装箱单") == -1) {
				parseYS3Packing(html, main);
			}

			// 海关放行
			httpget = new HttpGet("http://edi.easipass.com/dataportal/query.do?entryid=&blno=" + main.getBookingNo() + "&ctnno=" + main.getContainerNo() + "&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb471004a");
			httpReturn=executeGet(httpclient, httpget, StringManage.FS_EMPTY);
			html = httpReturn.getReturnHtml();
			// System.out.println(html);
			parseCustoms(html, main);
			main.setFecthEndTime(Console.FS_TIME.getNow());

			return objToJson(main);

		} catch (Exception e) {
			if (S_logger.isDebugEnabled()) {
				S_logger.debug("fetch-center - [com.kingsoft.business.implement.fetch.sh.Ys3Fetch] fetch error");
			}
			e.printStackTrace();
		} finally {
				httpclient.close();
		}
		return "";
	}

	// 测试用例
	// public static void main(String[] args) throws Exception {
	// Ys3Fetch fetch = new Ys3Fetch();
	// String html =
	// "<html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=gb2312' /> <title>洋山三期箱货信息查询</title> <style type='text/css'> <!-- .style2 {font-size: 12px} body {font-size:9pt} td {font-size:9pt} --> </style> </head> <body> <span class='style2'> 假如发生错误，大家可以访问 http://www.fob001.cn/ys3.htm 查询 <br> <br> <br> <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=gb2312'> <title></title> </head> <!--调用自身目录下js文件--> <script language = 'javaScript' src = 'current.js' type='text/javascript'></script> <!--本页内部js--> <script language='javascript'> function openCloseDetail(i){if(i.style.display=='none'){i.style.display=''; }else{i.style.display='none'; } } </script> <body> <table id='resultTable'> <tr> <td class='query_title'>&nbsp;</td> </tr> <tr> <td class='query_title'>箱货查询结果：</td> </tr> <tr> <td height='1' colspan='2' bgcolor='#D1D3D4'></td> </tr> <tr> <td valign='top'> <table width='100%' border='0' cellpadding='0' cellspacing='5'> <tr> <td align='center'> 你查询的是箱号为 <strong>SUDU5577524</strong> 的箱货信息 </td> </tr> <tr> <td> 总共有1条纪录。点击可展开详细信息 <br></td> </tr> <tr> <td> <table width=100% border=0 cellspacing=1 cellpadding=1> <tr class=result_th> <td align=center>箱号</td> <td align=center>进场</td> <td align=center>出场</td> <td align=center>进场方式</td> <td align=center>出场方式</td> <td align=center>放关</td> <td align=center>配船</td> </tr> <tr class=result_tr1 onClick='openCloseDetail(row0)' title=点击展开详细信息 style='cursor:hand'> <td align=center height='25'>SUDU5577524</td> <td align=center height='25'>14-07-04 16:01</td> <td align=center height='25'>07-08 15:04</td> <td align=center height='25'>出口重箱进场</td> <td align=center height='25'>装船出场</td> <td align=center height='25'>Y/</td> <td align=center height='25'>--</td> </tr> <tr id='row0'> <td colspan=7> <table width=90% border=0 cellspacing=1 cellpadding=1 class=font align=center> <tr class=result_tr1> <td width=15% align=right height=25>箱号：</td> <td width=35% >SUDU5577524(无残损)</td> <td width=15% align=right>尺寸：</td> <td width=35% >40英尺</td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25>箱型：</td> <td width=35% >GP/普通</td> <td width=15% align=right >高度：</td> <td width=35% >9英尺6''</td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >状态：</td> <td width=35% >出口重箱</td> <td width=15% align=right >铅封号：</td> <td width=35% >H1178623</td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >提单号：</td> <td width=35% title=提单号(重量:件数)> <a href=?choice=2&billno=4SHAKU7146 target=_blank>4SHAKU7146</a> (19940;26;28.73;) </td> <td width=15% align=right >持箱人：</td> <td width=35% >HSD/汉堡南美（中国）船务有限公司</td> </tr> <tr class=result_tr1 > <td width=15% height=25 align=right >重量：</td> <td width=35% >23740</td> <td width=15% align=right >冷藏箱温度：</td> <td width=35% ></td> </tr> <tr class=result_tr2 > <td width=15% height=25 align=right >危险品类型：</td> <td width=35% ></td> <td width=15% align=right >危品联合国编号：</td> <td width=35% ></td> </tr> <tr class=result_tr1 > <td width=15% height=25 align=right >计划作业方式：</td> <td width=35% ></td> <td width=15% align=right >计划作业时间：</td> <td width=35% ></td> </tr> <tr > <td height=2 colspan=4 align=right ></td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >进场时间：</td> <td width=35% >14-07-04 16:01</td> <td width=15% align=right >出场时间：</td> <td width=35% >07-08 15:04</td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >进场方式：</td> <td width=35% >出口重箱进场</td> <td width=15% align=right >出场方式：</td> <td width=35% >装船出场</td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >箱变空时间：</td> <td width=35% ></td> <td width=15% align=right >箱变空原因：</td> <td width=35% ></td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >一程船船名：</td> <td width=35% ></td> <td width=15% align=right >一程船航次：</td> <td width=35% ></td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >二程船船名：</td> <td width=35% >MONTE ACONCAGUA/蒙特阿肯卡</td> <td width=15% align=right >二程船航次：</td> <td width=35% >427E</td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >装货港：</td> <td width=35% >CNSHA/上海</td> <td width=15% align=right >卸货港：</td> <td width=35% >MXLZC/拉扎诺卡蒂娜斯</td> </tr> <tr class=result_tr2 > <td width=15% height=25 align=right >目的港：</td> <td width=35% >MXLZC/拉扎诺卡蒂娜斯</td> <td width=15% align=right >放关：</td> <td width=35% >Y/</td> </tr> <tr class=result_tr1 > <td height=25 align=right >配船：</td> <td >--</td> <td align=right >场箱位：</td> <td ></td> </tr> <tr > <td height=2 colspan=4 align=right ></td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >超限箱类型：</td> <td width=35% ></td> <td width=15% align=right >&nbsp;</td> <td width=35% >&nbsp;</td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >超重：</td> <td width=35% ></td> <td width=15% align=right >超高：</td> <td width=35% ></td> </tr> <tr class=result_tr1 > <td width=15% align=right height=25 >前超长：</td> <td width=35% ></td> <td width=15% align=right >后超长：</td> <td width=35% ></td> </tr> <tr class=result_tr2 > <td width=15% align=right height=25 >左超宽：</td> <td width=35% ></td> <td width=15% align=right >右超宽：</td> <td width=35% ></td> </tr> </table> </td> </tr> </table> <table width='100%' border='0' cellspacing='1' cellpadding='1'> <tr> <td width='52%' align='left'>&nbsp;</td> <td width='12%'></td> </tr> </table> </table> </td> </tr> <tr> <td height='30' align='center'> <a href='#' onClick='window.close()'> <img src='http://www.sgict.com.cn:7001/query/images/close.jpg' width='70' height='24' border='0'></a> </td> </tr> <tr> <td height='4' colspan='2' bgcolor='#D1D3D4'></td> </tr> </table> </body> </html> </tr> </table> <br> <br> <br> <br> <br> <div style='display:none'> <script language='javascript' type='text/javascript' src='http://js.users.51.la/3524399.js'></script> <noscript> <a href='http://www.51.la/?3524399' target='_blank'> <img alt='&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;' src='http://img.users.51.la/3524399.asp' style='border:none' /> </a> </noscript> </div> </body> </html>";
	// FetchDataVO main = new FetchDataVO();
	// fetch.parseYS3Goods(html, main);
	// System.out.println(JSONObject.fromObject(main));
	// }

	// 解析货物信息
	private void parseYS3Goods(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		parser.setEncoding("gb2312");
		NodeFilter aFilter = new TagNameFilter("table");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length > 0) {
			List<FetchData> goods = new ArrayList<FetchData>();
			TableTag tg = (TableTag) nodes[2];// 第三个table
			TableRow[] tr = tg.getRows();
			FetchData dt;
			String[] vs;
			for (int i = tr.length - 2; i > 0; i = i - 2) {
				// 取得某行的所有列
				TableColumn[] td = tr[i].getColumns();
				if (td[3].toPlainTextString().replace("&nbsp;", "").equals("出口重箱进场")) {
					String date = td[1].toPlainTextString().trim();
					if (!StringManage.isEmpty(date) && date.length() >= 8) {// 如果有进场时间 且 距当前日期超出20天的话就返回
						date = Console.FS_DATE.getNow().substring(0, 2) + date.substring(0, 8);
						main.setEnterTime(date.replace("&nbsp;", ""));
						if (Console.FS_DATE.compareDay(Console.FS_DATE.getNow(), date) > 20) {
							continue;
						}
					}

					td = tr[i + 1].getColumns();
					html = td[0].toHtml();
					// System.out.println(html);
					parser = new Parser(html);
					parser.setEncoding("gb2312");
					aFilter = new TagNameFilter("table");
					threadFilter = new AndFilter();
					threadFilter.setPredicates(new NodeFilter[] { aFilter });
					nodes = parser.parse(threadFilter).toNodeArray();
					tg = (TableTag) nodes[0];
					tr = tg.getRows();
					for (int k = 0; k < tr.length; k++) {
						td = tr[k].getColumns();
						if (td != null && td.length > 1) {
							dt = new FetchData();
							dt.setName(td[0].toPlainTextString().replace("：", ""));
							dt.setValue(td[1].toPlainTextString().trim());
							goods.add(dt);
							// 设置提单号
							if (dt.getName().equals("提单号") && !StringManage.isEmpty(dt.getValue())) {
								vs = dt.getValue().split("\\(");
								if (vs.length > 0) {
									main.setBookingNo(vs[0]);
								}
							}
							// System.out.println(dt.getIdx()+"---"+dt.getName()+"="+dt.getValue());

							if (td.length > 3) {
								dt = new FetchData();
								dt.setName(td[2].toPlainTextString().replace("：", ""));
								dt.setValue(td[3].toPlainTextString().trim());
								goods.add(dt);
								// 设置提单号
								if (dt.getName().equals("提单号") && !StringManage.isEmpty(dt.getValue())) {
									vs = dt.getValue().split("\\(");
									if (vs.length > 0) {
										main.setBookingNo(vs[0]);
									}
								}
								// System.out.println(dt.getIdx()+"---"+dt.getName()+"="+dt.getValue());
							}
						}
					}
					main.setGoods(goods.toArray(new FetchData[0]));
					break;
				}
			}
		}
	}

	// 解析 装箱单信息
	private void parseYS3Packing(String html, FetchDataVO main) throws Exception {
		Parser parser = new Parser(html);
		NodeFilter aFilter = new TagNameFilter("table");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { aFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		if (nodes != null && nodes.length >= 5) {
			List<FetchData> packings = new ArrayList<FetchData>();
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
