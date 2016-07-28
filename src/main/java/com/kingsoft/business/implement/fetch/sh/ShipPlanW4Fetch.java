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
import com.kingsoft.dao.entity.baseinfo.customs.ShipPlan;

/**
 * 该类为读取外四船泊进箱计划 数据业务类 
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */
public class ShipPlanW4Fetch extends AbstractFetch{
	private static final long serialVersionUID = 1L;

   // 外四的定时抓取船舶计划  
	public List<ShipPlan> fetch() throws Exception {
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.sect.com.cn/wat/controllerServlet.do?querysid=g0001&velscope=p&queryid=0001003&imexport=&method=doquery&veltype=%3C%3E%27BAR%27");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"GBK");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(3);// 提取第4个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				TableColumn[] col;
				col = rows[0].getColumns();
				String str = col[0].getStringText().trim();
				int beidx = str.lastIndexOf("&showpage=");
				int edidx = str.lastIndexOf("\">");
				String page = str.substring(beidx + 10, edidx);
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
			StringBuffer url = new StringBuffer("http://www.sect.com.cn/wat/controllerServlet.do?querysid=g0001&velscope=p&queryid=0001003&imexport=&method=doquery&veltype=%3C%3E%27BAR%27")
				.append("&showpage=").append(page);// 外四
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"GBK");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html.replace("&nbsp;", "")
					.replace("<span class='cssRed'>", "")
					.replace("</span>", "").replace("<span class=''>", ""),
					"UTF-8");

			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(2);// 提取第3个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				ShipPlan shipPlan = null;
				TableColumn[] col;
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					shipPlan = new ShipPlan();
					shipPlan.setDock("外四期");
					shipPlan.setNameEn(col[1].getStringText().trim());
					shipPlan.setVoyage(col[2].getStringText().trim());
					shipPlan.setHarborTimeStart(col[4].getStringText().trim());// 计划靠泊时间
					if (col[5].getStringText().trim().length() < 12) {// 防止没有年
						shipPlan.setHarborTimeEnd(shipPlan.getHarborTimeStart()
								.substring(0, 4)
								+ "-"
								+ col[5].getStringText().trim());// 计划离泊时间
					} else {
						shipPlan.setHarborTimeEnd(col[5].getStringText().trim());// 计划离泊时间
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
