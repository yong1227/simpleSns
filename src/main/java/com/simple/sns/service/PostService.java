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
	
	public int insertPost(PostVO postVO) {
		return postDAO.insertPost(postVO);
	}
	
	public PostVO findPostById(Long id) {
		return postDAO.findPostById(id);
	}
	
	public List<PostAndUserVO> findPostsAndUser(){
		return postDAO.findPostsAndUser();
	}
	
	public List<PostAndUserVO> findPostAndUserByUserId(PostAndUserVO postAndUserVO){
		return postDAO.findPostAndUserByUserId(postAndUserVO);
	}
}
