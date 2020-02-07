package com.simple.sns.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;
import com.simple.sns.repository.PostDAO;

@Service
public class PostService {

	@Autowired
	PostDAO postDAO;
	
	public int insertPostByTitleAndContent(PostVO postVO) {
		return postDAO.insertPostByTitleAndContent(postVO);
	}
	
	public PostVO findPostById(Long id) {
		return postDAO.findPostById(id);
	}
	
	public List<PostVO> findPostsAndUser(){
		return postDAO.findPostsAndUser();
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(PostAndUserVO postAndUserVO){
		return postDAO.findPostAndUserByUserId(postAndUserVO);
	}
	
	public List<PostAndUserVO> findPostAndUserByToken(String token){
		return postDAO.findPostAndUserByToken(token);
	}
	
	public PostAndUserVO findPostAndUserByPostId(Long postId) {
		return postDAO.findPostAndUserByPostId(postId);
	}

	public void deletePostByPostId(Long postId) {
		postDAO.deletePostByPostId(postId);
	}

	public int updatePostTitleAndContent(PostVO postVO) {
		return postDAO.updatePostTitleAndContent(postVO);
	}
	
}
