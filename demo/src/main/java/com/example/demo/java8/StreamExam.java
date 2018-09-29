package com.example.demo.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName StreamExam
 * @Description Stream
 * @Author Mr.Jangni
 * @Date 2018/9/25 16:45
 * @Version 1.0
 **/
public class StreamExam {
    public static void main(String[] args) {
        List<Car> car = Arrays.asList(
                new Car("红色", "宝马", 1),
                new Car("黑色", "奥迪", 2),
                new Car("白色", "大众", 3),
                new Car("灰白", "雪弗莱", 4));
        List<Integer> cars = car.stream()
                .filter(a -> a.getSum() > 1)
                .map(Car::getMingpai)
                .limit(2)
                .skip(1)
                .map(String::length)
                .collect(toList());
        cars.stream().forEach(c->System.out.println(c));

        OptionalDouble a = car.stream().mapToInt(Car::getSum).average();
        System.out.println(a.getAsDouble());

        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(2,4);
        List<int[]> list = list1.stream()
                .flatMap(i ->
                        list2.stream()
                                .filter(j -> (i+j)%3==0)
                                .map(j-> new int[]{i,j}))
                .collect(toList());
        list.stream().forEach(i-> System.out.println(String.valueOf(i[0])+"|"+String.valueOf(i[1])));


        Stream.iterate(0,n -> n+2).limit(6).forEach(System.out::println);
        Stream.generate(Math::random).limit(2).forEach(System.out::println);

    }
}
