// D:\project\suanfa\src\jvm\Test01.java
package jvm;

import java.util.concurrent.*;



public class Test01 {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                2,  // 核心线程数
                2,  // 最大线程数
                0,  // 空闲线程存活时间
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),  // 队列容量为2
                new ThreadPoolExecutor.AbortPolicy()  // 拒绝策略
        );
        printThreadPoolStatus(poolExecutor);
        // 先提交足够的任务填满线程池
        for (int i = 0; i < 4; i++) {  // 总共4个任务：2个核心线程 + 2个队列容量
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 正在执行任务");
                        Thread.sleep(5000);  // 休眠5秒，模拟实际工作
                        System.out.println(Thread.currentThread().getName() + " 任务完成");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };
            poolExecutor.execute(runnable);
            System.out.println("已提交第 " + (i + 1) + " 个任务");
        }



        // 关闭线程池
       // poolExecutor.shutdown();
    }
    /**
     * 打印线程池的状态
     *
     * @param threadPool 线程池对象
     */
    public static void printThreadPoolStatus(ThreadPoolExecutor threadPool) {

    }


}