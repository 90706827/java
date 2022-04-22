package com.jimmy.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.*;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author jimmy
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private CuratorFramework curatorFramework;
    @Resource
    private CuratorFramework curatorFrameworkTwo;

    /**
     * 可重入 排他锁
     *
     * @param path
     * @return
     */
    @RequestMapping("/lock/{path}")
    @ResponseBody
    public String InterProcessMutex(@PathVariable("path") String path) {
        path = File.separator + path;
        logger.info("path:{}", path);
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        InterProcessMutex lockTwo = new InterProcessMutex(curatorFrameworkTwo, path);
        try {
            boolean isLock = lock.acquire(10, TimeUnit.SECONDS);
            logger.info("加锁是否成功：{}", isLock);
            if (isLock) {
                logger.info("业务处理开始...");
                Thread.sleep(2000);
                logger.info("业务处理结束...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                logger.info("释放锁完成！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "over";
    }


    /**
     * 共享锁
     * InterProcessSemaphoreMutex
     *
     * @param path
     * @return
     */
    @RequestMapping("/lock2/{path}")
    @ResponseBody
    public String interProcessSemaphoreMutex(@PathVariable("path") String path) {
        path = File.separator + path;
        logger.info("path:{}", path);
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        try {
            boolean isLock = lock.acquire(10, TimeUnit.SECONDS);
            logger.info("加锁是否成功：{}", isLock);
            if (isLock) {
                logger.info("业务处理开始...");
                Thread.sleep(2000);
                logger.info("业务处理结束...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                logger.info("释放锁完成！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "over";
    }

    /**
     * 共享可重入读写锁
     * InterProcessMutex
     *
     * @param path
     * @return
     */
    @RequestMapping("/lock3/{path}")
    @ResponseBody
    public String interProcessReadWriteLock(@PathVariable("path") String path) {
        path = File.separator + path;
        logger.info("path:{}", path);
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, path);
        try {
            boolean isLock = lock.acquire(10, TimeUnit.SECONDS);
            logger.info("加锁是否成功：{}", isLock);
            if (isLock) {
                logger.info("业务处理开始...");
                Thread.sleep(2000);
                logger.info("业务处理结束...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
                logger.info("释放锁完成！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "over";
    }


    /**
     * 共享信号量
     *
     * @param path
     * @return
     */
    @RequestMapping("/lock4/{path}")
    @ResponseBody
    public String interProcessSemaphoreV2(@PathVariable("path") String path) {
        path = File.separator + path;
        logger.info("path:{}", path);
        // 创建一个信号量, Curator 以公平锁的方式进行实现
        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(curatorFramework, path, 6);
        // semaphore2 用于模拟其他客户端
        InterProcessSemaphoreV2 semaphore2 = new InterProcessSemaphoreV2(curatorFrameworkTwo, path, 6);
        // 获取一个许可
        Lease lease = null;
        Lease lease2 = null;
        Collection<Lease> leases = null;
        Collection<Lease> leases2 = null;
        try {
            lease = semaphore.acquire();
            Assert.assertNotNull(lease);
            // semaphore.getParticipantNodes() 会返回当前参与信号量的节点列表, 俩个客户端所获取的信息相同
            Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

            // 超时获取一个许可
            lease2 = semaphore2.acquire(2, TimeUnit.SECONDS);
            Assert.assertNotNull(lease2);
            Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

            // 获取多个许可, 参数为许可数量
            leases = semaphore.acquire(2);
            Assert.assertTrue(leases.size() == 2);
            Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

            // 超时获取多个许可, 第一个参数为许可数量
            leases2 = semaphore2.acquire(2, 2, TimeUnit.SECONDS);
            Assert.assertTrue(leases2.size() == 2);
            Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

            // 目前 semaphore 已经获取 3 个许可, semaphore2 也获取 3 个许可, 加起来为 6 个, 所以他们无法再进行许可获取
            Assert.assertNull(semaphore.acquire(2, TimeUnit.SECONDS));
            Assert.assertNull(semaphore2.acquire(2, TimeUnit.SECONDS));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放一个许可
            semaphore.returnLease(lease);
            semaphore2.returnLease(lease2);
            // 释放多个许可
            semaphore.returnAll(leases);
            semaphore2.returnAll(leases2);
            return "over";
        }

    }

    /**
     * 多重共享锁
     *
     * @param path
     * @return
     */
    @RequestMapping("/lock5/{path}")
    @ResponseBody
    public String multiLock(@PathVariable("path") String path) {
        path = File.separator + path;
        logger.info("path:{}", path);
        // 可重入锁
        InterProcessLock interProcessLock1 = new InterProcessMutex(curatorFramework, path);
        // 不可重入锁
        InterProcessLock interProcessLock2 = new InterProcessSemaphoreMutex(curatorFrameworkTwo, path);
        // 创建多重锁对象
        InterProcessLock lock = new InterProcessMultiLock(Arrays.asList(interProcessLock1, interProcessLock2));
        try {
            // 获取参数集合中的所有锁
            lock.acquire();

            // 因为存在一个不可重入锁, 所以整个 InterProcessMultiLock 不可重入
            Assert.assertFalse(lock.acquire(2, TimeUnit.SECONDS));
            // interProcessLock1 是可重入锁, 所以可以继续获取锁
            Assert.assertTrue(interProcessLock1.acquire(2, TimeUnit.SECONDS));
            // interProcessLock2 是不可重入锁, 所以获取锁失败
            Assert.assertFalse(interProcessLock2.acquire(2, TimeUnit.SECONDS));

            // 释放参数集合中的所有锁
            lock.release();

            // interProcessLock2 中的锁已经释放, 所以可以获取
            Assert.assertTrue(interProcessLock2.acquire(2, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "over";
    }

}
