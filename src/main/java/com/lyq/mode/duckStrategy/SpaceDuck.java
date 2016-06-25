package com.lyq.mode.duckStrategy;

public class SpaceDuck extends Duck{
    public SpaceDuck(){
        super();
        super.setFlyingStragety(new FlyWithRocket());
    }
    @Override
    public void display() {
        System.out.println("我是太空鸭子");    
    }
    public void quack() {
        System.out.println("无线电通讯");
    }
}