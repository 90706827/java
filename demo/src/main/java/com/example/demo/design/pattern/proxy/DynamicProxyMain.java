package com.example.demo.design.pattern.proxy;

import java.lang.reflect.Proxy;

/**
 * @Description jdk动态代理
 * @Author Jangni
 * @Date 2018/11/29 22:24
 **/
public class DynamicProxyMain {
    public static void main(String[] args) {
        DynamicRealSubject subject = new DynamicRealSubject();
        IDynamicSubject dynamicSubject = (IDynamicSubject) Proxy.newProxyInstance(IDynamicSubject.class.getClassLoader(),
                new Class[]{IDynamicSubject.class},new DynamicProxyHandler(subject));
        dynamicSubject.show();

    }
}

