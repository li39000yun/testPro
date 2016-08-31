package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
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
//        String containerNo = "FSCU8820962";
        String[] containerNos = {"TRHU2327440", "FSCU8820962", "KKFU7910818", "TGHU9472005"};

        boolean isNeedLogin = true;// 需要登录
//        boolean isNeedLogin = false;// 免登录，使用cookie
        System.out.println("begin：" + Console.FS_TIME.getNow());
        try {
            // 登录
            if (WebservicesMonitor.FS_FETCH_NEW_LOGIN == null) {
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = new LoginNewEdi();
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
                System.out.println("String cookie = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + "\";");
                System.out.println("String cookie2 = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2() + "\";");

                String cookie = "JSESSIONID=WrGJee3YOR6IN0BuGkg5U54n.edi-webb; Path=/ediportal-web";
                String cookie2 = "SESSION_ID_IN_BIZ=fed546618253726cb8a40a5d097b01fd; Expires=Thu, 01-Sep-2016 07:17:30 GMT; Path=/";
                if (!isNeedLogin) {// 不需要登录时设置cookie
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie(cookie);
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.setCookie2(cookie2);
                }
                WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
                MessageByContainerNoImpl nbEdi = new MessageByContainerNoImpl();
                int j = 1;
                for (int i = 1; i < 41; i++) {
                    System.out.println("第" + i + "次" + Console.FS_TIME.getNow());
                    System.out.println("cookie:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + " cookie2:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2());
                    nbEdi.fetch("ctnno=" + containerNos[j % 4]);
                    j++;
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
                    Thread.sleep(4000);
                }
            } else {
                System.out.println("未登录");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end：" + Console.FS_TIME.getNow());
    }

}
