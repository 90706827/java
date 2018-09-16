package com.jangni.socket.core;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public interface IExecutor {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    void submit(Runnable task);
    void execute(Runnable task);


}
