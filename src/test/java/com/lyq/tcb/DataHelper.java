package com.lyq.tcb;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DataHelper {
	
	public final static String UTF8Encode="UTF-8";
	public final static String GBKEncode="GBK";
	
	public static String GetQueryString(Map<String, String> map)
	{
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (iter.hasNext()) {
			 Entry<String, String> entry = iter.next(); 
			 Object key = entry.getKey().toString();
			 Object val = entry.getValue().toString();
			 sb.append(key + "=" +val).append("&");
			}		
        if(sb.length()==0) return "";
		return sb.substring(0, sb.length()-1);
		
	}
	
	
	public static String GetSortFilterQueryString(Map<String, String> map,String[] filterKey)
	{
		List<Map.Entry<String, String>> keyValues =
			    new ArrayList<Map.Entry<String, String>>(map.entrySet());

		Collections.sort(keyValues, new Comparator<Map.Entry<String, String>>() {   
		    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
		
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<keyValues.size();i++) {
			boolean filter=false;
			if(filterKey!=null&&filterKey.length>0)
			{
				for(int index=0;index<filterKey.length;index++)
				{
					if(filterKey[index].equalsIgnoreCase(keyValues.get(i).getKey()))
					{
						filter=true;
						break;
					}
				}
			}
			//过滤的KEY不参与
			if(filter) continue;
			sb.append(keyValues.get(i).getKey()+ "=" + keyValues.get(i).getValue());
			sb.append("&");
		}
		return sb.substring(0, sb.length()-1);
		
	}
	
	//对值进行转码 将本地编码的字符 转换为汇付宝的编码
	public  static void TranferCharsetEncode(Map<String, String> map) throws UnsupportedEncodingException
	{
		for (Entry<String, String> entry : map.entrySet()) {
		   if(entry.getValue()==null) continue;		
		   String utf8=URLEncoder.encode(entry.getValue(), DataHelper.UTF8Encode);
//		   String encodeValue1=new String(val1.getBytes("UTF-8"),"UTF-8");
		   entry.setValue(utf8);
		}
		     
	}
		
	
	public static String GetSortQueryToLowerString(Map<String, String> map)
	{
		List<Map.Entry<String, String>> keyValues =
			    new ArrayList<Map.Entry<String, String>>(map.entrySet());

		Collections.sort(keyValues, new Comparator<Map.Entry<String, String>>() {   
		    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
		
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<keyValues.size();i++) {	
			if(keyValues.get(i).getValue()==null)
			{
				sb.append(keyValues.get(i).getKey()+ "= " );
			}
			else
			{
				sb.append(keyValues.get(i).getKey()+ "=" + keyValues.get(i).getValue().toLowerCase());
			}
			sb.append("&");
		}
		
		return sb.substring(0, sb.length()-1);
		
	}
	
	public static String GetSortQueryString(Map<String, String> map)
	{
		List<Map.Entry<String, String>> keyValues =
			    new ArrayList<Map.Entry<String, String>>(map.entrySet());

		Collections.sort(keyValues, new Comparator<Map.Entry<String, String>>() {   
		    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {      
		        //return (o2.getValue() - o1.getValue()); 
		        return (o1.getKey()).toString().compareTo(o2.getKey());
		    }
		}); 
		
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<keyValues.size();i++) {			
			sb.append(keyValues.get(i).getKey()+ "=" + keyValues.get(i).getValue());
			sb.append("&");
		}
		
		return sb.substring(0, sb.length()-1);
		
	}
	
	
	public static String RequestGetUrl(String getUrl)
	{
		return GetPostUrl(null,getUrl,"GET");
	}
	
	public static String RequestPostUrl(String getUrl,String postData)
	{
		return GetPostUrl(postData,getUrl,"POST");
	}
	
	
	public static void main(String[] args) {
		String j = "{\"accreditid\":\"zcdcs\"}";
		JSONObject js = new JSONObject();
		js.put("accreditid", "zcdcs");
		//String j = "{\"accreditid\":\"zcdcs\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"YUQIANG\",\"data\":[{\"accreditid\":\"zcdcs\",\"address\":\"\",\"appointTime\":\"2015-10-16 16:57:46\",\"arriveTime\":\"\",\"bookingNo\":\"DDFFFF\",\"businessid\":\"Y15100002-1-1\",\"businesstype\":0,\"contact\":\"\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"测试客户\",\"customs\":\"冠通\",\"customsMode\":\"\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":0,\"driver\":\"王志平\",\"driverMobilePhone\":\"13126733579\",\"endPort\":\"\",\"factoryShortName\":\"\",\"getConPile\":\"\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"\",\"goodName\":\"\",\"ismatch\":0,\"leaveTime\":\"\",\"line\":\"\",\"loadPlace\":\"\",\"matchbusinessid\":\"\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"深圳\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"测试车队\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0}],\"userid\":918}";
		String url = "http://124.127.127.166:8002/api/Desktop/KingTech";
		System.out.println(GetPostUrl(j, url, "POST"));
		System.out.println(js.toString());
		System.out.println(GetPostUrl(js.toString(), url, "POST"));
	}
	
	private static String  GetPostUrl(String postData,String postUrl,String submitMethod) {
		System.out.println("aa");
		   URL url = null;
		   HttpURLConnection httpurlconnection = null;
		   try {
			    url = new URL(postUrl);
			    httpurlconnection = (HttpURLConnection) url.openConnection();
			    httpurlconnection.setRequestMethod(submitMethod.toUpperCase());
			    httpurlconnection.setRequestProperty("Content-Type", "application/json");
			    httpurlconnection.setDoInput(true);
			    httpurlconnection.setDoOutput(true);    
			    if(submitMethod.equalsIgnoreCase("POST"))
			    {
				    httpurlconnection.getOutputStream().write(postData.getBytes(UTF8Encode));
				    httpurlconnection.getOutputStream().flush();
				    httpurlconnection.getOutputStream().close();
			    }   
			    int code = httpurlconnection.getResponseCode();
			    System.out.println(code);
			    if (code == 200) {
			    DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
			    int len = in.available();
			    byte[] by = new byte[len];
			    in.readFully(by);
			    String rev = new String(by,UTF8Encode);	
			    in.close();
			    return rev;
		    }
			    
		   }
		   catch (Exception e) 
		   {
			   e.printStackTrace();
		   } 
		   finally 
		   {
		    if (httpurlconnection != null) {
		     httpurlconnection.disconnect();
		    }
		   }
		   return null;
	}
	
	
//	public static HeepayXMLReturn GetRetuenXmlContent(String xmlString)
//	{
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db; org.w3c.dom.Document doc; 
//		
//		HeepayXMLReturn returnValue=new HeepayXMLReturn();
//		returnValue.set_retcode("");
//		returnValue.set_retmsg("");
//		returnValue.set_encryptdata("");
//	    try {
//	    	
//			 db = dbf.newDocumentBuilder(); 
//			 InputStream x=new ByteArrayInputStream(xmlString.getBytes(UTF8Encode));;
//			 doc = db.parse(x);
//			 Element root=(Element) doc.getDocumentElement();
//			 NodeList nodeList= root.getChildNodes();
//			 
//			 String retCode=nodeList.item(0).getTextContent();
//			 String ret_msg=nodeList.item(1).getTextContent(); 
//			 returnValue.set_retcode(retCode);
//			 returnValue.set_retmsg(ret_msg);
//			 if(nodeList.getLength()>2)
//			 {
//				 String encrypt_data=nodeList.item(2).getTextContent(); 
//				 returnValue.set_encryptdata(encrypt_data);
//			 }
//	
//			 
//			 return returnValue;
//			 
//		 } catch (ParserConfigurationException e) {
//			 // TODO Auto-generated catch block
//			 e.printStackTrace();
//		 } catch (SAXException e) {
//			 // TODO Auto-generated catch block
//			 e.printStackTrace();
//		 } catch (IOException e) {
//			 // TODO Auto-generated catch block
//			 e.printStackTrace();
//		 }
//	    
//	    return returnValue;
//	}
	
	
	
	/** 
	* 解析出url参数中的键值对 
	* 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中 
	* @param URL url地址 
	* @return url请求参数部分 
	*/ 
	public static Map<String, String> URLRequestParams(String URL) 
	{ 
		Map<String, String> mapRequest = new HashMap<String, String>(); 
		String[] arrSplit=null; 
		String strUrlParam=URL; 
		if(strUrlParam==null) 
		{ 
			return mapRequest; 
		} 
		strUrlParam=URL.indexOf("?")>0?URL.substring(URL.indexOf("[?]")+1) :URL ;
		//解决返回值中带有URL含参数“?”导致破坏键值对 返回的URL后的参数 需要重新组合
		//例如：key1=value1&key2=value2&key3=url?a=a1&b=b1&key4=value4
		//分别获取后的键值为 key1,key2,key3,akey,bkey,key4
		//如需获取key3 的原始值 key3+"?"+akey+bkey
		strUrlParam=strUrlParam.replaceAll("[?]", "&");
		//每个键值为一组 
		arrSplit=strUrlParam.split("[&]"); 
		for(String strSplit:arrSplit) 
		{ 
			String[] arrSplitEqual=null; 
			arrSplitEqual= strSplit.split("[=]"); 
			//解析出键值 
			if(arrSplitEqual.length>1) 
			{ 
			//正确解析 
			mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]); 
			} 
			else 
			{ 
			if(arrSplitEqual[0]!="") 
			{ 
			//只有参数没有值，不加入 
			mapRequest.put(arrSplitEqual[0], ""); 
			} 
			} 
		} 
		return mapRequest; 
	} 


}
