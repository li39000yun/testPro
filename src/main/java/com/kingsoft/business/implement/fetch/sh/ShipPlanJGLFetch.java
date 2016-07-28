package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
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
import com.kingsoft.dao.entity.baseinfo.customs.ShipPlan;

/**
 * 该类为读取军工路船泊进箱计划 数据业务类 
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */
public class ShipPlanJGLFetch extends AbstractFetch{
	private static final long serialVersionUID = 1L;

   // 军工路的定时抓取船舶计划  
	public List<ShipPlan> fetch() throws Exception {
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://116.228.229.252/webroot/controllerServlet.do?queryid=0001001&imexport=&method=doquery&querysid=g0001&velscope=p&veltype=%3C%3E'BAR'");// 军工路
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(2);// 提取第3个table节点
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

	public void fetchPage(int page,List<ShipPlan> list,CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://116.228.229.252/webroot/controllerServlet.do?queryid=0001001&imexport=&method=doquery&querysid=g0001&velscope=p&veltype=%3C%3E'BAR'&showpage=")
				.append(page);
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			
			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html.replace("\r\n", ""),
					"UTF-8");

			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(1);// 提取第2个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				ShipPlan shipPlan = null;
				TableColumn[] col;
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					shipPlan = new ShipPlan();
					shipPlan.setDock("军工路");
					shipPlan.setNameEn(col[0].getStringText()
							.replaceAll("&nbsp;", "").trim());// 存放中文船名
					shipPlan.setVoyage(col[2].getStringText()
							.replaceAll("&nbsp;", "").trim());
					shipPlan.setHarborTimeStart(col[4].elements().nextNode()
							.getChildren().elementAt(0).getText()
							.replaceAll("&nbsp;", "").trim());// 计划靠泊时间
					if (col[5].getStringText().replaceAll("&nbsp;", "").trim()
							.length() < 16) {// 防止没有年
						shipPlan.setHarborTimeEnd(shipPlan.getHarborTimeStart()
								.substring(0, 4)
								+ "-"
								+ col[5].getStringText()
										.replaceAll("&nbsp;", "").trim());// 计划离泊时间
					} else {
						shipPlan.setHarborTimeEnd(col[5].getStringText()
								.replaceAll("&nbsp;", "").trim());// 计划离泊时间
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
