package com.lyq.test.thread;

class Thread5 extends Thread {
    private String name;

    public Thread5(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程开始运行!");
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "子线程开始运行" + i);
            try {
                sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
            }
        }
        System.out.println(Thread.currentThread().getName() + "线程结束运行!");
    }
}


/**
 * 多线程练习，join
 * Created by lyq on 2016/11/24.
 */
public class TestThread4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "主线程开始运行!");
        Thread5 threadA = new Thread5("A");
        Thread5 threadB = new Thread5("B");
        threadA.start();
        threadB.start();
        try {
            // join使主线程需要子线程执行完才往下走
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "主线程结束运行!");
    }
}
