package com.pomelo.pack.common;

import com.pomelo.pack.exception.PackException;

import java.nio.ByteBuffer;

/**
 * @Author Mr.jimmy
 * @Description 标准8583包的接口
 * @Date 2018/12/3 19:04
 **/
public interface IMessage {
    /**
     * 获取域值
     *
     * @param fieldId
     * @return
     */
    public ByteBuffer getField(int fieldId);

    /**
     * 获取域最大长度
     *
     * @param fieldId 域ID
     * @return 长度
     */
    public int getFieldMaxLen(int fieldId);

    /**
     * @param fieldId
     * @return
     */
    public String getFieldString(int fieldId);

    /**
     * 获取交易信息的唯一标识
     *
     * @return 交易标识
     */
    public String getTranId();

    /**
     * 判断域是否存在
     *
     * @param fieldId
     * @return
     */
    public boolean hasField(int fieldId);

    /**
     * 设置域值
     *
     * @param fieldId
     * @param fieldValue
     */
    public void setField(int fieldId, ByteBuffer fieldValue) throws PackException;

    /**
     * @param fieldId
     * @param fieldString
     */
    public void setFieldString(int fieldId, String fieldString) throws PackException;
}
