package com.example.demo.thread.FutureTask;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName CallableTest
 * @Description Callable
 * @Author Mr.Jangni
 * @Date 2018/9/18 15:55
 * @Version 1.0
 **/
public class CallableTest {

    public static void main(String[] args)  {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> result = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        });
        executor.shutdown();

        try {
            System.out.println("result:" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
