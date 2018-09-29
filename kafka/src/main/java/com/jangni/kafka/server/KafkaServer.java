package com.jangni.kafka.server;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: java
 * @description: 消费者
 * @author: Mr.Jangni
 * @create: 2018-09-07 14:00
 **/
public class KafkaServer {
    protected Logger logger = LoggerFactory.getLogger(KafkaServer.class);
    /**
     *  kafka的server地址
     */
    protected String bootstrapServers = "192.168.0.121:50005,192.168.0.122:50005,192.168.0.123:50005";
    /**
     *设置kafka组id
     */
    protected String groupId = "groupid";
    //设置value的反序列化类
    protected String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    //设置key的反序列化类
    protected String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    //请求等待响应时间,单位毫秒。默认值是305000
    protected String reqTimeoutMs = "60000";
    //如果缓冲区中没有数据，轮训的间隔时间，单位毫秒
    protected long pollMillions = 20000;
    //设置工作线程数
    protected int workThreadNum = 1;
    protected LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
    protected ExecutorService workerExecutor;
    protected ExecutorService monitorExecutor;
    protected KafkaConsumer<String, String> kafkaConsumer;
    //设置关注的topic列表
    protected List<String> topics = new ArrayList<String>();
    protected KafkaServerHandler kafkaServerHandler = new KafkaServerHandler();


    /**
     * 启动服务
     */
    @PostConstruct
    public void start() {
        workerExecutor = Executors.newFixedThreadPool(workThreadNum);
        monitorExecutor = Executors.newFixedThreadPool(1);
        Properties props = new Properties();
        //kafka集群地址
        props.setProperty("bootstrap.servers", bootstrapServers);
        // 消费者的groupId
        props.setProperty("group.id", groupId);
        //value的序列化
        props.setProperty("value.deserializer", valueDeserializer);
        //key的序列化
        props.setProperty("key.deserializer", keyDeserializer);
        //请求等待响应时间，单位毫秒。默认值是305000
        props.setProperty("request.timeout.ms", reqTimeoutMs);
        props.setProperty("enable.auto.commit", "true");
        //每次从单个分区中拉取的消息最大尺寸（byte），默认1MB
        props.setProperty("max.partition.fetch.bytes", "1048576");
        topics.add("test");
        kafkaConsumer = new KafkaConsumer<String, String>(props);
        kafkaConsumer.subscribe(topics);
        for (int i = 0; i < workThreadNum; i++) {
            workerExecutor.execute(new WorkerRunnable(queue, kafkaServerHandler));
        }

        monitorExecutor.execute(new MonitorRunnable(kafkaConsumer,queue,topics,pollMillions));

        logger.info("服务连接到Kafka节点" + props.get("bootstrap.servers") + "成功！");

    }


    /**
     * 停止服务
     */
    @PreDestroy
    private void stop() {
        workerExecutor.shutdown();
        monitorExecutor.shutdown();
        kafkaConsumer.close();
        kafkaConsumer = null;
        queue.clear();
        logger.info("Kafka服务停止操作完成.....");
    }


    public static void main(String[] args){
        KafkaServer kafkaServer = new KafkaServer();
        kafkaServer.start();
    }
}
