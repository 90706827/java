package com.jimmy.thread.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author zhangguoq
 **/
public class SynchronizedObject {
    private static final Logger logger = LoggerFactory.getLogger(SynchronizedObject.class);

    public AtomicInteger tickets = new AtomicInteger(1);

    public synchronized void add(String user) {

        if (tickets.get() >= 100) {
            logger.info("售票结束。");
        } else {
            logger.info("{}购到第{}张票。", user, tickets);
            tickets.getAndAdd(1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}