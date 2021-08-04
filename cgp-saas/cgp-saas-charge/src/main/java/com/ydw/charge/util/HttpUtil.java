package com.ydw.charge.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 封装http请求
 * @author xulh
 *
 */
public class HttpUtil {
	
	public static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES) // 设置连接超时时间
            .readTimeout(2, TimeUnit.MINUTES) // 设置读取超时时间
            .build();
	
	public static String doGet(String url){
		return doGet(url,null,null);
	}
	
	public static String doGet(String url,Map<String,String> params){
		return doGet(url,null,params);
	}

	/**
	 * 同步get请求
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 地址
	 * @param headers 头部信息
	 * void
	 */
	public static String doGet(String url,Map<String,String> headers,Map<String,String> params){
		String result = null;
		Request.Builder reqBuild = new Request.Builder();
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				reqBuild.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(params != null && !params.isEmpty()){
			HttpUrl.Builder urlBuilder =HttpUrl.parse(url)
	                .newBuilder();
			for(Map.Entry<String, String> entry : params.entrySet()){
				urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
			}
			reqBuild.url(urlBuilder.build());
		}else{
			reqBuild.url(url);
		}
		Request request = reqBuild.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    result = response.body().string();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 同步post请求(表单形式)
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 请求地址
	 * @param headers 头部信息
	 * @param params 请求参数
	 * @return
	 * String
	 */
	public static String doFormPost(String url,Map<String,String> headers,Map<String,String> params) {
		String result = null;
		okhttp3.FormBody.Builder bodyBuilder = new FormBody.Builder();
		if(params != null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				bodyBuilder.add(entry.getKey(), entry.getValue());
			}
		}
		RequestBody requestBody = bodyBuilder.build();
		Builder builder = new Request.Builder().url(url).post(requestBody);
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    result = response.body().string();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
	/**
	 * 同步post请求(json格式)
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 请求地址
	 * @param headers 头部信息
	 * @param params 请求参数
	 * @return
	 * String
	 */
	public static String doJsonPost(String url,Map<String,String> headers,Object params) {
		String result = null;
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(params));
		Builder builder = new Request.Builder().url(url).post(requestBody);
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    ResponseBody body = response.body();
		    result = body.string();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	   public static String doStringPost(String url,Map<String,String> headers,String params) {
	        String result = null;
	        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
	        RequestBody requestBody = RequestBody.create(mediaType, params);
	        Builder builder = new Request.Builder().url(url).post(requestBody);
	        if(headers != null && !headers.isEmpty()){
	            for(Map.Entry<String, String> entry : headers.entrySet()){
	                builder.addHeader(entry.getKey(), entry.getValue());
	            }
	        }
	        Request request = builder.build();
	        Call call = okHttpClient.newCall(request);
	        try {
	            Response response = call.execute();
	            ResponseBody body = response.body();
	            result = body.string();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	
	/**
	 * 同步post请求(json格式)
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 请求地址
	 * @param headers 头部信息
	 * @param params 请求参数
	 * @return
	 * String
	 */
	public static byte[] doFilePost(String url,Map<String,String> headers,Map<String,Object> params) {
		byte[] result = null;
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(params));
		Builder builder = new Request.Builder().url(url).post(requestBody);
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    ResponseBody body = response.body();
		    result = body.bytes();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 同步delete请求
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 地址
	 * @param headers 头部信息
	 * void
	 */
	public static String doDelete(String url,Map<String,String> headers,Map<String,String> params){
		String result = null;
		Request.Builder reqBuild = new Request.Builder().delete();
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				reqBuild.addHeader(entry.getKey(), entry.getValue());
			}
		}
		if(params != null && !params.isEmpty()){
			HttpUrl.Builder urlBuilder =HttpUrl.parse(url)
	                .newBuilder();
			for(Map.Entry<String, String> entry : params.entrySet()){
				urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
			}
			reqBuild.url(urlBuilder.build());
		}else{
			reqBuild.url(url);
		}
		Request request = reqBuild.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    result = response.body().string();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 同步put请求
	 * @author xulh
	 * @date 2019年7月31日
	 * @param url 地址
	 * @param headers 头部信息
	 * void
	 */
	public static String doPut(String url,Map<String,String> headers,Map<String,String> params){
		String result = null;
		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
		RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(params));
		Builder builder = new Request.Builder().url(url).put(requestBody);
		if(headers != null && !headers.isEmpty()){
			for(Map.Entry<String, String> entry : headers.entrySet()){
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		Request request = builder.build();
		Call call = okHttpClient.newCall(request);
		try {
		    Response response = call.execute();
		    ResponseBody body = response.body();
		    result = body.string();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return result;
	}
}
