package com.jimmy.socket.client;

/**
 * @program: java
 * @description: 报文+key
 * @author: Mr.jimmy
 * @create: 2018-09-09 17:26
 **/
public final class SendKeyReqMsg {
    private String key;
    private String reqMsg;

    public SendKeyReqMsg(String key, String reqMsg) {
        this.key = key;
        this.reqMsg = reqMsg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }
}
