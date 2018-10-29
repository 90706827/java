package com.example.demo.pfx;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class ReadP12Cert {

    private KeyStore keyStore = null;
    private String keystoreAlias = null;
    private String keystoreFile = null;
    private String keystorePassrowd = null;

    public ReadP12Cert() {
    }

    public ReadP12Cert(String keystoreFile, String keystorePassrowd) {
        this.keystoreFile = keystoreFile;
        this.keystorePassrowd = keystorePassrowd;
        keyStore();
    }

    public ReadP12Cert(String keystoreFile, String keystorePassrowd, String keystoreAlias) throws Exception {
        this.keystoreFile = keystoreFile;
        this.keystorePassrowd = keystorePassrowd;
        this.keystoreAlias = keystoreAlias;
        keyStore();
    }
    private void keyStore() {
        try {
            InputStream in = new FileInputStream(new File(keystoreFile));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(in, keystorePassrowd.toCharArray());
            keyStore.store(out, keystorePassrowd.toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            if (keystoreAlias == null) {
                while (true) {
                    try {
                        keystoreAlias = aliases.nextElement();
                    } catch (NoSuchElementException e) {
                        break;
                    }
                }
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ReadP12Cert readP12Cert = new ReadP12Cert("E://sposzmkuat.pfx", "111111");
        String publicStr =readP12Cert.getPublicKey();
        String privateStr = readP12Cert.getPrivateKey();

//        String privateStr = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDPX1pMUkjYyT77vtOmjNGoU88GsU8qFeD8UpbZ0FBvtvl3UnR7HRQuwcGypBgdZMRu+cS+d/s+zgxDowAdNwToU384DhvrsfnE/Mr+cSOB+BZGOxCi3Uhoklcnads5OqxdFLN9toahOm2rZFsUrgJ+8Ir2bc4FP3pVbwS5lbVnCxaujQjBXSggXw3eC0Irgo+UZiQqM8Wse2ynJJz7d4SBqGoBfEmx92LboIOKo8ql2yowZLpsjtRchmBE5nQ+zzMoQcup10+DZoB1XS3SFil3LOu2wXVOPEY3cotmHCAVDUqeIW43XrLOhwAdZffWkg/6GkTZ227zrUBrzu6QM/DbAgMBAAECggEAMIHbe3Lb+2nHw9d1ZsYV3MwN50DehbescxlWlAi4aAli/VhaStoYeH92MbVUtrt1f4gJT4x9Rsmn8MJWKn9ONJIjdhdGzLFK/ZssWKxaY/KGM1Npps8gOuXQYTXPw+lHnFZVThi+EUS7lpXZAFNb3hmiR9aTlIKuuQFJF6g/UKTJfMO/WbzWPcW8r7o0IL0QwZA9A7BdBRDo2XXYo1yKAD7KPCKCWSngI4vjj/6sA50w7xrLaWNduJ5p16uJilxW234m9/KKgnbXr6mfYTQkWbcGR5H9DyrPyk/jTAQ9LqeYe2GHNFHYKAtRH9ThbY/EF+M4h/xawfrlc0XIEOufYQKBgQDwMSPzDbyiMNn1DvR57v0kZsPV/LwNqkTLSN0/VhBI6gfWkOYbNh/wZzpxXLA6Ec7wWFpN5S5RqoW+JUGkpjoFoyq/xbRro10Qx9+pSYMcwGp8FOkXjt0zoJ+1swO1b/aPMQlKvxm6MNc1aW2oPKle5mmQc+aXD9ki0EGuA70+mQKBgQDdBUFWq6RDoRm2fhUl51W75F/VJHXeuiP2QuFdbPClJu1idkkOZKjEfb/2IGvMqFV+Rcr9zzzbEXQNEH2/bs2Z3h0eBmDFIbWR5x1HbwhRyNas+bZbH4crFb5Vpq3J0Nx/3DYNy/LembAKG5J9t50d1BPTGtQIlFiEX0e6NTtXkwKBgH3lU/F+3sOolWW0bAJJeRgOYVGVQkBrYdmnnIgpJSoCuQ8HLgVPTlhk0yY+LgSR9wTVWqf/m1Kk7asnvgt+MWVpC+wuxY2xuAMmsJ378SQt2uKk1zRI5rq701qatTPxtquBSVyLZAHKvdK6KwcGnMQoQ2a6yT+ex/JOdE6wmQ3pAoGAMCbi80T1xp4kgfO6G8XokcTRdBg7bcjT6OTtQHNpgjGW6iqnA/G+KwDSf4bUtYO/DIGcFeu+cGO/CtGa88fqFymi4lW1Y31Sl3TolhWElHzS+GB28CdQRXy5OOPVXPCZ+Wk6hYr3YLPO/ITBR45nNoZrw0RHCY94MW6oq9LJGO0CgYAp8lsYKOH2/zH1kzlBoKpCLtCYjbcycPWueqKMyAXwxpOrPi3NzHPSo8oXvDIovEG6UKLl6d4CREqIjSpZYvRE+k203o6+yR74PPM8BkkmRVhHMgbtquoMUsz75FCG3Ol4UAABzax4LiOdhZ3zwcvnp7RILSIPEOiSQJnJiqr5CA==";
//        String publicStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz19aTFJI2Mk++77TpozRqFPPBrFPKhXg/FKW2dBQb7b5d1J0ex0ULsHBsqQYHWTEbvnEvnf7Ps4MQ6MAHTcE6FN/OA4b67H5xPzK/nEjgfgWRjsQot1IaJJXJ2nbOTqsXRSzfbaGoTptq2RbFK4CfvCK9m3OBT96VW8EuZW1ZwsWro0IwV0oIF8N3gtCK4KPlGYkKjPFrHtspySc+3eEgahqAXxJsfdi26CDiqPKpdsqMGS6bI7UXIZgROZ0Ps8zKEHLqddPg2aAdV0t0hYpdyzrtsF1TjxGN3KLZhwgFQ1KniFuN16yzocAHWX31pIP+hpE2dtu861Aa87ukDPw2wIDAQAB";

        String signStr = signString(privateStr, "123123");
        System.out.println(signVerify(publicStr, "123123", signStr));

    }

    /**
     * 字符私钥 签名
     *
     * @param privateKey 字符私钥
     * @param data       加密数据
     * @return 签名数据
     */
    private static String signString(String privateKey, String data) {
        String signValue = "";
        try {

            Signature sign = Signature.getInstance("SHA1WithRSA");
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            sign.initSign(priKey);//设置私钥
            sign.update(data.getBytes());//设置明文
            byte[] signRstByte = sign.sign();//加密
            signValue = Base64.encode(signRstByte);
            System.out.println("密文：[" + signValue + "]");
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e1) {
            e1.printStackTrace();
        }
        return signValue;
    }

    /**
     * 字符公钥 验签
     *
     * @param publicKey 字符公钥
     * @param data      加密数据
     * @param signMsg   签名数据
     * @return 验签结果
     */
    private static boolean signVerify(String publicKey, String data, String signMsg) {
        boolean signBool = false;
        try {
            Signature sign = Signature.getInstance("SHA1WithRSA");
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey)));
            sign.initVerify(pubKey);
            sign.update(data.getBytes());
            byte[] signBytes = Base64.decode(signMsg);
            signBool = sign.verify(signBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e1) {
            e1.printStackTrace();
        }
        return signBool;
    }

    /**
     * 私钥对象 签名
     *
     * @param privateKey 私钥对象
     * @param data       加密数据
     * @return 签名数据
     */
    private static String signString(PrivateKey privateKey, String data) {
        String signValue = "";
        try {
            Signature sign = Signature.getInstance("SHA1WithRSA");
            sign.initSign(privateKey);//设置私钥
            sign.update(data.getBytes());//设置明文
            byte[] signRstByte = sign.sign();//加密
            signValue = Base64.encode(signRstByte);
            System.out.println("密文：[" + signValue + "]");
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e1) {
            e1.printStackTrace();
        }
        return signValue;
    }

    /**
     * 公钥对象 验签
     *
     * @param publicKey 公钥对象
     * @param data      加密数据
     * @param signMsg   签名数据
     * @return 验签结果
     */
    private static boolean signVerify(PublicKey publicKey, String data, String signMsg) {
        boolean signBool = false;
        try {
            Signature sign = Signature.getInstance("SHA1WithRSA");
            sign.initVerify(publicKey);
            sign.update(data.getBytes());
            byte[] signBytes = Base64.decode(signMsg);
            signBool = sign.verify(signBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e1) {
            e1.printStackTrace();
        }
        return signBool;
    }

    public String getPrivateKey() {
        String priKey = "";
        try {

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keystoreAlias, keystorePassrowd.toCharArray());
            priKey = Base64.encode(privateKey.getEncoded());
            System.out.println("私钥：[" + priKey + "]");
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e1) {
            e1.printStackTrace();
        }
        return priKey;
    }

    public String getPublicKey() {
        String pubKey = "";
        try {
            Certificate cert = keyStore.getCertificate(keystoreAlias);
            PublicKey pubkey = cert.getPublicKey(); // 公钥
            pubKey = Base64.encode(pubkey.getEncoded());
            System.out.println("公钥：[" + pubKey + "]");
        } catch (KeyStoreException e1) {
            e1.printStackTrace();
        }
        return pubKey;
    }


}
