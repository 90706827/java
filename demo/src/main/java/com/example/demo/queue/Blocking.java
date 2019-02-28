package com.example.demo.queue;

import java.util.concurrent.*;

/**
 * ClassName Blocking
 * Description 阻塞队列
 * Author  Mr.Jangni
 * Date 2019/1/8 17:12
 * Version 1.0
 **/
public class Blocking {
    public static void main(String[] args) {
        /**
         *  Queue方法使用说明：
         *     add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
         * 　　remove     移除并返回队列头部的元素         如果队列为空，则抛出一个NoSuchElementException异常
         * 　　element    返回队列头部的元素               如果队列为空，则抛出一个NoSuchElementException异常
         * 　　offer      添加一个元素并返回true         如果队列已满，则返回false
         * 　　poll       移除并返问队列头部的元素       如果队列为空，则返回null
         * 　　peek       返回队列头部的元素             如果队列为空，则返回null
         * 　　put        添加一个元素                      如果队列满，则阻塞
         * 　　take       移除并返回队列头部的元素          如果队列为空，则阻塞
         * LinkedBlockingQueue的容量是没有上限的（说的不准确，在不指定时容量为Integer.MAX_VALUE，不要然的话在put时怎么会受阻呢），但是也可以选择指定其最大容量，
         * 它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。
         *
         * ArrayBlockingQueue在构造时需要指定容量， 并可以选择是否需要公平性，如果公平参数被设置true，等待时间最长的线程会优先得到处理（其实就是通过将ReentrantLock
         * 设置为true来 达到这种公平性的：即等待时间最长的线程会先操作）。通常，公平性会使你在性能上付出代价，只有在的确非常需要的时候再使用它。它是基于数组的阻塞循
         * 环队 列，此队列按 FIFO（先进先出）原则对元素进行排序。
         *
         * PriorityBlockingQueue是一个带优先级的 队列，而不是先进先出队列。元素按优先级顺序被移除，该队列也没有上限（看了一下源码，PriorityBlockingQueue是对
         * PriorityQueue的再次包装，是基于堆数据结构的，而PriorityQueue是没有容量限制的，与ArrayList一样，所以在优先阻塞 队列上put时是不会受阻的。虽然此队列
         * 逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），但是如果队列为空，那么取元素的操作take就会阻塞，所以它的检
         * 索操作take是受阻的。另外，往入该队列中的元 素要具有比较能力。
         *
         * DelayQueue（基于PriorityQueue来实现的）是一个存放Delayed 元素的无界阻塞队列，只有在延迟期满时才能从中提取元素。该队列的头部是延迟期满后保存时间最
         * 长的 Delayed 元素。如果延迟都还没有期满，则队列没有头部，并且poll将返回null。当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于或等于零
         * 的值时，则出现期满，poll就以移除这个元素了。此队列不允许使用 null 元素。
         */
        ExecutorService fixPool = Executors.newFixedThreadPool(10);
        LinkedBlockingDeque<String> blockingQueue = new LinkedBlockingDeque<>(1000000);

        fixPool.execute(new Runnable() {
            @Override
            public void run() {
                for (long i =1 ; i<=1000000;i++){
                    try {
                        blockingQueue.put(String.valueOf(i));
//                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fixPool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long count = 1;
                while (true){
                    try {
                        System.out.println("1-"+count+"-"+blockingQueue.take());
                        count+=1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        fixPool.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long count = 1;
                while (true){
                    try {
                        System.out.println("2-"+count+"-"+blockingQueue.take());
                        count+=1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
