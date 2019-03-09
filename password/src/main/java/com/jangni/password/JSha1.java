package com.jangni.password;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Jsha1
 * @Description SHA1 摘要算法
 * @Author Mr.Jangni
 * @Date 2019/3/8 21:23
 * @Version 1.0
 **/
public class JSha1 {

    public static void main(String[] args) {
        System.out.println(getSHA1("12345678"));
    }

    //对文件进行SHA1摘要
    private static String getSHA1(String str) {
        String sha1 = "";
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(str.getBytes());
            sha1 = Hex.encodeHexString(sha.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }

}