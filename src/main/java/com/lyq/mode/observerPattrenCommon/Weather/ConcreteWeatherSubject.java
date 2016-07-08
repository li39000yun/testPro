package com.lyq.mode.observerPattrenCommon.Weather;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ConcreteWeatherSubject extends WeatherSubject {

    private String weatherContent;

    public String getWeatherContent() {
        return weatherContent;
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;
        this.notifyObservers();
    }

}
