package com.example.demo.executor;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.concurrent.Callable;

/**
 * ClassName ExecCallThread
 * Description
 * Author Mr.jimmy
 * Date 2019/1/13 20:22
 * Version 1.0
 **/
public class ExecCallThread implements Callable<Integer> {

    private String orgId;

    ExecCallThread(String orgId){
        this.orgId = orgId;
    }

    @Override
    public Integer call() throws Exception {
        int a = (int)(Math.random()*10000);
        System.out.println("执行开始:"+orgId+"|"+a);
        if(a<1000){
            throw new RuntimeException("小于1000");
        }
        Thread.sleep(a);
        System.out.println("执行结束:"+orgId);
        return 1;
    }
}