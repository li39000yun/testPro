package com.lyq.test.thread;

class Thread2 implements Runnable {

    public String name;

    public Thread2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行：" + i);
            try {
                Thread.sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 多线程练习
 * Created by jkx on 2016/11/24.
 */
public class TestThread2 {

    public static void main(String[] args) {
        Thread2 thread3 = new Thread2("C");
        Thread2 thread4 = new Thread2("D");
        new Thread(thread3).start();
        new Thread(thread4).start();
    }
}
