package com.jangni.socket.tps;

import akka.actor.AbstractActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.ExecutionContextExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TpsActor
 * @Description 统计TPS
 * @Author Mr.Jangni
 * @Date 2018/9/17 15:18
 * @Version 1.0
 **/
public class TpsActor extends AbstractActor {
    Logger logger = LoggerFactory.getLogger(this.getClass());
//    static ScheduledExecutorService ses = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static String TranSuccess = "00";
    private static int countTime = 60;
    private int count =0;
    private int success =0;

//    static {
//        ses.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                getSender().tell("count",getSelf());
//            }
//        }, 0, 1, TimeUnit.SECONDS);
//    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(TransInfo.class, tranInfo ->{
            logger.info("收到一笔交易");
            count++;
            if(TranSuccess.equals(tranInfo.getRespCode())){
                success++;
            }
        }).matchEquals("count",req ->{
            logger.error("交易TPS:["+count+"]笔每秒，成功率：["+success/(count==0?1:count)+"]%");
            count=0;
            success=0;
        }).build();
    }


}
