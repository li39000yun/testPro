package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;
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
public class EdiMore {

    public static List<LoginNewEdi> ediList = new ArrayList<LoginNewEdi>();

    public static void main(String[] args) {
        String[] containerNos = {"TRHU2327440", "FSCU8820962", "KKFU7910818", "TGHU9472005"};
        System.out.println("begin：" + Console.FS_TIME.getNow());
        ediList = initEdiList(3);
        try {
            MessageByContainerNoImpl nbEdi = new MessageByContainerNoImpl();
            int j = 1;
            for (int i = 1; i < 41; i++) {
                System.out.println("第" + i + "次" + Console.FS_TIME.getNow());
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = getEDI();
                if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                    System.out.println("String cookie = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + "\";");
                    System.out.println("String cookie2 = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2() + "\";");
                    System.out.println("num = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.num + "\";");
                    nbEdi.fetch("ctnno=" + containerNos[j % 4]);
                    j++;
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
                } else {
                    System.out.println("未登录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end：" + Console.FS_TIME.getNow());
    }

    private static LoginNewEdi getEDI() {
        LoginNewEdi edi = null;
        String now = Console.FS_TIME.getNow();
        if (ediList.size() > 0) {
            for (int i = 0; i < ediList.size(); i++) {
                LoginNewEdi loginNewEdi = ediList.get(i);
                try {
                    loginNewEdi.isKeepConnection();
                    if (!loginNewEdi.isLogin()) {
                        loginNewEdi.login();
                        if (loginNewEdi.isLogin()) {
                            edi = loginNewEdi;
                            break;
                        }
                    }
                    if (!StringManage.isEmpty(loginNewEdi.cookieTime)) {
                        if (Console.FS_TIME.compareSecond(now, loginNewEdi.cookieTime) > 4) {
                            edi = loginNewEdi;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (edi == null) {
            edi = new LoginNewEdi();
            try {
                edi.login();
                edi.num = ediList.size();
                ediList.add(edi);// 添加到列表上
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return edi;
    }

    /**
     * 初始化cookie数
     *
     * @param num
     * @return
     */
    private static List<LoginNewEdi> initEdiList(int num) {
        List<LoginNewEdi> ediList = new ArrayList<LoginNewEdi>();
        LoginNewEdi edi;
        for (int i = 0; i < num; i++) {
            edi = new LoginNewEdi();
            try {
                edi.login();
            } catch (Exception e) {
                e.printStackTrace();
            }
            edi.num = ediList.size();
            ediList.add(edi);
        }
        return ediList;
    }

}
