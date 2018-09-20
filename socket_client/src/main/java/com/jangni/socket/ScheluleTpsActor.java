package com.jangni.socket;

import akka.actor.ActorRef;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ScheluleTpsActor
 * @Description 定时器
 * @Author Mr.Jangni
 * @Date 2018/9/18 10:38
 * @Version 1.0
 **/
@Component
public class ScheluleTpsActor {

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());

    @Autowired
    ActorRef tpsActor;

//    @PostConstruct
    public void init(){
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tpsActor.tell("count",ActorRef.noSender());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
