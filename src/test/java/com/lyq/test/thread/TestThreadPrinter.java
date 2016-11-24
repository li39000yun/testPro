package com.lyq.test.thread;

class ThreadPrinter extends Thread {
    private String name;
    private Object prev;
    private Object self;

    public ThreadPrinter(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.println(i+":"+name);
                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * wait练习,多线程打印
 * Created by lyq on 2016/11/25.
 */
public class TestThreadPrinter {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+"主程序开始");
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadPrinter pa = new ThreadPrinter("A", c, a);
        ThreadPrinter pb = new ThreadPrinter("B", a, b);
        ThreadPrinter pc = new ThreadPrinter("C", b, c);
        System.out.println("alive count:"+Thread.activeCount());
        pa.start();
        Thread.sleep(100);//确保按顺序A、B、C执行
        System.out.println("alive count:"+Thread.activeCount());
        pb.start();
        Thread.sleep(100);
        System.out.println("alive count:"+Thread.activeCount());
        pc.start();
        System.out.println("alive count:"+Thread.activeCount());
        Thread.sleep(100);
        System.out.println("alive count:"+Thread.activeCount());
        System.out.println(Thread.currentThread().getName()+"主程序结束");
    }

}
