package com.lyq.mode.duckStrategy;

public class BigYellowDuck extends Duck{
    public BigYellowDuck(){
        super();
        super.setFlyingStragety(new FlyNoWay());
    }
    @Override
    public void display() {
        System.out.println("我是大黄鸭子");    
    }
    public void quack() {
    }
}