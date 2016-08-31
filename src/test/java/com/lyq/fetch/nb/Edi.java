package com.lyq.fetch.nb;

import com.kingsoft.control.database.Connection;
import com.kingsoft.nb.business.vo.outer.npedi.Voyage;
import com.kingsoft.nb.common.WebservicesMonitor;
import com.kingsoft.nb.outer.VoyageInfo;
import com.kingsoft.nb.outer.npedi.LoginNewEdi;
import com.kingsoft.nb.outer.npedi.MessageByContainerNoImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * EDI测试类
 * Created by liyunqiang on 2016/8/30.
 */
public class Edi {

    @Test
    public void ediTest() {
        String containerNo = "TRHU2327440";
        try {
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN == null) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = new LoginNewEdi();
                WebservicesMonitor.FS_FETCH_NEW_LOGIN.login();
            }
            if (!WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN.login();
            }
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                List<VoyageInfo> cons = new ArrayList<VoyageInfo>();
                VoyageInfo voyageInfo = new VoyageInfo();
                voyageInfo.setContainerNo("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
//        String containerNo = "TRHU2327440";
        String containerNo = "FSCU8820962";
        boolean isNeedLogin = false;
        try {
            // 登录
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN == null) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = new LoginNewEdi();
                System.out.println("登录状态:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin());
                if (isNeedLogin) {
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.login();
                }
            }
            if (!WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                if (isNeedLogin) {
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.login();
                }
            }
            System.out.println("登录状态:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin());

            if (!isNeedLogin) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN.login = true;
            }
            // 获取柜号
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                System.out.println("cookie:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie());
                System.out.println("-----------");
                System.out.println("cookie2:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2());
//                JSESSIONID=7CJyDks4JlWPYY5WK0ATfJqS.edi-weba; Path=/ediportal-web
                String cookie = "JSESSIONID=8CJyDks4JlWPYY5WK0ATfJqS.edi-weba; Path=/ediportal-web";
                String cookie2 = "SESSION_ID_IN_BIZ=039517b88bcfbf70016aa13a79794385; Expires=Wed, 31-Aug-2016 04:19:03 GMT; Path=/";
//                String cookie = "JSESSIONID=7CJyDks4JlWPYY5WK0ATfJqS.edi-weba; Path=/ediportal-web";
//                String cookie2 = "SESSION_ID_IN_BIZ=939517b88bcfbf70016aa13a79794385; Expires=Wed, 31-Aug-2016 04:19:03 GMT; Path=/";
//                SESSION_ID_IN_BIZ=939517b88bcfbf70016aa13a79794385; Expires=Wed, 31-Aug-2016 04:19:03 GMT; Path=/
                if (!isNeedLogin) {// 不需要登录时设置cookie
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie(cookie);
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie2(cookie2);
                }
                MessageByContainerNoImpl nbEdi = new MessageByContainerNoImpl();
                nbEdi.fetch("ctnno=" + containerNo);
                WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
            } else {
                System.out.println("未登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
