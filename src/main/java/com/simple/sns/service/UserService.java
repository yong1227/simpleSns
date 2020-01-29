package com.simple.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.UserVO;
import com.simple.sns.repository.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	
	public List<UserVO> listUsers() throws DataAccessException{
		List<UserVO> userList = null;
		userList = userDAO.selectAllUserList();
		return userList;
	}
	
	public UserVO login(UserVO userVO) throws Exception{
		return userDAO.loginById(userVO);
	}
	
	public UserVO getUserOne(int id) throws Exception{
		return userDAO.getUserOne(id);
	}
	
	public int insertUser(UserVO userVO) throws DataAccessException{
		return userDAO.insertUser(userVO);
	}
}