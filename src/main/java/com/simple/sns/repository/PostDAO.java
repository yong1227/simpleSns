package com.simple.sns.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;

@Repository
public class PostDAO {

	@Autowired
	private SqlSession sqlSession;
	
	// insert post
	public int insertPost(PostVO postVO) {
		return sqlSession.insert("mapper.post.insertPost", postVO);
	}

	// findPostById
	public PostVO findPostById(Long id) {
		return sqlSession.selectOne("mapper.post.findPostById", id);
	}
	
	// findPostsAndUser
	public List<PostAndUserVO> findPostsAndUser(){
		return sqlSession.selectList("mapper.post.findPostsAndUser");
	}
	
	// findPostByUserId
	public List<PostAndUserVO> findPostAndUserByUserId(PostAndUserVO postAndUserVO){
		return sqlSession.selectList("mapper.post.findPostAndUserByUserId", postAndUserVO);
	}
}
