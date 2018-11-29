package com.example.demo.design.pattern.proxy;

/**
 * @Description cglib动态代理-被代理类 代理对象是目标对象的子类;拦截器必须实现MethodInterceptor接口
 * @Author Jangni
 * @Date 2018/11/29 23:13
 **/
public class CglibTargetObject {
    public void business() {
        System.out.println("business");
    }

}
