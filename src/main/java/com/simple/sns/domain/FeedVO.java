package com.simple.sns.domain;

import org.springframework.stereotype.Component;

@Component
public class FeedVO {

	private Long userId;
	private Long followeeId;
	private Long postId;
	private String createdAt;
	
	public FeedVO() {
		super();
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFolloweeId() {
		return followeeId;
	}
	public void setFolloweeId(Long followeeId) {
		this.followeeId = followeeId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "FeedVO [userId=" + userId + ", followeeId=" + followeeId + ", postId=" + postId + ", createdAt="
				+ createdAt + "]";
	}
}
