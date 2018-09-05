package com.jangni.actor.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-08-29 22:54
 **/
public class TheActor extends AbstractActor {
    Logger logger = LoggerFactory.getLogger(TheActor.class);

    final String s;

    public TheActor(String s) {
        this.s = s;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    logger.info("info:"+msg);
                    getSender().tell(s, getSelf());
                })
                .build();
    }
    static ActorSystem system = null;

    public static void mian(String[] args){
        //this is just to make the test below a tiny fraction nicer
        private ActorSystem getContext() {
            return system;
        }
    }
}
