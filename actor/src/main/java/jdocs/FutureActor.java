package jdocs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.ExecutionContexts;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-08 15:23
 **/
public class FutureActor {
    public static void main(String[] args) throws Exception {
//        ExecutorService yourExecutorServiceGoesHere = Executors.newSingleThreadExecutor();
//        //#diy-execution-context
//        ExecutionContext ec = ExecutionContexts.fromExecutorService(yourExecutorServiceGoesHere);
//
//        //Use ec with your Futures
//        Future<String> f1 = Futures.successful("foo");
//
//        // Then you shut down the ExecutorService at the end of your application.
//        yourExecutorServiceGoesHere.shutdown();
        ActorSystem system = ActorSystem.create("system");
        ActorRef actor = system.actorOf(Props.create(MyActor.class));
        String msg = "hello";
        //#ask-blocking
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(actor, msg, timeout);
        Car result = (Car) Await.result(future, timeout.duration());
        System.out.println(result.getName());
        //#ask-blocking
        //#pipe-to
//        akka.pattern.Patterns.pipe(future, system.dispatcher()).to(actor);
        //#pipe-to
//        assertEquals("HELLO", result);
    }
}
