package com.pomelo.pack.jpos;

import org.jpos.iso.ISOException;

/**
 * 总对总包解析
 */
public class TransPacker extends CustomPacker<JposMessage> {

    public TransPacker() throws ISOException {
        super(7, "/8583hhap.xml", "trans");
    }

    @Override
    protected JposMessage generateJposMessage() {
        JposMessage msg = new JposMessage();
        msg.setHeader(new byte[]{0x60, 0x12, 0x34, 0x56, 0x78, 0x10, 0x00});
        return msg;
    }

    @Override
    protected String generateTranId(JposMessage message) {
        return message.getFieldString(11);
    }

}
