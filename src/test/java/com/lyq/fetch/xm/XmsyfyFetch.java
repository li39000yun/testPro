package com.lyq.fetch.xm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kingsoft.business.implement.fetch.AbstractFetch;
import com.kingsoft.business.vo.fetch.FetchData;
import com.kingsoft.business.vo.fetch.FetchSearch;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * 厦门嵩屿码头费用数据抓取服务类
 *
 * @author liyunqiang
 * @version 2015-5-6
 * @since JDK 1.6
 */
public class XmsyfyFetch extends AbstractFetch {
    private static Logger S_Logger = Logger.getLogger(XmsyfyFetch.class);
    private static final long serialVersionUID = 1L;
    /**
     * 查询网址维护后费用
     */
    private static final String FS_URL = "http://www.xsct.com.cn/new/Com_Tarrif_report.aspx";
    /**
     * 查询网址维护前费用
     */
    private static final String FS_URL_BEFORE = "http://www.xsct.com.cn/new/Com_Tarrif_report_latest.aspx";
    /**
     * 返回结果
     */
    private List<FetchData> fetchDatas;

    private String bn = StringManage.FS_EMPTY;// 提单
    private String cn = StringManage.FS_EMPTY;// 箱号
    private int type = 2;//用于查询页面表格id 2维护后，1维护前 (2个网址对应的数据表格Id不一样  id=GridView+type)
    private int hasPages = 0;//存在分页

