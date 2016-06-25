package com.lyq.tcb;

import com.kingsoft.control.util.StringManage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

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
public class TestShipxyPost {
	protected static List<NameValuePair> nvps = null;// 查询参数
	protected static HttpResponse response = null;
	protected static String url = "http://124.127.127.162:8888/SaaS/KingTech/GetFeeImageUrl";
	protected static String feeUrl = "http://124.127.127.162:8888/SaaS/KingTech/CommitFee";
	protected static String appkey = "D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632";
	protected static int userId = 113;
	protected static boolean isDouble = false;
	protected static String businessid = "Y15090001-1-1";
	protected static int feeid = 1551;
	protected static String accreditid = "jkx";
	
	public static String j = "{\"accreditid\":\"zcdcs\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"YUQIANG\",\"data\":[{\"accreditid\":\"zcdcs\",\"address\":\"\",\"appointTime\":\"2015-10-16 16:57:46\",\"arriveTime\":\"\",\"bookingNo\":\"DDFFFF\",\"businessid\":\"Y15100002-1-1\",\"businesstype\":0,\"contact\":\"\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"测试客户\",\"customs\":\"冠通\",\"customsMode\":\"\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":0,\"driver\":\"王志平\",\"driverMobilePhone\":\"13126733579\",\"endPort\":\"\",\"factoryShortName\":\"\",\"getConPile\":\"\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"\",\"goodName\":\"\",\"ismatch\":0,\"leaveTime\":\"\",\"line\":\"\",\"loadPlace\":\"\",\"matchbusinessid\":\"\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"深圳\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"测试车队\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0}],\"userid\":918}";

	public static String wwwUrl = "http://124.127.127.166:8002/api/Desktop/KingTech";
//	String url = "http://124.127.127.166:8002/SaaS/KingTech/YUPAI";

	
	public static void main(String[] args) throws Exception {
		testWelUrl();
	}
	
	private static void testWelUrl() throws Exception {
		TestShipxyPost t = new TestShipxyPost();
		String rvalue = t.sendShipxy(j, wwwUrl);
		System.out.println(rvalue);
	}
	
	private String sendShipxy(String jsonStr, String url) throws Exception {
		String rvalue = "";
		if (url != "") {
			nvps = new ArrayList<NameValuePair>();// 参数
			// 添加参数
			// appkey
			nvps.add(new BasicNameValuePair("appkey", appkey));
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
	protected static String execute(String wwwUrl, String encoding) throws Exception {
		// 获取最新记录
		DefaultHttpClient httpclient = null;
		String html = StringManage.FS_EMPTY;
		try {
			// 设置请求参数
			HttpPost httpPost = new HttpPost(wwwUrl);
			if (StringManage.isEmpty(encoding)) {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
			}

			httpclient = new DefaultHttpClient();
			response = httpclient.execute(httpPost);
			html = getEntityString(response, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();
			}
		}
		return html;
	}

	private static String getEntityString(HttpResponse response, String encoding) throws Exception {
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
	
}
