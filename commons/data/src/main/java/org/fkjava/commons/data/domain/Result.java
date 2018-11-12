package org.fkjava.commons.data.domain;

public class Result {
	public static final int CODE_OK = 1;
	public static final int CODE_ERROR = 2;
	
	//结果的状态码
	private int code;
	//提示信息
	private String message;
	
	public static Result ok() {
		Result r = new Result();
		r.setCode(CODE_OK);
		return r;
	}

	public static Result ok(String message) {
		Result r = ok();
		r.setMessage(message);
		return r;
	}
	
	public static Result error() {
		Result r = new Result();
		r.setCode(CODE_ERROR);
		return r;
	}
	
	public static Result error(String message) {
		Result r = error();
		r.setMessage(message);
		return r;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
