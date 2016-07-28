package com.kingsoft.business.implement.fetch.sh;

import java.net.URI;
import java.net.URL;

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
 * 该类为根据提单号从亿通读取船名航次和港区
 * 
 * @author kangweishui
 * 
 * @version 2015年8月18日
 * 
 * @since JDK 1.6
 *
 */
public class BookingNoGetShipFetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	public Anchor fetch(String bookingNo)throws Exception {

		Anchor anchor=new Anchor();
		CloseableHttpClient httpclient = createHttp();//创建http链接
		//从海关回执船名航次列表 获取船名航次  因为预配舱单船名航次列表有时候会为空
		StringBuffer url = new StringBuffer();
		url.append("http://edi.easipass.com/dataportal/query.do?qn=dp_premanifest_ack_list&blno=")
				.append(bookingNo)
				.append("&vslname=&voyage=");
		try {
			HttpGet httpget = new HttpGet(url.toString());
			HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
			String html = httpReturn.getReturnHtml();
			Parser myParser;
			String filterStr = "table";// 节点名

			myParser = Parser.createParser(html, "UTF-8");
			myParser.setEncoding("UTF-8");// 设置编码
			NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
			NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
			TableTag tabletag = (TableTag) nodeList.elementAt(1);// 提取第2个table节点
			TableRow[] rows = tabletag.getRows();// 数据行数
			if(rows.length>1){
				TableColumn[] col=rows[1].getColumns();
				
				anchor.setNameEn(col[0].getStringText()
						.replaceAll("&nbsp;", "").trim());// 存放船名
				anchor.setVoyageOut(col[1].getStringText()  //存放航次
						.replaceAll("&nbsp;", "").trim());
				//通过船名航次获取港口信息
				getAnchor(anchor,httpclient);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close(); //关闭链接
		}
				
		return anchor;
	}

	public void getAnchor(Anchor anchor,CloseableHttpClient httpclient) throws Exception {
		StringBuffer strUrl = new StringBuffer("http://edi.easipass.com/dataportal/query.do?vess_call1=&carr_code1=&ship_agen1=&berth1=&esti_sail1=&esti_sail2=&qid=402803af0ecb1a4c010ecb1bc2bb00a0&pagesize=30&submit=&vessel1=");
		strUrl.append(anchor.getNameEn()).append("&voyage1=").append(anchor.getVoyageOut());
		URL url = new URL(strUrl.toString()); 
		URI urll = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
		HttpGet httpget = new HttpGet(urll);
		httpget.setHeader("Referer", "http://edi.easipass.com");

		HttpReturn httpReturn=executeGet(httpclient,httpget,"UTF-8");//获取html页面数据
		
		Parser myParser;
		String filterStr = "table";// 节点名
		myParser = new Parser(httpReturn.getReturnHtml());
		myParser.setEncoding("UTF-8");// 设置编码
		NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
		NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
		TableTag tabletag = (TableTag) nodeList.elementAt(4);// 提取第5个table节点
		TableRow[] rows = tabletag.getRows();// 数据行数
		if(rows.length>1){
			TableColumn[] col=rows[1].getColumns();
			anchor.setDock(col[6].getStringText().trim());// 存放港区
		}
	}

	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
