package com.jimmy.shiro.core;

import java.io.Serializable;

/**
 * ClassName ResultBean
 * Description 返回bean
 * Author Mr.jimmy
 * Date 2019/1/13 23:06
 * Version 1.0
 **/
public class ResultBean<T> implements Serializable {
    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    private String msg = "SUCCESS";
    private int code = SUCCESS;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}