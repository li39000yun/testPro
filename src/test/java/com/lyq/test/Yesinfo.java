package com.lyq.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyq on 2016/5/20.
 */
public class Yesinfo {

    public static void main(String[] d) throws IOException {
//        new Yesinfo().httPget();
        new Yesinfo().login();
    }

    public void login() throws IOException {
//        Connection.Response response = Jsoup.connect("http://www.yesinfo.com.cn/j_security_check")
////                .header("Cookie", "loginName=SZZCD; lzstat_uv=11707845643283298448|2112694; Hm_lvt_4abde670ace888216387076226f9457a=1463560225,1463560801,1463647087,1463705906; Hm_lvt_8c1df953e8dc4368fb2df6340d5fe602=1463560827; lzstat_ss=818749244_3_1463737085_2112694; Hm_lpvt_4abde670ace888216387076226f9457a=1463708286; JSESSIONID=2E7728CC00640E8FF175A71C2BBEFEAF.portal_81")
//                .header("Cookie","JSESSIONID=E89AF6CA4FA097CF74A593BD8CA3C0A6.vipspweb_83; loginName=SZZCD")
//                .data("j_username", "szzcd")
//                .data("j_password", "z22726496.")
//                .data("imageField", "1")
//                .data("imageField", "14")
//                .method(Connection.Method.POST)
//                .execute();
//        System.out.println(response.cookies().toString());
//        System.out.println(response.body());

        Connection.Response response2 = Jsoup.connect("http://www.yesinfo.com.cn/vipspweb/spweb/ccisInspectInquiry.action")
//                .header("Cookie","JSESSIONID=E89AF6CA4FA097CF74A593BD8CA3C0A6.vipspweb_83; lzstat_uv=5460252292359279700|2112694; lzstat_ss=1432017950_9_1463740617_2112694; Hm_lvt_4abde670ace888216387076226f9457a=1463710299; Hm_lpvt_4abde670ace888216387076226f9457a=1463711818; JSESSIONID=0E4784D502D94082BCB8942FA4BFEF47.portal_81; Hm_lvt_8c1df953e8dc4368fb2df6340d5fe602=1463710299; Hm_lpvt_8c1df953e8dc4368fb2df6340d5fe602=1463710489; loginName=SZZCD")
                .header("Cookie","JSESSIONID=E89AF6CA4FA097CF74A593BD8CA3C0A6.vipspweb_83; loginName=SZZCD")
//                .header("Cookie"," JSESSIONID=0E4784D502D94082BCB8942FA4BFEF47.portal_81;loginName=SZZCD")
                .method(Connection.Method.GET)
                .execute();
        System.out.println(response2.cookies().toString());
        System.out.println(response2.body());


    }

    public void httPget() throws IOException {
        Document doc = Jsoup.connect("http://www.yesinfo.com.cn/vipspweb/spweb/supCntrEnquire/query.action")
                .header("Cookie", "loginName=SZZCD; lzstat_uv=11707845643283298448|2112694; Hm_lvt_4abde670ace888216387076226f9457a=1463560225,1463560801,1463647087,1463705906; Hm_lvt_8c1df953e8dc4368fb2df6340d5fe602=1463560827; lzstat_ss=818749244_3_1463737085_2112694; Hm_lpvt_4abde670ace888216387076226f9457a=1463708286; JSESSIONID=2E7728CC00640E8FF175A71C2BBEFEAF.portal_81")
                .get();
        System.out.println(doc.body());
    }
}

