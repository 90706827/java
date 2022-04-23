package com.jimmy.async.context;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 14:36
 **/
@Getter
@Setter
public class ServiceContext {

    private Long tenantId;
    public ServiceContext(){
        ContextHelper.initTraceId("127.0.01",false);
    }

}
