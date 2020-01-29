package com.simple.sns.domain;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class UserVO {

	private int id;
	private String userName;
	private String password;
	private Date createdAt;
	
	public UserVO() {
		super();
	}
	
	
	public UserVO(int id, String userName, String password, Date createdAt) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.createdAt = createdAt;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", userName=" + userName + ", password=" + password + ", createdAt=" + createdAt
				+ "]";
	}
}
