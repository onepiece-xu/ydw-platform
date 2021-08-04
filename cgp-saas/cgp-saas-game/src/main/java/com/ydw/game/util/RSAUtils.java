package com.ydw.game.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * RSA加密工具类
 * @author xulh
 *
 */
public class RSAUtils {
    /**
     * 密钥长度 于原文长度对应 以及越长速度越慢
     */
    private final static int KEY_SIZE = 1024;
    
    /**
     * 公钥
     */
    private static String publicKeyString = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALiAQwxYWK2w4i4KmoYpvz0Vzq/sc5ePlVoNVZjV4qwODQDA+MeedzGARMwt09drnCJ+4YPfyeoEPRrizJ9TiM8CAwEAAQ==";
    
    /**
     * 私钥
     */
    private static String privateKeyString = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAuIBDDFhYrbDiLgqahim/PRXOr+xzl4+VWg1VmNXirA4NAMD4x553MYBEzC3T12ucIn7hg9/J6gQ9GuLMn1OIzwIDAQABAkAtH1FGh6TIdeYbFITOIw+eROMTq2RpwLfqGjDA/C4/nZXKuXg4AqsTBw0v2V3AcnX6YlUWxkTk6UOOu/c8/5ShAiEA2Z/ejZ1xgLbRR+MhFt3u61kvQ+TuCzuENxElgxBoYfsCIQDZCR0mE/XXh78l8bKhWIyftvhaVDSTwwmhmxe4tVWQPQIgMQae8HBDnaeRxgwY7DnbFRHmX/k21zj1NCKcvMX5ffkCIQC5dKk8vXeoaW2z/blXV7QqUg4cGbInxVR4jG+TvRfVPQIgbcUXLColDFMQ8p+W+nXrUd284fPj60rKS18Ai4jpUkk=";
    
    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str) {
    	String outStr = new String();
    	try {
    		//base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(publicKeyString);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return outStr;
    }
    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str){
    	String outStr = new String();
    	try {
    		//64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str);
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKeyString);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return outStr;
    }
    
    public static void main(String[] args) {
//    	createKey();
    	String password = "123456";
    	String pubPass = encrypt(password);
    	System.out.println(pubPass);
    	String priPass = decrypt(pubPass);
    	System.out.println(priPass);
	}
    
    /**
     * 创建公钥和私钥
     * @author xulh
     * @date 2019年7月11日
     * void
     */
    private static void createKey(){
    	try {
    		KeyPairGenerator keyPairGen = null;
    		keyPairGen = KeyPairGenerator.getInstance("RSA");
    		// 初始化密钥对生成器
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            
            publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            // 得到私钥字符串
            privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}