package com.lyq.test.thread;

/**
 * Created by jkx on 2016/11/24.
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThrad run();");
    }

    public static void main(String[] args) {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        myThread1.start();
        myThread2.start();
    }
}
