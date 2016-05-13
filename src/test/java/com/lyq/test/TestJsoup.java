package com.lyq.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lyq on 2016/4/11.
 */
public class TestJsoup {

    @Test
    public void testCustoms() throws IOException {
//        String url = "http://query.customs.gov.cn/MNFTQ";
        String url = "http://www.customs.gov.cn/tabid/49564/Default.aspx";
        Connection.Response response = Jsoup.connect(url).timeout(8000).method(Connection.Method.GET).execute();
        Map<String, String> cookies = response.headers();
        System.out.println(response.cookies());
        String url2 = "http://query.customs.gov.cn/MNFTQ/MQuery.aspx";

//        System.out.printf(Jsoup.connect(url).timeout(5000).get().outerHtml());
////        response = Jsoup.connect(url).timeout(5000).cookies(cookies).method(Connection.Method.GET).execute();
//        response = Jsoup.connect(url).timeout(5000).method(Connection.Method.GET).execute();
//        System.out.println(response.cookies());
//        System.out.printf("=============================");
//        System.out.printf(response.parse().html());


        Connection.Response response2 = Jsoup.connect(url2).header("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.5, en; q=0.3")
                .header("Connection", "Keep-Alive")
                .header("Host", "query.customs.gov.cn")
                .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; LCJB)")
                .method(Connection.Method.POST).timeout(8000).header("language_0=zh-CN", cookies.get("language_0=zh-CN")).execute();
        System.out.println(response2.cookies());
    }

}
