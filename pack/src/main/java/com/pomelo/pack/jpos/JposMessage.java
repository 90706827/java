package com.pomelo.pack.jpos;


import com.pomelo.pack.common.IMessage;
import com.pomelo.pack.exception.PackException;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import java.nio.ByteBuffer;

public class JposMessage extends ISOMsg implements IMessage {

    private ITranIdParser tranIdParser;

    public JposMessage() {
        super();
    }

    @Override
    public ByteBuffer getField(int fieldId) {
        return ByteBuffer.wrap(this.getBytes(fieldId));
    }

    @Override
    public int getFieldMaxLen(int fieldId) {
        ISOBasePackager packer = (ISOBasePackager) this.getPackager();
        return packer.getFieldPackager(fieldId).getLength();
    }

    @Override
    public String getFieldString(int fieldId) {
        return this.getString(fieldId);
    }

    @Override
    public String getTranId() {
        return tranIdParser == null ? null : tranIdParser.getTranId(this);
    }

    @Override
    public void setField(int fieldId, ByteBuffer fieldValue) throws PackException {
        byte[] bts = new byte[fieldValue.remaining()];
        fieldValue.get(bts);
        try {
            this.set(fieldId, bts);
        } catch (ISOException e) {
            throw new PackException(e);
        }
    }

    @Override
    public void setFieldString(int fieldId, String fieldValue) throws PackException {
        try {
            this.set(fieldId, fieldValue);
        } catch (ISOException e) {
            throw new PackException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(System.getProperty("line.separator"));
        for (int i = 0; i < 129; i++) {
            if (i == 1) {
                // 位图
                continue;
            }
            String value = this.getFieldString(i);
            if (value == null) {
                continue;
            }
            sb.append(String.format("Field[%03d]value[%s]%n", i, value));
        }
        return sb.toString();
    }

    void setTranIdParser(ITranIdParser tranIdParser) {
        this.tranIdParser = tranIdParser;
    }

}