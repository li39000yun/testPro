

package com.kingsoft.business.implement.port.anchor;

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
import com.kingsoft.dao.entity.baseinfo.customs.Anchor;

/**
 * 该类为读取洋山港一期港口数据实现类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */

public class PortShipYS1Fetch extends AbstractFetch {

	private static final long serialVersionUID = 1L;

	public List<Anchor> fetch() throws Exception {// 洋山一
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<Anchor> temp = new ArrayList<Anchor>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://cx.shsict.com:8100/wat/controllerServlet.do?querysid=g0002&queryid=0002001&button=%E7%A1%AE++%E5%AE%9A&method=doquery&showpage=1");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser parser = new Parser(html);
			parser.setEncoding("UTF-8");
			NodeFilter tableFilter = new TagNameFilter("table");
			NodeFilter classfilter = new HasAttributeFilter("class", "availability-table");
			AndFilter threadFilter = new AndFilter();
			threadFilter.setPredicates(new NodeFilter[] { tableFilter, classfilter });
			Node[] nodes = parser.parse(threadFilter).toNodeArray();
			if (nodes == null || nodes.length == 0) {
				return temp;
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
					this.fetchPage(i, temp,httpclient);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close(); //关闭链接
		}
		return temp;
	}

	// 分页读取页面数据
	public void fetchPage(int page, List<Anchor> temp,CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://cx.shsict.com:8100/wat/controllerServlet.do?querysid=g0002&queryid=0002001&button=%E7%A1%AE++%E5%AE%9A&method=doquery&showpage=")
				.append(page);// 外二
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();

			Parser parser = new Parser(html);
			parser.setEncoding("UTF-8");
			NodeFilter tableFilter = new TagNameFilter("table");
			NodeFilter idFilter = new HasAttributeFilter("id", "ctable");
			AndFilter threadFilter = new AndFilter();
			threadFilter.setPredicates(new NodeFilter[] { tableFilter, idFilter });
			Node[] nodes = parser.parse(threadFilter).toNodeArray();
			if (nodes == null || nodes.length == 0) {
				return;
			}
			TableTag tabletag = (TableTag) nodes[0];
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				Anchor anchor = null;

				TableColumn[] col;
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					
					anchor = new Anchor();
					anchor.setDock("洋山港一期");
					anchor.setNameCn(col[0].getStringText().replaceAll("&nbsp;", "").trim());// 中文船名
					anchor.setNameEn(col[1].getStringText().replaceAll("&nbsp;", "").trim());// 英文船名
					anchor.setCsi(col[2].getStringText().replaceAll("&nbsp;", "").trim());// 船代码
					anchor.setVoyageIn("");
					anchor.setVoyageOut(col[3].getStringText().replaceAll("&nbsp;", "").trim());// 出港航次
					anchor.setHarborTimeStart(col[5].elements().nextNode().getChildren().elementAt(0).getText()
							.replaceAll("&nbsp;", "").trim());// 开始收箱时间
					if (col[6].getStringText().replaceAll("&nbsp;", "").trim().length() < 16) {// 防止没有年
						anchor.setHarborTimeEnd(anchor.getHarborTimeStart().substring(0, 4) + "-"
								+ col[6].getStringText().replaceAll("&nbsp;", "").trim());// 截止收箱时间
					} else {
						anchor.setHarborTimeEnd(col[6].getStringText().replaceAll("&nbsp;", "").trim());// 计划离泊时间
					}
					temp.add(anchor);
				}
			}
	}

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

