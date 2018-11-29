package com.example.demo.design.pattern.proxy;

/**
 * @Description 静态代理-使用
 * @Author Jangni
 * @Date 2018/11/29 21:14
 **/
public class StaticProxyMain {
    public static void main(String[] args) {
        //实例化被代理类
        IStaticService iStaticService = new StaticServiceImpl();
        //实例化代理类 构造方法传入被代理类
        StaticServiceProxy staticServiceProxy = new StaticServiceProxy(iStaticService);
        //代理实现
        System.out.println(staticServiceProxy.echo("美女") + staticServiceProxy.getTime());
    }
}
