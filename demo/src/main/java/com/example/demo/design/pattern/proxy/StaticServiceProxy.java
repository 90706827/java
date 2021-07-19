package com.example.demo.design.pattern.proxy;

import java.util.Date;

/**
 * @Description 静态代理-代理类 固定代理一个接口，实现此接口的类都被代理，代理什么类已经固定，代理类同被代理类一样实现此接口
 * @Author jimmy
 * @Date 2018/11/29 21:11
 **/
public class StaticServiceProxy implements IStaticService {

    private IStaticService iStaticService;

    /**
     * @Author Mr.jimmy
     * @Description 构造方法参数接口类
     * @Date 2018/11/29 21:32
     * @Param [iStaticService]
     * @return 无
     **/
    public StaticServiceProxy(IStaticService iStaticService) {
        this.iStaticService = iStaticService;
    }

    public void setiStaticService(IStaticService iStaticService) {
        this.iStaticService = iStaticService;
    }

    @Override
    public String echo(String msg) {
        return iStaticService.echo(msg);
    }

    @Override
    public Date getTime() {
        return iStaticService.getTime();
    }
}
