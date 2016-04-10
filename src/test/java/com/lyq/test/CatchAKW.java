package com.lyq.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 抓取爱考网试题数据
 * Created by Administrator on 2016/4/8.
 */
public class CatchAKW {

    @Test
    public void test() throws Exception {
        System.out.println("开始");

        String loginurl = "http://www.aikao100.com/logina.jsp";
        Document doc = Jsoup.connect(loginurl).timeout(5000).get();
//        System.out.println(doc.select("form").get(1));
        FormElement form = (FormElement) doc.select("form").get(1);
//        System.out.println(form.attr("action"));
        String loginAction = "http://www.aikao100.com/" + form.attr("action");

//        System.out.println("=============================");
        HashMap<String, String> maps = new HashMap<String, String>();
        maps.put("usernamex", "li39000yun@qq.com");
        maps.put("passwordx", "li39000yun");
        maps.put("prepage", "http://www.aikao100.com/logina.jsp?");
        maps.put("x", "56");
        maps.put("y", "16");
        Connection.Response response = Jsoup.connect(loginAction).data(maps).method(Connection.Method.POST).execute();
        Map<String, String> cookies = response.cookies();
//        doc = response.parse();
//        System.out.println(cookies);
//        System.out.println("=============================");
//        System.out.println(doc.html());
//        doc = Jsoup.connect("http://www.aikao100.com/w58751.jsp").cookies(cookies).get();
//        System.out.println(doc.html());
        // 基金练习第一套
//        String sturl = "http://www.aikao100.com/w55981.jsp?idx=c64f7640b-868a-44c4-b15b-fb287fd688ef&parentx=c62123";
        // 基金练习第二套
        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c79689&idx=cb426bd29-30cc-4a2d-9af9-79a3205d3e32";

        StringBuilder sb = new StringBuilder("");
        while (true) {
            doc = Jsoup.connect(sturl).cookies(cookies).get();
            // 类型
            Elements elements = doc.select(".shiti_con_left_1");
            System.out.println(elements.get(0).text());
            sb.append(elements.get(0).text()).append("\r\n");
            // 问题
            elements = doc.select(".shiti_con_left_2");
            System.out.println(elements.get(0).text());
            sb.append(elements.get(0).text()).append("\r\n");
            // 选项
            elements = doc.select("[name=xuanxiang]");
            for (Element element : elements) {
                System.out.println(element.val() + " " + element.nextSibling().outerHtml());
                sb.append(element.val()).append(" ").append(element.nextSibling().outerHtml()).append("\r\n");
            }
            // 解答
            elements = doc.select(".shiti_con_right");
            System.out.println(elements.get(0).text());
            sb.append(elements.get(0).text()).append("\r\n");

//            System.out.println("=====================");
            elements = doc.select(".stnext");
//            System.out.println(elements.get(0).attr("onclick"));
            String next = elements.get(0).attr("onclick");
            if (next.contains("jsp")) {
                int begin = next.indexOf("if('") + 4;
                int end = next.indexOf("==") - 1;
                sturl = "http://www.aikao100.com/" + next.substring(begin, end);
            } else {
                break;
            }
            System.out.println("=======================");
            sb.append("=======================" + "\r\n");
        }

        FileWriter writer;
        try {
            writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\jj.txt");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("end");
//        sturl="http://www.aikao100.com/w55981.jsp?parentx=c62123&idx=cdab11e9d-7aa2-42b1-bd37-b40cf8cd2cff&xiaoshi=0&fenzhong=10&miao=41";
//        sturl="http://www.aikao100.com/w55981.jsp?parentx=c62123&idx=cc722adf8-b6b6-4124-ab63-613ec3550fc4&xiaoshi=0&fenzhong=14&miao=20";


//        http://www.aikao100.com/84cf63fa-2d07-4d9e-a628-a87290a292d6_save.jsp
//        prepage=http://www.aikao100.com/logina.jsp?
//        usernamex=li39000yun@qq.com
//                passwordx=li39000yun1
//        x=56
//        y=16

//        f397a872-c6a1-4d94-981d-87849a2569f4_save.jsp


//        String ksurl = "http://www.aikao100.com/w95691.jsp";
//        doc = Jsoup.connect(ksurl).timeout(5000).get();
//        System.out.println(doc.html());

    }

    @Test
    public void writeFileTest() throws IOException {
        FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\jj1.txt");
        writer.write("2323\n123\r\n23243\r2323");
        writer.flush();
        writer.close();
    }
}
