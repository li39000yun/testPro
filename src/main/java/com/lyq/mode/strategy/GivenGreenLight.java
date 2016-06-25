package com.lyq.mode.strategy;

/**
 * 求吴国太开个绿灯。
 * Created by liyunqaing on 2016/6/25.
 */
public class GivenGreenLight implements IStrategy {

    @Override
    public void operate() {
        System.out.println("求吴国太开个绿灯，放行！");
    }
}
