package com.lyq.baidu;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import sun.text.normalizer.UnicodeSet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by jkx on 2016/9/30.
 */
public class Api {

    public static String apikey = "de69e4f839520e5969937cd9b38712be";

    /**
     * 笑话
     */
    @Test
    public void joke() {
        String httpUrl = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
        String httpArg = "page=1";
        System.out.println(request(httpUrl,httpArg));
    }

    @Test
    public void testIp() {
        String httpUrl = " http://apis.baidu.com/apistore/iplookupservice/iplookup";
        String httpArg = "ip=117.89.35.58";
        System.out.println(request(httpUrl, httpArg));
    }

    /**
     * @param httpUrl :请求接口
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", apikey);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(result).toString();
    }

    @Test
    public void testCode() throws UnsupportedEncodingException {
        String str = "{\"errNum\":0,\"retMsg\":\"success\",\"retData\":{\"phone\":\"15210011578\",\"prefix\":\"1521001\",\"supplier\":\"\\u79fb\\u52a8\",\"province\":\"\\u5317\\u4eac\",\"city\":\"\\u5317\\u4eac\",\"suit\":\"152\\u5361\"}}";
        System.out.println(JSONObject.fromObject(str).toString());
    }

    /**
     * 测试手机归宿地接口
     */
    @Test
    public void testPhone() {
        String httpUrl = "http://apis.baidu.com/apistore/mobilenumber/mobilenumber";
        String httpArg = "phone=15210011578";
        System.out.println(request(httpUrl, httpArg));
        try {
            Document doc = Jsoup.connect(httpUrl + "?" + httpArg)
                    .header("apikey", apikey)
                    .post();

            System.out.println(decodeUnicode(doc.text()));
            System.out.println(doc.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * unicode转化为中文
     *
     * @param utfString
     * @return
     */
    public String decodeUnicode(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

}
