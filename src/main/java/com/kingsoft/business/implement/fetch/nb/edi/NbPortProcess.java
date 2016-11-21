package com.kingsoft.business.implement.fetch.nb.edi;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Date;

/**
 * 定时抓取宁波船名航次
 * Created by jkx on 2016/10/21.
 */
public class NbPortProcess {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            if (!fetchNb()) {
                Thread.sleep(1000 * 60 * 60);
            }
        }
    }

    public static boolean fetchNb() {
        // 服务中心
        String serviceurl = "http://service.cttms.com:8180/service_center/catchNbPort.jsp";
        int times = 1;
        while (new Date().getHours() > 7 || new Date().getHours() < 22) {
            try {
                System.out.println("执行次数：" + times++);
                System.out.println("执行时间" + new Date().toLocaleString());
                System.out.println("----------------------------------" + serviceurl + "开始执行--------------------------------");
                try {
                    Jsoup.connect(serviceurl).timeout(1000 * 60 * 2).post().html();
                    System.out.println("----------------------------------" + serviceurl + "执行完成--------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.sleep(1000 * 60 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        return false;
    }


}
