package jdocs;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import java.time.Duration;
import akka.actor.AbstractActorWithTimers;
import java.time.Duration;

/**
 * @program: java
 * @description:
 * @author: Mr.jimmy
 * @create: 2018-09-08 15:32
 **/
public class MyActor extends AbstractActorWithTimers {

    public MyActor() {
        getTimers().startSingleTimer("helloa", "hellob", Duration.ofMillis(500));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    System.out.println("String"+msg);
                    Car car = new Car(msg);
                    getSender().tell(car, getSelf());
                })
                .match(Integer.class, i -> {
                    if (i < 0) {
                        getSender().tell(new Status.Failure(new ArithmeticException("Negative values not supported")), getSelf());
                    } else {
                        System.out.println("Integer"+i);
                        getSender().tell("HELLO", getSelf());
                    }
                })
                .build();
    }
}