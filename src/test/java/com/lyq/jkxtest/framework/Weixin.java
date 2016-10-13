package com.lyq.jkxtest.framework;

import com.kingsoft.control.database.WebPager;
import com.lyq.common.LyqUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jkx on 2016/10/11.
 */
public class Weixin {

    public static void main(String[] args) throws Exception {
        String www = "http://ys.cttms.com/services/WeixinService";
//        String www = "http://crm.cttms.com:8180/crm/services/WeixinService";

        WebPager webPager = new WebPager();
        String jsonPager = JSONObject.fromObject(webPager).toString();

        WeixinSearch search = new WeixinSearch();
        search.setAccreditId("demo");
        search.setBeginTime("2016-10-10 00:00:00");
        search.setEndTime("2016-10-20 23:59:59");
        search.setParamString("APLU1111111");
        String jsonSearch = JSONObject.fromObject(search).toString();


        Object[] params = new Object[]{"demo", "jkx", "jkx168", jsonSearch, jsonPager};
//        Object[] params = new Object[]{"kingsoft", "web", "jkxweb2014", jsonSearch, jsonPager};
//        Object[] params = new Object[]{"rjb", "rjbadmin", jsonSearch, jsonPager};

        String rvalue = LyqUtil.execute(www, params, "online");
        System.out.println(rvalue);
        JSONObject json = JSONObject.fromObject(rvalue);
        List<Online> onlines = new ArrayList<Online>();
        if (json.containsKey("online")) {
            JSONArray jsonArray = JSONArray.fromObject(json.getString("online"));
            Online online = null;
            Iterator<?> ite = jsonArray.iterator();
            while (ite.hasNext()) {
                online = (Online) JSONObject.toBean(JSONObject.fromObject(ite.next()), Online.class);
                onlines.add(online);
            }
        }
        for (Online online : onlines) {
            System.out.println(online.getContainerNo()+":"+online.getContainerAddition()+":"+online.getContainerAddition().getFileName());
        }
    }

}
