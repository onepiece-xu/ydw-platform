package com.ydw.authentication.utils;

public class SystemConstants {
    //成功状态
    public static final String STATUS_SUCCESS = "0";
    //失败状态
    public static final String STATUS_FAIL = "1";
    //使用固定SALT，如果密级加强可以使用随机SALT
    public static final String SALT = "cloudGames";
    
    public static final String AccountNameNull ="账户name为空";
    
    public static final String PleaseRegist ="手机号不存在,请注册";
    
    public static final String ErrorPassword="密码错误";

    public static final String SamePassword="新旧密码不可相同";
    
    public static final String NotRegist ="用户尚未注册";
    
    public static final String CodeExpire ="验证码已失效";
    
    public static final String CodeError ="验证码错误";
    
    public static final String UnrecognizedReferences ="推荐人信息不存在";
    
    public static final String RegistSuccess ="注册成功";
    
    public static final String NewpasswordNull ="新密码为空";

    public static final String OldpasswordNull ="旧密码为空";
    
    public static final String Exist ="用户已存在";
    
    public static final String Disable ="账号被禁用，请尽快联系客服";
    
    public static final String UnkonwCodeType ="未知的验证码类型";
    
    public static final String PhoneNumberNull="手机号不能为空"; 
    
    public static final String PictureNull="图片不能为空"; 
    
    public static final String UnsupportPictureType="不支持的图片格式"; 
    
    public static final String PWD_MD5 = "GEEK#@YDN";
}
