package com.simple.sns.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.simple.sns.domain.PostAndUserVO;
import com.simple.sns.domain.PostVO;
import com.simple.sns.domain.ResponseResult;
import com.simple.sns.domain.TokenVO;
import com.simple.sns.domain.UserVO;
import com.simple.sns.service.PostService;
import com.simple.sns.service.UserService;

@RestController
public class PostController {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;

	@Autowired
	UserVO userVO;

	@Autowired
	TokenVO tokenVO;
	
	@Autowired
	PostAndUserVO postAndUserVO;
	
	@Autowired
	PostVO postVO;

	// 글 저장
	@PostMapping("/post")
	public ResponseResult insertPost(@RequestBody PostVO postVO,
			@RequestHeader(value = "accesstoken") String accesstoken) {
		logger.info("insertPostByTitleAndContent() called");

		logger.info("accesstoken : " + accesstoken);

		// 토큰 이용해서 토큰 테이블의 userId 추출
		tokenVO.setToken(accesstoken);
		tokenVO = userService.findTokenByToken(tokenVO);

		Long userId = tokenVO.getUserId();

		// insert PostVO
		postVO.setUserId(userId);
		postService.insertPost(postVO);

		// findPostById
		Long id = postVO.getId();

		logger.info("postId : " + id);
		postVO = postService.findPostById(id);

		return new ResponseResult(HttpStatus.OK.value(), "Success", postVO);
	}
	
	// 전체 글 조회 날짜 내림차순
	@GetMapping("/post")
	public ResponseResult findPostsAndUser() {
		logger.info("findPostsAndUser called");
		
		List<PostAndUserVO> posts = new ArrayList<PostAndUserVO>();
		
		posts = postService.findPostsAndUser();
		
		for (PostAndUserVO post : posts) {
			logger.info("post : " + post);
		}
		
		return new ResponseResult(HttpStatus.OK.value(), "Success", posts);
	}
	
	// 내가 쓴 글 리스트 조회 
	@GetMapping("/post/my")
	public ResponseResult findPostAndUserByToken(@RequestHeader(value = "accesstoken") String accesstoken) {
		
		logger.info("findPostAndUserByToken called");
		
		logger.info("accesstoken : "+accesstoken);
		
		tokenVO.setToken(accesstoken);
		
		tokenVO =  userService.findTokenByToken(tokenVO);
		
		Long userId = tokenVO.getUserId();
		logger.info("userId : "+ userId);
		
		postAndUserVO.setUserId(userId);
		logger.info("postAndUserVO : "+ postAndUserVO);
		
		List<PostAndUserVO> posts = postService.findPostAndUserByUserId(postAndUserVO);
		
		for (PostAndUserVO post : posts) {
			logger.info("post : " + post);
		}
		
		
		// post, user, token 3중 조인으로 token값에 의해서 findPostAndUser하기
//		List<PostAndUserVO> posts = postService.findPostAndUserByToken(accesstoken);
//		
//		for (PostAndUserVO post : posts) {
//			logger.info("post : " + post);
//		}
		
		return new ResponseResult(HttpStatus.OK.value(), "Success", posts);
	}
}
