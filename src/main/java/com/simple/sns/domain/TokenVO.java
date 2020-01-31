package com.simple.sns.domain;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TokenVO {

	private String token;
	private Long userId;
	private String createdAt;
	
	public TokenVO() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "TokenVO [token=" + token + ", userId=" + userId + ", createdAt=" + createdAt + "]";
	}
}
