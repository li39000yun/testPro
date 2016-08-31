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
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.dao.entity.baseinfo.customs.ShipPlan;

/**
 * 该类为读取外二船泊进箱计划 数据业务类 
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */
public class ShipPlanW2Fetch extends AbstractFetch{
	private static final long serialVersionUID = 1L;

   // 外二的定时抓取船舶计划  
	public List<ShipPlan> fetch() throws Exception {
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.sipgzct.com/wat/controllerServlet.do?querysid=g0001&velscope=p&queryid=0001003&imexport=&method=doquery&veltype=%3C%3E%27BAR%27");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"GBK");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser parser = new Parser(html);
			parser.setEncoding("UTF-8");
			NodeFilter tableFilter = new TagNameFilter("table");
			NodeFilter classfilter = new HasAttributeFilter("class",
					"availability-table");
			AndFilter threadFilter = new AndFilter();
			threadFilter.setPredicates(new NodeFilter[] { tableFilter,
					classfilter });
			Node[] nodes = parser.parse(threadFilter).toNodeArray();
			if (nodes == null || nodes.length == 0) {
				return list;
			}
			TableTag tabletag = (TableTag) nodes[0];
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				TableColumn[] col = rows[0].getColumns();
				SimpleNodeIterator div = col[0].elements();// div
				NodeList links = div.nextNode().getChildren();// <a>
				LinkTag a = (LinkTag) links.elementAt(1);// 总页数在第二个链接
				String page = a.getLinkText().replaceAll("&nbsp;", "").trim();
				page = page.substring(page.indexOf("/") + 1, page.length());
				int pageCount = Integer.parseInt(page);

				// 分页
				for (int i = 1; i <= pageCount; i++) {
					this.fetchPage(i,list,httpclient); // 分页读取
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close(); //关闭链接
		}
		return list;
	}

	// 外二分页读取页面数据
	public void fetchPage(int page, List<ShipPlan> list, CloseableHttpClient httpclient)
			throws Exception {
		StringBuffer url = new StringBuffer(
				"http://www.sipgzct.com/wat/controllerServlet.do?querysid=g0001&velscope=p&queryid=0001003&imexport=&method=doquery&veltype=%3C%3E%27BAR%27&showpage=")
				.append(page);// 外二
		HttpGet httpget = new HttpGet(url.toString());
		HttpReturn httpReturn = executeGet(httpclient, httpget, "GBK");// 获取html页面数据
		String html = httpReturn.getReturnHtml();

		Parser parser = new Parser(html);
		parser.setEncoding("UTF-8");
		NodeFilter tableFilter = new TagNameFilter("table");
		NodeFilter idFilter = new HasAttributeFilter("id", "ctable");
		AndFilter threadFilter = new AndFilter();
		threadFilter.setPredicates(new NodeFilter[] { tableFilter, idFilter });
		Node[] nodes = parser.parse(threadFilter).toNodeArray();
		TableTag tabletag = (TableTag) nodes[0];
		TableRow[] rows = tabletag.getRows();// 数据行数
		int len = rows.length;
		if (len > 0) {
			ShipPlan shipPlan = null;
			TableColumn[] col;
			for (int i = 1; i < len; i++) {
				col = rows[i].getColumns();
				shipPlan = new ShipPlan();
				shipPlan.setDock("外二期");
				if (col.length > 1) {
					String nameEn = col[1].toPlainTextString().replaceAll("&nbsp;", "").trim();
					shipPlan.setNameEn(nameEn);
				}
				if (col.length > 2) {
					String voyage = col[2].toPlainTextString().replaceAll("&nbsp;", "").trim();
					shipPlan.setVoyage(voyage);
				}
				if (col.length > 4) {
					String harborTimeStart = col[4].toPlainTextString().replaceAll("&nbsp;", "")
							.trim();
					shipPlan.setHarborTimeStart(harborTimeStart);// 计划靠泊时间
				}
				if (col.length > 5) {
					String harborTimeEnd = col[5].toPlainTextString().replaceAll("&nbsp;", "")
							.trim();
					if (harborTimeEnd.length() < 16
							&& !StringManage.isEmpty(shipPlan.getHarborTimeStart())) {
						harborTimeEnd = shipPlan.getHarborTimeStart().substring(0, 4) + "-"
								+ harborTimeEnd;// 计划离泊时间
					}
					shipPlan.setHarborTimeEnd(harborTimeEnd);// 计划离泊时间
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
