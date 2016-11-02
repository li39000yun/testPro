package com.lyq.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 抓取PMP题库电子书
 * Created by lyq on 2016/10/27.
 */
public class Pmptiku {

    public static void main(String[] args) throws IOException {
        String url = "http://www.pmptiku.com/PMBOK%E7%AC%AC%E4%BA%94%E7%89%88/pg_0022.htm";
//                    http://www.pmptiku.com/PMBOK%E7%AC%AC%E4%BA%94%E7%89%88/pg_0023.htm
        for (int i = 2; i < 4; i++) {
            System.out.println(i + "-----------------------------------------------");
            url = "http://www.pmptiku.com/PMBOK%E7%AC%AC%E4%BA%94%E7%89%88/pg_002" + i + ".htm";
            Document document = Jsoup.connect(url).get();
            System.out.println(document.html());
        }
    }
}
