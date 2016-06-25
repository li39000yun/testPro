package com.lyq.mode.duckStrategy;

public class Test {
    public static void main(String[] args) {
        Duck duck =null;
        duck = new MallardDuck();
        duck.display();
        duck.quack();
        duck.fly();
        System.out.println("**************");
        duck = new RedheadDuck();
        duck.display();
        duck.quack();
        duck.fly();
        System.out.println("*****************");
        duck = new RubberDuck();
        duck.display();
        duck.quack();
        duck.fly();
        System.out.println("*****************");
        duck = new BigYellowDuck();
        duck.display();
        duck.quack();
        duck.fly();
        System.out.println("*****************");
        duck = new SpaceDuck();
        duck.display();
        duck.quack();
        duck.fly();
    }
}