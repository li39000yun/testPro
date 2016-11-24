package com.lyq.test.thread;

class ThreadYeild extends Thread {

    public ThreadYeild(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println("----" + this.getName() + "---" + i);
            // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i == 30) {
                this.yield();
            }
        }
    }
}

/**
 * Created by lyq on 2016/11/24.
 */
public class TestThreadYield {
    public static void main(String[] args) {
        ThreadYeild threadYeild1 = new ThreadYeild("A");
        ThreadYeild threadYeild2 = new ThreadYeild("B");
        threadYeild1.start();
        threadYeild2.start();
    }
}
