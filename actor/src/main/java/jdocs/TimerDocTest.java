package jdocs;

import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.time.Duration;

/**
 * @program: java
 * @description:
 * @author: Mr.jimmy
 * @create: 2018-09-09 10:24
 **/
public class TimerDocTest {
    static
    //#timers
    public class IActor extends AbstractActorWithTimers {

        private static Object TICK_KEY = "TickKey";
        private static final class FirstTick {
        }
        private static final class Tick {
        }

        public IActor() {
            getTimers().startSingleTimer(TICK_KEY, new FirstTick(), Duration.ofMillis(1));
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(FirstTick.class, message -> {
                       logger.info("a");
                        // do something useful here
                        getTimers().startPeriodicTimer(TICK_KEY, new Tick(), Duration.ofSeconds(1));
                    })
                    .match(Tick.class, message -> {
                        // do something useful here
                    })
                    .build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("system");
        ActorRef actor = system.actorOf(Props.create(IActor.class));
    }
}
