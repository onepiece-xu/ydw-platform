package com.ydw.message.model.vo;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ResultInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7621938337649271329L;
	/**
	 * 返回码
	 */
	private int code;
	/**
	 * 返回信息
	 */
	private String msg;
	/**
	 * 返回体
	 */
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ResultInfo() {
		super();
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ResultInfo(int code, String msg, Object t) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = t;
	}
	
	public static ResultInfo success(){
		return new ResultInfo(HttpStatus.OK.value(), "", null);
	}
	
	public static ResultInfo success(String msg){
		return new ResultInfo(HttpStatus.OK.value(), msg, null);
	}
	
	public static ResultInfo success(Object t){
		return new ResultInfo(HttpStatus.OK.value(), "", t);
	}
	
	public static ResultInfo success(String msg, Object t){
		return new ResultInfo(HttpStatus.OK.value(), msg, t);
	}
	
	public static ResultInfo fail(){
		return new ResultInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null);
	}
	
	public static ResultInfo fail(String msg){
		return new ResultInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
	}
	
	public static ResultInfo fail(int code, String msg){
		return new ResultInfo(code, msg, null);
	}
	
	public static ResultInfo fail(int code, String msg, Object t){
		return new ResultInfo(code,msg,t);
	}
}