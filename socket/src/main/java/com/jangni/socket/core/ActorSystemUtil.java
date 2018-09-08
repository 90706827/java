package com.jangni.socket.core;

import akka.actor.ActorSystem;

/**
 * @program: java
 * @description: Actor system
 * @author: Mr.Jangni
 * @create: 2018-09-08 00:07
 **/
public final class ActorSystemUtil {

    private final ActorSystem actorSystem = ActorSystem.create("system");
    static
    {
        new ActorSystemUtil();
    }
    public ActorSystem actorSystem()
    {
        return actorSystem;
    }
}
