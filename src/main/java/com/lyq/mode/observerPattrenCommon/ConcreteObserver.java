package com.lyq.mode.observerPattrenCommon;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ConcreteObserver implements Observer {

    private String observerState;

    public void update(Subject subject) {
        observerState = ((ConcreteSubject) subject).getSubjectState();
    }

}
