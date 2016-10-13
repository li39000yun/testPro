package com.lyq.fetch.sh;

import com.kingsoft.control.util.StringManage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by jkx on 2016/10/12.
 */
public class ETFetch extends AbstractOuterFetch {

    public static void main(String[] args) {
        ETFetch etFetch = new ETFetch();
        etFetch.et("SITU2808417");
    }

    public void et(String con) {
        //验证码url
        String img_url = "http://edi.easipass.com/dataportal/query.do?qn=dp_captcha";
        ETFetch fetch = new ETFetch();
        String cookieUrl = "http://edi.easipass.com/dataportal/q.do?qn=dp_cst_vsl";
//        String cookieUrl = "http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail";
        try {
            Connection.Response response = Jsoup.connect(cookieUrl).timeout(10000).execute();
            Map<String, String> cookies = response.cookies();
//            cookie = response.cookies().toString();
//            cookie = "JSESSIONID=" + response.cookie("JSESSIONID");
//            System.out.println("cookie：" + cookie);

//            CNZZDATA3631560=cnzz_eid%3D1439322429-1475115702-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1475115702
//            CNZZDATA3631514=cnzz_eid%3D1890910942-1476263710-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1476316168
            cookies.put("CNZZDATA3631560","cnzz_eid%3D1439322429-1475115702-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1475115702");
            cookies.put("CNZZDATA3631514","cnzz_eid%3D1890910942-1476263710-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1476316168");
            Document doc = response.parse();
            String validateCode;
            try {
                setHost("edi.easipass.com");
                validateCode = getCode(img_url,cookies);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("qid:"+doc.select("[name=qid]").first().val());
            String qid = doc.select("[name=qid]").first().val();
            try {
//                setHost("edi.easipass.com");
//                String validateCode = getValidateCode(img_url);
                // 手动输入验证码
                Scanner scanner = new Scanner(System.in);
                System.out.println("输入验证码");
                validateCode = scanner.nextLine();


                String query_url = "http://edi.easipass.com/dataportal/query.do";
//                String query_url = "http://edi.easipass.com/dataportal/query.do?ctn0=SITU2808417&bln0=&captcha=wppm&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb2b3003e";
                //提交查询
                doc = Jsoup.connect(query_url).cookies(cookies)
                        .data("ctn0", con)
                        .data("bln0", "")
                        .data("captcha", validateCode)
                        .data("pagesize", "30")
                        .data("submit", "执行")
                        .data("qid", qid)
//                        .data("qn", "dp_cst_vsl")
                        .get();
                System.out.println("--------------");
                System.out.println(doc.html());


            } catch (Exception e) {
                e.printStackTrace();
            }
//            http://edi.easipass.com/dataportal/query.do?qn=dp_captcha&t=0.5038017690119208
//            http://edi.easipass.com/dataportal/query.do?ctn0=SITU2808417&bln0=&captcha=5gp5&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb2b3003e
//            http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail&vs1name=SITC%20LAEM%20CHABANG&v0yage=1618S&ctn0=SITU2808417&captcha=5gp5


//            http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail
//            http://edi.easipass.com/dataportal/query.do?ctn0=SITU2808417&bln0=&captcha=npnp&pagesize=30&submit=%E6%9F%A5%E8%AF%A2&qn=dp_cst_vsl


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCode(String imageUrl, Map<String, String> cookies) throws Exception {
        if (StringManage.isEmpty(imageUrl)) {// 传入的验证码图片生成地址为空，返回null值.
            return null;
        }
        Connection.Response response = Jsoup.connect(imageUrl).timeout(10000)
                .cookies(cookies)
                .ignoreContentType(true)
                .header("Host", host)
                .header("Cache-Control", "no-cache")
                .method(Connection.Method.GET)
                .execute();

//        GET http://edi.easipass.com/dataportal/query.do?qn=dp_captcha HTTP/1.1
//        Host: edi.easipass.com
//        User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0
//        Accept: */*
//Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
//Accept-Encoding: gzip, deflate
//Referer: http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail&vs1name=SITC%20LAEM%20CHABANG&v0yage=1618S&ctn0=SITU2808417&captcha=wppm
//Cookie: CNZZDATA3631560=cnzz_eid%3D1439322429-1475115702-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1475115702; CNZZDATA3631514=cnzz_eid%3D1890910942-1476263710-http%253A%252F%252Fedi.easipass.com%252F%26ntime%3D1476316168; JSESSIONID=X2XQlsygHp6QBTGQ34LMPYMh1vlv0wdxLFnjn0p9t9gKwDXQQl9l!-272986349
//Connection: keep-alive


        System.out.println(response.contentType());
        File imageFile = new File(S_Project_Path + "vcode.jpg");
        FileOutputStream outStream = new FileOutputStream(imageFile);
        outStream.write(response.bodyAsBytes());

        // 解析验证码
//		UUWiseHelper httpTest = new UUWiseHelper();
//		httpTest.login();
//		return httpTest.getResult(httpTest.upload(imageFile.getPath(), "1004", false));
        return "";
    }


}
