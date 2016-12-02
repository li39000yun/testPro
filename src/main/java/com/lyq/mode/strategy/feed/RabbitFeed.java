package com.lyq.mode.strategy.feed;

/**
 * 喂养兔子<br/>
 * 具体策略角色
 * Created by lyq on 2016/12/2.
 */
public class RabbitFeed implements FowlFeed {
    @Override
    public void feed() {
        System.out.println("喂兔子吃青菜和萝卜");
    }
}
