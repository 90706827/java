package com.example.demo.mianshi;

/**
 * ClassName Zilei
 * Description
 * Author  Mr.Jangni
 * Date 2018/12/24 9:17
 * Version 1.0
 **/
public class Zilei extends Bean {
    String name ="子类";
    String age ="13";
    static {
        System.out.println("子类静态加载");
    }
    Zilei(){
        System.out.println("构造方法 "+ name);
        init();
        print();
    }

    public void init() {
        System.out.println(name + " init方法 ");
    }
    public void print(){
        System.out.println( name + " print方法 " + age);
    }

    public static void main(String[] args) {
        Zilei zi = new Zilei();

    }
}
