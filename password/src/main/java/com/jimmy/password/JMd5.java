package com.jimmy.password;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Jmd5
 * @Description MD5 摘要算法
 * @Author Mr.jimmy
 * @Date 2019/3/8 21:10
 * @Version 1.0
 **/
public class JMd5 {


    public static void main(String[] args) {

        System.out.println(getMD5("12345678"));
        System.out.println(DigestUtils.md5DigestAsHex("12345678".getBytes()));

    }

    //对文件进行MD5摘要
    private static String getMD5(String str) {

        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            md5 = Hex.encodeHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

}