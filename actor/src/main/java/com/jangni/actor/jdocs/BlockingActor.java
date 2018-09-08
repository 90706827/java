package com.jangni.actor.jdocs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-08 10:41
 **/
public class BlockingActor extends AbstractActor {
    ExecutionContext ec = getContext().getSystem().dispatchers().lookup("my-blocking-dispatcher");


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class,i->{
                    Future<Integer> future = Futures.future(()->{
                        Thread.sleep(5000);
                        System.out.println("Receive:"+i);
                        return i;
                    },ec);
                }).build();
    }
    public static void main(String args[]){
//        ActorSystem system = ActorSystem.create("system");

        Config config = ConfigFactory.parseString(
                "my-blocking-dispatcher {\n" +
                        "  type = Dispatcher\n" +
                        "  executor = \"thread-pool-executor\"\n" +
                        "  thread-pool-executor {\n" +
                        "    fixed-pool-size = 16\n" +
                        "  }\n" +
                        "  throughput = 1\n" +
                        "}\n"
        );

        ActorSystem system = ActorSystem.create("BlockingDispatcherTest", config);
        ActorRef actor1 = system.actorOf(Props.create(BlockingActor.class));
        ActorRef actor2 = system.actorOf(Props.create(PrintActor.class));
        try {
            for(int i =0;i<100;i++){
                actor1.tell(i,ActorRef.noSender());
                actor2.tell(String.valueOf(i),ActorRef.noSender());
            }
//        system.terminate();
            Thread.sleep(5000*6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            system.terminate();
        }

    }
}
