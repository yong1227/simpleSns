package com.simple.sns.controller;

import java.util.List;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.sns.domain.ResponseResult;
import com.simple.sns.domain.TokenVO;
import com.simple.sns.domain.UserVO;
import com.simple.sns.service.UserService;
import com.simple.sns.util.RandomToken;

@RestController
public class UserController {

	static org.slf4j.Logger logger  = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserVO userVO;
	
	@Autowired
	ResponseResult responseResult;
	
	@Autowired
	TokenVO tokenVO;
	
	// 모든 user
	@GetMapping("/userAll")
	public ResponseResult findUsers() {
		logger.info("findUsers() called");
		
		List<UserVO> userList = userService.findUsers();
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userList);
		
		return responseResult;
	}
	
	// id로 회원 정보 하나 조회 
	@GetMapping("/user")
	public ResponseResult findUserById(@RequestParam("id") Long id) throws Exception {
		logger.info("findUserById() called");
		
		userVO = userService.findUserById(id);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		return responseResult;
	}
	
	// 회원가입
	@PostMapping("/user")
	public ResponseResult insertUser(@RequestBody UserVO userVO) throws Exception{
		logger.info("insertUser() 메서드 호출");
		
		logger.info("userVO : " + userVO);
		
		userService.insertUser(userVO);

		//responeData
		Long id = userVO.getId();
		
		userVO = userService.findUserById(id);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		return responseResult;
	}
	
	// 회원 인증
	@PostMapping("/auth")
	public ResponseResult insertToken(@RequestBody UserVO userVO) throws Exception {
		logger.info("insertToken() called");
		
		logger.info("username : "+userVO.getUsername());
		logger.info("password : "+userVO.getPassword());
		
		//getUerId
		userVO = userService.findUserByUsernameAndPassword(userVO);
		logger.info("userVO " + userVO);
		Long userId = userVO.getId();
		logger.info("userId : " +userId);
		
		//token 생성
		StringBuffer token = RandomToken.makeToken();
		
		//token toString
		String tokenToString = token.toString();
		
		//insert token
		tokenVO.setUserId(userId);
		tokenVO.setToken(tokenToString);
		userService.insertToken(tokenVO);
		
		//select token
		tokenVO = userService.findTokenByToken(tokenToString);
		
		//responseData
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(tokenVO);
		
		return responseResult;
	}
}
