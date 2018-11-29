package com.example.demo.design.pattern.proxy;

/**
 * @Description TODO
 * @Author Jangni
 * @Date 2018/11/29 23:17
 **/
public class CglibDynamicProxyMain {

    public static void main(String[] args) {
        CglibMyInterceptor myInterceptor = new CglibMyInterceptor(new CglibTargetObject());
        CglibTargetObject proxyObj = (CglibTargetObject) myInterceptor.createProxy();
        proxyObj.business();
    }
}
