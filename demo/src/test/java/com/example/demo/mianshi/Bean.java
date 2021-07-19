package com.example.demo.mianshi;

/**
 * ClassName Bean
 * Description 主类
 * Author  Mr.jimmy
 * Date 2018/12/24 9:13
 * Version 1.0
 **/
public class Bean {
    String name = "父类";
    public String age = "12";

    static {
        System.out.println("父类的静态加载");
    }

    Bean() {
        System.out.println("构造方法 " + name);
        init();
        print();
    }

    public void init() {
        System.out.println(name + " init方法 ");
    }

    public void print() {
        System.out.println(name + " print方法 " + age);
    }
}
