package com.jangni.socket;

import com.jangni.socket.core.IListener;
import com.jangni.socket.core.JobContext;
import org.dom4j.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-06 22:17
 **/
public class DateListener implements IListener {
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
        jobContext.setRespCode("00");
        jobContext.setRespDesc("交易成功");

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
