package com.ydw.resource.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Base64;

import org.springframework.util.DigestUtils;

/**
 * Base64工具
 * @author Wang926454
 * @date 2018/8/21 15:14
 */
public class EncryptionUtil {
	
	/**
     * 加密JDK1.8
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String MD5encode(String str){
    	try {
    		byte[] input = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input);
            // 获得密文
            byte[] md = mdInst.digest();
            return new BigInteger(1, md).toString(16);
		} catch (Exception e) {
		}
        return str;
    }

    /**
     * 加密JDK1.8
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String base64encode(String str){
    	try {
    		byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
    		return new String(encodeBytes);
		} catch (Exception e) {
		}
        return str;
    }

    /**
     * 解密JDK1.8
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String base64decode(String str){
    	try {
    		byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
    		return new String(decodeBytes);
		} catch (Exception e) {
		}
        return str;
    }

    public static void main(String[] args) {
		try {
			String s = URLEncoder.encode("CesLMRtVi7jn1zv1565332827879wsapp","UTF-8").toUpperCase();
			System.out.println(s);
			s = DigestUtils.md5DigestAsHex(s.getBytes());
			System.out.println(s);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
