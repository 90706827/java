package com.jimmy.zookeeper.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 分布式可重入排它锁
 *
 * @author jimmy
 */
public class InterProcessMutexTest {
    private static final Logger logger = LoggerFactory.getLogger(InterProcessMutexTest.class);
    public static void main(String[] args) throws InterruptedException {
        String lockPath = "/lock1";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2182")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zoo")
                .build();
        client.start();
        CuratorFramework client2 = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2182")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zoo")
                .build();
        client2.start();
        // 创建共享锁
        final InterProcessLock lock = new InterProcessMutex(client, lockPath);
//        // lock2 用于模拟其他客户端
        final InterProcessLock lock2 = new InterProcessMutex(client2, lockPath);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                lock.acquire();
               logger.error("1获取锁===============");
                // 测试锁重入
                lock.acquire();
               logger.error("1再次获取锁===============");
                Thread.sleep(5 * 1000);
                lock.release();
               logger.error("1释放锁===============");
                lock.release();
               logger.error("1再次释放锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                lock2.acquire();
               logger.error("2获取锁===============");
                // 测试锁重入
                lock2.acquire();
               logger.error("2再次获取锁===============");
                Thread.sleep(5 * 1000);
                lock2.release();
               logger.error("2释放锁===============");
                lock2.release();
               logger.error("2再次释放锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

}
