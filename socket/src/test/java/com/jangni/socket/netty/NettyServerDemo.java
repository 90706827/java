package com.jangni.socket.netty;


/**
 * @program: java
 * @description: 服务端
 * @author: Mr.Jangni
 * @create: 2018-09-06 20:37
 **/
public class NettyServerDemo {

    public static void main(String[] args) {
        new NettyServer(new DateListener()).start();
    }
}
