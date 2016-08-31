package com.kingsoft.business.implement.fetch.sz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.common.HttpReturn;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.dao.entity.data.userFetch.UserFetch;

/**
 * 箱动态业务层
 * @author zhh
 * @date 2015-10-28
 * @since JDK 1.6
 */
@SuppressWarnings("unused")
public class SZDynamicContainerFetch extends AbstractFetch{
	private static final long serialVersionUID = 1L;
	private static Logger S_Logger = Logger.getLogger(SZDynamicContainerFetch.class);
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static void main(String[] args) throws Exception {
		System.out.println("1");
		SZDynamicContainerFetch test = new SZDynamicContainerFetch();
//		System.out.println(test.searchTrack("CARTON","PPLU1234560","bp09oe","ahy61cv"));
		System.out.println(test.searchTrack("CARTON","PPLU1234560","luke1019","luke123"));
		System.out.println("2");
	}

	public String searchTrack(String queryType, String queryParam,String loginId,String passwrod)
			throws Exception {
		if (S_Logger.isDebugEnabled()) {
			S_Logger.debug("fetch_center - [com.kingsoft.business.implement.fetch.sz.DynamicContainerFetch.java] searchTrack");
		}
		String rvalue = StringManage.FS_EMPTY;
		CloseableHttpClient httpClient = createHttp();// 创建http链接
		//先判断是否已经登陆，如果已经登陆就直接抓取信息，否则先登陆再抓取
		if(!isLogin(httpClient)){
			rvalue = login(httpClient,loginId,passwrod);
			if(!StringManage.isEmpty(rvalue)){
				return rvalue;
			}
		}
		HttpPost httpPost = null;
		try {
			//post 访问
			httpPost = new HttpPost("http://mlisp.cmclink.com/Track_search.shtml");
			//设置提交参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("queryType", queryType));
			list.add(new BasicNameValuePair("queryParam", queryParam));
			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(list));
			
			// 执行
			HttpReturn httpReturn = executePost(httpClient, httpPost, "utf-8");
			//获取返回数据
			rvalue = httpReturn.getReturnHtml();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return rvalue;
	}
	
