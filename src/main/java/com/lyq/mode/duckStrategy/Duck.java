package com.lyq.mode.duckStrategy;

/**
 * 超类，所有的鸭子都有继承此类
 * 抽象了鸭子的行为：显示和鸣叫
 * Created by liyunqaing on 2016/6/26.
 * */
public abstract class Duck {
    /*
     * 鸭子发出叫声
     * 通用行为，由超类实现
     * */
    public void quack(){
        System.out.println("嘎嘎。。。。");
    }
    /*
     * 显示鸭子的外观
     * 鸭子的外观各不相同，声明外abstract,由子类实现**/
    public abstract void display();

    /** 组合：将飞行行为抽象为接口，在父类中持有该接口，并由该接口代替飞行行为*/
    @SuppressWarnings("unused")
    private FlyingStragety flyingStragety;
    public void setFlyingStragety(FlyingStragety flyingStragety){
        this.flyingStragety = flyingStragety;
    }
    public void fly(){
        flyingStragety.performFly();
    }
}

