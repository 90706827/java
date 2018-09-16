package com.jangni.socket.core;

/**
 * @ClassName JobContextUtil
 * @Description
 * @Author Mr.Jangni
 * @Date 2018/9/12 10:08
 * @Version 1.0
 **/
public class JobContextUtil {

    public static String getKey(JobContext jobContext){
        StringBuffer sb = new StringBuffer();
        sb.append(jobContext.getThridLsid());
        return sb.toString();
    }
}
