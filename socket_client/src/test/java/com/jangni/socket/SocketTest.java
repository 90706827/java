package com.jangni.socket;

import akka.actor.ActorRef;
import com.jangni.socket.core.JobContext;
import com.jangni.socket.core.TaskExecutors;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SocketTest
 * @Description 测试
 * @Author Mr.Jangni
 * @Date 2018/9/15 16:02
 * @Version 1.0
 **/
public class SocketTest {
    Logger logger  = LoggerFactory.getLogger(this.getClass());
    private String host = "127.0.0.1";
    private int port = 8888;
    private int timeout = 70000;

    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel = null;


    public static void main(String[] args) {
        SocketTest socketTest = new SocketTest();
        socketTest.connect();
        int start = 100000;
        while(start < 100001){
            for(int i =0;i<1;i++){
                start++;
                JobContext jobContext = new JobContext();
                jobContext.setThridLsid(String.valueOf(start));
                StringBuilder sb = new StringBuilder("<context>");
                for (Map.Entry<String, String> entry : jobContext.getContextValues().entrySet()) {
                    sb.append("<").append(entry.getKey()).append(">")
                            .append(entry.getValue())
                            .append("</").append(entry.getKey()).append(">");
                }
                sb.append("</context>");
                String reqMsg = sb.toString().replaceAll(">[\\s]+<", "><");

                socketTest.write(reqMsg.getBytes());
            }
        }



    }
    private boolean isConnect() {
        return channel.isActive() && channel.isRegistered() && channel.isOpen();
    }

    public void write(byte[] reqMsg) {
        if (!isConnect()) {
            connect();
        }
        channel.writeAndFlush(reqMsg);
    }

    public void connect(){
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler("client", LogLevel.DEBUG))
                                    .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 102400, 0, 2, 0, 2, true))
                                    .addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 2, 0, false))
                                    .addLast(new ByteArrayDecoder())
                                    .addLast(new ByteArrayEncoder())
                                    .addLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                                    .addLast(new SimpleChannelInboundHandler<byte[]>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
                                            CompletableFuture.runAsync(()->{
                                                JobContext jobContext = new JobContext();
                                                try {
                                                    Document document = DocumentHelper.parseText(new String(msg, "utf-8"));
                                                    //获取根节点
                                                    Element root = document.getRootElement();
                                                    List<Node> list = root.elements();
                                                    for (Node node : list) {
                                                        jobContext.toValues(node.getName().trim(), node.getText().trim());
                                                    }
                                                } catch (DocumentException | UnsupportedEncodingException e) {
                                                    jobContext.setRespCode("99");
                                                    jobContext.setRespDesc("报文格式错误");
                                                }
                                                logger.info(jobContext.getRespCode() + "=" + jobContext.getRespDesc());
                                            }, TaskExecutors.pool);
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            if (cause instanceof ReadTimeoutException) {
                                                logger.error("请求服务端在" + timeout + "毫秒内没有收到相应，关闭连接！");
                                            } else {
                                                logger.error("连接服务端发生异常，关闭连接！", cause);
                                            }
                                            ctx.close();
                                        }
                                    });
                        }
                    });
            channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
        } catch (Exception e) {
            logger.error("连接[$host:$port]发生异常", e);
        }
    }
}
