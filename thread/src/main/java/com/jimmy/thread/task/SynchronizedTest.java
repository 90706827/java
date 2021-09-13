package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author zhangguoq
 **/
public class SynchronizedTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedTest.class);
    private static Object object = new Object();
    private static AtomicInteger atomicCount = new AtomicInteger(0);
    private Integer count = 0;

    @Override
    public void method() {
        methods();
//        synchronized (object) {
//            logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
//            count++;
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            logger.info("{},释放锁,结束业务count:{}", Thread.currentThread().getName(), count);
//        }
    }

    private static synchronized void methods() {
        logger.info("{},获得锁,开始业务......", Thread.currentThread().getName());
        try {
            logger.info("{},开始方法......", Thread.currentThread().getName());
            Thread.sleep(1000);

            atomicCount.addAndGet(1);
            logger.info("{},结束方法......", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("{},释放锁,结束业务count:{}", Thread.currentThread().getName(), atomicCount);
    }

}