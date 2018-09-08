package com.jangni.socket.netty;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import static akka.pattern.PatternsCS.ask;

import com.jangni.socket.core.ActorSystemUtil;
import com.jangni.socket.core.JobContext;
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
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: java
 * @description: 客户端
 * @author: Mr.Jangni
 * @create: 2018-09-06 23:24
 **/
public class NettyClient{
    protected Logger logger = LoggerFactory.getLogger("client");
    private String host = "127.0.0.1";
    private Integer port = 8989;
    private Integer timeout = 60000;
    private ActorRef matchActor = new ActorSystemUtil().actorSystem().actorOf(Props.create(MatchsActor.class));
    private Integer connectTimeout = 5000;
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel = null;

    public void connect(){
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,connectTimeout)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LoggingHandler("client", LogLevel.DEBUG))
                                    .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN,102400,0,2,0,2,true))
                                    .addLast(new LengthFieldPrepender(ByteOrder.BIG_ENDIAN,2,0,false))
                                    .addLast(new ByteArrayDecoder())
                                    .addLast(new ByteArrayEncoder())
                                    .addLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                                    .addLast(new SimpleChannelInboundHandler<byte[]>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
                                            JobContext jobContext = new JobContext();
                                            try {
                                                Document document = DocumentHelper.parseText(new String(msg,"utf-8"));
                                                Element root = document.getRootElement();//获取根节点
                                                List<Node> list = root.elements();
                                                for (Node node : list) {
                                                    jobContext.toValues(node.getName().trim(), node.getText().trim());
                                                }
                                            } catch (DocumentException e) {
                                                jobContext.setRespCode("99");
                                                jobContext.setRespDesc("报文格式错误");
                                            }
                                            matchActor.tell(jobContext,ActorRef.noSender());
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                            if(cause instanceof ReadTimeoutException){
                                                logger.error("请求服务端在"+timeout+"毫秒内没有收到相应，关闭连接！");
                                            }else{
                                                logger.error("连接服务端发生异常，关闭连接！", cause);
                                            }
                                            ctx.close();
                                        }
                                    });
                        }
                    });
            channel = bootstrap.connect(new InetSocketAddress(host,port)).sync().channel();
        }catch (Exception e){
            logger.error("连接[$host:$port]发生异常", e);
        }

    }
    private boolean isConnect(){
       return channel.isActive() && channel.isRegistered() && channel.isOpen();
    }

    private void write(byte[] reqMsg){
        if(!isConnect()){
            connect();
        }
        channel.writeAndFlush(reqMsg);
    }

    private void closeChannel(){
        channel.closeFuture();
    }
    private String getKey(JobContext jobContext){
        StringBuffer sb = new StringBuffer();
        sb.append(jobContext.getThridLsid());
        return sb.toString();
    }
    public void post(JobContext jobContext){
        StringBuffer sb = new StringBuffer("<context>");
        Iterator<Map.Entry<String, String>> entries = jobContext.getContextValues().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sb.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue())
                    .append("</").append(entry.getKey()).append(">");
        }
        sb.append("</context>");
        String reqMsg = sb.toString().replaceAll(">[\\s]+<", "><");
//        Timeout t = Timeout.create(Duration.ofSeconds(1));
        //#ask-blocking
        Timeout timeout =  Timeout.create(Duration.ofSeconds(1));
        Future<Object> future = Patterns.ask(matchActor, reqMsg, timeout);
        try {
            JobContext result = (JobContext) Await.result(future, timeout.duration());
            result.getContextValues().clear();
            Iterator<Map.Entry<String, String>> iterator = jobContext.getContextValues().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                jobContext.getContextValues().put(entry.getKey(),entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    class MatchsActor extends AbstractActor{
        private Logger logger = LoggerFactory.getLogger(MatchsActor.class);
        private Map<String,ActorRef> map = new HashMap<String,ActorRef>();
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(JobContext.class,jobContext ->{
                        //服务端返回的报文处理
                        String key = getKey(jobContext);
                        if(map.containsKey(key)){
                            getSender().tell(key,getSelf());
                            map.get(key).tell(jobContext,ActorRef.noSender());
                        }else{
                            logger.warn("服务端返回的交易不存在，交易唯一流水key:"+key);
                        }
                    }).match(String.class,key->{
                        map.remove(key);
                    }).build();
        }
    }

}
