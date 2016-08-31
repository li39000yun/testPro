

package com.kingsoft.business.implement.port.anchor;

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
import com.kingsoft.dao.entity.baseinfo.customs.Anchor;

/**
 * 该类为读取洋山港三期港口数据实现类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */

public class PortShipYS3Fetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	public List<Anchor> fetch() throws Exception {
		// 洋山港三
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<Anchor> temp = new ArrayList<Anchor>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.sgict.com.cn:7001/query/search/comm/continplan.jsp");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			
			Parser myParser;
			String filterStr = "table";// 节点名
			myParser = Parser.createParser(html.toString(), "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(3);// 提取第三个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				TableColumn[] col;
				col = rows[0].getColumns();
				String page = col[4].getStringText().trim();
				page = page.substring(page.indexOf("page=") + 5, page.indexOf("&"));
				int pageCount = Integer.parseInt(page);
	
				// 分页
				for (int i = 1; i <= pageCount; i++) {
					this.fetchPage(i,temp,httpclient); // 分页读取
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
	public void fetchPage(int page,List<Anchor> temp,CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://www.sgict.com.cn:7001/query/search/comm/continplan.jsp?page=")
				.append(page).append("&");// 洋山港三期
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			
			Parser myParser;
			String filterStr = "table";// 节点名
	
			myParser = Parser.createParser(html.replace("\r\n", "")
					.replace("<font color=red><b>", "").replace("</b></font>", ""),
					"UTF-8");
	
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(2);// 提取第二个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				Anchor anchor = null;
				TableColumn[] col;
				
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					anchor = new Anchor();

					anchor.setDock("洋山港三期");
					anchor.setNameCn(col[0].getStringText().trim());// 中文船名
					anchor.setNameEn(col[1].getStringText().trim());// 英文船名
					anchor.setCsi(col[2].getStringText().trim());// 船代码
					anchor.setVoyageIn("");
					anchor.setVoyageOut(col[3].getStringText().trim());// 出港航次
					anchor.setHarborTimeStart(col[5].getStringText().trim());// 开始收箱时间
					if (col[6].getStringText().trim().length() < 12) {// 防止没有年
						anchor.setHarborTimeEnd(anchor.getHarborTimeStart()
								.substring(0, 4)
								+ "-" + col[6].getStringText().trim());// 截止收箱时间
					} else if (col[6].getStringText().trim().length() > 19){
						anchor.setHarborTimeEnd(col[6].getStringText().trim().substring(0, 19));// 截止收箱时间
					} else {
						anchor.setHarborTimeEnd(col[6].getStringText().trim());// 截止收箱时间
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