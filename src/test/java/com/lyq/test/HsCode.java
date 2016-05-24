package com.lyq.test;

import com.lyq.common.LyqUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

/**
 * 通关信息网抓商品信息
 * Created by lyq on 2016/5/24.
 */
public class HsCode {

    public static void main(String[] args) throws IOException, InterruptedException {
        HashMap<String, String> unitMap = getUnitMap();
        int begin;
        int end;
        for (int k = 1; k < 8; k++) {
            StringBuilder sb = new StringBuilder("INSERT INTO sys_goods(`code`,`name`,control,legal_unit1,legal_unit2,tax_rate,remark) VALUES");
            // 共2641页数据
            begin = 1 + (k - 1) * 400;
            end = k * 400;
            if (k == 7) {
                end = 2641;
            }
            for (int j = begin; j <= end; j++) {
                System.out.println("正在获取第" + j + "页数据");
                Document document = Jsoup.connect("http://www.hscode.net/IntegrateQueries/YsInfoPager")
                        .timeout(10000)
                        .data("pageIndex", String.valueOf(j))
                        .data("productName", "")
                        .data("taxCode", "")
                        .post();
                Elements elements = document.select(".scx_item");
                for (int i = 0; i < elements.size(); i++) {
                    sb.append(getSqlString(unitMap, elements.get(i)));
                }
                Thread.sleep(200);
            }
            LyqUtil.TextToFile("C:\\Users\\lyq\\Desktop\\" + end + "goods.sql", sb.toString().substring(0, sb.toString().length() - 1) + ";");
            Thread.sleep(5000);
        }
        System.out.println("结束");
    }

    private static String getSqlString(HashMap<String, String> unitMap, Element element) {
        Elements es = element.select("div");
        String code = "";
        String name = "";
        String control = "";
        String legal_unit1 = "";
        String legal_unit1_name = "";
        String legal_unit2 = "";
        String legal_unit2_name = "";
        String tax_rate = "";
        String remark = "";
        code = es.get(3).text();
        legal_unit1_name = es.get(8).text();
        if (unitMap.containsKey(legal_unit1_name)) {
            legal_unit1 = unitMap.get(legal_unit1_name);
        }
        legal_unit2_name = es.get(10).text();
        if (unitMap.containsKey(legal_unit2_name)) {
            legal_unit2 = unitMap.get(legal_unit2_name);
        }
        tax_rate = es.get(27).text();
        control = es.get(29).text();
        Elements elements1 = element.select(".even");
        name = elements1.get(1).text();
        remark = elements1.get(3).text();
        return "(\"" + code + "\",\"" + name + "\",\"" + control + "\",\"" + legal_unit1 + "\",\"" + legal_unit2 + "\",\"" + tax_rate + "\",\"" + remark + "\"),";
    }

    public static HashMap<String, String> getUnitMap() {
        HashMap<String, String> unitMap = new HashMap<String, String>();
        unitMap.put("立方米", "033");
        unitMap.put("架", "005");
        unitMap.put("棵", "027");
        unitMap.put("株", "028");
        unitMap.put("井", "029");
        unitMap.put("亿支", "055");
        unitMap.put("盆", "037");
        unitMap.put("万个", "038");
        unitMap.put("具", "039");
        unitMap.put("刀", "045");
        unitMap.put("疋", "046");
        unitMap.put("公担", "047");
        unitMap.put("套", "006");
        unitMap.put("扇", "048");
        unitMap.put("亿个", "056");
        unitMap.put("万套", "057");
        unitMap.put("千张", "058");
        unitMap.put("万张", "059");
        unitMap.put("万双", "144");
        unitMap.put("万粒", "145");
        unitMap.put("千粒", "146");
        unitMap.put("千米", "147");
        unitMap.put("令", "118");
        unitMap.put("个", "007");
        unitMap.put("千英尺", "148");
        unitMap.put("百万贝可", "149");
        unitMap.put("只", "008");
        unitMap.put("头", "009");
        unitMap.put("张", "010");
        unitMap.put("件", "011");
        unitMap.put("支", "012");
        unitMap.put("条", "015");
        unitMap.put("把", "016");
        unitMap.put("千枝", "053");
        unitMap.put("块", "017");
        unitMap.put("卷", "018");
        unitMap.put("对", "026");
        unitMap.put("根", "014");
        unitMap.put("米", "030");
        unitMap.put("平方米", "032");
        unitMap.put("百副", "040");
        unitMap.put("千克", "035");
        unitMap.put("百支", "041");
        unitMap.put("百把", "042");
        unitMap.put("克", "036");
        unitMap.put("千个", "054");
        unitMap.put("千只", "050");
        unitMap.put("百枝", "049");
        unitMap.put("千块", "051");
        unitMap.put("千盒", "052");
        unitMap.put("千伏安", "060");
        unitMap.put("千瓦", "061");
        unitMap.put("千瓦时", "062");
        unitMap.put("百个", "043");
        unitMap.put("短吨", "072");
        unitMap.put("吨", "070");
        unitMap.put("司马担", "073");
        unitMap.put("司马斤", "074");
        unitMap.put("斤", "075");
        unitMap.put("磅", "076");
        unitMap.put("担", "077");
        unitMap.put("英担", "078");
        unitMap.put("短担", "079");
        unitMap.put("两", "080");
        unitMap.put("市担", "081");
        unitMap.put("千支", "143");
        unitMap.put("长吨", "071");
        unitMap.put("组", "021");
        unitMap.put("英尺", "067");
        unitMap.put("英寸", "088");
        unitMap.put("寸", "089");
        unitMap.put("升", "095");
        unitMap.put("毫升", "096");
        unitMap.put("英加仑", "097");
        unitMap.put("美加仑", "098");
        unitMap.put("立方英尺", "099");
        unitMap.put("立方尺", "101");
        unitMap.put("台", "001");
        unitMap.put("盎司", "083");
        unitMap.put("片", "020");
        unitMap.put("克拉", "084");
        unitMap.put("千升", "063");
        unitMap.put("平方码", "110");
        unitMap.put("市尺", "085");
        unitMap.put("码", "086");
        unitMap.put("公制马力", "116");
        unitMap.put("盘", "031");
        unitMap.put("箱", "120");
        unitMap.put("座", "002");
        unitMap.put("批", "121");
        unitMap.put("份", "022");
        unitMap.put("罐", "122");
        unitMap.put("桶", "123");
        unitMap.put("扎", "124");
        unitMap.put("包", "125");
        unitMap.put("箩", "126");
        unitMap.put("打", "127");
        unitMap.put("筐", "128");
        unitMap.put("枝", "013");
        unitMap.put("辆", "003");
        unitMap.put("双", "025");
        unitMap.put("罗", "129");
        unitMap.put("匹", "130");
        unitMap.put("册", "131");
        unitMap.put("本", "132");
        unitMap.put("发", "133");
        unitMap.put("枚", "134");
        unitMap.put("捆", "135");
        unitMap.put("袋", "136");
        unitMap.put("平方英尺", "111");
        unitMap.put("艘", "004");
        unitMap.put("平方尺", "112");
        unitMap.put("合", "141");
        unitMap.put("瓶", "142");
        unitMap.put("百片", "044");
        unitMap.put("副", "019");
        unitMap.put("英制马力", "115");
        unitMap.put("粒", "139");
        unitMap.put("盒", "140");
        unitMap.put("幅", "023");
        unitMap.put("筒", "034");
        return unitMap;
    }
}
