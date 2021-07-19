package com.example.demo.desginpatten.adapter;

/**
 * @ClassName Adapter
 * @Description 适配器
 * @Author Mr.jimmy
 * @Date 2018/11/8 16:00
 * @Version 1.0
 **/
public class Adapter extends Adaptee implements Target {
    @Override
    public void close() {
        System.out.println("Adapter close");
    }
}
