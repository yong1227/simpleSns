package com.simple.sns.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.ModelAndView;

import com.simple.sns.domain.TokenVO;
import com.simple.sns.domain.UserVO;
import com.simple.sns.service.UserService;

@Controller
public class MainController {

	static org.slf4j.Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	UserVO userVO;
	
	@Autowired
	TokenVO tokenVO;
	
	@GetMapping("/")
	 public ModelAndView index(@CookieValue(value = "accesstoken", required = false) String accesstoken) throws Exception {
		logger.info("index called");
		logger.info("accesstoken : "+ accesstoken);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		
		if( accesstoken != null ) {
			tokenVO = userService.findTokenByToken(accesstoken);
			
			Long userId = tokenVO.getUserId();
			
			userVO = userService.findUserById(userId);
			logger.info("userVO : "+ userVO);
			mv.addObject("user" , userVO);
		}
		return mv;
	}
	
	@GetMapping("/signup")
	public ModelAndView signup() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("signup");
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	@GetMapping("/post/detail/{id}")
	public ModelAndView postDetail(@PathVariable("id") Long id, @CookieValue(value = "accesstoken", required = false) String accesstoken) throws Exception {
		logger.info("postDetail called");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("detail");
		logger.info("accesstoken : "+ accesstoken);

		if( accesstoken != null ) {
			tokenVO = userService.findTokenByToken(accesstoken);
			
			Long userId = tokenVO.getUserId();
			
			userVO = userService.findUserById(userId);
			logger.info("userVO : "+ userVO);
			mv.addObject("user" , userVO);
		}
		return mv;
	}
}
