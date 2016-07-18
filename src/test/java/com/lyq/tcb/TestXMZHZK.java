package com.lyq.tcb;

import com.lyq.tcb.vo.Xmzhzk;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by lyq on 2016/7/7.
 */
public class TestXMZHZK {

    public static void main(String[] args) throws IOException {
        Xmzhzk xmzhzk = new Xmzhzk();

        xmzhzk.setMenuID("0103");
        xmzhzk.setLoginName("ancht");
        xmzhzk.setPWD("654321");
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
        System.out.println(url+para);
        Document document = Jsoup.connect(url+para).ignoreContentType(true).get();
//        Document document = Jsoup.connect(url).data("para",para).post();
        String rvalue = document.body().text();
        System.out.println(rvalue);
        System.out.println(document.body().html());
        System.out.println(document.body().text());
        System.out.println(document.text());
    }

}
