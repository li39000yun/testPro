package com.lyq.jkxtest.serviceCenter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingsoft.control.util.StringManage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IGZService {
	
	public String ProcessRequest(String portcode, String dockcode, String traceno)
    {
		//portcode  指港口 营口传"CNYIK"  dockcode 指港口下码头,可以传空字符

        //请求地址
		String www = "http://api3.igenzong.com/d2d/trace";
        //请求参数 其中strAppKey、strAppSecret需双方约定
		String strAppKey = "JinKeuserCN";
		String strAppSecret = "cn0506!";
		String strCharset = "UTF-8";
		//String strFormat = "json";//返回数据格式
		String info = StringManage.FS_EMPTY;
		 //签名参数数组
		String[] requestArr = { "appkey=" + strAppKey, "appsecret=" + strAppSecret, "portcode=" + portcode, "dockcode=" + dockcode, "traceno=" + traceno, "info=" + info };
        //对签名数组进行排序
		String[] sortedArr = BubbleSort(requestArr);
        //拼接签名数组
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<sortedArr.length;i++){
			if(i<sortedArr.length-1){
				sb.append(sortedArr[i]).append("&");
			}else if(i==sortedArr.length-1){
				sb.append(sortedArr[i]);
			}
			
		}
		 //生成签名字符串
		String strSign = GetSign(sb.toString(), strCharset);
		
		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer param = new StringBuffer();
		param.append("appkey=").append(strAppKey);
		param.append("&portcode=").append(portcode);
		param.append("&dockcode=").append(dockcode);
		param.append("&traceno=").append(traceno);
		param.append("&info=").append(info);
		param.append("&sign=").append(strSign);
		StringBuffer rvalue = new StringBuffer();
		try {
			// 打开与网站服务器的连接
			url = new URL(www);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.getOutputStream().write(param.toString().getBytes(strCharset));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();
			// 解析数据集
			InputStream is = conn.getInputStream();
			BufferedInputStream buff = new BufferedInputStream(is);
			Reader r = new InputStreamReader(buff, strCharset);
			BufferedReader br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				rvalue.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rvalue.toString();
		
       
    }
    //获取签名的方法
    private static String GetSign(String _input, String _charset)
    {
            MessageDigest md;
            byte[] btInput = null;
			try {
				md = MessageDigest.getInstance("MD5");
				try {
					btInput = md.digest(_input.getBytes(_charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
           
        StringBuilder sb = new StringBuilder(32);
        for(byte x:btInput) {
            if(Integer.toHexString(x & 0xff).length()<2) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
    
    
    //数据排序 按参数名称
    private static String[] BubbleSort(String[] r)
    {
        int i, j; //交换标志 
        String temp;
        boolean exchange;
        for (i = 0; i < r.length; i++) //最多做R.Length-1趟排序 
        {
            exchange = false; //本趟排序开始前，交换标志应为假
            
            for (j = r.length - 2; j >= i; j--)
            {//交换条件
                if (r[j + 1].compareTo(r[j]) < 0)
                {
                    temp = r[j + 1];
                    r[j + 1] = r[j];
                    r[j] = temp;
                    exchange = true; //发生了交换，故将交换标志置为真 
                }
            }
            if (!exchange) //本趟排序未发生交换，提前终止算法 
            {
                break;
            }
        }
        return r;
    }

    public static void main(String[] arg) throws Exception{
    	File file=new File("D:/IGZ.txt");
		FileReader fileReader=new FileReader(file);
		BufferedReader buffReader=new BufferedReader(fileReader);
		StringBuffer sb=new StringBuffer();
		String html=null;
		while((html=buffReader.readLine())!=null){
			sb.append(html);
		}
		buffReader.close();
		//String jsonStr=igz.ProcessRequest("CNYIK","",containerNo);
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
		StringBuffer rvalue=new StringBuffer("{");
		JSONObject jsonObject=JSONObject.fromObject(sb.toString());
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
		System.out.println(rvalue.toString());
    }
    
    public static Date parseDate(String date){
    	//Date d=new Date("2016/4/18 23:11:00")不推荐
    	int year=0,month=0,day=0,hour=0,minute=0;
    	if(!StringManage.isEmpty(date.trim())){
    		Calendar cd=Calendar.getInstance();
    		String[] dateTime=date.split(" ");
    		if(dateTime.length==2){
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
    		cd.set(year, month-1, day, hour, minute);
    		Date d=cd.getTime();
    		return d;
    	}
    	return new Date();
    }
    
}
