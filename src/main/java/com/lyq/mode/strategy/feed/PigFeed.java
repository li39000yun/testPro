package com.lyq.mode.strategy.feed;

/**
 * 喂养猪<br/>
 * 具体策略角色
 * Created by lyq on 2016/12/2.
 */
public class PigFeed implements FowlFeed {
    @Override
    public void feed() {
        System.out.println("喂猪吃糠");
    }
}
