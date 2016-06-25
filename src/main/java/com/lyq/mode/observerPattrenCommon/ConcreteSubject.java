package com.lyq.mode.observerPattrenCommon;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ConcreteSubject extends Subject{

    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
        this.notifyObservers();
    }

}
