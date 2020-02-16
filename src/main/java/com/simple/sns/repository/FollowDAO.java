package com.simple.sns.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.simple.sns.domain.FollowVO;

@Repository
public class FollowDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public void insertFollow(FollowVO followVO) {
		sqlSession.insert("mapper.follow.insertFollow", followVO);
	}
	
	public void deleteFollow(FollowVO followVO) {
		sqlSession.delete("mapper.follow.deleteFollow", followVO);
	}

	public int findCountFollowByFolloweeIdAndFollowerId(FollowVO followVO) {
		return sqlSession.selectOne("mapper.follow.findCountFollowByFolloweeIdAndFollowerId", followVO);
	}


	public List<FollowVO> findFollowVOsByFolloweeId(Long userId) {
		return sqlSession.selectList("mapper.follow.findFollowVOsByFolloweeId", userId);
	}

	public List<FollowVO> findFollowVOsByFollowerId(Long userId) {
		return sqlSession.selectList("mapper.follow.findFollowVOsByFollowerId", userId);
	}
	
	public int findCountFollowVOsByFollowerId(Long userId) {
		return sqlSession.selectOne("mapper.follow.findCountFollowVOsByFollowerId", userId);
	}
}
