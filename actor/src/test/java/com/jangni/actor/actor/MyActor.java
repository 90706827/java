package com.jangni.actor.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.jangni.actor.pojo.Greeting;

/**
 * @program: java
 * @description: actor
 * @author: Mr.Jangni
 * @create: 2018-08-29 18:53
 **/
public class MyActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, g -> {
                    log.info("I was greeted by {}", g.getGreeter());
                })
                .match(String.class, s -> {
                    log.info("Received String message: {}", s);
                    getSender().tell(s, getSelf());
                })
                .match(Integer.class,i ->{
                    log.info("Received String message: {}", i);
                    getSender().tell(i, getSelf());
                })
                .matchAny(x -> getSender().tell(x, getSelf()))
                .build();
    }

    public static void main(String[] args){
        ActorRef actor = ActorSystem.create("actorsystem").actorOf(Props.create(MyActor.class),"myactor");
        actor.tell("abcd",ActorRef.noSender());
        actor.tell(1,ActorRef.noSender());
        actor.tell(true,ActorRef.noSender());
        actor.tell(new Greeting("Greeting"),ActorRef.noSender());
    }
}
