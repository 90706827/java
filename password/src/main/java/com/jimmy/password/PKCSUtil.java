package com.jimmy.password;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Enumeration;

/**
 * @author WUHY
 * @date 2018.10.30
 * @since JDK1.8
 * @version 1.0 
 * @see PKCSUtil
 * Note:It's tools for analysis PKCS#12(.pfx/.p12) certificate files and sign or verify message,
 * another one you can gets public and private certificate data,
 * you can puts certificate 'String' pattern .
 * Sign algorithm only support 'SHA1WithRSA' .
 */
public class PKCSUtil {
	
	private KeyStore keyStore = null;
	private Certificate cert = null ;
	private String keystoreAlias = "";
	private final static String algorithm = "SHA1WithRSA"; 
	
	volatile static PKCSUtil instance = null ;
	public static PKCSUtil getInstance(String filePath,String pwd) {
		if (instance == null) {
            synchronized (PKCSUtil.class) {
                if (instance == null) {
                    instance = new PKCSUtil(filePath,pwd);
                }
            }
	    }
		return instance; 
	}
	
	/**
	 * initial key store 
	 * @param path key file path
	 * @param pwd
	 * only support single certificate
	 */
	public PKCSUtil(String path,String pwd) {
		try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(new File(path)), pwd.toCharArray());
            keyStore.store(new ByteArrayOutputStream(), pwd.toCharArray());
            Enumeration<String> aliases = keyStore.aliases();
            while(aliases.hasMoreElements()) {
            	keystoreAlias = aliases.nextElement();
            	break;
            }
            if(!"".equals(keystoreAlias)) {
            	cert = keyStore.getCertificate(keystoreAlias);
            }
        } catch (Exception e) {
           keyStore = null ;
        }
	}
	
	/**get public key data
	 * @return public key byte array data
	 * @throws Exception
	 */
	public byte[] getPublicKeyByteArray()throws Exception {
        try {
            return cert.getPublicKey().getEncoded();
        } catch (Exception e1) {
            throw e1;
        }
     }
	
	/**
	 * get private key data
	 * @param pwd
	 * @return private key array data
	 * @throws Exception
	 */
	public byte[] getPrivateKeyByteArray(String pwd) throws Exception {
        try {
        	return keyStore.getKey(keystoreAlias, pwd.toCharArray()).getEncoded();
        } catch (Exception e1) {
        	throw e1;
        }
    }
	
	/**
	 * sign data by private key string 
	 * @param privateKey
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] privateKey, String data) throws Exception {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            return doSign(priKey,data);
        } catch (Exception e1) {
            throw e1 ;
        }
	 }
	
	/**
	 * @param privateKey
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte [] doSign(PrivateKey privateKey, String data)throws Exception {
        try {
            Signature sign = Signature.getInstance(algorithm);
            sign.initSign(privateKey); 
            sign.update(data.getBytes()); 
            return sign.sign(); 
        } catch (Exception e1) {
            throw e1 ;
        }
	}
	
	/**
	 * verify message
	 * @param publicKey
	 * @param data
	 * @param signData
	 * @return
	 * @throws Exception
	 */
    public static boolean verify(byte [] publicKey, String data, byte[] signData)throws Exception {
        try {
        	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
            return doVerify(pubKey,data,signData);
        } catch (Exception e1) {
            throw e1 ;
        }
    }
	
    /**
     * @param publicKey
     * @param data
     * @param signData
     * @return
     * @throws Exception
     */
	private static boolean doVerify(PublicKey publicKey, String data, byte [] signData)throws Exception {
        try {
            Signature sign = Signature.getInstance(algorithm);
            sign.initVerify(publicKey);
            sign.update(data.getBytes());
            return sign.verify(signData);
        } catch (Exception e1) {
            throw e1 ;
        }
	 }
	
	public static String enc2Str(byte [] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static byte[] dec2byte(String encStr) {
		return Base64.getDecoder().decode(encStr);
	}
	
	/**
	 * TEST ..... 
	 * @param args
	 */
	public static void main(String[] args) {

		String filePath  = "D:\\sposzmkuat.pfx";
		String pwd = "111111";
		try {
			// ?????
			byte [] pkData = PKCSUtil.getInstance(filePath,pwd).getPrivateKeyByteArray(pwd);
			System.out.println("??Base64:"+enc2Str(pkData));
			
			// ??????
			byte [] pukData = PKCSUtil.getInstance(filePath,pwd).getPublicKeyByteArray();
			System.out.println("???Base64:"+enc2Str(pukData));
			
			String plainText = "????????..." ;
			
			// ???????
			byte[] signData = PKCSUtil.sign(pkData,plainText);
			System.out.println("???????Base64:"+enc2Str(signData));
			
			// ???????
			boolean isVerifyOk = PKCSUtil.verify(pukData, plainText, signData);
			System.out.println("??????:"+isVerifyOk);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
