package com.ydw.authentication.utils;

import java.io.UnsupportedEncodingException;

/**
 * url转码、解码
 *
 * @author 
 * @date 
 */
public class UrlUtil {
    private final static String ENCODE = "GBK"; 
    /**
     * URL 解码
     *
     * @return String
     * @author 
     * @date 
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * URL 转码
     *
     * @return String
     * @author 
     * @date 
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @return void
     * @author 
     * @date 
     */
    public static void main(String[] args) {
        String str = "测试1";
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(str));
        
    }

}