package com.simple.sns.domain;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseResult {

	private int code;
	private String message;
	private Object data;
	
	public ResponseResult() {
		super();
	}
	
	public ResponseResult(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(HttpStatus httpStatus) {
		this.code = httpStatus.value();
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}
