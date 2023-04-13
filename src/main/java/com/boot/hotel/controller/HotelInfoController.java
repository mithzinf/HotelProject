package com.boot.hotel.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;
import com.boot.hotel.service.HotelInfoService;

@RestController
public class HotelInfoController {
	
	@Resource
	private HotelInfoService hotelInfoService;
	

	
	@GetMapping("/hotel/hotellist")
	public ModelAndView list() throws Exception{

		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("hotel/hotelList");
		
		return mav;
		
	}




}
