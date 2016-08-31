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
public class EdiMore {

    public static void main(String[] args) {
        String[] containerNos = {"TRHU2327440", "FSCU8820962", "KKFU7910818", "TGHU9472005"};
        System.out.println("begin：" + Console.FS_TIME.getNow());
        List<LoginNewEdi> ediList = initEdiList(3);
        try {
            MessageByContainerNoImpl nbEdi = new MessageByContainerNoImpl();
            int j = 1;
            for (int i = 1; i < 41; i++) {
                System.out.println("第" + i + "次" + Console.FS_TIME.getNow());
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = getEDI(ediList);
                if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                    System.out.println("String cookie = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + "\";");
                    System.out.println("String cookie2 = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2() + "\";");
                    nbEdi.fetch("ctnno=" + containerNos[j % 4]);
                    j++;
                    WebservicesMonitor.FS_FETCH_NEW_LOGIN.isKeepConnection();
                    Thread.sleep(4000);
                } else {
                    System.out.println("未登录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end：" + Console.FS_TIME.getNow());
    }

    private static LoginNewEdi getEDI(List<LoginNewEdi> ediList) {
        LoginNewEdi edi = null;
        if (ediList.size() > 0) {
            edi = ediList.get(0);
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
            ediList.add(edi);
        }
        return ediList;
    }

}
