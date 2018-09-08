package com.jangni.actor.jdocs;

import akka.actor.AbstractActor;
import akka.actor.Status;

/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-08 15:32
 **/
public class MyActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    getSender().tell(msg.toUpperCase(), getSelf());
                })
                .match(Integer.class, i -> {
                    if (i < 0) {
                        getSender().tell(new Status.Failure(new ArithmeticException("Negative values not supported")), getSelf());
                    } else {
                        getSender().tell(i, getSelf());
                    }
                })
                .build();
    }
}