package com.jangni.socket.netty;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @program: java
 * @description: Actor
 * @author: Mr.Jangni
 * @create: 2018-08-29 13:34
 **/
public class MatchActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    log.info("Received String message: {}", s);
                    //#my-actor
                    //#reply
                    getSender().tell(s, getSelf());
                    //#reply
                    //#my-actor
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    public static void main(String[] args){
        final ActorRef actor = ActorSystem.create("system").actorOf(Props.create(MatchActor.class), "MatchActor");

        actor.tell(1 ,ActorRef.noSender());
    }
}
