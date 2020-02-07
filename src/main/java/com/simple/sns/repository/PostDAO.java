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
	
	public int insertPostByTitleAndContent(PostVO postVO) {
		return sqlSession.insert("mapper.post.insertPostByTitleAndContent", postVO);
	}

	public PostVO findPostById(Long id) {
		return sqlSession.selectOne("mapper.post.findPostById", id);
	}
	
	public List<PostVO> findPostsAndUser(){
		return sqlSession.selectList("mapper.post.findPostsAndUser");
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(PostAndUserVO postAndUserVO){
		return sqlSession.selectList("mapper.post.findPostAndUserByUserId", postAndUserVO);
	}
	
	public List<PostAndUserVO> findPostAndUserByToken(String token){
		return sqlSession.selectList("mapper.post.findPostAndUserByToken", token);
	}
	
	public PostAndUserVO findPostAndUserByPostId(Long postId) {
		return sqlSession.selectOne("mapper.post.findPostAndUserByPostId", postId);
	}

	public void deletePostByPostId(Long postId) {
		sqlSession.delete("mapper.post.deletePostByPostId",postId);
	}

	public int updatePostTitleAndContent(PostVO postVO) {
		return sqlSession.update("mapper.post.updatePostTitleAndContent", postVO);
	}
}
