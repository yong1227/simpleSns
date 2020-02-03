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
	
	// 전체 조회
	public List<UserVO> findUsers() throws DataAccessException{
		return sqlSession.selectList("mapper.user.findUsers");
	}
	
	// 로그인
	public UserVO findUserByUsernameAndPassword(UserVO userVO) throws DataAccessException{
		return sqlSession.selectOne("mapper.user.findUserByUsernameAndPassword", userVO);
		
	}
	
	// 한 명만 조회
	public UserVO findUserById(Long id) throws DataAccessException{
		return sqlSession.selectOne("mapper.user.findUserById", id);
	}
	
	// 회원 가입
	public int insertUser(UserVO userVO) throws DataAccessException{
		return sqlSession.insert("mapper.user.insertUser",userVO);
	}
	
	// 로그인 시 토큰 생성
	public int insertToken(TokenVO tokenVO) {
		return sqlSession.insert("mapper.user.insertToken", tokenVO);
	}
	
	// 토큰 조회
	public TokenVO findTokenByToken(TokenVO token) {
		return sqlSession.selectOne("mapper.user.findTokenByToken", token);
	}
}
