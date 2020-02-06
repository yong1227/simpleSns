package com.simple.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.TokenVO;
import com.simple.sns.domain.UserVO;
import com.simple.sns.repository.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public List<UserVO> findUsers() throws DataAccessException{
		return userDAO.findUsers();
	}
	
	public UserVO findUserByUsernameAndPassword(UserVO userVO) throws Exception{
		return userDAO.findUserByUsernameAndPassword(userVO);
	}
	
	public UserVO findUserById(Long id) throws Exception{
		return userDAO.findUserById(id);
	}
	
	public int insertUser(UserVO userVO) throws DataAccessException{
		return userDAO.insertUser(userVO);
	}
	
	public int insertToken(TokenVO tokenVO) {
		return userDAO.insertToken(tokenVO);
	}
	
	public TokenVO findTokenByToken(String token) {
		return userDAO.findTokenByToken(token);
	}
}
