package com.example.demo.boot1.map;

import javax.print.DocFlavor;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName ConcurrentHashMap_Example
 * @Description 线程安全的Map  【推荐使用】
 * 在JDK8之前采用分段加锁的方式，分为16个桶，每次只对其中一个加锁
 * 在JDK8中又增加了红黑树算法和CAS算法来实现
 * concurrent [kənˈkʌrənt] 同时发生的
 * @Author Mr.Jangni
 * @Date 2019/2/27 9:51
 * @Version 1.0
 **/
public class ConcurrentHashMap_Example {

    public static void main(String[] args) {
       ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap<String,Integer>();
        concurrentHashMap.put("count",0);
      Thread thread1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<100;i++){
                    int a =concurrentHashMap.get("count");
                    concurrentHashMap.put("count",a+2);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<100;i++){
                    int a =concurrentHashMap.get("count");
                    concurrentHashMap.put("count",a-1);
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i<100;i++){
                    int a =concurrentHashMap.get("count");
                    concurrentHashMap.put("count",a-1);
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(concurrentHashMap.get("count"));
        concurrentHashMap.clear();
        System.out.println(concurrentHashMap.get("count"));
    }
}
