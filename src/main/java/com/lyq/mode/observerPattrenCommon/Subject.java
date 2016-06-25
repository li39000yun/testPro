package com.lyq.mode.observerPattrenCommon;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * Created by Administrator on 2016/6/25.
 */
public class Subject {
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    // 通知所有注册的观察者对象
    protected void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

}
