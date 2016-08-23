package com.lyq.tcb;

import com.lyq.tcb.vo.Xmzhzk;
import com.lyq.tcb.vo.XmzhzkSearch;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lyq on 2016/7/7.
 */
public class TestXMZHZK {
    //    一、根据提单号获取箱子列表
    //    请求地址：
    //    http://data.jkx.tuochebao.com/XMPORT/GetBlInfo?para=xxx
    //    菜单类型(0101:进口码头提箱,0102:进口堆场返箱,0103:出口堆场提空,0104:出口重箱进码头)[需要根据TMS系统里的进出口和提空提重区分]
    //    请求参数：
    //    {
    //        "MenuID":"0103",         //操作菜单，必填项
    //            "LoginName":"ancht",     //用户名，必填项
    //            "PWD":"654321",          //登录密码，必填项
    //            "Blo":"604586734"        //提单号，必填项
    //    }
    @Test
    public void GetBlInfo() {
        XmzhzkSearch xmzhzkSearch = new XmzhzkSearch();
        xmzhzkSearch.setBlo("13018879991");
        xmzhzkSearch.setMenuID("0103");
        xmzhzkSearch.setLoginName("ancht");
        xmzhzkSearch.setPWD("654321");
//        String url = "http://data.jkx.tuochebao.com/XMPORT/GetBlInfo?para=";
        String url = "http://58.135.76.132:6543/XMPORT/GetBlInfo?para=";
        String para = null;
        try {
            para = URLEncoder.encode(JSONObject.fromObject(xmzhzkSearch).toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(url + para);
        Document document = null;
        try {
            document = Jsoup.connect(url + para).ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String rvalue = document.body().text();
        System.out.println(rvalue);
    }

    public static void main(String[] args) throws IOException {
        Xmzhzk xmzhzk = new Xmzhzk();

        xmzhzk.setMenuID("0103");
        xmzhzk.setLoginName("ancht");
        xmzhzk.setPWD("654321111111");
        xmzhzk.setEir_No("E201607040575");
        xmzhzk.setBlo("604586734");
        xmzhzk.setCtn("MRKU0573222");
        xmzhzk.setDriver("毕磊洋");
        xmzhzk.setE_SEALNO("12345678");
        xmzhzk.setIsWhole(0);
        xmzhzk.setTC_LOAD_DEPOT("华大基因");

        System.out.println(JSONObject.fromObject(xmzhzk).toString());

//        {
//            "MenuID":"0103",
//                "LoginName":"ancht",
//                "PWD":"654321",
//                "Eir_No":"E201607040575",
//                "Blo":"604586734",
//                "Ctn":"MRKU0573222",
//                "Driver":"毕磊洋",
//                "E_SEALNO":"1234567",
//                "IsWhole":0,
//                "TC_LOAD_DEPOT":"填写收货人/工厂装货地址、联系人、联系方式等"
//        }


//        String url = "http://58.135.76.132:6543/XMPORT/PrepareOrder?para=";
        String url = "http://data.jkx.tuochebao.com/XMPORT/PrepareOrder?para=";
//        String url = "http://58.135.76.132:6543/XMPORT/PrepareOrder";
        String para = URLEncoder.encode(JSONObject.fromObject(xmzhzk).toString(), "UTF-8");
        System.out.println(url + para);
        Document document = Jsoup.connect(url + para).ignoreContentType(true).get();
//        Document document = Jsoup.connect(url).data("para",para).post();
        String rvalue = document.body().text();
        System.out.println(rvalue);
        System.out.println(document.body().html());
        System.out.println(document.body().text());
        System.out.println(document.text());
    }

}
