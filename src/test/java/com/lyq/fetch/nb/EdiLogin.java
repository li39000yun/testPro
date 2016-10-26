package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

/**
 * EDI测试类
 * Created by liyunqiang on 2016/8/30.
 */
public class EdiLogin {

    @Test
    public void testEdi() throws IOException {
//       String url =  "http://szyt.net:8180/transit_nb2/www/wwwFetch.action?accreditId=nbdemo&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1";
//       String url =  "http://szyt.net:8180/transit_nb/www/wwwFetch.action?accreditId=nbdemo&busi_id=16080003&sequence_no=1&busi_id=16080003&sequence_no=1";
//       String url =  "http://szyt.net:8180/transit_nb2/www/wwwFetch.action?accreditId=nbdemo&busi_id=16080003&sequence_no=1";
//       System.out.println("宁波:" + Jsoup.connect(url).timeout(80000).get().body().text());
//        String cookie = "JSESSIONID=UcY0T7QYW+Jq+ogyKUHCTgy3.edi-weba; Path=/ediportal-web";
//        String cookie2 = "SESSION_ID_IN_BIZ=f4c37af871bda0a7489ea6e17f65e713; Expires=Thu, 08-Sep-2016 03:47:29 GMT; Path=/";

        String cookie = "JSESSIONID=Ro6u8cMbiQ9umG51DDfuugIF.edi-weba; Path=/ediportal-web";
        String cookie2 = "SESSION_ID_IN_BIZ=578d9e57a0635f0be7391c45df2879d8; Expires=Thu, 08-Sep-2016 03:48:56 GMT; Path=/";
        String url = "http://ys.cttms.com/service_center/ediListConn.jsp?ediNum=1000&ediCookie="
                + cookie + "&ediCookie2=" + cookie2;
        System.out.println(url);
//        http://ys.cttms.com/service_center/ediListConn.jsp?ediNum=1000&ediCookie=JSESSIONID=Ro6u8cMbiQ9umG51DDfuugIF.edi-weba; Path=/ediportal-web&ediCookie2=SESSION_ID_IN_BIZ=578d9e57a0635f0be7391c45df2879d8; Expires=Thu, 08-Sep-2016 03:48:56 GMT; Path=/

    }

    public static void main(String[] args) throws IOException {


        System.out.println(Jsoup.connect("http://service.cttms.com:8180/service_center/catchNbPort.jsp").timeout(30000).post().body().toString());

//        String url = "http://ys.cttms.com/service_center/ediLogin.jsp";
//        String nburl = "http://szyt.net:8180/transit_nb/ediLogin.jsp";
//
//        for (int i = 0; i < 100; i++) {
//            System.out.println("第" + i + "次进行登录校验");
//            try {
//                System.out.println(Console.FS_TIME.getNow());
//                System.out.println("服务中心:" + Jsoup.connect(url).timeout(10000).get().body().text());
//                System.out.println("宁波:" + Jsoup.connect(nburl).timeout(10000).get().body().text());
//                System.out.println("-------------------------------------------------------------------");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    Thread.sleep(5 * 60 * 1000);// 休眠5分钟
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

}
