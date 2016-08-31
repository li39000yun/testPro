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
import com.kingsoft.control.util.StringManage;
import com.kingsoft.dao.entity.baseinfo.customs.Anchor;

/**
 * 该类为读取外二港口数据实现类
 * 
 * @author kangweishui
 * 
 * @version 2015年7月14日
 * 
 * @since JDK 1.6
 *
 */

public class PortShipW2Fetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	public List<Anchor> fetch() throws Exception {// 外二

		CloseableHttpClient httpclient = createHttp();//创建http链接
		List<Anchor> temp = new ArrayList<Anchor>();//返回列表数据
		try {
			HttpGet httpget = new HttpGet("http://www.sipgzct.com/wat/controllerServlet.do?querysid=g0002&queryid=0002001&method=doquery");
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
	
			Parser parser = new Parser(html);
			parser.setEncoding("UTF-8");
			NodeFilter tableFilter = new TagNameFilter("table");
			NodeFilter classfilter = new HasAttributeFilter("class","availability-table");
			AndFilter threadFilter = new AndFilter();
			threadFilter.setPredicates(new NodeFilter[] { tableFilter, classfilter });
			Node[] nodes = parser.parse(threadFilter).toNodeArray();
			if(nodes==null || nodes.length==0){
				return temp;
			}
			TableTag tabletag = (TableTag)nodes[0];
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 0) {
				TableColumn[] col = rows[0].getColumns();
				SimpleNodeIterator div =  col[0].elements();//div
				NodeList links = div.nextNode().getChildren();// <a>
				LinkTag  a =  (LinkTag) links.elementAt(1);//总页数在第二个链接
				String page =a.getLinkText().replaceAll("&nbsp;", "").trim();
				page = page.substring(page.indexOf("/")+1, page.length());
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
				"http://www.sipgzct.com/wat/controllerServlet.do?querysid=g0002&queryid=0002001&method=doquery&showpage=")
				.append(page);// 外二
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
	
			Parser parser = new Parser(html);
			parser.setEncoding("UTF-8");
			NodeFilter tableFilter = new TagNameFilter("table");
			NodeFilter idFilter = new HasAttributeFilter("id","ctable");
			AndFilter threadFilter = new AndFilter();
			threadFilter.setPredicates(new NodeFilter[] { tableFilter, idFilter });
			Node[] nodes = parser.parse(threadFilter).toNodeArray();
			if(nodes==null || nodes.length==0){
				return;
			}
			TableTag tabletag = (TableTag)nodes[0];
			TableRow[] rows = tabletag.getRows();// 数据行数
			int len = rows.length;
			if (len > 1) {
				Anchor anchor = null;
				TableColumn[] col;
				for (int i = 1; i < len; i++) {
					col = rows[i].getColumns();
					anchor = new Anchor();
					
					anchor.setDock("外二期");
					if(col.length > 0) {
						String nameCn = col[0].toPlainTextString().replaceAll("&nbsp;", "").trim();
						anchor.setNameCn(nameCn);// 中文船名
					}
					if(col.length > 1) {
						String nameEn = col[1].toPlainTextString().replaceAll("&nbsp;", "").trim();
						anchor.setNameEn(nameEn);// 英文船名
					}
					if(col.length > 2) {
						String csi = col[2].toPlainTextString().replaceAll("&nbsp;", "").trim();
						anchor.setCsi(csi);// 船代码
					}
					anchor.setVoyageIn("");
					if(col.length > 3) {
						String voyageOut = col[3].toPlainTextString().replaceAll("&nbsp;", "").trim();
						anchor.setVoyageOut(voyageOut);// 出港航次
					}
					if(col.length > 5) {
						String harborTimeStart = col[5].toPlainTextString().replaceAll("&nbsp;", "").trim();
						anchor.setHarborTimeStart(harborTimeStart);// 开始收箱时间
					}
					if(col.length > 6) {
						String harborTimeEnd = col[6].toPlainTextString().replaceAll("&nbsp;", "").trim();
						if(harborTimeEnd.length() < 16 && !StringManage.isEmpty(anchor.getHarborTimeStart())) {
							harborTimeEnd = anchor.getHarborTimeStart().substring(0, 4) + "-" + harborTimeEnd;
						} 
						anchor.setHarborTimeEnd(harborTimeEnd);// 截止收箱时间
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

