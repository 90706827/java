package com.jimmy.zookeeper.lock;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 共享信号量
 *
 * @author jimmy
 */
public class InterProcessSemaphoreV2Test {
    private static final Logger logger = LoggerFactory.getLogger(InterProcessSemaphoreV2Test.class);

    public static void main(String[] args) throws InterruptedException {
        String lockPath = "/lock1";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zoo")
                .build();
        client.start();
        // 创建一个信号量, Curator 以公平锁的方式进行实现
        final InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, lockPath, 2);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取一个许可
                Lease lease = semaphore.acquire();
                logger.info("1获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnLease(lease);
                logger.info("1释放读信号量===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取一个许可
                Lease lease = semaphore.acquire();
                logger.info("2获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnLease(lease);
                logger.info("2释放读信号量===============");

                countDownLatch.countDown();
            } catch (
                    Exception e) {
                e.printStackTrace();
            }

        }).start();

        countDownLatch.await();
    }
}
