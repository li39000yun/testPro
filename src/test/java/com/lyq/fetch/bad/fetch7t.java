package com.lyq.fetch.bad;

import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * Created by lyq on 2016/11/17.
 */
public class fetch7t {
    public static void main(String[] args) {
        String url = "http://17tav4.top/download.php?id=1";
        String rvalue = null;
        try {
            rvalue = Jsoup.connect(url).timeout(5000).post().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(rvalue);
    }
}
