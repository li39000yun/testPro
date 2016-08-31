package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.dao.entity.baseinfo.customs.ShipPlan;

/**
 * 该类为读取外五船泊进箱计划 数据业务类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 * 
 */
public class ShipPlanW5Fetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	// 外五的定时抓取船舶计划
	public List<ShipPlan> fetch(String v_type) throws Exception {
		CloseableHttpClient httpclient = createHttps();// 创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();// 返回列表数据
		try {
			StringBuffer url = new StringBuffer(
					"http://www.smct.com.cn/wat/controllerServlet.do?velscope=p&veltype=")
					.append(v_type)
					.append("&imexport=&queryid=0001003&method=doquery&querysid=g0001&showpage=1");// 外五期
			// v_type=%3C%3E%27BAR%27大船船舶计划
			// v_type=%3D%27BAR%27 驳船计划
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn = executeGet(httpclient, httpget, "GBK");// 获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(2);// 提取第三个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				TableColumn[] col;
				col = rows[0].getColumns();
				String page = col[0].getStringText().trim();
				myParser = Parser.createParser(page, "UTF-8");
				NodeFilter f1 = new HasAttributeFilter("class", "p_pages");
				AndFilter threadFilter = new AndFilter();
				threadFilter.setPredicates(new NodeFilter[] { f1 });
				Node[] nodes = myParser.parse(threadFilter).toNodeArray();
				if (nodes != null && nodes.length > 0) {
					page = nodes[0].toPlainTextString().replace("&nbsp;", "")
							.split("/")[1];
				}
				int pageCount = Integer.parseInt(page);

				// 分页
				for (int i = 1; i <= pageCount; i++) {
					this.fetchPage(i, v_type, list, httpclient); // 分页读取
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close(); // 关闭链接
		}
		return list;
	}

	public void fetchPage(int page, String v_type, List<ShipPlan> list,
			CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://www.smct.com.cn/wat/controllerServlet.do?velscope=p&veltype=")
				.append(v_type)
				.append("&imexport=&queryid=0001003&method=doquery&querysid=g0001&showpage=")
				.append(page);// 外五期 v_type=%3C%3E%27BAR%27大船船舶计划
		// v_type=%3D%27BAR%27 驳船计划
		HttpGet httpget = new HttpGet(url.toString());
		HttpReturn httpReturn = executeGet(httpclient, httpget, "GBK");// 获取html页面数据
		
		String html = httpReturn.getReturnHtml();
		Parser myParser;
		myParser = Parser.createParser(html, "UTF-8");
		myParser.setEncoding("UTF-8");// 设置编码
		NodeFilter filter = new TagNameFilter("table"); // 过滤table
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
		TableTag tabletag = (TableTag) nodeList.elementAt(1);// 提取第2个table节点
		TableRow[] rows = tabletag.getRows();// 数据行数
		int len = rows.length;
		if (rows != null && len > 1) {
			ShipPlan shipPlan = null;
			TableColumn[] col;
			for (int i = 1; i < len; i++) {
				col = rows[i].getColumns();
				shipPlan = new ShipPlan();
				shipPlan.setDock("外五期");
				shipPlan.setNameEn(col[1].toPlainTextString().replace(
						"&nbsp;", ""));
				shipPlan.setVoyage(col[2].toPlainTextString().replace(
						"&nbsp;", ""));
				shipPlan.setHarborTimeStart(col[4].toPlainTextString()
						.replace("&nbsp;", ""));
				shipPlan.setHarborTimeEnd(col[5].toPlainTextString()
						.replace("&nbsp;", ""));
				if (shipPlan.getHarborTimeEnd().length() < 16) {
					shipPlan.setHarborTimeEnd(shipPlan.getHarborTimeStart()
							.substring(0, 5) + shipPlan.getHarborTimeEnd());
				}

				list.add(shipPlan);
			}
		}
	}

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
