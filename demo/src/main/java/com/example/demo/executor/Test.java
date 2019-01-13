package com.example.demo.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ClassName Test
 * Description
 * Author Mr.Jangni
 * Date 2019/1/13 20:19
 * Version 1.0
 **/
public class Test {
    public static void main(String[] args) {
        List<Future> list = new ArrayList<Future>();
        ExecutorService fixPool = Executors.newFixedThreadPool(10);
        try{
            for (int i = 0; i < 10; i++) {
                Future<Integer> result =  fixPool.submit(new ExecCallThread("a"+i));
                list.add(result);
                throw new RuntimeException("a");
            }
        }finally {
            System.out.println("shutdown");
            fixPool.shutdown();
        }

        System.out.println("list");
        int count = 0;
        for (Future<Integer> f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try {
                int a = f.get();
                count +=a;
                System.out.println(">>>" + f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count);
    }
}