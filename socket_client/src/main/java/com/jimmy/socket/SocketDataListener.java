package com.jimmy.socket;

import akka.actor.ActorRef;
import com.jimmy.socket.client.NettyClient;
import com.jimmy.socket.core.IListener;
import com.jimmy.socket.core.JobContext;
import com.jimmy.socket.tps.TransInfo;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SocketDataListener
 * @Description
 * @Author Mr.jimmy
 * @Date 2018/9/15 15:55
 * @Version 1.0
 **/

public class SocketDataListener implements IListener {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    NettyClient nettyClient;
    ActorRef tpsActor;

    public SocketDataListener(NettyClient nettyClient, ActorRef tpsActor) {
        this.nettyClient = nettyClient;
        this.tpsActor = tpsActor;
    }

    @Override
    public String proc(String req) {
        JobContext jobContext = new JobContext();
        try {
            Document document = DocumentHelper.parseText(req);
            //获取根节点
            Element root = document.getRootElement();
            List<Node> list = root.elements();
            for (Node node : list) {
                jobContext.toValues(node.getName().trim(), node.getText().trim());
            }
        } catch (DocumentException e) {
            jobContext.setRespCode("99");
            jobContext.setRespDesc("报文格式错误");
        }
        logger.info("业务逻辑处理");
        //业务逻辑处理开始
        nettyClient.post(jobContext);
        logger.info("业务逻辑处理完成");

        tpsActor.tell(new TransInfo("",jobContext.getRespCode()),ActorRef.noSender());

        //业务逻辑处理完成
        StringBuffer sb = new StringBuffer("<context>");
        Iterator<Map.Entry<String, String>> entries = jobContext.getContextValues().entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sb.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue())
                    .append("</").append(entry.getKey()).append(">");
        }
        sb.append("</context>");
        return sb.toString();
    }
}
