package com.jangni.rabbitmq.utils;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description 消费者
 * @Author Jangni
 * @Date 2018/11/4 16:31
 **/
public class Customer {
    private final static String QUEUE_NAME = "doc";

    public static void main(String[] argv) throws java.io.IOException {

        /* 创建连接工厂 */
        Connection connection = ConnectionUtil.getConnection();
        /* 创建信道 */
        Channel channel = connection.createChannel();

        // 声明一个队列：名称、持久性的（重启仍存在此队列）、非私有的、非自动删除的
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        System.out.println("Waiting for messages.");

        /* 定义消费者 */
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received the message -> " + message);
            }
        };
        // 将消费者绑定到队列，并设置自动确认消息（即无需显示确认，如何设置请慎重考虑）
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
