package com.example.demo.design.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description cglib动态代理-拦截器
 * @Author Jangni
 * @Date 2018/11/29 23:14
 **/
public class CglibMyInterceptor implements MethodInterceptor {
    private Object target;//目标类

    public CglibMyInterceptor(Object target) {
        this.target = target;
    }

    /**
     * 返回代理对象
     * 具体实现，暂时先不追究。
     */
    public Object createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);//回调函数  拦截器
        //设置代理对象的父类,可以看到代理对象是目标对象的子类。所以这个接口类就可以省略了。
        enhancer.setSuperclass(this.target.getClass());
        return enhancer.create();
    }

    /**
     * args 目标方法的参数
     * method 目标方法
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (true) {//不要在意这为什么是恒成立的条件语句，为的是说明一个aop的概念：切入点。
            System.out.println("aaaaa");//切面方法a();
            //。。。
            method.invoke(this.target, objects);//调用目标类的目标方法
            //。。。
            System.out.println("bbbbb");//切面方法f();
        }
        return null;
    }
}
