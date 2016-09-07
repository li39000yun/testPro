package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
import com.kingsoft.nb.common.WebservicesMonitor;
import com.kingsoft.nb.outer.VoyageInfo;
import com.kingsoft.nb.outer.npedi.LoginNewEdi;
import com.kingsoft.nb.outer.npedi.MessageByContainerNoImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * EDI测试类
 * Created by liyunqiang on 2016/8/30.
 */
public class EdiLoginState {


    public static void main(String[] args) {
        System.out.println("begin：" + Console.FS_TIME.getNow());
        try {
            // 登录
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN == null) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = new LoginNewEdi();
            }
            WebservicesMonitor.FS_FETCH_NEW_LOGIN.login = true;
            String cookie = "JSESSIONID=jeTsB9VEgzxNPVvZUF2XWlt7.edi-webd; Path=/ediportal-web";
            String cookie2 = "SESSION_ID_IN_BIZ=5ccd6c2634a0fe6eb23d5d22224d8f23; Expires=Fri, 02-Sep-2016 07:54:25 GMT; Path=/";
            System.out.println("http://szyt.net:8180/transit_nb/ediLoginSetup.jsp?ediCookie=" + cookie + "&ediCookie2=" + cookie2);
            WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie(cookie);
            WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie2(cookie2);
            WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
            System.out.println(cookie2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end：" + Console.FS_TIME.getNow());
    }

}
