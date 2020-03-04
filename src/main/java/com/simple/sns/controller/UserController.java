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
		
		List<UserVO> userList = userService.findUsers();
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userList);
		
		return responseResult;
	}
	
	@GetMapping("/user")
	public ResponseResult findUserById(@RequestParam("id") Long id) throws Exception {
		
		userVO = userService.findUserById(id);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		return responseResult;
	}
	
	@PostMapping("/user")
	public ResponseResult insertUser(@RequestBody UserVO userVO) throws Exception{
		
		userService.insertUser(userVO);

		//responeData
		Long id = userVO.getId();
		
		userVO = userService.findUserById(id);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(userVO);
		
		return responseResult;
	}
	
	@PostMapping("/auth")
	public ResponseResult insertToken(@RequestBody UserVO userVO) throws Exception {
		userVO = userService.findUserByUsernameAndPassword(userVO);
		Long userId = userVO.getId();
		
		StringBuffer token = RandomToken.makeToken();
		
		String tokenToString = token.toString();
		
		tokenVO.setUserId(userId);
		tokenVO.setToken(tokenToString);
		userService.insertToken(tokenVO);
		
		tokenVO = userService.findTokenByToken(tokenToString);
		
		responseResult.setCode(HttpStatus.OK);
		responseResult.setMessage("Success");
		responseResult.setData(tokenVO);
		
		return responseResult;
	}
}
