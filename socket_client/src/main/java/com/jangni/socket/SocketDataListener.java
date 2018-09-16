package com.jangni.socket;

import com.jangni.socket.client.NettyClient;
import com.jangni.socket.core.IListener;
import com.jangni.socket.core.JobContext;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SocketDataListener
 * @Description
 * @Author Mr.Jangni
 * @Date 2018/9/15 15:55
 * @Version 1.0
 **/

public class SocketDataListener implements IListener {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    NettyClient nettyClient;

    public SocketDataListener(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
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
