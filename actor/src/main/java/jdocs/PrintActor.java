package jdocs;

import akka.actor.AbstractActor;

/**
 * @program: java
 * @description: 打印actor
 * @author: Mr.jimmy
 * @create: 2018-09-08 10:47
 **/
public class PrintActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,str->{
//                   logger.info("PrintActor: " + str);
                })
                .build();
    }
}
