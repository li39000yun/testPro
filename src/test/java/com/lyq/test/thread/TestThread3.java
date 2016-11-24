package com.lyq.test.thread;


class Thread3 extends Thread {
    private int count = 5;
    private String name;

    public Thread3(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " 运行 count=" + count--);
            try {
                sleep((long) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread4 implements Runnable {

    private int count = 15;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " 运行 count=" + count--);
            try {
                Thread.sleep((long) (Math.random()*10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 多线程练习
 * Created by lyq on 2016/11/24.
 */
public class TestThread3 {

    public static void main(String[] args) {
//        Thread3 thread1 = new Thread3("A");
//        Thread3 thread2 = new Thread3("B");
//        thread1.start();
//        thread2.start();

        Thread4 thread4 = new Thread4();
        new Thread(thread4,"C").start();
        new Thread(thread4,"D").start();
        new Thread(thread4,"E").start();
    }

}
