package com.pomelo.pack.field;

import org.jpos.iso.ISOException;
import org.jpos.iso.Interpreter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class EncodingLiteralInterpreter implements Interpreter {
    private static HashMap<String, EncodingLiteralInterpreter> interpreterMap;

    static {
        interpreterMap = new HashMap<>();
    }

    private String encoding = "GBK";

    public EncodingLiteralInterpreter(String encoding) {
        encoding = encoding;
    }

    public static EncodingLiteralInterpreter getInterpreter(String encoding) {
        if (interpreterMap.containsKey(encoding)) {
            return interpreterMap.get(encoding);
        } else {
            EncodingLiteralInterpreter interpreter = new EncodingLiteralInterpreter(encoding);
            interpreterMap.put(encoding, interpreter);
            return interpreter;
        }
    }

    public byte[] getBytes(String data) throws UnsupportedEncodingException {
        return data.getBytes(encoding);
    }

    @Override
    public void interpret(String data, byte[] b, int offset)
            throws ISOException {
        try {
            byte[] raw = data.getBytes(encoding);
            System.arraycopy(raw, 0, b, offset, raw.length);
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    @Override
    public String uninterpret(byte[] rawData, int offset, int length)
            throws ISOException {
        try {
            return new String(rawData, offset, length, encoding);
        } catch (UnsupportedEncodingException ignored) {
        }
        return null;
    }

    @Override
    public int getPackedLength(int nDataUnits) {
        return nDataUnits;
    }
}