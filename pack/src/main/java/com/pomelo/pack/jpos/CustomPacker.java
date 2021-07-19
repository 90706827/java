package com.pomelo.pack.jpos;

import com.pomelo.pack.common.IMessage;
import com.pomelo.pack.exception.PackException;
import org.jpos.iso.ISOException;

import java.nio.ByteBuffer;

/**
 * @Author Mr.jimmy
 * @Description 一个可定制的packer
 * @Date 2018/12/3 21:06
 **/
public abstract class CustomPacker<T extends JposMessage> extends JposPacker {
    CustomTranIdParser tranIdParser = new CustomTranIdParser();

    /**
     * @param headLength 头长度
     * @param path       文件路径
     * @throws ISOException JPOS异常
     */
    public CustomPacker(int headLength, String path) throws ISOException {
        super(headLength, CustomPacker.class.getResourceAsStream(path), "");
    }

    /**
     * @param headLength 头长度
     * @param path       文件路径
     * @param realmName  解析8583时日志中的realm名称
     * @throws ISOException JPOS异常
     */
    public CustomPacker(int headLength, String path, String realmName) throws ISOException {
        super(headLength, CustomPacker.class.getResourceAsStream(path), realmName);
    }

    @Override
    public IMessage unpack(ByteBuffer byteBuffer) throws PackException {
        IMessage message = super.unpack(byteBuffer);
        ((JposMessage) message).setTranIdParser(tranIdParser);
        return message;
    }

    @Override
    public JposMessage createEmpty() {
        T msg = generateJposMessage();
        msg.setPackager(super.packer);
        return msg;
    }

    /**
     * 构建一个T类型的message。用于储存定制信息。
     *
     * @return JposMessage的实例
     */
    protected abstract T generateJposMessage();

    /**
     * 生成一个交易唯一标志的方法
     *
     * @param message JposMessage的实例
     * @return 交易唯一标识
     */
    protected abstract String generateTranId(JposMessage message);

    private class CustomTranIdParser implements ITranIdParser {

        @Override
        public String getTranId(JposMessage message) {
            return generateTranId(message);
        }
    }

}
