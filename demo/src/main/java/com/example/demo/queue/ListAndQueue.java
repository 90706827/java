package com.example.demo.queue;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * ClassName ListAndQueue
 * Description 并发情况下使用queue替换list
 * Author  Mr.Jangni
 * Date 2019/1/8 15:59
 * Version 1.0
 **/
public class ListAndQueue {
    public static void main(String[] args) {
        //list在多线程环境下 会丢失存入数据
        LinkedList<String> list = new LinkedList<>();
        //blockingQueue 在多线程环境下会保证不会丢失存入数据 因为Blocking是阻塞类
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(100000);
        /**
         *     add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
         * 　　remove     移除并返回队列头部的元素         如果队列为空，则抛出一个NoSuchElementException异常
         * 　　element    返回队列头部的元素               如果队列为空，则抛出一个NoSuchElementException异常
         * 　　offer      添加一个元素并返回true         如果队列已满，则返回false
         * 　　poll       移除并返问队列头部的元素       如果队列为空，则返回null
         * 　　peek       返回队列头部的元素             如果队列为空，则返回null
         * 　　put        添加一个元素                      如果队列满，则阻塞
         * 　　take       移除并返回队列头部的元素          如果队列为空，则阻塞
         */
        ExecutorService fixPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            fixPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        list.add("a" + i);
                        queue.add("b" + i);
                    }
                }
        });
    }
        fixPool.shutdown();
        try

    {
        Thread.sleep(5000);
    } catch(
    InterruptedException e)

    {
        e.printStackTrace();
    }
        System.out.println(list.size());
        System.out.println(queue.size());
}
}
