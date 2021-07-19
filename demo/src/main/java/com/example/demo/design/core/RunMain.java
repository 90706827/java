package com.example.demo.design.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RunMain
 * @Description Test
 * @Author Mr.jimmy
 * @Date 2018/12/18 9:54
 * @Version 1.0
 **/
public class RunMain implements Logging {
    public static void main(String[] args) {
        AService a = new AService();
        BService b = new BService();
        List<IExecService> list = new ArrayList<IExecService>();
        list.add(a);
        list.add(b);
        try {
            new HandlerServiceChain(list).exec(new MsgContext());
        } catch (Exception e) {
            logger.error("最终捕获异常",e);
        }
    }
}
