package com.example.demo.design.core;

import java.util.List;

/**
 * @ClassName HandlerServiceChain
 * @Description 业务处理类
 * @Author Mr.jimmy
 * @Date 2018/12/18 13:10
 * @Version 1.0
 **/
public class HandlerServiceChain implements IExec {
    private List<IExecService> serviceList;
    private IExecService lastExecService = null;

    HandlerServiceChain(List<IExecService> serviceList) {
        this.serviceList = serviceList;
    }

    HandlerServiceChain(List<IExecService> serviceList, IExecService lastExecService) {
        this.serviceList = serviceList;
        this.lastExecService = lastExecService;
    }

    @Override
    public final void exec(Context context) throws Exception{
        for (IExecService service : serviceList) {
            try {
                service.exec(context);
            } catch (Exception e) {
                service.err(context, e);
                break;
            }
        }
        try {
            if (lastExecService != null) {
                lastExecService.exec(context);
            }
        } catch (Exception e) {
            lastExecService.err(context, e);
        }
    }
}
