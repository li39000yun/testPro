package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * EDI测试类
 * Created by liyunqiang on 2016/8/30.
 */
public class EdiLogin {

    public static void main(String[] args) {
        String url = "http://ys.cttms.com/service_center/ediLogin.jsp";
        String nburl = "http://szyt.net:8180/transit_nb/ediLogin.jsp";

        for (int i = 0; i < 100; i++) {
            System.out.println("第" + i + "次进行登录校验");
            try {
                System.out.println(Console.FS_TIME.getNow());
                System.out.println("服务中心:" + Jsoup.connect(url).timeout(10000).get().body().text());
                System.out.println("宁波:" + Jsoup.connect(nburl).timeout(10000).get().body().text());
                System.out.println("-------------------------------------------------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(5 * 60 * 1000);// 休眠5分钟
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
