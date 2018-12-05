package com.pomelo.pack.common;

import com.pomelo.pack.exception.PackException;

import java.nio.ByteBuffer;

/**
 * @Author Mr.Jangni
 * @Description 标准的包装者接口
 * @Date 2018/12/3 19:05
 **/
public interface IPacker {

    public IMessage createEmpty();

    /**
     * 打包
     *
     * @param message
     * @return
     */
    public ByteBuffer pack(IMessage message) throws PackException;

    /**
     * 解包
     *
     * @param byteBuffer
     * @return
     */
    public IMessage unpack(ByteBuffer byteBuffer) throws PackException;
}
