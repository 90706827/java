package com.jimmy.zookeeper.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 共享可重入读写锁
 *
 * @author jimmy
 */
public class InterProcessReadWriteLockTest {
    private static final Logger logger = LoggerFactory.getLogger(InterProcessReadWriteLockTest.class);
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
        // 创建共享可重入读写锁
        final InterProcessReadWriteLock locl1 = new InterProcessReadWriteLock(client, lockPath);
        // 获取读写锁(使用 InterProcessMutex 实现, 所以是可以重入的)
        final InterProcessLock readLock = locl1.readLock();

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                readLock.acquire();
                logger.info("1获取读锁===============");
                // 测试锁重入
                readLock.acquire();
                logger.info("1再次获取读锁===============");
                Thread.sleep(5 * 1000);
                readLock.release();
                logger.info("1释放读锁===============");
                readLock.release();
                logger.info("1再次释放读锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                Thread.sleep(500);
                readLock.acquire();
                logger.info("2获取读锁===============");
                // 测试锁重入
                readLock.acquire();
                logger.info("2再次获取读锁==============");
                Thread.sleep(5 * 1000);
                readLock.release();
                logger.info("2释放读锁===============");
                readLock.release();
                logger.info("2再次释放读锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

}
