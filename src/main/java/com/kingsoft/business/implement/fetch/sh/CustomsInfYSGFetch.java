package com.kingsoft.business.implement.fetch.sh;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.control.util.StringManage;

/**
 * 洋山港报关信息查询
 * 
 * @author kangweishui
 * 
 * @version 2015年9月8日
 * 
 * @since JDK 1.6
 *
 */
public class CustomsInfYSGFetch extends AbstractFetch {
	private static final long serialVersionUID = 1L;

	public String fetch(String containerNo)throws Exception {

		CloseableHttpClient httpclient = createHttp();//创建http链接
		String cusinf=StringManage.FS_EMPTY;//返回报关状态信息
		try {
			HttpPost httpPost = new HttpPost("http://www.tongguanbao.net/Bussiness/SimpleQuery_JZXH");
			

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

				nvps.add(new BasicNameValuePair("ContaID", containerNo));
				nvps.add(new BasicNameValuePair("OperationType", "YSG"));
				nvps.add(new BasicNameValuePair("X-Requested-With", "XMLHttpRequest"));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				//获取返回的JSON
				JSONObject json= JSONObject.fromObject(executePost(httpclient, httpPost, "utf-8").getReturnHtml());
				if(json.containsKey("Value")){ //存在Value的时候处理
					//获取Value值 并对格式在这里转码(这里的值为转码过的html),
					String html=new String(json.getString("Value").getBytes("utf-8"),"utf-8");
					
					Parser myParser;
					String filterStr = "table";// 节点名
					myParser = Parser.createParser(html, "UTF-8");
					myParser.setEncoding("UTF-8");// 设置编码
					
					NodeFilter filter = new TagNameFilter(filterStr); // 过滤table
					NodeList nodeList = myParser.extractAllNodesThatMatch(filter);// 创建table节点
					TableTag tabletag = (TableTag) nodeList.elementAt(3);// 提取第4个table节点
					TableRow[] rows = tabletag.getRows();// 数据行数
					if(rows.length>0){
						TableColumn[] col=rows[0].getColumns();
						cusinf=col[0].getStringText();//取得报关信息状态
					}
				}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.close(); //关闭链接
		}
				
		return cusinf;
	}


	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
