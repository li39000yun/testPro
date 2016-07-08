package com.lyq.mode.strategy;

/**
 * 找乔国老帮忙，使孙权不能杀刘备。
 * Created by liyunqaing on 2016/6/25.
 */
public class BackDoor implements IStrategy {

    @Override
    public void operate() {
        System.out.println("找乔国老帮忙，让吴国太给孙权施加压力，使孙权不能杀刘备...");
    }
}
