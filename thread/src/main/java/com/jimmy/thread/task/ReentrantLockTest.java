package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author zhangguoq
 **/
public class ReentrantLockTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockTest.class);

    /**
     * 支持公平锁，默认非公平
     */
    private ReentrantLock lock = new ReentrantLock();
    private int count;

    @Override
    public void method() {
        lock();
//        tryLock();
//        tryLockTime();
//        lockInterrupt();
    }


    /**
     * 同步操作
     */
    public void lock() {
        lock.lock();
        try {
            logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                new Exception(e);
            }
            logger.info("{},结束业务count:{}", Thread.currentThread().getName(), count);
        } finally {
            lock.unlock();
            logger.info("{},释放锁", Thread.currentThread().getName());
        }
    }

    /**
     * 1、用在定时任务时，如果任务执行时间可能超过下次计划执行时间，确保该有状态任务只有一个正在执行，忽略重复触发。
     * 2、用在界面交互时点击执行较长时间请求操作时，防止多次点击导致后台重复执行（忽略重复触发）
     */
    public void tryLock() {
        if (lock.tryLock()) {
            try {
                logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
                count++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    new Exception(e);
                }
                logger.info("{},结束业务count:{}", Thread.currentThread().getName(), count);
            } finally {
                lock.unlock();
                logger.info("{},释放锁", Thread.currentThread().getName());
            }
        } else {
            logger.info("{},业务正在执行,跳过操作！", Thread.currentThread().getName());
        }


    }

    public void tryLockTime() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                try {
                    logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
                    count++;
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        new Exception(e);
                    }
                    logger.info("{},结束业务count:{}", Thread.currentThread().getName(), count);
                } finally {
                    lock.unlock();
                    logger.info("{},释放锁", Thread.currentThread().getName());
                }
            } else {
                logger.info("{},重复执行,跳过操作！", Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            logger.info("{},当前线程被中断时抛出异常！", Thread.currentThread().getName());
        }
    }

    public void lockInterrupt() {
        try {
            lock.lockInterruptibly();
            logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
            count++;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                new Exception(e);
            }
            Thread.interrupted();
            logger.info("{},结束业务count:{}", Thread.currentThread().getName(), count);
        } catch (InterruptedException e) {
            logger.info("{},当前线程被中断时抛出异常！", Thread.currentThread().getName());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                logger.info("{},释放锁", Thread.currentThread().getName());
            }
        }
    }
}