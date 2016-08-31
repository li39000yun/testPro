package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.dao.entity.baseinfo.customs.ShipPlan;

/**
 * 该类为读取洋山港三期船泊进箱计划 数据业务类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 * 
 */
public class ShipPlanYS3Fetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	// 洋山港三期的定时抓取船舶计划
	public List<ShipPlan> fetch(String v_type) throws Exception {
		CloseableHttpClient httpclient = createHttp();// 创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();// 返回列表数据
		try {
			StringBuffer url = new StringBuffer(
					"http://www.sgict.com.cn:7001/query/search/comm/shipplan_j.jsp?isI=a&v_type=")
					.append(v_type).append("&");// 洋山港三期 v_type=d大船船舶计划 v_type=b
												// 驳船计划
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
				String page = col[4].getStringText().trim();
				int pageCount = 1;
				if (!StringManage.isEmpty(page)) {
					page = page.substring(page.indexOf("page=") + 5,
							page.indexOf("&"));
					pageCount = Integer.parseInt(page);
				}

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

	public void fetchPage(int page, String typeName, List<ShipPlan> list,
			CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://www.sgict.com.cn:7001/query/search/comm/shipplan_j.jsp?page=")
				.append(page).append("&isI=a&v_type=").append(typeName)
				.append("&");// 洋山港三期
								// v_type=d大船船舶计划
								// v_type=b
								// 驳船计划
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
				shipPlan.setDock("洋山港三期");
				shipPlan.setNameEn(col[1].toPlainTextString());
				shipPlan.setVoyage(col[2].toPlainTextString());
				shipPlan.setHarborTimeStart(col[4].toPlainTextString());
				shipPlan.setHarborTimeEnd(col[5].toPlainTextString());
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
