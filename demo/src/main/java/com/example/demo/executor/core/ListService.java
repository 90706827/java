package com.example.demo.executor.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ListService implements IService {
    private ChinaService service;
    private List<Future> listFuture = new ArrayList<>();
    private AsyncExecutor executor = new AsyncExecutor();

    public ListService(ChinaService list) {
        this.service = list;
    }

    @Override
    public int proc(Context context) throws Exception {
        Future future = executor.submit(service, context);
        try {
            future.get(60000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return 0;
    }
}
