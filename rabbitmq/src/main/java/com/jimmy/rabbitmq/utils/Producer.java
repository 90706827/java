package com.jimmy.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.concurrent.TimeoutException;

/**
 * @Description 生产者
 * @Author jimmy
 * @Date 2018/11/4 16:12
 **/
public class Producer {
    private final static String QUEUE_NAME = "test";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        Connection connection = null;
        Channel channel = null;
        try {
            connection = ConnectionUtil.getConnection();
            /* 创建信道 */
            channel = connection.createChannel();

            // 声明一个队列：名称、持久性的（重启仍存在此队列）、非私有的、非自动删除的
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

//            String message = "hello world..."; // 需发送的信息
//            /* 发送消息，使用默认的direct交换器 */
//            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//           logger.info("Send message -> " + message);

            for(int i=0;i<100;i++){
                //消息内容
                String message = "Hello World!"+i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

                Thread.sleep(i*10);//随着发送的信息越多而间隔越长
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /* 关闭连接、通道 */
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
