package com.example.demo.executor;

import java.util.concurrent.*;

/**
 * @program: java
 * @description: 缓存类线程池
 * @author: Mr.Jangni
 * @create: 2018-09-04 22:17
 **/
public class NewCachedThreadPool {
    private static Runnable getThread(final int i) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {

                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) {
        /**
         * corePoolSize（线程池的基本大小）
         * workQueue(任务队列)
         *  ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，按FIFO原则进行排序
         *  LinkedBlockingQueue:一个基于链表结构的阻塞队列，吞吐量高于ArrayBlockingQueue。静态工厂方法Excutors.newFixedThreadPool()
         *  使用了这个队列
         *  SynchronousQueue: 一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
         *  吞吐量高于LinkedBlockingQueue，静态工厂方法Excutors.newCachedThreadPool()使用了这个队列
         *  PriorityBlockingQueue:一个具有优先级的无限阻塞队列。
         * maximumPoolSize（线程池最大数量）
         * threadFactory（线程工厂）
         *  RejectedExecutionHandler （饱和策略）
         *  AbortPolicy:直接抛出异常，默认情况下采用这种策略
         *  CallerRunsPolicy:只用调用者所在线程来运行任务
         *  DiscardOldestPolicy:丢弃队列里最近的一个任务，并执行当前任务
         *  DiscardPolicy:不处理，丢弃掉
         * keepAliveTime(线程活动时间):线程池的工作线程空闲后，保持存活的时间
         * TimeUnit(线程活动时间的单位)
         */
        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,100,1, TimeUnit.SECONDS,blockingQueue);
        ExecutorService cachePool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            cachePool.execute(getThread(i));
        }
    }
}
