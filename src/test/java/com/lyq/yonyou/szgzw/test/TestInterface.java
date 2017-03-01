package com.lyq.yonyou.szgzw.test;

import com.lyq.common.Json;
import com.lyq.yonyou.szgzw.finance.EntFinanace;
import net.sf.json.JSONObject;

/**
 * Created by 云强 on 2017/2/28.
 */
public class TestInterface {

    public static void main(String[] args) {
        EntFinanace entFinanace = new EntFinanace();
        Json json = new Json(true,"",entFinanace);
        System.out.println(JSONObject.fromObject(json).toString());
    }

}
