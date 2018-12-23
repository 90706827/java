package com.example.demo.design.core;

import java.util.List;

/**
 * @ClassName CHandler
 * @Description 链路处理
 * @Author Mr.Jangni
 * @Date 2018/12/20 10:58
 * @Version 1.0
 **/
public class CHandler extends HandlerServiceChain {
    CHandler(List<IExecService> serviceList) {
        super.serviceList = serviceList;
    }
}
