package com.example.demo;

/**
 * @ClassName TaskWithoutResult
 * @Description
 * @Author Mr.jimmy
 * @Date 2018/9/16 13:11
 * @Version 1.0
 **/
public class TaskWithoutResult implements Runnable {
    private int sleepTime=60000;//默认睡眠时间1s
    public TaskWithoutResult(int sleepTime)
    {
        this.sleepTime=sleepTime;
    }
    @Override
    public void run()
    {
        System.out.println("线程"+Thread.currentThread()+"开始运行");
        try {
            int i=10000*10000;
            while(i>0)
            {
                i--;
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {//捕捉中断异常

            System.out.println("线程"+Thread.currentThread()+"被中断");
        }
        System.out.println("线程"+Thread.currentThread()+"结束运行");
    }
}
