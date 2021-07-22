package com.jimmy.thread.pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.jimmy.thread.task.CallableTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;

/**
 * @Description
 * @Author zhangguoq
 **/
public class FutureThreadPool {
    protected ThreadPoolTaskExecutor pool = getThreadPoolTaskExecutor();
    protected static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(4);
        pool.setMaxPoolSize(8);
        pool.setKeepAliveSeconds(10);
        pool.setAllowCoreThreadTimeOut(true);
        pool.setThreadNamePrefix("task-");
        pool.afterPropertiesSet();
        return pool;
    }
    public void task(){
        pool.submit(new CallableTask());
    }

    public static void main(String[] args) {

    }
}