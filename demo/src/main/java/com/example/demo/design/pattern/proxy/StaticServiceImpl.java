package com.example.demo.design.pattern.proxy;

import java.util.Date;

/**
 * @Description 静态代理-被代理类
 * @Author jimmy
 * @Date 2018/11/29 21:09
 **/
public class StaticServiceImpl implements IStaticService {

    @Override
    public String echo(String msg) {
        return "ECHO:" + msg;
    }

    @Override
    public Date getTime() {
        return new Date();
    }

}
