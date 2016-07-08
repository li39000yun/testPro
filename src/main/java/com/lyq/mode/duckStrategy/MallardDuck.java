package com.lyq.mode.duckStrategy;

public class MallardDuck extends Duck{
    public MallardDuck(){
        super();
        super.setFlyingStragety(new FlyWithWin());
    }
    @Override
    public void display() {
        System.out.println("我的脖子是绿色的");    
    }
}