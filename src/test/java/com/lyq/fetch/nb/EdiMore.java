package com.lyq.fetch.nb;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.common.WebservicesMonitor;
import com.kingsoft.nb.outer.VoyageInfo;
import com.kingsoft.nb.outer.npedi.LoginNewEdi;
import com.kingsoft.nb.outer.npedi.MessageByContainerNoImpl;
import org.junit.Test;

import java.util.*;

/**
 * EDI测试类
 * Created by liyunqiang on 2016/8/30.
 */
public class EdiMore {

    public static void main(String[] args) {
        String[] containerNos = {"TRHU2327440", "FSCU8820962", "KKFU7910818", "TGHU9472005"};
        System.out.println("begin：" + Console.FS_TIME.getNow());
//        WebservicesMonitor.FS_EDI_LIST = WebservicesMonitor.initEdiList(3);
        try {
            MessageByContainerNoImpl nbEdi = new MessageByContainerNoImpl();
            int j = 1;
            for (int i = 1; i < 2; i++) {
                System.out.println("第" + i + "次" + Console.FS_TIME.getNow());
                WebservicesMonitor.FS_FETCH_NEW_LOGIN = WebservicesMonitor.getEDI();
                if (WebservicesMonitor.FS_FETCH_NEW_LOGIN.isLogin()) {
                    System.out.println("String cookie = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie() + "\";" + "String cookie2 = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.getCookie2() + "\";");
                    System.out.println("num = \"" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.num + "\";hashCode:" + WebservicesMonitor.FS_FETCH_NEW_LOGIN.hashCode());
                    nbEdi.fetch("ctnno=" + containerNos[j % 4]);
                    j++;
                } else {
                    System.out.println("未登录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end：" + Console.FS_TIME.getNow());
    }

}
