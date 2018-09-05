package com.jangni.actor.pojo;

/**
 * @program: java
 * @description: pojo
 * @author: Mr.Jangni
 * @create: 2018-08-29 19:21
 **/
public class Greeting {
    private final String from;

    public Greeting(String from) {
        this.from = from;
    }

    public String getGreeter() {
        return from;
    }
}
