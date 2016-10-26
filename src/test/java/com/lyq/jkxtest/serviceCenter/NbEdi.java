package com.lyq.jkxtest.serviceCenter;

import com.kingsoft.control.cryptography.DES;
import com.kingsoft.nb.dao.entity.manager.ServiceCenter;
import com.lyq.common.LyqUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkx on 2016/10/14.
 */
public class NbEdi {

    public static void main(String[] args) throws Exception {
        fetchCheckContainer();
//        String www = "http://ys.cttms.com/service_center/services/FetchCustomsService";
//
//        DES des = new DES();
//        Object[] params = new Object[]{des.decrypt("kingsoft"), "nbdemo", "16100013", 1, 1, "nbRegion"};
//
//        String rvalue = "111";
//        try {
//            rvalue = LyqUtil.execute(www, params, "getCheckContainer");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(rvalue);
    }
    public static String fetchCheckContainer()
            throws Exception {
        List<CheckContainer> vos = new ArrayList<CheckContainer>();
        CheckContainer checkContainer = new CheckContainer();
        checkContainer.setAccreditId("nbdemo");
        checkContainer.setBusiId("16100013");
        checkContainer.setSequence(1);
        checkContainer.setIsDoubleCon(1);
        checkContainer.setContainerNo("SUDU8701773");
        checkContainer.setBookingNo("MZA6NGBNJ0522");
        checkContainer.setShip("CSCLGLDBE/0011W");
        checkContainer.setVoyage("");
        checkContainer.setSealNo("H2624204");
        vos.add(checkContainer);
        checkContainer = new CheckContainer();
        checkContainer.setAccreditId("nbdemo");
        checkContainer.setBusiId("16100013");
        checkContainer.setSequence(1);
        checkContainer.setIsDoubleCon(1);
        checkContainer.setContainerNo("SUDU8701773");
        checkContainer.setBookingNo("MZA6NGBNJ0522");
        checkContainer.setShip("CSCLGLDBE/0011W");
        checkContainer.setVoyage("");
        checkContainer.setSealNo("H2624204");
        vos.add(checkContainer);

        if (vos != null && vos.size() > 0) {
            // 校验就去数据中心执行
            JSONObject json = new JSONObject();
            json.put("container", JSONArray.fromObject(vos.toArray()));
            // 修改柜校验状态信息
            String www = "http://ys.cttms.com/service_center/services/FetchCustomsService";
//            String www = "http://114.215.178.70/service_center/services/FetchCustomsService";
//            String www = "http://int.cttms.com/service_center/services/FetchCustomsService";
            DES des = new DES();
            Object[] arguments = new Object[] { des.encrypt("kingsoft"),
                    json.toString() };
            // 提交请求获取数据集
            String rvalue= "1";
            try {
                rvalue = LyqUtil.execute(www, arguments, "fetchCheckContainer");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(rvalue);
        }
        return "没有需要校验的柜号";
    }
}
