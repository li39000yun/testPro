package com.lyq.fetch.sh;

import com.kingsoft.business.implement.port.anchor.PortShipW5Fetch;
import com.kingsoft.dao.entity.baseinfo.customs.Anchor;

import java.util.List;

/**
 * Created by liyunqiang on 2016/7/28.
 */
public class TestPortShipW5Fetch {

    public static void main(String[] args) {
        PortShipW5Fetch portShipW5Fetch = new PortShipW5Fetch();
        try {
            List<Anchor> list = portShipW5Fetch.fetch();
            System.out.println("size:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Anchor anchor = list.get(i);
                System.out.println(anchor.getNameEn() + "-" + anchor.getVoyageIn() + "-" + anchor.getPullTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
