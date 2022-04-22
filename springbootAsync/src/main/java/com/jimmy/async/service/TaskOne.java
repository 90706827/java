package com.jimmy.async.service;

import com.jimmy.async.core.HandlerAsync;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/20 23:27
 **/
public class TaskOne extends HandlerAsync<String,String> {

    @Override
    protected String handler(String o) {
        return o+"A";
    }

    public static void main(String[] args) {
        TaskOne task1 = new TaskOne();
        try {
            String str = task1.supplyAsync("a").get();
            System.out.println(str);
        }catch (Exception e){
          e.printStackTrace();
        }

    }
}
