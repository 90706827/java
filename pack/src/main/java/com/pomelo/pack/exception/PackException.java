package com.pomelo.pack.exception;

/**
 * @Description 异常
 * @Author jimmy
 * @Date 2018/12/1 13:13
 **/
public class PackException extends Exception {

    public PackException() {
        super();
    }

    public PackException(String message) {
        super(message);
    }

    public PackException(Throwable cause) {
        super(cause);
    }

    public PackException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
