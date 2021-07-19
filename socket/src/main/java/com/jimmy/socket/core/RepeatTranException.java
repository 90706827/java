package com.jimmy.socket.core;

/**
 * @program: java
 * @description: 交易重复异常
 * @author: Mr.jimmy
 * @create: 2018-09-09 17:33
 **/
public class RepeatTranException extends Exception {

    public RepeatTranException(String message){
        super(message);
    }

}
