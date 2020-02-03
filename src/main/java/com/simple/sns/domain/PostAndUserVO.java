package com.simple.sns.domain;

import org.springframework.stereotype.Component;

@Component
public class PostAndUserVO {

	private Long id;
	private Long userId;
	private String title;
	private String content;
	private String createdAt;
	private UserVO user;
	
	public PostAndUserVO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "PostAndUserVO [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content
				+ ", createdAt=" + createdAt + ", user=" + user + "]";
	}
}
