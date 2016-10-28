/**
 * @(#)ShipTimeForIgzServiceImpl.java 2016年10月21日
 * <p>
 * Copyright 2016 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved.
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.jkxtest.serviceCenter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingsoft.business.BusinessService;
import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通过IGZ服务获取船信息,数据接口业务服务层接口实现类
 *
 * @author wangchao
 *
 * @version 2016年10月21日
 *
 * @since JDK 1.6
 *
 */
public class ShipTimeForIgzServiceImpl {

    public static void main(String[] args) throws Exception {
        ShipTimeForIgzServiceImpl shipTimeForIgzService = new ShipTimeForIgzServiceImpl();
        System.out.println(shipTimeForIgzService.queryForShipTime("CNYIK", "MAGU2242672", "16100054-1"));
    }

    public String queryForShipTime(String portcode, String containerNo,
                                   String busiId) throws Exception {
        // 通过传入的参数获取数据
        IgzServiceHelper igzServiceHelper = new IgzServiceHelper();
        String rvalue = igzServiceHelper.ProcessRequest(portcode, "",
                containerNo);
        System.out.println(rvalue);

        // 解析数据结果集
        JSONObject result = new JSONObject();
        result.put("busiId", busiId);
        JSONObject jsonObject = parseResult(rvalue);

        /************** 设置出口状态 *****************/
        String conState = StringManage.FS_EMPTY;// 箱状态
        // 提空箱时间
        if (jsonObject.containsKey("EE")) {
            conState = "提空箱";
            result.put("EE", jsonObject.getString("EE"));
        }
        // 出口重箱进场时间
        String Itime = StringManage.FS_EMPTY;
        if (jsonObject.containsKey("I")) {
            conState = "重箱进场";
            result.put("I", jsonObject.getString("I"));
            Itime = jsonObject.getString("I");
        }
        // 运抵
        if (jsonObject.containsKey("TR")) {
            conState = "运抵";
        }
        // 放行
        if (jsonObject.containsKey("CT")) {
            conState = "放行";
        }
        // 装船出场时间
        if (jsonObject.containsKey("AE")) {
            conState = "装船出场";
            result.put("AE", jsonObject.getString("AE"));
        }
        // 离港
        if (jsonObject.containsKey("VD")) {
            conState = "离港";
        }
        /************** 设置进口状态 *****************/
        // 卸船进场时间
        String UVtime = StringManage.FS_EMPTY;
        if (jsonObject.containsKey("UV")) {
            conState = "卸船";
            result.put("UV", jsonObject.getString("UV"));
            UVtime = jsonObject.getString("UV");
        }
        // 放行
        if (jsonObject.containsKey("CC")) {
            conState = "放行";
        }
        // 提进口重箱时间
        String OAtime = StringManage.FS_EMPTY;
        if (jsonObject.containsKey("OA")) {
            conState = "重箱出场";
            result.put("OA", jsonObject.getString("OA"));
            OAtime = jsonObject.getString("OA");
        }
        // 回空箱时间
        String MTtime = StringManage.FS_EMPTY;
        if (jsonObject.containsKey("MT")) {
            conState = "空箱返场";
            result.put("MT", jsonObject.getString("MT"));
            MTtime = jsonObject.getString("MT");
        }
        result.put("conState", conState);

        // 重堆天数和滞箱天数
        setupDays(result, Itime, UVtime, OAtime, MTtime);
        return result.toString();
    }

    private JSONObject parseResult(String rvalue) {
        JSONObject resultObject = new JSONObject();
        try {
            JSONObject jsonObject = JSONObject.fromObject(rvalue);
            JSONObject JSONObjectStatus = jsonObject.getJSONObject("Status");
            if (JSONObjectStatus.getString("Code").equals("0")) {
                jsonObject = jsonObject.getJSONObject("MessageBody");
                JSONArray rJSONArray = jsonObject.getJSONArray("Trace");
                SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm");
                if (rJSONArray.size() > 0) {
                    jsonObject = rJSONArray.getJSONObject(0);
                    rJSONArray = jsonObject.getJSONArray("TraceDetails");
                    for (int i = 0; i < rJSONArray.size(); i++) {
                        jsonObject = rJSONArray.getJSONObject(i);
                        String keyString = jsonObject.getString("EventCode");
                        jsonObject = jsonObject.getJSONObject("EventLocation");
                        jsonObject = jsonObject.getJSONObject("DataTime");
                        String valueString = jsonObject.getString("Value");
                        resultObject.put(keyString, sdf.format(
                                valueString));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObject;
    }

    public static Date parseDate(String date) {
        //Date d=new Date("2016/4/18 23:11:00")不推荐
        int year = 0, month = 0, day = 0, hour = 0, minute = 0;
        if (!StringManage.isEmpty(date.trim())) {
            Calendar cd = Calendar.getInstance();
            String[] dateTime = date.split(" ");
            if (dateTime.length == 2) {
                String[] da = dateTime[0].split("/");
                if (da.length == 3) {
                    year = Integer.parseInt(da[0]);
                    month = Integer.parseInt(da[1]);
                    day = Integer.parseInt(da[2]);
                }
                String[] ti = dateTime[1].split(":");
                if (da.length == 3) {
                    hour = Integer.parseInt(ti[0]);
                    minute = Integer.parseInt(ti[1]);
                }
            }
            cd.set(year, month - 1, day, hour, minute);
            Date d = cd.getTime();
            return d;
        }
        return new Date();
    }

    private void setupDays(JSONObject result, String Itime, String UVtime,
                           String OAtime, String MTtime) {
        if (!StringManage.isEmpty(UVtime)) {
            // 重堆天数
            if (!StringManage.isEmpty(OAtime)) {
                long heavyPileDays = Console.FS_DATE.compareDay(OAtime, UVtime) + 1;
                result.put("heavyPileDays", heavyPileDays);
            }
            // 滞箱天数MT - UV
            long demurrageConDays = 0;
            if (!StringManage.isEmpty(MTtime)) {
                demurrageConDays = Console.FS_DATE.compareDay(MTtime, UVtime) + 1;
            } else if (!StringManage.isEmpty(Itime)) {
                demurrageConDays = Console.FS_DATE.compareDay(Itime, UVtime) + 1;
            }
            result.put("demurrageConDays", demurrageConDays);
        }
    }
}
