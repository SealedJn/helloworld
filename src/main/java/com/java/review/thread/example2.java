package com.java.review.thread;

public class example2 {
    public static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("new Thread");
        }
    }

    public static void main(String[] args) {
        new MyThread().run();

        // 函数式编程
        new Thread(()->{
           System.out.println("java 8 匿名类");
        }).start();
    }
}
