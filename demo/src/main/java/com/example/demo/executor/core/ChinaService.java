package com.example.demo.executor.core;

import java.util.ArrayList;
import java.util.List;

public class ChinaService implements IService {

    private List<IService> list = new ArrayList<>();

    ChinaService(List<IService> list){
        this.list = list;
    }

    @Override
    public int proc(Context context) throws Exception {
        for (IService service : list) {
            service.proc(context);
        }
        return 0;
    }
}
