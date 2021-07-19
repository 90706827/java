package com.jimmy.kafka.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: java
 * @description: kafka监听线程
 * @author: Mr.jimmy
 * @create: 2018-09-07 16:54
 **/
public class MonitorRunnable implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(MonitorRunnable.class);
    protected KafkaConsumer<String, String> consumer;
    protected LinkedBlockingQueue<ConsumerRecord<String, String>> queue;
    protected List<String> topics;
    //如果缓冲区中没有数据，则在轮询中等待，单位是毫秒
    protected Long poolMillions= 20000L;
    protected Boolean pauseToRead = false;

    public MonitorRunnable(KafkaConsumer<String, String> consumer, LinkedBlockingQueue<ConsumerRecord<String, String>> queue, List<String> topics, Long poolMillions) {
        this.consumer = consumer;
        this.queue = queue;
        this.topics = topics;
        this.poolMillions = poolMillions;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //读取数据
                ConsumerRecords<String, String> records = consumer.poll(poolMillions);
                Set<TopicPartition> assignmentTopicPartitions = consumer.assignment();
                //获取当前topic的读取的partition
                Optional<TopicPartition> topicPartitionOptional = consumer.assignment().stream().findFirst();
//                TopicPartition topicPartition = topicPartitionOptional.get();
//                String assignmentTopicPartitionsMsg = "topic[" + topicPartition.topic() + "]partition[" + topicPartition.partition() + "]position[" + consumer.position(topicPartition) + "]";
                logger.debug("拉取到的记录条数为 [" + records.count() + "]");
                if (records.count() > 0) {
                    // 添加到队列
                   records.forEach(iterator -> {
                       try {
                           queue.put(iterator);
                       } catch (InterruptedException e) {
                           logger.info("发生异常 InterruptedException", e);
                       }
                   });
//                    while (iterator.hasNext()) {
//                        queue.put(iterator.next());
//
//                    }
                }
                int curTotalSize = queue.size();
                if (curTotalSize > 1000) {
                    // 读取到了数据，缓冲数据超过1000，暂停读取新数据
                    logger.info("curTotalSize["+curTotalSize+"] > 1000...pause read...");
                    // 暂停读取
                    consumer.pause(assignmentTopicPartitions);
                    pauseToRead = true;
                } else if (pauseToRead) {
                    // 恢复读取数据,数据小于等于1000，并且曾经暂停过
                    consumer.resume(assignmentTopicPartitions);
                    logger.info("curTotalSize["+curTotalSize+"] <= 1000. resume read...");
                    pauseToRead = false;
                } else {
                    logger.debug("当前队列的深度为[" + curTotalSize + "]. 继续拉取...");
                }
            } catch (Throwable t) {
                logger.warn("Boss线程发生未知异常", t);
            }
        }
        logger.info("线程退出");
    }
}

