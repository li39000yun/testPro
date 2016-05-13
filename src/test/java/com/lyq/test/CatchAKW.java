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
//        maps.put("usernamex", "1124052906@qq.com");
//        maps.put("passwordx", "111111qq");
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
//        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c79689&idx=cb426bd29-30cc-4a2d-9af9-79a3205d3e32";
        // 一 2-1
//        String sturl = "http://www.aikao100.com/w55981.jsp?idx=cb95971a8-56c6-4d6d-8e8c-1445302660c2&parentx=c84321";
        // 二 2-2
//        String sturl = "http://www.aikao100.com/w55981.jsp?idx=ce70d2fcb-cb6e-44fc-9ed8-74e41966413a&parentx=c89619";
        // 3套1
//        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c17339&idx=c5e88b0b6-cb63-49cf-837f-216708d6b8c0&xiaoshi=0&fenzhong=0&miao=33";
        // 3套2
//        String sturl = "http://www.aikao100.com/w55981.jsp?idx=c62531f4c-9778-4c0d-ae27-928bd3f30d2c&parentx=c31029";
        // 4套1
//        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c6888418&idx=c38470d55-79a3-4826-9b77-b226ac997d1c&xiaoshi=0&fenzhong=2&miao=35";
        // 4套2
//        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c14365&idx=c18675272-5e19-4ae4-a5ab-86643de7d917&xiaoshi=0&fenzhong=0&miao=26";
        // 5套1
//        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c96516&idx=c6238d307-e45e-43ec-8850-3bca823c2b0f&xiaoshi=0&fenzhong=1&miao=46";
        // 5套2
        String sturl = "http://www.aikao100.com/w55981.jsp?parentx=c57572&idx=c50ced528-7e06-48f1-8803-ac962df1225d&xiaoshi=0&fenzhong=0&miao=40";


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
//            writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\jj.txt");
            writer = new FileWriter("C:\\Users\\lyq\\Desktop\\jj.txt");
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
