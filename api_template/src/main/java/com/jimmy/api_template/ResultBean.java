package com.jimmy.api_template;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName ResultBean
 * Description
 * Author Mr.jimmy
 * Date 2019/3/2 10:41
 * Version 1.0
 **/
@Data
public class ResultBean<T> implements Serializable {


    private static final long serializableUID = 1L;
    private static final int FAIL = 1;

    private static final int NO_PERMISSION = 2;

    private static final int SUCCESS = 0;

    private int code = SUCCESS;
    private T data;
    private String msg = "success";

    public ResultBean(){
        super();
    }

    public ResultBean(T data){
        super();
        this.data = data;
    }

    public ResultBean(Throwable e){
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }


}