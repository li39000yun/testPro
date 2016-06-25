package com.lyq.tcb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.kingsoft.control.util.StringManage;

/**
 * 对外业务发送服务层接口实现
 * 
 * @author wmy
 * 
 * @version 2015-8-31
 * 
 * @since JDK 1.6
 * 
 */
public class PostShipxy {
	protected List<NameValuePair> nvps = null;// 查询参数
	private static String FS_Tcb_Appkey = "D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632";// 拖车宝接口密钥

	String j = "{\"accreditid\":\"zcdcs\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"YUQIANG\",\"data\":[{\"accreditid\":\"zcdcs\",\"address\":\"\",\"appointTime\":\"2015-10-16 16:57:46\",\"arriveTime\":\"\",\"bookingNo\":\"DDFFFF\",\"businessid\":\"Y15100002-1-1\",\"businesstype\":0,\"contact\":\"\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"测试客户\",\"customs\":\"冠通\",\"customsMode\":\"\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":0,\"driver\":\"王志平\",\"driverMobilePhone\":\"13126733579\",\"endPort\":\"\",\"factoryShortName\":\"\",\"getConPile\":\"\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"\",\"goodName\":\"\",\"ismatch\":0,\"leaveTime\":\"\",\"line\":\"\",\"loadPlace\":\"\",\"matchbusinessid\":\"\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"深圳\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"测试车队\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0}],\"userid\":918}";

	String url = "http://124.127.127.166:8002/api/Desktop/KingTech";

	private String sendShipxy(String jsonStr, String url) throws Exception {
		String rvalue = StringManage.FS_EMPTY;
		if (!StringManage.isEmpty(url)) {
			nvps = new ArrayList<NameValuePair>();// 参数
			// 添加参数
			// appkey
			nvps.add(new BasicNameValuePair("appkey", FS_Tcb_Appkey));
			// userId
			nvps.add(new BasicNameValuePair("userId", "918"));
			// accreditId
			nvps.add(new BasicNameValuePair("accreditId", "zcdcs"));
			// json数据
			nvps.add(new BasicNameValuePair("param", jsonStr));
			rvalue = execute(url, "UTF-8");
		}
		return rvalue;
	}

	/**
	 * post访问,访问一次后关闭链接
	 * 
	 * @param wwwUrl
	 *            网址
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	protected String execute(String wwwUrl, String encoding) throws Exception {
		// 获取最新记录
		DefaultHttpClient httpclient = null;
		HttpResponse response = null;
		String html = StringManage.FS_EMPTY;
		try {
			// 设置请求参数
			HttpPost httpPost = new HttpPost(wwwUrl);
//			if (StringManage.isEmpty(encoding)) {
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//			} else {
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
//			}

			StringEntity entity = new StringEntity(j);
	        entity.setContentEncoding( "UTF-8" );
	        entity.setContentType( "application/json" );//设置为 json数据
	        httpPost.setEntity(entity);
	        
//			httpPost.addHeader("Accept-Charset", DEFAULT_CHARSET);
//			httpPost.addHeader("Host", HOST);
//			httpPost.addHeader("Accept", ACCEPT);
//			httpPost.addHeader("User-Agent", USER_AGENT);
			
			httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true); 
			response = httpclient.execute(httpPost);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			html = "post报错!";
		} finally {
			if (response != null) {
				response = null;
			}
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return html;
	}

	private String getEntityString(HttpResponse response, String encoding) throws Exception {
		String html = StringManage.FS_EMPTY;
		HttpEntity entity = response.getEntity();
		if (StringManage.isEmpty(encoding)) {
			html = EntityUtils.toString(entity);
		} else {
			html = EntityUtils.toString(entity, encoding);
		}
		// 释放资源
		EntityUtils.consume(entity);
		return html;
	}

	public void advanceAssignment() throws Exception {
		String rvalue = sendShipxy(j, url);
		System.out.println(rvalue);
	}

	public static void main(String[] args) throws Exception {
		PostShipxy p = new PostShipxy();
		p.advanceAssignment();
	}
	
}
