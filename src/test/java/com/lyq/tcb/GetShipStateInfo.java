package com.lyq.tcb;

import com.kingsoft.control.Console;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

/**
 * 天津港运抵状态和物流信息查询接口
 * Created by Administrator on 2016/6/23.
 */
public class GetShipStateInfo {

    public String url = "http://data.jkx.tuochebao.com";
    public String testUrl = "http://58.135.76.132:6543";

    //region 运抵信息接口  url:http://query.customs.gov.cn/MNFTQ/MQuery.aspx
    @Test
    public void getShipStateInfo() throws IOException {
        // 141600175034 0217 天津东疆保税港区海关（港区） 测试Ok
        // CUCHI160563525 0202 新港海关 测试X
        // OOLU2575473270 0202 新港海关

        Connection.Response response =
                Jsoup.connect(url + "/ShipToInfo/GetShipStateInfo")
                        .data("para", "141600175034")//提单号
                        .data("portCode", "0217")//地区代码
//                        .data("para", "OOLU2575473270")//提单号
//                        .data("portCode", "0202")//地区代码
                        .ignoreContentType(true)
                        .timeout(60000 * 5)
                        .execute();
        System.out.println(JsonFmt.format(response.body()));
    }
    //endregion

    //region 物流信息接口 url:http://wl.tjportnet.com/frontdesk/index.action
    //http://wl.tjportnet.com/ggxxfw/hkgz/xxcx/NewYpcd_list.action
    //OOLU2574276830 0217  测试OK
    // http://wl.tjportnet.com/ggxxfw/hkgz/xxcx/Ypcd_list.action 东疆 预配舱单查询
    @Test
    public void getLogisticsInfo() throws IOException {
        // OOLU2575473270 0202 新港海关
        // OOLU2574276830 0217 东疆海关
        Connection.Response response =
                Jsoup.connect(url + "/ShipToInfo/GetLogisticsInfo")
//                        .data("para", "OOLU2574276830")//提单号
//                        .data("portCode", "0217")//地区代码
                        .data("para", "OOLU2575473270")//提单号
                        .data("portCode", "0202")//地区代码
                        .ignoreContentType(true)
                        .timeout(60000 * 5)
                        .execute();
        System.out.println(JsonFmt.format(response.body()));

    }
    //endregion

    //region 运抵和物流合并接口
    @Test
    public void shipToInfo() throws IOException {
        Connection.Response response =
                Jsoup.connect(url + "/ShipToInfo/GetCompleteInfo")
                        .data("para", "141600175034")//提单号
                        .data("portCode", "0217")//地区代码
                        .ignoreContentType(true)
                        .timeout(60000 * 5)
                        .execute();
        System.out.println(JsonFmt.format(response.body()));
    }
    //endregion

}
