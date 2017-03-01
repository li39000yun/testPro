package com.lyq.fetch.taobao;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by lyq on 2017/1/4.
 */
public class TestQy {

    public static void main(String[] args) {
        String url = "";
        try {
            System.out.println(Jsoup.connect(url).get().html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
