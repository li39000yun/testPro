


package com.kingsoft.business.implement.port.anchor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.htmlparser.Node;
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
 * 该类为读取外一港口数据实现类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */

public class PortShipW1Fetch extends AbstractFetch  {
	private static final long serialVersionUID = 1L;

	public List<Anchor> fetch() throws Exception {// 外一
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<Anchor> list = new ArrayList<Anchor>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.fob001.cn/guestbook/jxjh/indexw1.php?");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
	
			Parser myParser;
			String filterStr = "span";// 节点名
	
			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建span节点
			if(nodeList!=null && nodeList.size()>0){
				Node tag = (Node) nodeList.elementAt(0);// 提取第1个span节点
				String page = tag.toPlainTextString().trim();
				page = page.substring(page.indexOf("/")+1, page.length());
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
	
	
	public void fetchPage(int page,List<Anchor> list,CloseableHttpClient httpclient) throws Exception {
		StringBuffer url = new StringBuffer(
				"http://www.fob001.cn/guestbook/jxjh/indexw1.php?page="+page); // 外一
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			
			Parser myParser;
			String filterStr = "table";// 节点名
			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(0);// 提取第1个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 1) {
				Anchor anchor ;
				TableColumn[] col;
					for (int i = 1; i < len; i++) {
						col = rows[i].getColumns();
						anchor = new Anchor();
						anchor.setDock("外一期");
						anchor.setNameEn(col[0].getStringText().replace("&nbsp;", "").trim());
						anchor.setVoyageOut(col[1].getStringText().replace("&nbsp;", "").trim());
						//抓取的是带   秒的
						anchor.setHarborTimeStart(col[2].getStringText().replace("&nbsp;", "").trim().substring(0,16));
						anchor.setHarborTimeEnd(col[3].getStringText().replace("&nbsp;", "").trim().substring(0,16));
						list.add(anchor);
					}
			}
	}


	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}


