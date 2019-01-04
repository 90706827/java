package com.example.demo.executor.core;

import java.util.concurrent.Callable;

public class HandleExceptionCallable implements Callable<Integer> {

    private final ChinaService service;
    private final Context context;

    HandleExceptionCallable(ChinaService service,Context context) {
        this.service = service;
        this.context =context;
    }

    @Override
    public Integer call() throws Exception {
        return service.proc(context);
    }
}