    public static void main(String[] args) {
        FetchSearch serch = new FetchSearch();
        serch.setRegionId(4);
        serch.setDock("XMSYFY");
//        serch.setBookingNo("1811X16NWD10594Q1");
//        serch.setBookingNo("MSCUT9816343");
//        serch.setContainerNo("FSCU3927393");
        serch.setBookingNo("570222863");
        serch.setContainerNo("MSKU0443196");
//        serch.setBookingNo("1811X16FWD30099R1");
//        serch.setContainerNo("MEDU7332458");
        XmsyfyFetch main = new XmsyfyFetch();
        try {
            String rvalue = main.preExecute(serch);
            System.out.println(rvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加接口日志
     *
     * @param wwwUrl
     * @param html
     */
    private void addLog(String wwwUrl, String html) {
//        FetchLog fetchLog = new FetchLog();
//        fetchLog.setUrl(wwwUrl);
//        fetchLog.setParameter(nvpsToString(nvps));
//        fetchLog.setResult(html);
//        fetchLog.setOperateTime(Console.FS_TIME.getNow());
//        LogHelper.addFetchLog(fetchLog);
    }

    @Override
    public String preExecute(FetchSearch search) throws Exception {
        bn = search.getBookingNo();
        cn = search.getContainerNo();
        String rvalue = "[]";
        fetchDatas = new ArrayList<FetchData>();
        try {
            //抓取费用
            rvalue = fetch(FS_URL + "?bn=" + search.getBookingNo() + "&cn=");
            addLog(FS_URL + "?bn=" + search.getBookingNo() + "&cn=", rvalue);
            if (rvalue.equals("[]")) {
                type = 1;
                rvalue = fetch(FS_URL_BEFORE + "?bn=" + search.getBookingNo() + "&cn=");
                addLog(FS_URL_BEFORE + "?bn=" + search.getBookingNo() + "&cn=", rvalue);
            }
            //多个同名费用，处理成一个
            if (rvalue.length() > 2) {
                return totalMoney();
            }

        } catch (Exception e) {
            if (S_Logger.isDebugEnabled())
                S_Logger.debug("service_center - XmsyfyFetch error type=" + type + "  " + search.toString2() + " " + e.getMessage());
            e.printStackTrace();
        }
        return rvalue;
    }

    private String totalMoney() throws Exception {
        Map<String, FetchData> fees = new HashMap<String, FetchData>();
        FetchData tempObj;
        for (FetchData temp : fetchDatas) {
            if (fees.containsKey(temp.getName())) {
                tempObj = fees.get(temp.getName());
                tempObj.setValue(Console.FS_NUMBER.add(Double.parseDouble(tempObj.getValue()), Double.parseDouble(temp.getValue()), 2) + "");
            } else
                fees.put(temp.getName(), temp);
        }
        return arrayToJson(fees.values().toArray(new FetchData[0]));
    }

    public String fetch(String url) throws Exception {
        Document doc = Jsoup.connect(url).timeout(8000).post();
        String VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
        String EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION").val();

        // 标题
        Elements headName = doc.select("table#GridView" + type + " tr th");
        // 字段表示在表格中td下标，key=标题中文名 es:提单号
        Map<String, Integer> headNameMap = new HashMap<String, Integer>();
        Map<Integer, String> headIndexMap = new HashMap<Integer, String>();
        int rowIndex = 0;
        for (Element th : headName) {
            headNameMap.put(th.text(), rowIndex);
            headIndexMap.put(rowIndex, th.text());
            rowIndex++;
        }
        if (headName.isEmpty())
            return "[]";

        // 获取页面表格，最后一行，判断是否有分页(id=GridView1的表格 tr class =gv-foot)
        Elements lastRow = doc.select("table#GridView" + type + " tr.gv-foot");
        int pageLength = 1;
        if (lastRow != null && lastRow.size() > 0) {// lastRow.text() 结果 1 2 3
            pageLength = lastRow.text().split(" ").length;
            hasPages = 1;
        }

        // 处理分页
        String result = "[]";
        for (int page = 1; page <= pageLength; page++) {
            result = nextPage(url, doc, headNameMap, headIndexMap, page, VIEWSTATE, EVENTVALIDATION);
        }
        return result;
    }

    /**
     * 处理分页
     *
     * @param doc          文档对象
     * @param headNameMap  标题下标Map
     * @param headIndexMap 标题下标Map
     * @param page         第几页
     * @return
     * @throws Exception
     */
    public String nextPage(String url, Document doc, Map<String, Integer> headNameMap, Map<Integer, String> headIndexMap, int page, String VIEWSTATE, String EVENTVALIDATION) throws Exception {
        if (page != 1) {
            try {
                doc = Jsoup.connect(url + "?bn=" + bn + "&cn=")
                        .header("Cookie", "ASP.NET_SessionId=duwceg451wlc5a45gyeur345")
                        .data("__EVENTARGUMENT", "Page$" + page)
                        .data("__EVENTTARGET", "GridView" + type)
                        .data("__EVENTVALIDATION", EVENTVALIDATION)
                        .data("__VIEWSTATE", VIEWSTATE)
                        .timeout(8000)
                        .post();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                addLog(url + "?bn=" + bn + "&cn=", doc.html());
            }
        }

        // 获取 tr带align属性的行(table id=GridView1表格)
        Elements trs = doc.select("table#GridView" + type + " tr[align=center]");
        if (trs == null)
            return "[]";

        // feeBeginIndex:费用开始下标 , feeEndIndex:结束下标
        int feeBeginIndex = headNameMap.get("堆存天数") + 1;
        int feeEndIndex = headNameMap.get("费用合计");

        // 循环去除最后一行分页(有分页才去 -hasPages )
        for (int i = 0; i < trs.size() - hasPages; i++) {
            // 箱号封号与查询的不一致，跳过
            if (!bn.equals(trs.get(i).child(headNameMap.get("提单号")).text())
                    || !cn.equals(trs.get(i).child(headNameMap.get("箱号")).text()))
                continue;

            // 循环费用
            for (int feeIndex = feeBeginIndex; feeIndex < feeEndIndex; feeIndex++) {
                if ("0".equals(trs.get(i).child(feeIndex).text()))
                    continue;
                if (isNumeric(trs.get(i).child(feeIndex).text())) {
                    if (Double.parseDouble(trs.get(i).child(feeIndex).text()) != 0) {
                        FetchData fetchData = new FetchData();
                        fetchData.setName(headIndexMap.get(feeIndex));
                        fetchData.setValue(trs.get(i).child(feeIndex).text());
                        fetchDatas.add(fetchData);
                    }
                }
            }
        }
        return arrayToJson(fetchDatas.toArray(new FetchData[0]));
    }
}
