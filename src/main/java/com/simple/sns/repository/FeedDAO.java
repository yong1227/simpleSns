package com.simple.sns.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.simple.sns.domain.FeedVO;

@Repository
public class FeedDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public void insertFeed(FeedVO feedVO) {
		sqlSession.insert("mapper.feed.insertFeed", feedVO);
	}
}
