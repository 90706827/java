package com.jimmy.socket.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.jimmy.socket.core.JobContext;
import com.jimmy.socket.core.SpringUtil;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MatchActor
 * @Description actor
 * @Author Mr.jimmy
 * @Date 2018/9/14 15:34
 * @Version 1.0
 **/

public class MatchActor extends AbstractActor {
    private Logger logger = LoggerFactory.getLogger(MatchActor.class);
    private Map<String, ActorRef> map = new HashMap<String, ActorRef>();

    NettyClient nettyClient = SpringUtil.getBean(NettyClient.class);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SendKeyReqMsg.class, req->{
                    if (map.containsKey(req.getKey())) {
                        JobContext jobContext = new JobContext();
                        try {
                            Document document = DocumentHelper.parseText(req.getReqMsg());
                            //获取根节点
                            Element root = document.getRootElement();
                            List<Node> list = root.elements();
                            for (Node node : list) {
                                jobContext.toValues(node.getName().trim(), node.getText().trim());
                            }
                            logger.info("交易重复");
                            jobContext.setRespCode("99");
                            jobContext.setRespDesc("交易重复");
                        } catch (DocumentException e) {
                            jobContext.setRespCode("99");
                            jobContext.setRespDesc("报文格式错误");
                        }
                        getSender().tell(jobContext,getSender());
                    } else {
                        logger.info("存储唯一key");
                        map.put(req.getKey(),getSender());
                        logger.info("发送报文");
                        nettyClient.write(req.getReqMsg().getBytes());
                    }
                }).match(JobContext.class, jobContext -> {
                    //服务端返回的报文处理
                    String key = nettyClient.getKey(jobContext);
                    if (map.containsKey(key)) {
                        logger.info("服务端返回发送actor响应");
                        map.get(key).tell(jobContext, getSender());
                        logger.info("移除唯一key");
                        map.remove(key);
                    } else {
                        logger.info("服务端返回的交易不存在，交易唯一流水key:" + key);
                    }
                }).build();
    }

    public static void main(String[] args) {

         Map<String, String> map = new HashMap<String, String>();
         map.put("123","1234");
    }
}
