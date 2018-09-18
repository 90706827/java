package com.example.demo.thread.FutureTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName ComplenTableFuture
 * @Description
 * @Author Mr.Jangni
 * @Date 2018/9/18 16:49
 * @Version 1.0
 **/
public class ComplenTableFuture {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1/0;
            return 100;
        });

        try {
            //future.join();
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
