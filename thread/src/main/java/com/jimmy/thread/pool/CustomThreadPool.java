package com.jimmy.thread.pool;

import com.jimmy.thread.common.ThreadPoolMonitor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author zhangguoq
 **/
public class CustomThreadPool extends ThreadPoolMonitor {


    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return null;
    }


    @Override
    public void test() {

    }
}