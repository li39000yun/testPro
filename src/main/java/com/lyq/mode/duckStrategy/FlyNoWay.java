package com.lyq.mode.duckStrategy;

public class FlyNoWay implements FlyingStragety{
    @Override
    public void performFly() {
        System.out.println("我不会飞行");    
    }
}