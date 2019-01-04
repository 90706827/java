package com.example.demo.executor.core;


public class Test2Service implements IService {


    @Override
    public int proc(Context context) {
        System.out.println(context.getText() + " " + context.getText() + " 2业务逻辑处理开始");
        context.setText(context.getText()+3);
        System.out.println(context.getText() + " " + context.getText()  + " 2业务逻辑处理完成");
        return 0;
    }
}
