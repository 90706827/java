package com.pomelo.pack.jpos;


import com.pomelo.pack.common.IMessage;
import com.pomelo.pack.common.IPacker;
import com.pomelo.pack.exception.PackException;
import org.jpos.iso.ISOException;
import org.jpos.iso.packager.GenericPackager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 通过JPOS实现的8583包的解析器
 *
 * @author jiangfengming
 */
public class JposPacker implements IPacker {

    /**
     * JPOS提供的标准的8583解析器
     */
    protected GenericPackager packer = null;

    /**
     * @param headLength 头长度
     * @param filePath   配置文件路径
     * @throws IOException  配置文件读取异常
     * @throws ISOException JPOS异常
     */
    public JposPacker(int headLength, String filePath) throws ISOException, IOException {
        this(headLength, Files.newInputStream(Paths.get(filePath), StandardOpenOption.READ), "");
    }

    /**
     * @param headLength  头长度
     * @param inputStream 配置文件输入流
     * @param realmName   解析8583日志中的realm的名称
     * @throws ISOException JPOS异常
     */
    public JposPacker(int headLength, InputStream inputStream, String realmName) throws ISOException {
        packer = new GenericPackager(inputStream);
        packer.setHeaderLength(headLength);
        org.jpos.util.Logger l = new org.jpos.util.Logger();
        l.addListener(new JposLogListener());
        packer.setLogger(l, realmName);
    }

    @Override
    public ByteBuffer pack(IMessage message) throws PackException {
        try {
            JposMessage msg = (JposMessage) message;
            return ByteBuffer.wrap(msg.pack());
        } catch (Exception e) {
            throw new PackException(e);
        }
    }

    @Override
    public IMessage unpack(ByteBuffer byteBuffer) throws PackException {
        try {
            byte[] bts = new byte[byteBuffer.remaining()];
            byteBuffer.get(bts);
            JposMessage message = createEmpty();
            message.unpack(bts);
            return message;
        } catch (Exception e) {
            throw new PackException(e);
        }
    }

    @Override
    public JposMessage createEmpty() {
        JposMessage message = new JposMessage();
        message.setPackager(packer);
        return message;
    }
}