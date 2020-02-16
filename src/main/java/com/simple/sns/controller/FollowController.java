package com.simple.sns.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.sns.domain.FollowVO;
import com.simple.sns.domain.ResponseResult;
import com.simple.sns.service.FollowService;

@RestController()
@RequestMapping("/follow")
public class FollowController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
	
	@Autowired
	FollowService followService;
	
	@PostMapping("")
	public ResponseResult insertFollow(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@RequestBody FollowVO followVO) {
		return followService.insertFollow(accesstoken, followVO); 
	}
	
	@DeleteMapping("")
	public ResponseResult deleteFollow(@CookieValue(value = "accesstoken", required = false) String accesstoken,
			@RequestBody FollowVO followVO) {
		return followService.deleteFollow(accesstoken, followVO); 
	}
}
