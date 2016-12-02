package com.lyq.mode.strategy.feed;

/**
 * 喂养员<br/>
 * 环境角色
 * Created by lyq on 2016/12/2.
 */
public class Feeder {
    private FowlFeed fowlFeed;

    public FowlFeed getFowlFeed() {
        return fowlFeed;
    }

    public void setFowlFeed(FowlFeed fowlFeed) {
        this.fowlFeed = fowlFeed;
    }

    public Feeder(FowlFeed fowlFeed) {
        this.fowlFeed = fowlFeed;
    }

    // 策略方法
    public void feedInterface() {
        fowlFeed.feed();
    }
}
