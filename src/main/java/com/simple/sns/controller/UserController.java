package com.simple.sns.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.sns.domain.UserVO;
import com.simple.sns.service.UserService;

@RestController
public class UserController {

	static org.slf4j.Logger logger  = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserVO userVO;
	
	// 모든 user
	@GetMapping("/userAll")
	public ResponseEntity<List<UserVO>> listUsers() {
		logger.info("listUsers() called");
		List<UserVO> userList = userService.listUsers();
		
		for(UserVO user : userList) {
			System.out.println(user);
		}

		
		
		return new ResponseEntity<List<UserVO>>(userList,HttpStatus.OK);
	}
	
	// 회원 정보 하나 조회
	@GetMapping("/user")
	public ResponseEntity<UserVO> getUserOne(@RequestParam("id") int id) throws Exception {
		logger.info("getUserOne() called");
		
		userVO = userService.getUserOne(id);
		System.out.println(userVO);
		
		return new ResponseEntity<UserVO>(userVO,HttpStatus.OK);
	}
	
	// 회원가입
	@PostMapping("/user")
	public ResponseEntity<Integer> insertUser(@RequestBody UserVO userVO){
		
		logger.info("insertUser() 메서드 호출");
		
		// 임시
		userVO.setId(7);
				
		int result = userService.insertUser(userVO);
		
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}

}
