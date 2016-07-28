package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
 * 该类为读取宜东船泊进箱计划 数据业务类 
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */
public class ShipPlanZHBFetch extends AbstractFetch{
	private static final long serialVersionUID = 1L;

   // 宜东的定时抓取船舶计划  
	public List<ShipPlan> fetch() throws Exception {
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<ShipPlan> list = new ArrayList<ShipPlan>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.sydct.com.cn/webroot/controllerServlet.do?method=getsubformsinput&querysid=g0002");// 宜东
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Header[] hs = httpReturn.getHead();
			String cookie = "";
			for (Header h : hs) {
				if (h.getName().equals("Set-Cookie")) {
					cookie = h.getValue();
				}
			}
			
			// 响应头信息
			HttpPost httpPost = new HttpPost("http://www.sydct.com.cn/webroot/controllerServlet.do?queryid=0002001");
			httpPost.setHeader("Cookie", cookie);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("method", "doquery"));
			nvps.add(new BasicNameValuePair("querysid", "g0002"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			httpReturn=executePost(httpclient, httpPost, "utf-8");
			html = httpReturn.getReturnHtml();
		
			System.out.println(html);
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
				"http://www.sydct.com.cn/webroot/controllerServlet.do?queryid=0002001&querysid=g0002&method=doquery&showpage=")
				.append(page);// 宜东
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
					shipPlan.setDock("宜东");
					shipPlan.setNameEn(col[1].getStringText()
							.replaceAll("&nbsp;", "").trim());// 存中文名称
					shipPlan.setVoyage(col[3].getStringText()
							.replaceAll("&nbsp;", "").trim());
					shipPlan.setHarborTimeStart(col[5].elements().nextNode()
							.getChildren().elementAt(0).getText()
							.replaceAll("&nbsp;", "").trim());// 计划靠泊时间
					if (col[6].getStringText().replaceAll("&nbsp;", "").trim()
							.length() < 16) {// 防止没有年
						shipPlan.setHarborTimeEnd(shipPlan.getHarborTimeStart()
								.substring(0, 4)
								+ "-"
								+ col[6].getStringText()
										.replaceAll("&nbsp;", "").trim());// 计划离泊时间
					} else {
						shipPlan.setHarborTimeEnd(col[6].getStringText()
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
