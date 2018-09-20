package com.example.demo.thread.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @ClassName ThenApplyAsync
 * @Description
 * @Author Mr.Jangni
 * @Date 2018/9/19 11:11
 * @Version 1.0
 **/
public class ThenApplyAsync {
    static Logger logger = LoggerFactory.getLogger("abcd");
    // Async结尾的方法都是可以异步执行的，
    // 如果指定了线程池，会在指定的线程池中执行，如果没有指定，默认会在ForkJoinPool.commonPool()中执行。
    // 关键的入参只有一个Function，它是函数式接口，所以使用Lambda表示起来会更加优雅。
    //  它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。

    public static CompletableFuture<Car> starts(Car car) {
        return CompletableFuture.supplyAsync(() -> {
            logger.info(car.model);
            car.model=("black");
            return car;
        }, TaskExecutors.pool);
    }

    public static void main(String[] args) {
//        Car car = new Car();
//        CompletableFuture.supplyAsync(() -> {
//            logger.info(car.getColor());
//            car.setColor("red");
//            return car;
//        }, TaskExecutors.pool).thenCompose(result ->
//                starts(car))
//                .thenCompose(result -> CompletableFuture.supplyAsync(() -> {
//                    logger.info(result.getColor());
//                    result.setColor("green");
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return result;
//                }, TaskExecutors.pool)).join();
//        logger.info(car.getColor());
    }

}
