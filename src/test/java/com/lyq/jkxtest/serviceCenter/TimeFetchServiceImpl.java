package com.lyq.jkxtest.serviceCenter;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.kingsoft.business.BusinessService;
import com.kingsoft.control.util.StringManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询各种时间
 * 
 * @author zhh
 * 
 * @version 2016年05月18日
 * 
 * @since JDK 1.6
 * 
 */
public class TimeFetchServiceImpl {
	private static Logger S_Logger = Logger
			.getLogger(TimeFetchServiceImpl.class);

	public String search(String shipName, String startPort,String endPort)
			throws Exception {
		if (S_Logger.isDebugEnabled()) {
			S_Logger.debug("fetch_center - [com.kingsoft.business.interfaces.data.ykcd.YKTimeFetch.java] search");
		}
		
		CloseableHttpClient client=null;
		HttpGet httpGet=null;
		HttpPost httpPost=null;
		CloseableHttpResponse response=null;
		HttpEntity entity=null;
		List<NameValuePair> nvps=null;
		StringBuffer rvalue=new StringBuffer("{");
		try {
			client =HttpClients.createDefault();
			httpGet=new HttpGet("http://api.shipxy.com/apicall/SearchShip?v=2&k=6eaa00e7974743e79b46dfbb247f41bb&enc=1&kw="+shipName+"&tp=1&max=3");
			response=client.execute(httpGet);
			entity=response.getEntity();
			String value=EntityUtils.toString(entity,"utf-8");
			//String value="{\"status\":0,\"data\":[{\"MatchType\":1,\"ShipID\":413761000,\"From\":0,\"mmsi\":413761000,\"shiptype\":70,\"imo\":9400538,\"name\":\"TIAN LONG HE\",\"callsign\":\"BQBH\",\"length\":2940,\"width\":320,\"left\":230,\"trail\":730,\"draught\":13400,\"dest\":\"ZHANG ZHOU -FUJIAN  \",\"eta\":\"05-28 16:00\",\"navistat\":0,\"lat\":28131030,\"lon\":122145032,\"sog\":6070,\"cog\":21240,\"hdg\":21000,\"rot\":0,\"lasttime\":1464317758}]}";
			JSONObject jsonObject=JSONObject.fromObject(value);
			String mmsi="";
			//得到唯一标识mmsi
			if(jsonObject.getString("status").equals("0")){
				JSONArray jsonArray=jsonObject.getJSONArray("data");
				if(jsonArray.size()>0){
					jsonObject=jsonArray.getJSONObject(0);
					mmsi=jsonObject.getString("mmsi");
				}
			}
			if(!StringManage.isEmpty(mmsi)){
				httpPost=new HttpPost("http://www.kuaicang.cn/home/GetCurrentExcuteVoyage");
				nvps=new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("mmsi", mmsi));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				response=client.execute(httpPost);
				entity=response.getEntity();
				value=EntityUtils.toString(entity,"utf-8");
				//value="{\"Status\":1,\"Message\":\"Success\",\"ShipCompany\":\"中远\",\"ShipName\":\"天隆河\",\"Voyage\":\"123S\",\"PortList\":[{\"PortCode\":\"3\",\"PortName\":\"鲅鱼圈\",\"DockCode\":\"72\",\"DockName\":\"营口集装箱\",\"ETA\":\"2016/5/22\",\"ETD\":\"2016/5/23\",\"ShipxyETA\":\"\",\"ShipxyETD\":\"\",\"Sequence\":1},{\"PortCode\":\"1\",\"PortName\":\"锦州\",\"DockCode\":\"146\",\"DockName\":\"集装箱二期\",\"ETA\":\"2016/5/24\",\"ETD\":\"2016/5/24\",\"ShipxyETA\":\"\",\"ShipxyETD\":\"\",\"Sequence\":2},{\"PortCode\":\"44\",\"PortName\":\"漳州\",\"DockCode\":\"74\",\"DockName\":\"招商局\",\"ETA\":\"2016/5/28\",\"ETD\":\"2016/5/29\",\"ShipxyETA\":\"2016-05-28 14:44\",\"ShipxyETD\":\"2016-05-29 17:38:00\",\"Sequence\":3},{\"PortCode\":\"55\",\"PortName\":\"南沙\",\"DockCode\":\"10\",\"DockName\":\"南沙海港\",\"ETA\":\"2016/5/31\",\"ETD\":\"2016/6/1\",\"ShipxyETA\":\"2016-05-30 23:59:00\",\"ShipxyETD\":\"2016-06-01 04:32:00\",\"Sequence\":4}]}";
				jsonObject=JSONObject.fromObject(value);
				if(jsonObject.getString("Status").equals("1")){
					//日期格式化
					SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
					//得到港口集合
					JSONArray jsonArray=jsonObject.getJSONArray("PortList");
					int flag=0;//
					if(jsonArray.size()>0){
						String portName=StringManage.FS_EMPTY;
						for(int i=0;i<jsonArray.size();i++){
							jsonObject=jsonArray.getJSONObject(i);
							portName=jsonObject.getString("PortName");
							//如果是始发港,则取开船时间
							if(!StringManage.isEmpty(startPort)&&portName.indexOf(startPort)!=-1){
								//不确定是否始发港永远先循环到
								if(flag==1){
									rvalue.append(",");
								}
								rvalue.append("\"ETD\":\"").append(StringManage.isEmpty(jsonObject.getString("ETD"))?"":sdf.format(parseDate(jsonObject.getString("ETD")))).append("\"");
								flag=1;
							}
							//如果是目的港,则取预到港时间
							if(!StringManage.isEmpty(endPort)&&portName.indexOf(endPort)!=-1){
								if(flag==1){
									rvalue.append(",");
								}
								rvalue.append("\"ETA\":\"").append(StringManage.isEmpty(jsonObject.getString("ETA"))?"":sdf.format(parseDate(jsonObject.getString("ETA")))).append("\"");
								flag=1;
							}
						}
					}
				}
			}
			rvalue.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();// 关闭资源
			}
			if (client != null) {
				client.close();
			}
		}
		return rvalue.toString();
	}

	public static void main(String[] args) throws Exception {
		String conNo = "MAGU2242672";
		TimeFetchServiceImpl timeFetchService = new TimeFetchServiceImpl();
		System.out.println(timeFetchService.searchIGZByCon(conNo));
	}

	public String searchIGZByCon(String containerNo) throws Exception {
		IGZService igz=new IGZService();
		String jsonStr=igz.ProcessRequest("CNYIK","",containerNo);
		return parseResult(jsonStr);
		//return "{\"UV\":\"2016-04-18 23:11\",\"OA\":\"2016-04-24 12:51\",\"I\":\"2016-05-01 22:50\",\"AE\":\"2016-05-05 21:32\",\"EE\":\"2016-05-01 01:22\",\"MT\":\"2016-04-30 23:24\"}";
	}
	
	private String parseResult(String jsonString) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		StringBuffer rvalue=new StringBuffer("{");
		JSONObject jsonObject=JSONObject.fromObject(jsonString);
		JSONObject statusObject=jsonObject.getJSONObject("Status");
		if(statusObject.getString("Code").equals("0")){
			JSONObject messageBodyObject=jsonObject.getJSONObject("MessageBody");
			JSONArray traceArray=messageBodyObject.getJSONArray("Trace");
			if(traceArray.size()>0){
				JSONObject traceObject=traceArray.getJSONObject(0);
				JSONArray traceDetailsArray=traceObject.getJSONArray("TraceDetails");
				for(int i=0;i<traceDetailsArray.size();i++){
					JSONObject detailsObject=traceDetailsArray.getJSONObject(i);
					JSONObject eventLocationObject=detailsObject.getJSONObject("EventLocation");
					JSONObject dataTimeObject=eventLocationObject.getJSONObject("DataTime");
					String time=sdf.format(parseDate(dataTimeObject.getString("Value")));
					rvalue.append("\"")
						  .append(detailsObject.getString("EventCode"))
						  .append("\":\"")
						  .append(time)
						  .append("\"");
					if(i!=traceDetailsArray.size()-1){
						rvalue.append(",");
					}
				}//得到各个时间
			}//返回是否有业务数据
		}//判断状态是否为成功
		rvalue.append("}");
		return rvalue.toString();
	}
	private  Date parseDate(String date) throws Exception{
    	//Date d=new Date("2016/4/18 23:11:00")不推荐
    	int year=0,month=0,day=0,hour=0,minute=0,sec=0;
    	if(!StringManage.isEmpty(date.trim())){
    		Calendar cd=Calendar.getInstance();
    		String[] dateTime=date.split(" ");
    		if(dateTime.length==1){
    			String[] da=dateTime[0].split("/");
    			if(da.length==3){
    				year=Integer.parseInt(da[0]);
    				month=Integer.parseInt(da[1]);
    				day=Integer.parseInt(da[2]);
    			}
    		}else if(dateTime.length==2){
    			String[] da=dateTime[0].split("/");
    			if(da.length==3){
    				year=Integer.parseInt(da[0]);
    				month=Integer.parseInt(da[1]);
    				day=Integer.parseInt(da[2]);
    			}
    			String[] ti=dateTime[1].split(":");
    			if(da.length==3){
    				hour=Integer.parseInt(ti[0]);
    				minute=Integer.parseInt(ti[1]);
    			}
    		}
    		cd.set(year, month-1, day, hour, minute,sec);
    		Date d=cd.getTime();
    		return d;
    	}
    	return new Date();
    }
}
