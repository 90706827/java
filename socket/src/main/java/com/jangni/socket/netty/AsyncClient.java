package com.jangni.socket.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * @program: java
 * @description: 客户端
 * @author: Mr.Jangni
 * @create: 2018-08-28 17:31
 **/
public class AsyncClient {
    Logger logger = LoggerFactory.getLogger(AsyncClient.class);
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel = null;
    private int connecTimeout = 60000; //连接超时时间
    private int readTimeout = 60000; //读取超时
    private String host = "127.0.0.1";
    private int port = 9989;

    private void connect() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connecTimeout)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        channel.pipeline()
                                .addLast(new LoggingHandler("client", LogLevel.ERROR))
                                .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 10240, 0, 2, 0, 2, true))
                                .addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 2, 0, true))
                                .addLast(new ByteArrayDecoder())
                                .addLast(new ByteArrayEncoder())
                                .addLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                        try {
                                            cause.getCause();
                                        } catch (ReadTimeoutException e) {
                                            logger.error("读取" + host + ":" + port + "超时",e);
                                        }catch (Exception e){
                                            logger.error("other error ---",e);
                                        }
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

                                    }
                                });
                    }
                });
    }

}