	/**
	 * 根据签到判断是否已经登陆，如果已经登陆就不用输验证码
	 * @param httpClient
	 * @return
	 * @throws Exception
	 */
	private boolean isLogin(CloseableHttpClient httpClient) throws Exception{
		HttpGet httpGet = null;
		try {
			//get 访问
			httpGet = new HttpGet("http://mlisp.cmclink.com/Login_sign.shtml");
			// 执行
			HttpReturn httpReturn = executeGet(httpClient, httpGet, "utf-8");
			// 获取返回数据
			String rvalue = httpReturn.getReturnHtml();
			if(!StringManage.isEmpty(rvalue) && rvalue.indexOf("code")>-1){
				JSONObject jo = JSONObject.fromObject(rvalue);
				if(jo.getString("code").equals("success")){
					return true;
				}
			}
			return false;
		}finally{
			if(httpGet!=null){
				httpGet.releaseConnection();
				httpGet = null;
			}
		}
	}
	/**
	 * 登陆
	 * @param httpClient
	 * @return
	 * @throws Exception
	 */
	private String login(CloseableHttpClient httpClient,String loginId,String password) throws Exception{
		HttpPost httpPost = null;
		try {
			//post 访问
			httpPost = new HttpPost("http://mlisp.cmclink.com:80/Login_ajax.shtml");
			
			//设置提交参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("account",loginId));
			list.add(new BasicNameValuePair("password",password));
			//list.add(new BasicNameValuePair("vcode", checkCode));
			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(list));
			
			// 执行
			HttpReturn httpReturn = executePost(httpClient, httpPost, "utf-8");
			// 获取返回数据
			String rvalue = httpReturn.getReturnHtml();
			if(!StringManage.isEmpty(rvalue) && rvalue.indexOf("code")>-1){
				JSONObject jo = JSONObject.fromObject(rvalue);
				if(!jo.getString("code").equals("success")){
					return rvalue;
				}
			}
		}finally{
			if(httpPost!=null){
				httpPost.releaseConnection();
				httpPost = null;
			}
		}
		return StringManage.FS_EMPTY;
	}
	
	/**
	 * 查询打单信息
	 * @return
	 * @throws Exception
	 */
	public String searchEir(String bookingNo)throws Exception{
		String rvalue = StringManage.FS_EMPTY;
		HttpPost httpPost = null;
		if (S_Logger.isDebugEnabled()) {
			S_Logger.debug("fetch_center - [com.kingsoft.business.interfaces.data.DynamicContainerService.java] searchEir");
		}
		CloseableHttpClient httpClient = createHttp();// 创建http链接
		try {
			//post 访问
			httpPost = new HttpPost("http://mlisp.cmclink.com/Track_searchEir.shtml");
			//设置提交参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("queryType", "BOOKING"));
			list.add(new BasicNameValuePair("queryParam", bookingNo));
			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(list));
			// 执行
			HttpReturn httpReturn = executePost(httpClient, httpPost, "utf-8");
			// 获取返回数据
			rvalue = httpReturn.getReturnHtml();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return rvalue;
	}

	/**
	 * 查询缴费信息
	 * @return
	 * @throws Exception
	 */
	public String searchDpay(String bookingNo)throws Exception{
		String rvalue = StringManage.FS_EMPTY;
		if (S_Logger.isDebugEnabled()) {
			S_Logger.debug("fetch_center - [com.kingsoft.business.interfaces.data.DynamicContainerService.java] searchDpay");
		}
		CloseableHttpClient httpClient = createHttp();// 创建http链接
		HttpPost httpPost = null;
		try {
			//post 访问
			httpPost = new HttpPost("http://mlisp.cmclink.com/Track_searchDpay.shtml");
			//设置提交参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("queryType", "BOOKING"));
			list.add(new BasicNameValuePair("queryParam", bookingNo));
			httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(list));
			// 执行
			HttpReturn httpReturn = executePost(httpClient, httpPost, "utf-8");
			// 获取返回数据
			rvalue = httpReturn.getReturnHtml();
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return rvalue;
	}

	/***
	 * 注册鹏腾网账号
	 * @param userFetch
	 * @throws Exception
	 */
	public String addUser(UserFetch userFetch) throws Exception{
		CloseableHttpClient httpClient = createHttp();// 创建http链接
		HttpPost httpPost = new HttpPost("http://mlisp.cmclink.com/Register_insert.shtml");
		ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("member.account", userFetch.getLoginId()));
		nvps.add(new BasicNameValuePair("member.email", userFetch.getEmail()));
		nvps.add(new BasicNameValuePair("member.password", userFetch.getPassword()));
		nvps.add(new BasicNameValuePair("member.registType", "E"));
		nvps.add(new BasicNameValuePair("companyRegistType", "PERSONAL"));
		nvps.add(new BasicNameValuePair("vcode", "--"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		// response相当于你的所返回结合的一个集合，包括返回的一切信息
		HttpReturn httpReturn = executePost(httpClient, httpPost, "utf-8");
		String json=httpReturn.getReturnHtml();
		if(!StringManage.isEmpty(json) && json.indexOf("code")>-1){
			JSONObject jo = JSONObject.fromObject(json);
			if(jo.getString("obj").equals("success")){
				return "success";
			}
		}
		return "fail";
	}
	
	@Override
	public String preExecute(FetchSearch search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

	public  String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
		}
		char [] randBuffer = new char[length];
		for (int i=0; i<randBuffer.length; i++) {
			if(i==0){
				randBuffer[i] = numbersAndLetters[randGen.nextInt(25)+10];
			}else{
				randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
			}
		}
		return new String(randBuffer);
	}
//	public static void main(String[] args) {
//		Random rd=new Random();
//		char[] datas=("0123456789abcdefghijklmnopqrstuvwxyz").toCharArray();
//		char [] randBuffer = new char[6];
//		for (int i=0; i<randBuffer.length; i++) {
//			if(i==0){
//				randBuffer[i] = datas[rd.nextInt(25)+10];
//			}else{
//				randBuffer[i] = datas[rd.nextInt(35)];
//			}
//		}
//		for(char c:randBuffer){
//			System.out.println(c);
//		}
//	}
}

