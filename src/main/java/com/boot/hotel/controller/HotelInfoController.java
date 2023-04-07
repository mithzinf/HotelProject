package com.boot.hotel.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.boot.hotel.service.HotelInfoService;

@RestController
public class HotelInfoController {
	
	@Resource
	private HotelInfoService hotelInfoService;
	
	
	@GetMapping("/main")
	public ModelAndView main() throws Exception{

		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/main");
		
		return mav;
		
	}
	
	@GetMapping("/hotel/list")
	public ModelAndView register() throws Exception{
		System.out.println("회원가입창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/listing");
		
		return mav;
		
	}
	

	
}
