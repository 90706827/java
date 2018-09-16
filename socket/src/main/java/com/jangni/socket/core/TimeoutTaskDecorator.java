package com.jangni.socket.core;

import org.springframework.core.task.TaskDecorator;

/**
 * @ClassName TimeoutTaskDecorator
 * @Description 超时处理线程
 * @Author Mr.Jangni
 * @Date 2018/9/16 16:55
 * @Version 1.0
 **/
public class TimeoutTaskDecorator implements TaskDecorator {

    private int timeout = 60000;

    /**
     * @param timeout 超时时间 单位毫秒
     */
    public TimeoutTaskDecorator(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public Runnable decorate(Runnable runnable) {
        return null;
    }
}
