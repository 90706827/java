package com.jimmy.async.context;

import com.alibaba.dubbo.rpc.RpcContext;
import com.taobao.eagleeye.EagleEye;
import org.slf4j.MDC;

/**
 * @Description
 * @Author zhangguoq
 * @Date 2022/4/23 14:46
 **/
public class ContextHelper {
    private final static String RPC_TRACE_ID = "trace_id";

    public static void initTraceId(String initiator, boolean generateNew) {
        String traceId = null;
        if (generateNew) {
            traceId = EagleEye.generateTraceId(initiator);
        } else {
            traceId = EagleEye.getTraceId();
        }
        setTraceId(traceId);
    }

    public static void setTraceId(String requestId) {
        setRpcCtxValue(RPC_TRACE_ID, requestId);
    }

    public static String getTraceId() {
        return getRpcCtxValue(RPC_TRACE_ID);
    }

    static void setRpcCtxValue(String key, String value) {
        EagleEye.putUserData(key, value);
        // 非pandora容器鹰眼无效，做加固处理，在鹰眼设置的同时在dubbo的RpcContext也设置
        RpcContext.getContext().setAttachment(key, value);
    }

    static String getRpcCtxValue(String key) {
        String value = EagleEye.getUserData(key);
        // 非pandora容器鹰眼无效，做容错处理
        if (null == value) {
            value = RpcContext.getContext().getAttachment(key);
        }
        return value;
    }
}
