package com.jimmy.sofabolt;

import com.alipay.remoting.exception.RemotingException;
import com.alipay.remoting.rpc.RpcClient;

/**
 * ClassName MyClient
 * Description
 * Author Mr.jimmy
 * Date 2019/2/21 22:25
 * Version 1.0
 **/
public class MyClient {
    private static RpcClient client;

    public static void start() {
        // 创建 RpcClient 实例
        client = new RpcClient();
        // 初始化 netty 客户端：此时还没有真正的与 netty 服务端进行连接
        client.init();
    }

    public static void main(String[] args) throws RemotingException, InterruptedException {
        MyClient.start();
        // 构造请求体
        MyRequest request = new MyRequest();
        request.setReq("hello, bolt-server");
        /**
         * 1、获取或者创建连接（与netty服务端进行连接），Bolt连接的创建是延迟到第一次调用进行的
         * 2、向服务端发起同步调用（四种调用方式中最常用的一种）
         */
        MyResponse response = (MyResponse) client.invokeSync("127.0.0.1:8888", request, 30 * 1000);
    }
}