package com.lyq.test.thread;


/**
 * Created by jkx on 2016/11/24.
 */
public class MyThreadRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("MyThreadRunnable run();");
    }

    public static void main(String[] args) {
        MyThreadRunnable myThreadRunnable = new MyThreadRunnable();
        Thread thread = new Thread(myThreadRunnable);
        thread.start();
    }

}
