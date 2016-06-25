package com.lyq.mode.observerPattrenCommon.Weather;

/**
 * Created by liyunqaing on 2016/6/25.
 */
public class Client {

    public static void main(String[] args) {
        // 1创建目标
        ConcreteWeatherSubject weatherSubject = new ConcreteWeatherSubject();
        // 2创建观察者
        ConcreteWeatherObserver observerGirl = new ConcreteWeatherObserver();
        observerGirl.setObserverName("黄明的女朋友");
        observerGirl.setRemindThing("是我们的第一次约会，地点街心公园，不见不散哦");

        ConcreteWeatherObserver observerMum = new ConcreteWeatherObserver();
        observerMum.setObserverName("老妈");
        observerMum.setRemindThing("是一个购物的好日子，明天去天虹扫货");
        // 3注册观察者
        weatherSubject.attach(observerGirl);
        weatherSubject.attach(observerMum);
        // 4目标发布天气
        weatherSubject.setWeatherContent("明天天气晴朗，蓝天白云，气温28度");

    }
}
