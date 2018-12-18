package com.example.demo.design.core;

import org.springframework.core.task.TaskDecorator;

/**
 * @ClassName MaxWaitTimeoutTaskDecorator
 * @Description Runable 包装器 限制最大执行时间
 * @Author Mr.Jangni
 * @Date 2018/12/18 16:59
 * @Version 1.0
 **/
public class MaxWaitTimeoutTaskDecorator implements TaskDecorator {
    private long timeout = 60 *1000;

    MaxWaitTimeoutTaskDecorator(long timeout){
        this.timeout = timeout;
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        return runnable;
    }
}
