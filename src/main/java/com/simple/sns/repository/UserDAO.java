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
	public List<UserVO> selectAllUserList() throws DataAccessException{
		List<UserVO> userList = null;
		userList = sqlSession.selectList("mapper.user.selectAllUserList");
		return userList;
	}
	
	// 로그인
	public UserVO loginByUser(UserVO userVO) throws DataAccessException{
		UserVO vo = sqlSession.selectOne("mapper.user.loginByUser", userVO);
		return vo;
	}
	
	// 한 명만 조회
	public UserVO getUserOne(Long id) throws DataAccessException{
		UserVO vo = sqlSession.selectOne("mapper.user.getUserOne", id);
		return vo;
	}
	
	// 회원 가입
	public int insertUser(UserVO userVO) throws DataAccessException{
		int result = sqlSession.insert("mapper.user.insertUser",userVO);
		return result;
	}
	
	// 로그인 시 토큰 생성
	public int insertToken(TokenVO tokenVO) {
		int vo = sqlSession.insert("mapper.user.insertToken", tokenVO);
		return vo;
	}
	
	// 토근 조회
	public TokenVO selectToken(TokenVO token) {
		return sqlSession.selectOne("mapper.user.selectToken", token);
	}
}
