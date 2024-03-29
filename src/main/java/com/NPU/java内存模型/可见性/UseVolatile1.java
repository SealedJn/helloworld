package com.NPU.java内存模型.可见性;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description volatile适用的情况1
 */
public class UseVolatile1 implements Runnable{

    volatile boolean done = false;

    AtomicInteger realA = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new UseVolatile1();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(((UseVolatile1) r).done);
        System.out.println(((UseVolatile1) r).realA.get());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            // 赋值
            setDone();
            realA.incrementAndGet();
        }
    }

    private void setDone() {
        // 与之前状态没关系
        done = true;
    }
}
