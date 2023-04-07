package com.boot.hotel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.service.MemberService;

@RestController
public class MemberController {
	
	@Resource
	private MemberService memberService;
	
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
		
		int maxNum = memberService.maxNum();
		
		dto.setNum(maxNum + 1);
		
		memberService.insertDataMember(dto);
		
		System.out.println("회원가입 성공!!");
		
		mav.setViewName("redirect:/login");
		
		return mav;
		
	}

	@GetMapping("/login/idcheck1")
	public ModelAndView register11() throws Exception{
		System.out.println("중복체크 창 띄우기");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/idcheck");
		
		return mav;
		
	}
	
	@PostMapping("/login/check")
	public String idcheck(@RequestParam String id) throws Exception{
		System.out.println("중복체크 확인");
		System.out.println(id);
		
		return id + "는 사용 가능한 아이디입니다.";
		
	}
	
	
	
}
