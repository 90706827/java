package com.jangni.actor.actor;

import akka.actor.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @program: java
 * @description: test
 * @author: Mr.Jangni
 * @create: 2018-08-29 21:42
 **/
public class DependencyInjectionDocTest{


    static ActorSystem system = null;

    @BeforeClass
    public static void beforeClass() {
        system = ActorSystem.create("DependencyInjectionDocTest");
    }

    @AfterClass
    public static void afterClass() {
//        TestKit.shutdownActorSystem(system);
    }

    //this is just to make the test below a tiny fraction nicer
    private ActorSystem getContext() {
        return system;
    }

    static
            //#creating-indirectly
    class DependencyInjector implements IndirectActorProducer {
        final Object applicationContext;
        final String beanName;

        public DependencyInjector(Object applicationContext, String beanName) {
            this.applicationContext = applicationContext;
            this.beanName = beanName;
        }

        @Override
        public Class<? extends Actor> actorClass() {
            return TheActor.class;
        }

        @Override
        public TheActor produce() {
            TheActor result;
            //#obtain-fresh-Actor-instance-from-DI-framework
            result = new TheActor((String) applicationContext);
            //#obtain-fresh-Actor-instance-from-DI-framework
            return result;
        }
    }
    //#creating-indirectly

    @Test
    public void indirectActorOf() {
        final String applicationContext = "...";
        //#creating-indirectly

        final ActorRef myActor = getContext().actorOf(
                Props.create(DependencyInjector.class, applicationContext, "TheActor"),
                "TheActor");
        //#creating-indirectly
//        new TestKit(system) {
//            {
//                myActor.tell("hello", getRef());
//                expectMsgEquals("...");
//            }
//        };
    }
}
