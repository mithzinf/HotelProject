package com.boot.hotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HotelController {
	
	@GetMapping("/")
	public ModelAndView index() throws Exception{
		
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
	@GetMapping("/login")
	public ModelAndView login() throws Exception{
		
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/login");
		
		return mav;
		
	}
	
	@GetMapping("/login1")
	public ModelAndView login1() throws Exception{
		
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/html1");
		
		return mav;
		
	}
	
}
