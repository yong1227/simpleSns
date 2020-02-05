package com.simple.sns.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	PostVO postVO;

	@Autowired
	PostAndUserVO postAndUserVO;

	@Autowired
	ResponseResult responseResult;

	// 글 저장
	@PostMapping("/post")
	public ResponseResult insertPostByTitleAndContent(@RequestBody PostVO postVO,
			@RequestHeader(value = "accesstoken") String accesstoken) {
		logger.info("insertPostByTitleAndContent() called");

		logger.info("accesstoken : " + accesstoken);
		
		// 토큰 이용해서 토큰 테이블의 userId 추출
		tokenVO = userService.findTokenByToken(accesstoken);

		Long userId = tokenVO.getUserId();

		// insert PostVO
		postVO.setUserId(userId);
		postService.insertPostByTitleAndContent(postVO);

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

		return new ResponseResult(HttpStatus.OK.value(), "Success", posts);
	}

	// 내가 쓴 글 리스트 조회
	@GetMapping("/post/my")
	public ResponseResult findPostAndUserByToken(
			@RequestHeader(value = "accesstoken", required = false) String accesstoken) {

		logger.info("findPostAndUserByToken called");

		logger.info("accesstoken : " + accesstoken);

		tokenVO = userService.findTokenByToken(accesstoken);

		Long userId = tokenVO.getUserId();
		logger.info("userId : " + userId);

		postAndUserVO.setUserId(userId);

		List<PostAndUserVO> posts = postService.findPostAndUserByUserId(postAndUserVO);

		// post, user, token 3중 조인으로 token값에 의해서 findPostAndUser하기
//		List<PostAndUserVO> posts = postService.findPostAndUserByToken(accesstoken);
//		
//		for (PostAndUserVO post : posts) {
//			logger.info("post : " + post);
//		}

		return new ResponseResult(HttpStatus.OK.value(), "Success", posts);
	}

	// 글 상세 페이지
	@GetMapping("/post/{postId}")
	public ResponseResult findPostAndUserByPostId(@PathVariable("postId") Long postId) {
		logger.info("findPostAndUserByPostId called");
		logger.info("postId : " + postId);

		postAndUserVO = postService.findPostAndUserByPostId(postId);
		logger.info("postAndUserVO : " + postAndUserVO);

		return new ResponseResult(HttpStatus.OK.value(), "Success", postAndUserVO);
	}

	// 글 수정하기
	@PutMapping("/post")
	public ResponseResult updatePostTitleAndContent(@RequestBody PostVO postVO,
			@CookieValue(value = "accesstoken", required = false) String accesstoken) {
		logger.info("updatePostTitleAndContent called");
		PostVO postVO2 = new PostVO();
		
		String result = null;
		// 수정하려는 글의 번호를 통해서 userId를 찾고 토큰에 있는 userId를 찾아서 같으면 삭제할 수 있게
		// 수정하려는 글의 번호를 통해서 글 작성자의 userId를 찾기
		logger.info("postVO1 : "+ postVO);
		postVO2 = postService.findPostById(postVO.getId());
		Long userIdByPost = postVO2.getUserId();
		logger.info("userIdByPost : "+ userIdByPost);

		postVO.setUserId(userIdByPost);
		
		if (accesstoken != null) {
			// 토큰을 통해서 접속중인 userId 찾기
			tokenVO = userService.findTokenByToken(accesstoken);
			Long userIdByToken = tokenVO.getUserId();
			logger.info("userIdByToken : "+ userIdByToken);
			
			// 두 id 비교
			if (userIdByPost == userIdByToken) {
				logger.info("postVO1-2 : "+ postVO);
				postService.updatePostTitleAndContent(postVO);
				result = "Success";
				postVO = postService.findPostById(postVO.getId());

			} else {
				result = "글 작성자만 글을 수정할 수 있습니다.";
			}
		} else {
			result = "로그인을 해주세요. 글 작성자만 글을 수정할 수 있습니다.";
		}
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage(result);
		responseResult.setData(postVO);

		return responseResult;
	}

	// 해당 글 삭제 하기
	@DeleteMapping("/post/{postId}")
	public ResponseResult deletePostByPostId(@PathVariable("postId") Long postId,
			@CookieValue(value = "accesstoken", required = false) String accesstoken) throws Exception {
		logger.info("deletePostByPostId called");
		String result = null;
		// 삭제하려는 글의 번호를 통해서 userId를 찾고 토큰에 있는 userId를 찾아서 같으면 삭제할 수 있게
		// 삭제하려는 글의 번호를 통해서 userId를 찾기
		postVO = postService.findPostById(postId);
		Long userIdByPost = postVO.getUserId();

		if (accesstoken != null) {

			// 토큰을 통해서 userId 찾기
			tokenVO = userService.findTokenByToken(accesstoken);
			Long userIdByToken = tokenVO.getUserId();

			// 두 id 비교
			if (userIdByPost == userIdByToken) {
				postService.deletePostByPostId(postId);
				result = "삭제 성공";
			} else {
				result = "글 작성자만 글을 삭제할 수 있습니다.";
			}
		} else {
			result = "로그인을 해주세요. 글 작성자만 글을 삭제할 수 있습니다.";
		}
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage(result);

		return responseResult;
	}
}
