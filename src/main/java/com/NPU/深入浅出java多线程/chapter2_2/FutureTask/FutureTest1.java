package com.NPU.深入浅出java多线程.chapter2_2.FutureTask;

import java.util.concurrent.*;

public class FutureTest1 {
    public static void main(String[] args) {
        Task task = new Task(); // 新建异步任务
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task) {
            // 异步任务执行完成，回调
            @Override
            protected void done() {
                try {
                    System.out.println("future.done():" + get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        // 创建线程池（使用预定义的配置）
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(futureTask);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // 可以取消异步任务
        // future.cancel(true);
        try {
            // 阻塞，等待异步任务执行完毕-获取异步任务的返回值
            System.out.println("futureTask.get()" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步任务
     */
    static class Task implements Callable<Integer> {
        // 返回异步任务的执行结果
        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 10; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }
    }
}
