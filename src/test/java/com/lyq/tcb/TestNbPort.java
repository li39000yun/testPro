package com.lyq.tcb;

import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

/**
 * 宁波港打单测试
 * Created by liyunqiang on 2016/7/29.
 */
public class TestNbPort {


    @Test
    public void packing() throws IOException {
        JSONObject json = new JSONObject();
        json.put("blNo","MOLU11040080878");
        json.put("ctnCount",1);
        json.put("ctnSizeType","");
        System.out.println(json.toString());
        Connection.Response response =
                Jsoup.connect("http://58.135.76.132:6543/NBPORT/PrintNewEir")
                        .data("para", json.toString())//提单号
                        .ignoreContentType(true)
                        .timeout(60000 * 5)
                        .execute();
        System.out.println(JsonFmt.format(response.body()));
    }

//    二、根据装箱单编号打印已打印的单子
//    请求地址：
//    http://58.135.76.132:6543/NBPORT/PrintOldEir?para=xxx
//    请求参数：
//    {
//        "blNo":"MOLU11040080878",    	//打印提单号
//            "costcoNo":"99LU2016070700019995"     //装箱单编号
//    }
//    返回例子：
//    {
//        "State":1,                       //返回状态：1表示打单成功。0表示打单失败
//            "Message":"http://58.135.76.132:6543/NBPrintFile/99LU2016070700019995.pdf"
//        //如果打单成功，Message返回pdf打印文件的地址。如果失败，提示失败原因
//    }

    @Test
    public void  PrintOldEir() throws IOException {
        JSONObject json = new JSONObject();
        json.put("blNo","MOLU11040080878");
        json.put("costcoNo","99LU2016070700019995");
        System.out.println(json.toString());
        Connection.Response response =
                Jsoup.connect("http://58.135.76.132:6543/NBPORT/PrintOldEir")
                        .data("para", json.toString())//提单号
                        .ignoreContentType(true)
                        .timeout(60000 * 5)
                        .execute();
        System.out.println(JsonFmt.format(response.body()));
    }

}
