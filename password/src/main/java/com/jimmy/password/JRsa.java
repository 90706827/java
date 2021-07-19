package com.jimmy.password;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * @ClassName Jrsa
 * @Description
 * @Author Mr.jimmy
 * @Date 2019/3/9 18:07
 * @Version 1.0
 **/
public class JRsa {
    //指定加密算法为RSA
    private static String ALGORITHM = "RSA";
    //指定加密模式和填充方式
    private static String ALGORITHM_MODEL = "RSA/ECB/PKCS1Padding";
    //指定key的大小，一般为1024，越大安全性越高
    private static int KEY_SIZE = 1024;
    // 指定私钥存放文件
    private static Key PRIVATE_KEY = null;
    // 指定公钥存放文件
    private static Key PUBLIC_KEY = null;

    public static void main(String[] args) throws Exception {
        //生成密钥对
        generateKeyPair();
        // 要加密的字符串
        String source = "要加密的字符串";
        // 生成的密文
        byte[] cryptograph = encrypt(source);

        //可以将密文进行base64编码进行传输
        System.out.println(new String(Base64.encode(cryptograph)));
        // 解密密文
        String target = decrypt(cryptograph);
        System.out.println(target);
    }

    /**
     * 生成密钥对
     */
    private static void generateKeyPair() throws Exception {
        // RSA算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        // 利用上面的随机数据源初始化这个KeyPairGenerator对象
        kpg.initialize(KEY_SIZE, sr);
        // 生成密匙对
        KeyPair kp = kpg.generateKeyPair();
        // 得到公钥
        PUBLIC_KEY = kp.getPublic();
        // 得到私钥
        PRIVATE_KEY = kp.getPrivate();
    }

    /**
     * 加密方法 source： 源数据
     */
    private static byte[] encrypt(String source) throws Exception {

        // 得到Cipher对象来实现对源数据的RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL);
        //采用公钥
        cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
        // 执行加密操作
        return cipher.doFinal(source.getBytes());
    }

    /**
     * 解密算法 cryptograph:密文
     */
    private static String decrypt(byte[] cryptograph) throws Exception {
        // 得到Cipher对象对已用公钥加密的数据进行RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL);
        cipher.init(Cipher.DECRYPT_MODE, PRIVATE_KEY);
        // 执行解密操作
        byte[] b = cipher.doFinal(cryptograph);
        return new String(b);
    }
}