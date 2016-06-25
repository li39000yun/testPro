package com.lyq.mode.strategy;

/**
 * 孙夫人断后，挡住追兵。
 * Created by liyunqaing on 2016/6/25.
 */
public class BlackEnemy implements IStrategy {

    @Override
    public void operate() {
        System.out.println("孙夫人断后，挡住追兵...");
    }
}
