package com.simple.sns.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.simple.sns.domain.TokenVO;
import com.simple.sns.domain.UserVO;

@Repository
public class UserDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<UserVO> findUsers() throws DataAccessException{
		return sqlSession.selectList("mapper.user.findUsers");
	}
	
	public UserVO findUserByUsernameAndPassword(UserVO userVO) throws DataAccessException{
		return sqlSession.selectOne("mapper.user.findUserByUsernameAndPassword", userVO);
		
	}
	
	public UserVO findUserById(Long id) throws DataAccessException{
		return sqlSession.selectOne("mapper.user.findUserById", id);
	}
	
	public int insertUser(UserVO userVO) throws DataAccessException{
		return sqlSession.insert("mapper.user.insertUser",userVO);
	}
	
	public int insertToken(TokenVO tokenVO) {
		return sqlSession.insert("mapper.token.insertToken", tokenVO);
	}
	
	public TokenVO findTokenByToken(String token) {
		return sqlSession.selectOne("mapper.token.findTokenByToken", token);
	}
}
