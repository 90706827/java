package com.jangni.socket.netty;

import com.jangni.socket.core.JobContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName TimeHandle
 * @Description 定时发送交易测试
 * @Author Mr.Jangni
 * @Date 2018/9/14 15:41
 * @Version 1.0
 **/
@Component
public class TimeHandle {

    @Autowired
    NettyClient nettyClient;

    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    public void sendClientMsg() {
        System.out.println("----------------------发送交易-----------------");
//        JobContext jobContext = new JobContext();
//        jobContext.setThridLsid("123456789");
//        nettyClient.post(jobContext);
//        System.out.println("---------------"+jobContext.getRespCode()+"-"+jobContext.getRespDesc());
    }
}
