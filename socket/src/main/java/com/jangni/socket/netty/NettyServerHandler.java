package com.jangni.socket.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-06 23:25
 **/
public class NettyServerHandler extends SimpleChannelInboundHandler<byte[]> {
    private Logger logger = LoggerFactory.getLogger("Server");
    private boolean isKeepAlive = true;

    private IListener ilistener;
    private int timeoutSeconds;

    public NettyServerHandler(IListener ilistener, int timeoutSeconds) {
        this.ilistener = ilistener;
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        String reqXml = new String(msg,"utf-8");
        String respXml = ilistener.proc(reqXml);
        ctx.writeAndFlush(respXml.getBytes());
        if(!isKeepAlive){
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof ReadTimeoutException){
            logger.warn("客户端连接服务端在"+timeoutSeconds+"秒内未发送数据，连接将进行关闭！");
        }else if(cause instanceof TooLongFrameException){
            logger.warn("客户端发送给服务端的报文超长，请检查报文!",cause);
        }else{
            logger.warn("客户端请求服务端发送错误：",cause);
        }
        ctx.channel().close();
    }
}
