package com.lyq.jkxtest.serviceCenter;

import com.kingsoft.control.Console;
import com.lyq.jkxtest.common.DB;

/**
 * Created by liyunqiang on 2016/8/1.
 */
public class PreserveShPort extends DB {

    public static void main(String[] args) throws InterruptedException {
        int times = 1;
        for (int j = 0; j < 1000; j++) {
            System.out.println("第" + times++ + "次维护时间");
            System.out.println(Console.FS_TIME.getNow());
            PreserveShPort preserveShPort = new PreserveShPort();
            preserveShPort.updateTime();
            Thread.sleep(1000 * 60 * 10);
        }
        System.out.println("end");

    }

    public void updateTime() {
        user = "kingsoft";
        password = "kssa201606..";
        conn = getConn();
        String sql = "UPDATE base_anchor SET harbor_time_start=SUBSTRING(base_anchor.harbor_time_start,1,19) WHERE region_id='shRegion' AND LENGTH(harbor_time_start) >19";
        execute(sql);
        closeConn(rs);// 关闭连接
    }

}