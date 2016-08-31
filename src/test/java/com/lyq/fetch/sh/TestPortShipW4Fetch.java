package com.lyq.fetch.sh;

import com.kingsoft.business.implement.port.anchor.PortShipW4Fetch;
import com.kingsoft.dao.entity.baseinfo.customs.Anchor;

import java.util.List;

/**
 * Created by liyunqiang on 2016/7/28.
 */
public class TestPortShipW4Fetch {
    public static void main(String[] args) {
        PortShipW4Fetch portShipW4Fetch = new PortShipW4Fetch();
        try {
            List<Anchor> list = portShipW4Fetch.fetch();
            System.out.println("size:" + list.size());
            for (int i = 0; i < list.size(); i++) {
                Anchor anchor = list.get(i);
                System.out.println(anchor.getNameEn() + "-" + anchor.getVoyageIn() + "-" + anchor.getPullTime()+"-"+anchor.getHarborTimeStart()+"-"+anchor.getHarborTimeEnd());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
