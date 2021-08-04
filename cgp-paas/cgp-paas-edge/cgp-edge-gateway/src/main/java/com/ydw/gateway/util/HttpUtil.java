package com.ydw.gateway.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okhttp3.Request.Builder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

}
