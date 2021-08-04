package com.ydw.authentication.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;


public class PasswordUtil {
    public static String md5(String credentials) {
        Md5Hash salt = new Md5Hash(SystemConstants.SALT);
        return new SimpleHash("MD5", credentials, salt, 1024).toString();
    }
    
    public static void main(String[] args) {
        System.out.println(md5("Yidianwan!@#"));

    }
}
