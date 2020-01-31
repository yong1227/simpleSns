package com.simple.sns.domain;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseResult {

	private String message;
	private int code;
	private Object data;
	
	public ResponseResult() {
		super();
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
		return "ResponseResult [message=" + message + ", code=" + code + ", data=" + data + "]";
	}
}
