package com.example.demo.design.core;

/**
 * @ClassName IExecService
 * @Description 异常处理类
 * @Author Mr.jimmy
 * @Date 2018/12/18 13:02
 * @Version 1.0
 **/
public interface IExecService extends IExec, Logging {

    public void err(Context context,Exception e);
}
