package com.pomelo.pack.jpos;

import com.pomelo.pack.common.IMessage;
import com.pomelo.pack.exception.PackException;
import org.jpos.iso.ISOException;

import java.nio.ByteBuffer;

public class PospPacker extends JposPacker {

    PospTranIdParser tranIdParser = new PospTranIdParser();
    private byte[] header;

    public PospPacker(byte[] header) throws ISOException {
        super(header.length, PospPacker.class.getResourceAsStream("/8583posp.xml"), "posp");
        this.header = header;
    }

    @Override
    public IMessage unpack(ByteBuffer byteBuffer) throws PackException {
        IMessage message = super.unpack(byteBuffer);
        ((JposMessage) message).setTranIdParser(tranIdParser);
        return message;
    }

    @Override
    public JposMessage createEmpty() {
        JposMessage jposMessage = super.createEmpty();
        jposMessage.setTranIdParser(tranIdParser);
        jposMessage.setHeader(header);
        return jposMessage;
    }

    private class PospTranIdParser implements ITranIdParser {

        @Override
        public String getTranId(JposMessage message) {
            // posp + [terminal id] + [批次号] + [trance no] + [mti]
            // 4+8+6+6+4=28
            StringBuilder sb = new StringBuilder();
            String termId = message.getFieldString(41);
            String field61 = message.getFieldString(61);
            String batchNo = field61.substring(0, 6);
            String traceNo = message.getFieldString(11);
            String mti = message.getFieldString(0);
            return sb.append("posp").append(termId).append(batchNo).append(traceNo).append(mti).toString();
        }

    }
}
