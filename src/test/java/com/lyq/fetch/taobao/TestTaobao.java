package com.lyq.fetch.taobao;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by lyq on 2017/1/4.
 */
public class TestTaobao {

    public static void main(String[] args) {
        String url = "https://liangpinpuzi.tmall.com/search.htm?spm=a1z10.1-b-s.w5001-15641135991.4.1Ct5Hs#_#";
        try {
            System.out.println(Jsoup.connect(url).post().html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
