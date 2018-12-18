package com.example.demo.design.core;

/**
 * @ClassName BService
 * @Description
 * @Author Mr.Jangni
 * @Date 2018/12/18 9:56
 * @Version 1.0
 **/
public class BService implements IExecService {

    @Override
    public void err(Context context, Exception e) {
        logger.error("error", e);
        context.setCode("0002");
        context.setCode("打包错误");
    }

    @Override
    public void exec(Context context) {
        context.setAbc("B");
        System.out.println(context.getAbc());
    }
}
