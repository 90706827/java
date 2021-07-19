package jdocs;

/**
 * @program: java
 * @description: car
 * @author: Mr.jimmy
 * @create: 2018-09-09 10:07
 **/
public class Car {
    public Car(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
