package com.example.demo.java8;

/**
 * @ClassName Car
 * @Description car
 * @Author Mr.Jangni
 * @Date 2018/9/25 16:46
 * @Version 1.0
 **/
public class Car {
    private String color;
    private String mingpai;
    private Integer sum;

    public Car(String color, String mingpai,Integer sum) {
        this.color = color;
        this.mingpai = mingpai;
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMingpai() {
        return mingpai;
    }

    public void setMingpai(String mingpai) {
        this.mingpai = mingpai;
    }
}
