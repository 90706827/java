package com.jangni.sofabolt;

import java.io.Serializable;

/**
 * ClassName MyRequest
 * Description 请求统一封装类
 * 注意：必须实现 Serializable 接口，因为默认的编码器：ProtocolCodeBasedEncoder extends MessageToByteEncoder<Serializable>，
 * 只对 Serializable 实现类进行编码
 * Author Mr.Jangni
 * Date 2019/2/21 22:21
 * Version 1.0
 **/
public class MyRequest implements Serializable {
    private static final long serialVersionUID = -7242884346498114969L;
    private String req;

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    @Override
    public String toString() {
        return req;
    }
}