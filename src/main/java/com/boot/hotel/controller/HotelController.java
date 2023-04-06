package com.boot.hotel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.service.HotelService;

@RestController
public class HotelController {
	
	@Resource
	private HotelService hotelService;
	
	@GetMapping("/")
	public ModelAndView index() throws Exception{
		
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
	@GetMapping("/login")
	public ModelAndView login() throws Exception{
		System.out.println("로그인창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/login");
		
		return mav;
		
	}
	
	@GetMapping("/login/register")
	public ModelAndView register() throws Exception{
		System.out.println("회원가입창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/register");
		
		return mav;
		
	}
	
	@PostMapping("/login/register_ok")
	public ModelAndView register_ok(MemberDTO dto,HttpServletRequest request) throws Exception{
		//반환값은 MVC로
		System.out.println("회원가입 컨트롤러 진입");
		
		ModelAndView mav = new ModelAndView();
		
		int maxNum = hotelService.maxNum();
		
		dto.setNum(maxNum + 1);
		
		hotelService.insertDataMember(dto);
		
		System.out.println("회원가입 성공!!");
		
		mav.setViewName("redirect:/login");
		
		return mav;
		
	}
	
}
