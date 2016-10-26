package com.kingsoft.business.implement.fetch.nb.edi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.kingsoft.business.vo.fetch.nb.edi.Voyage;
import com.kingsoft.business.vo.fetch.nb.edi.VoyageInfo;
import com.kingsoft.business.vo.fetch.nb.edi.VoyageInfoVO;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

import net.sf.json.JSONArray;

/**
 * URL：http://www.npedi.com/ediportal-web/ediweb/VoyageInfo.jsp
 * EDI抓取 船名航次
 *
 * @author lxm
 */

/**
 * @author Administrator
 */
public class InGateInfoImpl {
    private static Logger S_Logger = Logger.getLogger(InGateInfoImpl.class);

    private static final Pattern FS_PATTERN = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
    private static Matcher matcher;
    private static Map<String, String> matous = new HashMap<String, String>();
    private List<String> voyages = new ArrayList<String>();
    private List<VoyageInfo> voyageInfos = new ArrayList<VoyageInfo>();

    public InGateInfoImpl() {
    }

    public static void main(String[] args) throws InterruptedException, IOException, DataFormatException {
        matous.put("BLCT", "NBCT(二期)");
        matous.put("BLCT2", "北二集司(三期)");
        matous.put("BLCT3", "港吉(四期)");
        matous.put("BLCTYD", "远东(五期)");
        matous.put("BLCTZS", "大榭招商(CMICT)");
        matous.put("BLCTMS", "梅山码头");
        matous.put("ZHCT", "镇司(ZHCT)");
        matous.put("ZIT", "世航五洲(乍浦码头)");
        matous.put("YZCT", "甬舟码头");
        matous.put("DXCTE", "大榭E港区");
        matous.put("B2SCT", "北仑山码头");

        for (String key : matous.keySet()) {
            System.out.println("抓取的码头：" + key + matous.get(key));
            System.out.println("--------------------------------------------------------------");
            String voyage_url = "http://www.npedi.com/ediportal-web/getVoyagePorts.action?cpcode=" + key + "&vesselcode=";
            Response response = Jsoup.connect(voyage_url)
                    .header("Cookie", "JSESSIONID=Y1yWBD9lUIrEGibTBPdVU6dZ.edi-webb; SESSION_ID_IN_BIZ=bfc0597738ded3a5c84331a4728237af")
                    .timeout(20000).method(Method.POST).execute();
            System.out.println(response.body().toString());

            JSONArray fromObject = JSONArray.fromObject(response.body().toString());
            Voyage[] datas = (Voyage[]) JSONArray.toArray(fromObject, Voyage.class);
            VoyageInfo info;
            List<VoyageInfo> voyageInfos = new ArrayList<VoyageInfo>();
            for (Voyage data : datas) {
                info = new VoyageInfo();
                info.setEn_ship(data.getVesselename());// 英文船名
                info.setUnCode(data.getVesselcode());// 船舶UN代码
                info.setVoyage(data.getVoyage());// 航次
                info.setDock(matous.get(data.getCpcode()));// 码头
                info.setType("E");
                if (!StringManage.isEmpty(data.getCtnstart()))
                    info.setInStartTime(fmtDate(data.getCtnstart()));
                if (!StringManage.isEmpty(data.getCtnend()))
                    info.setInCutOffTime(fmtDate(data.getCtnend()));
                voyageInfos.add(info);
            }

            int i = 1;
            for (VoyageInfo d : voyageInfos) {
                System.out.println(i++ + d.getEn_ship() + "/" + d.getVoyage() + ";" + d.getInStartTime() + ";" + d.getInCutOffTime() + ";" + d.getDock());
            }
        }

//    InGateInfoImpl nbEdi = new InGateInfoImpl();
//    VoyageInfo[] datas = nbEdi.fetch();
//    System.out.println("datas.length" + datas.length);
//    for (VoyageInfo data : datas) {
//      System.out.println("ship:" + data.getCn_ship() + data.getEn_ship() + ";voyage:" + data.getVoyage());
//    }


    }

    /**
     * 格式化日期
     *
     * @param dateStr
     * @return
     * @throws DataFormatException
     */
    static String fmtDate(String dateStr) throws DataFormatException {
        if (dateStr.equals("")) return "";
        matcher = FS_PATTERN.matcher(dateStr);
        if (matcher.find())
            return matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3) + " " + matcher.group(4) + ":" + matcher.group(5) + ":" + matcher.group(6);
        else
            throw new DataFormatException(dateStr + " 日期格式不对");
    }

}
