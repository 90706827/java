package com.example.demo.executor.core;


public class Test3Service implements IService {


    @Override
    public int proc(Context context) {
        System.out.println(context.getText() + " " + context.getText() + " 3业务逻辑处理开始");
        context.setText(context.getText()/3);
        System.out.println(context.getText() + " " + context.getText()  + " 3业务逻辑处理完成");
        return 0;
    }
}
