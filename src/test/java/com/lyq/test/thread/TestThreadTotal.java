package com.lyq.test.thread;

class ThreadTotal implements Runnable {

    private long total = 0;
    private int num = 0;
    private boolean stopSign = false;

    @Override
    public void run() {
        while (!stopSign) {
            synchronized (this) {
                if (num == 10000) {
                    System.out.println(Thread.currentThread().getName() + "：1+2+3+...+100000000=" + total);
                    stopSign = true;
                }
                num++;
                total += num;
            }
        }
    }
}

/**
 * 使用多线程计算1+2+3+...+10000的值
 * Created by lyq on 2016/12/3.
 */
public class TestThreadTotal {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        System.out.println("主线程开始..." + beginTime);

        // 主线程计算
        long total = 0;
//        for (int i = 1; i <= 100000000; i++) {
//        for (int i = 1; i <= 10000; i++) {
//            total += i;
//        }
//        System.out.println("total:" + total);
//        主线程开始...
//        total:5000000050000000
//        主线程结束...
//        执行时间：66

        // 多线程计算
        Runnable threadTotal = new ThreadTotal();
        Thread thread1 = new Thread(threadTotal, "线程1");
        Thread thread2 = new Thread(threadTotal, "线程2");
        Thread thread3 = new Thread(threadTotal, "线程3");
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        主线程开始...1480732319911
//        线程3：1+2+3+...+100000000=4337407716900067
//        线程2：1+2+3+...+100000000=4434458458165559
//        线程1：1+2+3+...+100000000=4999999929175503
//        主线程结束...1480732320106
//        执行时间：195

        long endTime = System.currentTimeMillis();
        System.out.println("主线程结束..." + endTime);
        System.out.println("执行时间：" + (endTime - beginTime));
    }

}
