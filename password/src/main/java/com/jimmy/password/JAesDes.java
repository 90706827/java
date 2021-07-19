package com.jimmy.password;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @ClassName Jaes
 * @Description AES 对称加密算法
 * @Author Mr.jimmy
 * @Date 2019/3/8 22:04
 * @Version 1.0
 **/
public class JAesDes {

    public static void main(String[] args) throws Exception {

        String encrypt = aesEncrypt("AES加密内容", "1234567890abcdef");
        System.out.println(encrypt);
        System.out.println(aesDecrypt(Hex.decodeHex(encrypt), "1234567890abcdef"));


        String dncrypt = desEncrypt("DES加密内容", "12345678");
        System.out.println(dncrypt);
        System.out.println(desDecrypt(Hex.decodeHex(dncrypt), "12345678"));
    }

    /**
     * 使用AES对字符串加密
     *
     * @param str utf8编码的字符串
     * @param key 密钥（16字节）
     * @return 加密结果
     * @throws Exception
     */
    private static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return Hex.encodeHexString(bytes);
    }

    /**
     * 使用AES对数据解密
     *
     * @param bytes utf8编码的二进制数据
     * @param key   密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    private static String aesDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }

    /**
     * 使用DES对字符串加密
     *
     * @param str utf8编码的字符串
     * @param key 密钥（8字节）
     * @return 加密结果
     * @throws Exception
     */
    public static String desEncrypt(String str, String key) throws Exception {
        if (str == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        return Hex.encodeHexString(bytes);
    }

    /**
     * 使用DES对数据解密
     *
     * @param bytes utf8编码的二进制数据
     * @param key   密钥（16字节）
     * @return 解密结果
     * @throws Exception
     */
    public static String desDecrypt(byte[] bytes, String key) throws Exception {
        if (bytes == null || key == null) return null;
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "DES"));
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}