package com.simple.sns.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;

@Repository
public class PostDAO {
	
	static org.slf4j.Logger logger = LoggerFactory.getLogger(PostDAO.class);

	@Autowired
	private SqlSession sqlSession;
	
	public int insertPostByTitleAndContent(PostVO postVO) {
		return sqlSession.insert("mapper.post.insertPostByTitleAndContent", postVO);
	}

	public PostVO findPostById(Long id) {
		return sqlSession.selectOne("mapper.post.findPostById", id);
	}
	
	public List<PostAndUserVO> findPostsAndUser(){
		return sqlSession.selectList("mapper.post.findPostsAndUser");
	}
	
	public List<PostAndUserVO> findPostsAndUserWithIsFollow(Long userId){
		logger.info("findPostsAndUserWithIsFollow called");
		return sqlSession.selectList("mapper.post.findPostsAndUserWithIsFollow", userId);
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(Long userId){
		return sqlSession.selectList("mapper.post.findPostAndUserByUserId", userId);
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

	public List<PostAndUserVO> findMyPostAndUserAndMyFollowerByUserId(Long userId) {
		return sqlSession.selectList("mapper.post.findMyPostAndUserAndMyFollowerByUserId", userId);
	}
}
