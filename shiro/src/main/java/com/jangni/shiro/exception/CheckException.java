package com.jangni.shiro.exception;

/**
 * ClassName CheckException
 * Description
 * Author Mr.Jangni
 * Date 2019/1/13 23:24
 * Version 1.0
 **/
public class CheckException extends RuntimeException {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -27048199131316992L;

    /**
     * Default constructor
     */
    public CheckException() {
        super();
    }

    /**
     * Constructor with message & cause
     *
     * @param message
     * @param cause
     */
    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message
     *
     * @param message
     */
    public CheckException(String message) {
        super(message);
    }

    /**
     * Constructor with message format
     *
     * @param msgFormat
     * @param args
     */
    public CheckException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     *
     * @param cause
     */
    public CheckException(Throwable cause) {
        super(cause);
    }
}