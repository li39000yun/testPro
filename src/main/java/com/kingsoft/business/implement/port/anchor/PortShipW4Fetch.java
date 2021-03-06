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
 * 该类为读取外四港口数据实现类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */

public class PortShipW4Fetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;
	private final String adress = "http://www.sect.com.cn/wat/controllerServlet.do?querysid=g0002&queryid=0002001&method=doquery";
	
	public List<Anchor> fetch() throws Exception {// 外四
		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<Anchor> temp = new ArrayList<Anchor>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet(adress);
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
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
				String page = str.substring(beidx+10, edidx);
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
		StringBuffer url = new StringBuffer(adress).append("&showpage=").append(page);// 外四
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			
			Parser myParser;
			String filterStr = "table";// 节点名
			myParser = Parser.createParser(html.replace("&nbsp;", "")
					.replace("<span class='cssRed'>", "").replace("</span>", "").replace("<span class=''>", ""),"UTF-8");
	
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(2);// 提取第3个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				Anchor anchor = null;
				TableColumn[] col;
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					anchor = new Anchor();
					anchor.setDock("外四期");
					anchor.setNameCn(col[0].getStringText().trim());// 中文船名
					anchor.setNameEn(col[1].getStringText().trim());// 英文船名
					anchor.setCsi(col[2].getStringText().trim());// 船代码
					if(col[4].getStringText().trim().equals("I")){
						anchor.setVoyageIn(col[3].getStringText().trim());//进口航次
					}else{
						anchor.setVoyageOut(col[3].getStringText().trim());// 出港航次
					}
					anchor.setHarborTimeStart(col[5].getStringText().trim());// 开始收箱时间
					
					if (col[6].getStringText().trim().length() < 12) {// 防止没有年
						anchor.setHarborTimeEnd(anchor.getHarborTimeStart().substring(0, 4)+ "-" + col[6].getStringText().trim());// 截止收箱时间
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