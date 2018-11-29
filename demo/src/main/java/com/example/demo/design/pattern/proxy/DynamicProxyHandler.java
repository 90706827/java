package com.example.demo.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description jdk动态代理-连接器类 实现此接口InvocationHandler为拦截器
 * 代理对象和目标对象实现了共同的接口; 拦截器必须实现InvocationHanlder接口
 * @Author Jangni
 * @Date 2018/11/29 22:20
 **/
public class DynamicProxyHandler implements InvocationHandler {

    private Object proxyId;

    public DynamicProxyHandler(Object proxyId) {
        this.proxyId = proxyId;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxyId, args);
    }
}
