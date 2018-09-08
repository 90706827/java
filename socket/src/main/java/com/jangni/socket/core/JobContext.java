package com.jangni.socket.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: java
 * @description:
 * @author: Mr.Jangni
 * @create: 2018-09-06 21:03
 **/
public class JobContext {
    private final long startMillions = System.currentTimeMillis();
    private final Map<String, Object> contextObjects = new HashMap<String, Object>();
    private final Map<String, String> contextValues = new HashMap<String, String>();

    public long getStartMillions() {
        return startMillions;
    }

    public Map<String, Object> getContextObjects() {
        return contextObjects;
    }

    public Map<String, String> getContextValues() {
        return contextValues;
    }

    public String fromValues(String key) {
        if (contextValues.containsKey(key)) {
            return contextValues.get(key);
        } else {
            return "";
        }
    }

    public void toValues(String key, String value) {
        contextValues.put(key, value);
    }

    public Object fromObjects(String key) {
        if (contextValues.containsKey(key)) {
            return contextObjects.get(key);
        } else {
            return null;
        }
    }

    public void toObjects(String key, Object value) {
        contextObjects.put(key, value);
    }

    public String getThridLsid() {
        return fromValues("thridLsId");
    }

    public void setThridLsid(String thridLsId) {
        toValues("thridLsId", thridLsId);
    }

    public String getRespCode() {
        return fromValues("respCode");
    }

    public void setRespCode(String respCode) {
        if(getRespCode().isEmpty()){
            toValues("respCode", respCode);
        }
    }

    public String getRespDesc() {
        return fromValues("respDesc");
    }

    public void setRespDesc(String respDesc) {
        if(getRespDesc().isEmpty()){
            toValues("respDesc", respDesc);
        }
    }
}
