package com.jimmy.sofabolt;

import java.io.Serializable;

/**
 * ClassName MyResponse
 * Description 响应统一封装类
 * 注意：必须实现 Serializable 接口，因为默认的编码器：ProtocolCodeBasedEncoder extends MessageToByteEncoder<Serializable>，
 * 只对 Serializable 实现类进行编码
 * Author Mr.jimmy
 * Date 2019/2/21 22:22
 * Version 1.0
 **/
public class MyResponse implements Serializable {
    private static final long serialVersionUID = -6215194863976521002L;
    private String resp = "default resp from server";

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    @Override
    public String toString() {
        return resp;
    }
}