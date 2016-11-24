package com.lyq.test.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyThread1 implements Runnable {
    int num = 100000;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("t1")) {
            increment();
        } else {
            decrement();
        }
    }

    //    public synchronized void increment() {
    public void increment() {
        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                num++;
            } finally {
                lock.unlock();
            }
        }
    }

    //    public synchronized void decrement() {
    public void decrement() {
        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                num--;
            } finally {
                lock.unlock();
            }
        }
    }

}

/**
 * 多线程内存可见性
 * Created by jkx on 2016/11/24.
 */
public class TestThread {
    public static void main(String[] args) {
        MyThread1 mythread1 = new MyThread1();
        Thread a = new Thread(mythread1, "t1");
        Thread b = new Thread(mythread1, "t2");
        a.start();
        b.start();
        try {
            a.join();
            b.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(mythread1.num);
    }
}
