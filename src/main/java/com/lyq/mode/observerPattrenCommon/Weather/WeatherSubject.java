package com.lyq.mode.observerPattrenCommon.Weather;

import com.lyq.mode.observerPattrenCommon.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * Created by Administrator on 2016/6/25.
 */
public class WeatherSubject {
    private List<WeatherObserver> observers = new ArrayList<WeatherObserver>();

    public void attach(WeatherObserver observer) {
        observers.add(observer);
    }

    public void detach(WeatherObserver observer) {
        observers.remove(observer);
    }

    // 通知所有注册的观察者对象
    protected void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(this);
        }
    }

}
