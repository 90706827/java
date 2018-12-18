package com.example.demo.design.core;

/**
 * @ClassName Context
 * @Description 上下文基类，定义框架所用参数，根据不同业务模块，子类可继承定义属于自己的上下问参数
 * @Author Mr.Jangni
 * @Date 2018/12/18 13:04
 * @Version 1.0
 **/
public class Context {
    private String abc;
    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
