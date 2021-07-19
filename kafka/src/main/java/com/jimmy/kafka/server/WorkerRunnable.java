package com.jimmy.kafka.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: java
 * @description: 工作线程
 * @author: Mr.jimmy
 * @create: 2018-09-07 16:55
 **/
public class WorkerRunnable implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(WorkerRunnable.class);
    private LinkedBlockingQueue<ConsumerRecord<String, String>> queue;
    protected KafkaServerHandler kafkaServerHandler;

    public WorkerRunnable(LinkedBlockingQueue<ConsumerRecord<String, String>> queue, KafkaServerHandler kafkaServerHandler) {
        this.queue = queue;
        this.kafkaServerHandler = kafkaServerHandler;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                queue.forEach(take-> kafkaServerHandler.proc(take));
            }catch (Throwable cause) {
                logger.error("工作线程发生未知异常", cause);
            }
        }
    }
}
