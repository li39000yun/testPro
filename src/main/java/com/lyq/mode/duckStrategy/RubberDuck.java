package com.lyq.mode.duckStrategy;

public class RubberDuck extends Duck{
    public RubberDuck(){    
        super();//调用父类的构造函数
        super.setFlyingStragety(new FlyNoWay());
    }
    @Override
    public void display() {
        System.out.println("我的橡胶鸭子");    
    }
    public void quack() {
        System.out.println("瑟瑟。。。");
    }
}