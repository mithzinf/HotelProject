package com.boot.hotel.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.service.HotelInfoService;
import com.nimbusds.oauth2.sdk.Request;

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

	
	@GetMapping("/hotel/hotellist1")
	public ModelAndView list1(HotelInfoDTO dto) throws Exception{
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("hotel/hotelSearchList");

	    int maxNum = hotelInfoService.maxNum();

	    // setNum 대신 setHotelId 사용
	    dto.setHotel_id(maxNum + 1);


	    hotelInfoService.getReadList(dto);

	    mav.setViewName("redirect:/list.action");

	    return mav;
	}

}
