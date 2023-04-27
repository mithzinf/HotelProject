package com.boot.hotel.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.service.NoticeService;
import com.boot.hotel.util.MyUtil2;

@RestController
public class HotelNoticeController {

		@Resource
		private NoticeService noticeService;
		
		@Autowired
		MyUtil2 myUtil2;
		//페이징처리 계산하는 건 언제 myUtil 받아서 하면 되나? 근데 난 검색이 필요 없는데
		//글타네?
	   
	   //1. 공지사항 글목록 페이지 띄우는 메소드 notice()
		@GetMapping("notice/noticeList")
	   public ModelAndView notice() throws Exception {
		   
		   ModelAndView mav = new ModelAndView();
		   
		   
		   mav.setViewName("notice/noticeList"); //templates.notice의 noticeList.html로 가라
		   return mav;
		   
	   }
	   
	   
	   
	  //공지사항 글 작성 메소드
	 
	   public ModelAndView insert() throws Exception {
		   
		   ModelAndView mav = new ModelAndView();
		   
		   
		   mav.setViewName("notice/noticeInsert");
		   return mav;
	   }
	   
	//공지사항 글 작성 create_ok(실질적으로 데이터 넘어가는 메소드)
	 @PostMapping("/noticecreate_ok")
	 public ModelAndView insert_ok() throws Exception{
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 
		 mav.setViewName("redirect:/notice/noticeList");
		 return mav;
		 
	 }
	 
	 
	 
	 //공지사항 article 진입
	 public ModelAndView noticeArticle() throws Exception {
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 mav.setViewName("notice/noticeArticle");
		 
		 return mav;
		 
	 }
	 
	 
	//공지사항 글 수정
	 public ModelAndView noticeUpdate() throws Exception {
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 mav.setViewName("notice/notice");
		 return mav;
	 }
	
}
