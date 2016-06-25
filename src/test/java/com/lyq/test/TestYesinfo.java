package com.lyq.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 盐田易港讯
 * Created by lyq on 2016/5/18.
 */
public class TestYesinfo {

    @Test
    public void testLogin() {
        Document document;
        Connection.Response response;
        Map<String, String> cookies;
        try {
            // 获取cookies
//            String url = "http://www.yesinfo.com.cn/login.jsp";
            String url = "http://www.yesinfo.com.cn/vipspweb/index.jsp?loginVerifyCode=XXiYCpVAlY5xYyzQf0gfBUqICrLBWa8goxNPLfvRxfDZl8OxpoDE8v6RUbR5jTGiMTAyMzYzNA==\";\n";
            response = Jsoup.connect(url).method(Connection.Method.GET).timeout(5000).execute();
            cookies = response.cookies();
            System.out.println(cookies);
            System.out.println(response.headers());

            // 登录
//            response = Jsoup.connect("http://www.yesinfo.com.cn/j_security_check")
////                    .cookies(cookies)
//                    .data("imageField.x", "32")
//                    .data("imageField.y", "11")
//                    .data("j_password", "SZZCD")
//                    .data("j_username", "z22726496.")
//                    .timeout(5000).method(Connection.Method.POST).execute();
//            cookies = response.cookies();
//            System.out.println(cookies);
//            System.out.println(response.headers());
//            System.out.println(response.body());

//            url = "http://www.yesinfo.com.cn/j_security_check";
//            HashMap<String, String> data = new HashMap<String, String>();
//            data.put("imageField.x", "32");
//            data.put("imageField.y", "11");
//            data.put("j_username", "SZZCD");
//            data.put("j_password", "z22726496.");
//            data.put("j_password", "666");
//            data.put("j_username", "444");
//            System.out.println(cookies);
//            cookies.put("loginName","SZZCD;expires=Sat, 18 Jun 2016 10:02:06 GMT");
//            cookies.put("loginName","444;lzstat_uv=11707845643283298448|2112694;Hm_lvt_4abde670ace888216387076226f9457a=1463560225,1463560801,1463647087,1463705906; Hm_lvt_8c1df953e8dc4368fb2df6340d5fe602=1463560827; lzstat_ss=818749244_0_1463734705_2112694; Hm_lpvt_4abde670ace888216387076226f9457a=1463705906;");
//            response = Jsoup.connect(url).cookies(cookies).data(data).timeout(5000).method(Connection.Method.POST).execute();
//            cookies = response.cookies();
//            System.out.println("---------------------------");
//            System.out.println(cookies);
//            System.out.println(response.headers());
//            System.out.println(response.parse().body().html());
            url = "http://www.yesinfo.com.cn/bridge.action";
//            cookies.put("loginName","SZZCD;expires=Sat, 18 Jun 2016 10:02:06 GMT");
//            System.out.println(Jsoup.connect(url).cookies(cookies).get().html());


//            url = "http://www.yesinfo.com.cn/vipspweb/spweb/index.action?loginVerifyCode=5mAVImYCjxJP7s605hASloYOLwIOn55Av8CixXq25q318Z8TveXPL4xqqWZsNnbVMTAyMzYzNA==";
//            Jsoup.connect(url).get();
//            System.out.println(Jsoup.connect(url).get().html());
//            url = "http://www.yesinfo.com.cn/vipspweb/spweb/index.action";
//            cookies.put("loginName", "SZZCD");
//            response = Jsoup.connect(url).timeout(5000).cookies(cookies).method(Connection.Method.GET).execute();
//            cookies = response.cookies();
//            System.out.println(cookies);
//            System.out.println(response.body());

            // 打开查验界面
//            url = "http://www.yesinfo.com.cn/vipspweb/spweb/ccisInspectInquiry.action";
//            document = Jsoup.connect(url).cookies(cookies).timeout(5000).get();
//            System.out.printf(document.text());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
