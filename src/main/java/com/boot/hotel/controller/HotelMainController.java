package com.boot.hotel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HotelMainController {

	@GetMapping("/main")
	public ModelAndView main(HttpServletRequest request) throws Exception {
		
		ModelAndView  mav = new ModelAndView();
		
		mav.setViewName("hotel/hotelMain");
		
		return mav;
	}
	
	
	
	
	
}
