package com.jangni.socket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.ByteOrder;

/**
 * @program: java
 * @description: 服务端
 * @author: Mr.Jangni
 * @create: 2018-09-06 20:26
 **/
public class NettyServer {
    protected Logger logger = LoggerFactory.getLogger("server");
    private int port = 8989;
    private int timeoutSeconds = 60;
    private NioEventLoopGroup listenGroup = new NioEventLoopGroup(1);
    private NioEventLoopGroup ioGroup = new NioEventLoopGroup();
    private IListener iListener;

    public NettyServer(IListener iListener) {
        this.iListener = iListener;
    }

    /**
     * 启动服务
     */
    @PostConstruct
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(listenGroup, ioGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LoggingHandler("server", LogLevel.DEBUG))
                                .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 10240, 0, 2, 0, 2, true))
                                .addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN, 2, 0, false))
                                .addLast(new ByteArrayDecoder())
                                .addLast(new ByteArrayEncoder())
                                .addLast(new ReadTimeoutHandler(timeoutSeconds))
                                .addLast(new NettyServerHandler(iListener,timeoutSeconds));
                    }
                });

        //绑定端口 调用sync方法等待绑定操作完成
        try {
            bootstrap.bind(port).sync();
        } catch (Exception e) {
            logger.info("服务监听断口[" + port + "]失败！");
            stop();
        }
        logger.info("服务监听断口[" + port + "]成功！");
    }

    /**
     * 停止服务
     */
    @PreDestroy
    public void stop() {
        listenGroup.shutdownGracefully();
        ioGroup.shutdownGracefully();
        logger.info("NETTY服务已停止完成");
    }
}
