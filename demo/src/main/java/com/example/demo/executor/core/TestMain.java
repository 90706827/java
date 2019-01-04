package com.example.demo.executor.core;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        List<IService> list = new ArrayList<>();
        list.add(new Test1Service());
        list.add(new Test2Service());
        list.add(new Test3Service());
        list.add(new Test4Service());
        list.add(new Test5Service());
        Context context = new Context();
        context.setText(18);
        try {
            new ListService(new ChinaService(list)).proc(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
