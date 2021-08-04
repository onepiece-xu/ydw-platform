package com.ydw.authentication.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;


import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.ydw.authentication.model.constant.SmsTemplate.*;

public class HttpSendSMS {
    /**
     * 发送短信
     * @param phone 手机号
     * @param msg 内容
     * @return  200成功  500失败
     */
        private Integer sendSMS(String phone,String msg){
            HttpPost httppost = null;
            try{
                DefaultHttpClient httpClient = new DefaultHttpClient();
                httppost = new HttpPost(smsurl);
                Map<String,String> map = new HashMap<String,String>();
                map.put("appkey", appkey);
                map.put("appcode", appcode);
                String timestamp = System.currentTimeMillis()+"";
                map.put("timestamp", timestamp);
                map.put("phone", phone);
                map.put("msg", msg);
                String sign = md5(appkey+appsecret+timestamp);
                map.put("sign", sign);
                map.put("extend", "123");
                String json = JSONObject.toJSONString(map);
                StringEntity formEntity = new StringEntity(json, "utf-8");
                httppost.setEntity(formEntity);
                HttpResponse httpresponse = null;
                httpresponse = httpClient.execute(httppost);
                HttpEntity httpEntity = httpresponse.getEntity();
                String response = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(response);

            }catch(Exception e){
                e.printStackTrace();
                return 500;
            }
            return 200;
        }



    public static void main( String[] args ) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        HttpClient httpClient = null;
        HttpPost httppost = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httppost = new HttpPost(smsurl);

            Map<String,String> map = new HashMap<String,String>();

            map.put("appkey", appkey);
            map.put("appcode", appcode);
            String timestamp = System.currentTimeMillis()+"";
            map.put("timestamp", timestamp);
            map.put("phone", "15207156293");
            String strTemp = (int) ((Math.random() * 9 + 1) * 100000) + "";

            map.put("msg", SMS_WARN);
            String sign = md5(appkey+appsecret+timestamp);
            map.put("sign", sign);
            map.put("extend", "123");
            String json = JSONObject.toJSONString(map);
            StringEntity formEntity = new StringEntity(json, "utf-8");
            httppost.setEntity(formEntity);
            HttpResponse httpresponse = null;
            httpresponse = httpClient.execute(httppost);
            HttpEntity httpEntity = httpresponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            System.out.println(response);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(httppost!=null){
                httppost.abort();
            }
//            if(httpClient!=null){
//                httpClient.getConnectTimeout().shutdown();
//            }
        }
    }

    private static String md5(String param) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Byte = md5.digest(param.getBytes("utf8"));
        String result = byteToHex(md5Byte);
        return result;
    }


    private static String byteToHex(byte[] md5Byte) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        for(byte each : md5Byte){
            int value = each&0xff;
            String hex = Integer.toHexString(value);
            if(value<16){
                sb.append("0");
            }
            sb.append(hex);
        }
        result = sb.toString();
        return result;
    }


    public static int byte4ToInteger(byte[] b, int offset) {
        return (0xff & b[offset]) << 24 | (0xff & b[offset+1]) << 16 |
                (0xff & b[offset+2]) << 8 | (0xff & b[offset+3]);
    }

}

