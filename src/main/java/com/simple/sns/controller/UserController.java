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
import com.simple.sns.repository.UserDAO;
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
	RandomToken ramdomToken;
	
	@Autowired
	TokenVO tokenVO;
	
	// 모든 user
	@GetMapping("/userAll")
	public ResponseResult listUsers() {
		logger.info("listUsers() called");
		
		List<UserVO> userList = userService.listUsers();
		
		for(UserVO user : userList) {
			System.out.println("first : " + user);
		}
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userList);
		
		System.out.println("responseResult : "+ responseResult);
		
		return responseResult;
	}
	
	// id로 회원 정보 하나 조회 
	@GetMapping("/user")
	public ResponseResult getUserOneById(@RequestParam("id") Long id) throws Exception {
		logger.info("getUserOne() called");
		
		userVO = userService.getUserOne(id);
		System.out.println("userVo : "+ userVO);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		System.out.println("responseResult : "+ responseResult);
		
		return responseResult;
	}
	
	// 회원가입
	@PostMapping("/user")
	public ResponseResult insertUser(@RequestBody UserVO userVO) throws Exception{
		logger.info("insertUser() 메서드 호출");
		
		userService.insertUser(userVO);

		//responeData
		Long id = userVO.getId();
		System.out.println("id : " + id);
		
		userVO = userService.getUserOne(id);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		System.out.println("responseResult : "+ responseResult);

		return responseResult;
	}
	
	// 회원 인증
	@PostMapping("/auth")
	public ResponseResult insertToken(@RequestBody UserVO userVO) throws Exception {
		logger.info("auth() called");
		
		//getUerId
		userVO = userService.login(userVO);
		System.out.println("userOne : "+userVO);
		Long userId = userVO.getId();
		System.out.println("userID : " + userId);
		
		//token 생성
		StringBuffer token = ramdomToken.makeToken();
		System.out.println("token : "  + token);
		
		//token toString
		String tokenToString = token.toString();
		
		//insert token
		tokenVO.setUserId(userId);
		tokenVO.setToken(tokenToString);
		System.out.println("tokenVO : "+ tokenVO);
		userService.insertToken(tokenVO);
		
		//select token
		tokenVO = userService.selectToken(tokenVO);
		System.out.println("tokenVO2 : " + tokenVO);
		
		//responseData
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(tokenVO);
		
		System.out.println(responseResult);
		
		return responseResult;
	}
}
