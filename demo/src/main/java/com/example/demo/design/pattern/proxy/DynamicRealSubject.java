package com.example.demo.design.pattern.proxy;

/**
 * @Description 动态代理-被代理类
 * @Author Jangni
 * @Date 2018/11/29 22:19
 **/
public class DynamicRealSubject implements IDynamicSubject {
    @Override
    public void show() {
        System.out.println("杀人是我指使的，我是幕后黑手！By---"+getClass());
    }
}
