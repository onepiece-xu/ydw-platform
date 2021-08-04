package com.ydw.open.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Base64工具
 * @author Wang926454
 * @date 2018/8/21 15:14
 */
public class Base64ConvertUtil {

    /**
     * 加密JDK1.8
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     * @param str
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/8/21 15:28
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
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
