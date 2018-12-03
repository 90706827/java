package com.pomelo.pack;

import com.pomelo.pack.exception.PackException;
import com.pomelo.pack.jpos.JposMessage;
import com.pomelo.pack.jpos.TransPacker;
import org.jpos.iso.ISOException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * @ClassName UnpackTest
 * @Description 测试
 * @Author Mr.Jangni
 * @Date 2018/12/2 22:59
 * @Version 1.0
 **/
public class UnpackTest {
    public static void main(String[] args) {
        try {
            TransPacker transPacker = new TransPacker();

            try {
                byte[] bytes = pack(transPacker);
                TransPacker unPacker = new TransPacker();
                JposMessage respData = (JposMessage)transPacker.unpack(ByteBuffer.wrap(bytes));
                System.out.println(respData.getFieldString(0));
                System.out.println(respData.getFieldString(1));
            } catch (PackException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        } catch (ISOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] pack(TransPacker transPacker) throws PackException, UnsupportedEncodingException {
        JposMessage reqJposMessage = transPacker.createEmpty();
        reqJposMessage.setFieldString(0, "0200");
        reqJposMessage.setFieldString(2, "5257461148769709"); //持卡人卡号
        reqJposMessage.setFieldString(3, "310000"); //用于描述交易类型的一系列数字。
        reqJposMessage.setFieldString(4, "000000000004");
        reqJposMessage.setFieldString(8, "20180410.01"); //终端版本号
        reqJposMessage.setFieldString(11, "573229"); //终端交易流水号
        reqJposMessage.setFieldString(12,"235959"); //
        reqJposMessage.setFieldString(13, "20181212"); //
        reqJposMessage.setFieldString(14, "2707"); //
        reqJposMessage.setFieldString(22, "012"); //
        reqJposMessage.setFieldString(24, "025"); //
        reqJposMessage.setFieldString(25, "08"); //
            reqJposMessage.setFieldString(31, "KJZF"); //接入机构号入机构类型 由中行分配

        reqJposMessage.setFieldString(32, "0702"); //接入机构号
        reqJposMessage.setFieldString(41, new String("33030028".getBytes(),"GBK")); //此数据域用于收单机构及商户系统的终端编号
//        reqJposMessage.setFieldString(42, "104330348160028"); //唯一地标识出卡片接受方（商户或者网点）的编号
//        reqJposMessage.setField(48, ByteBuffer.wrap("a.fff]".getBytes()));
        reqJposMessage.setFieldString(49, "156");
//         reqJposMessage.setFieldString(58, "9910111112222297013961115811980025"); //99-订单号
        reqJposMessage.setFieldString(64, "3030303030303030");
        return transPacker.pack(reqJposMessage).array(); //
    }
}