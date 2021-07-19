package com.jimmy.socket.tps;

/**
 * @ClassName TransInfo
 * @Description 交易信息
 * @Author Mr.jimmy
 * @Date 2018/9/17 15:22
 * @Version 1.0
 **/
public class TransInfo {

    private String tranType;
    private String respCode;

    public TransInfo(String tranType, String respCode) {
        this.tranType = tranType;
        this.respCode = respCode;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}
