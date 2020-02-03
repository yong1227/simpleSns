package com.simple.sns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@GetMapping("/")
	 public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
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
}
