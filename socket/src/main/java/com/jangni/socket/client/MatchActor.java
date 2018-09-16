package com.jangni.socket.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import com.jangni.socket.core.JobContext;
import com.jangni.socket.core.SpringUtil;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MatchActor
 * @Description actor
 * @Author Mr.Jangni
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
                            jobContext.setRespCode("99");
                            jobContext.setRespDesc("交易重复");
                        } catch (DocumentException e) {
                            jobContext.setRespCode("99");
                            jobContext.setRespDesc("报文格式错误");
                        }
                        getSender().tell(jobContext,getSender());
                    } else {
                        map.put(req.getKey(),getSelf());
                        //发送报文
                        nettyClient.write(req.getReqMsg().getBytes());
                    }
                })
                .match(JobContext.class, jobContext -> {
                    //服务端返回的报文处理
                    String key = nettyClient.getKey(jobContext);
                    if (map.containsKey(key)) {
                        getSender().tell(key, getSelf());
                        map.get(key).tell(jobContext, getSender());
                    } else {
                        logger.warn("服务端返回的交易不存在，交易唯一流水key:" + key);
                    }
                }).match(String.class, key -> {
                    map.remove(key);
                }).build();
    }
}
