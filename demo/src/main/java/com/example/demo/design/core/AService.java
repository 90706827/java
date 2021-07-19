package com.example.demo.design.core;

/**
 * @ClassName AService
 * @Description 普通业务逻辑
 * @Author Mr.jimmy
 * @Date 2018/12/18 9:54
 * @Version 1.0
 **/
public class AService implements IExecService {

    @Override
    public void err(Context context, Exception e) {
        logger.error("error", e);
        context.setCode("0001");
        context.setDesc("签名错误");
        int i = 1/0;
    }

    @Override
    public void exec(Context context) {
        context.setAbc("A");
        System.out.println(context.getAbc());
        int a = 1 / 0;
    }
}
