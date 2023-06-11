package com.boot.hotel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.NoticeBoardDTO;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.NoticeService;
import com.boot.hotel.util.MyUtil2;

@RestController
public class HotelNoticeController {

		@Autowired
		private HttpSession httpSession;

	
		@Resource
		private NoticeService noticeService;
		
		@Autowired
		MyUtil2 myUtil2;

	   
	   //1. 공지사항 글목록 페이지 띄우는 메소드 notice()
		@GetMapping("notice/noticeList")
	   public ModelAndView notice(HttpServletRequest request) throws Exception {
			ModelAndView mav = new ModelAndView();
			
			SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
			System.out.println("공지사항 리스트 진입");
			
			String pageNum = request.getParameter("pageNum");
			
			
			//페이징 처리
			int currentPage = 1;
			
			if(pageNum!=null) {
				currentPage = Integer.parseInt(pageNum);
			}
			
			int dataCount = noticeService.getListsCount();
			int numPerPage = 5;
			int totalPage = myUtil2.getPageCount(numPerPage, dataCount);
			
			if(currentPage > totalPage) {
				currentPage = totalPage;
			}
						
			int start = (currentPage-1) * numPerPage+1;
			int end = currentPage*numPerPage;
			
			Map<String,Object> params = new HashMap<>();
			
			params.put("start",start);
			params.put("end",end);
			
			List<NoticeBoardDTO> lists = noticeService.getNoticeList(params);
			
			String listUrl = "/notice/noticeList";
			String pageIndexList = myUtil2.pageIndexList(currentPage, totalPage, listUrl);
			String articleUrl = "/notice/noticeArticle?pageNum=" + currentPage;
			
			mav.addObject("lists", lists);
			mav.addObject("pageIndexList", pageIndexList);
			mav.addObject("dataCount", dataCount);
			mav.addObject("pageNum", pageNum);
			mav.addObject("articleUrl", articleUrl);
			mav.addObject("sessionUser", sessionUser);
			System.out.println("Lists 출력 결과 : "+lists);
		   mav.setViewName("notice/noticeList"); //templates.notice의 noticeList.html로 가라
		   return mav;
		   
	   }
	   
	   
	   
	  //공지사항 글 작성 메소드
		@GetMapping("notice/noticeInsert")
	   public ModelAndView insert(NoticeBoardDTO dto) throws Exception {
		   
		   ModelAndView mav = new ModelAndView();
		   System.out.println("공지사항 작성글 진입");
		   
		   SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
			// String userid = "";
			 
			 //sessionUser가 null이거나 admin이 아닌 경우 - 걍 로그인창으로 꺼지세요ㅠㅠ
			 if(sessionUser==null || !"admin".equals(sessionUser.getId())) {
				 mav.setViewName("/login/login");
				return mav;
			 }
				
			 //sessionUser가 admin인 경우!! noticeInsert.html으로 가렴
			 dto.setUserid(sessionUser.getId());
		   mav.setViewName("notice/noticeInsert");
		   return mav;
	   }
		
		
	   
	//공지사항 글 작성 create_ok(실질적으로 데이터 넘어가는 메소드)
	 @PostMapping("notice/noticecreate_ok")
	 public ModelAndView insert_ok(NoticeBoardDTO dto, @RequestParam String subject0,HttpServletRequest request) throws Exception{
		 ModelAndView mav = new ModelAndView();
		 System.out.println("공지사항 작성글 진입 - noticecreate_ok");
	
		 SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		 String userid="";
		 userid = sessionUser.getId();
		 String username="";
		 username = sessionUser.getName();
		 
		 int maxNoticeNum = noticeService.maxNoticeNum();
		 
		 
		 //줄바꿈
		 String context = dto.getContext();
		 
		 String replaced = context.replaceAll("\r\n", "<br/>");
			
		 dto.setContext(replaced);
			
			
		 
		 dto.setUserid(userid);
		 dto.setUsername(username);
		 dto.setSubject(subject0);
		 //dto.setContext(context);
		 dto.setNotice_num(maxNoticeNum+1);
		 
		 noticeService.insertNotice(dto);
		 System.out.println("작성글" + dto);
		 System.out.println(context);
		 System.out.println("replaced" +replaced);
		 mav.setViewName("redirect:/notice/noticeList");
		 return mav;
		 
	 }
	 
	 
	 
	 //공지사항 article 진입
	 @RequestMapping(value = "/notice/noticeArticle", method = { RequestMethod.GET, RequestMethod.POST })
	 public ModelAndView noticeArticle(HttpServletRequest request,NoticeBoardDTO notDTO) throws Exception {
		 
		 System.out.println("공지사항 article 진입");
		 
		 int num = Integer.parseInt(request.getParameter("num"));
		 String pageNum = request.getParameter("pageNum");
		 
		 noticeService.updateHitCount(num); //조회수 올리렴
		 
		 //게시글 조회
		 NoticeBoardDTO dto = noticeService.getReadNotice(num);
		 
		 SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		

		 
		 if(dto == null) {
			 
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("redirect:/noticeArticle?PageNum=" + pageNum);
			 
			 return mav;
		 }
		 
		 //--------------------------------
		 
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("dto", dto);
		 mav.addObject("notice_num", num);
		 mav.addObject("pageNum", pageNum);
		 mav.addObject("sessionUser", sessionUser);
		 
		 mav.setViewName("notice/noticeArticle");
		 return mav;
		 
	 }
	 
	 
	 
	//공지사항 글 수정
	 @GetMapping("notice/noticeUpdate")
	 public ModelAndView noticeUpdate(HttpServletRequest request) throws Exception {
		 
		 
		 
		 System.out.println("공지사항 글 수정 진입");
		 int num = Integer.parseInt(request.getParameter("num"));
		 String pageNum = request.getParameter("pageNum");
		 
		 NoticeBoardDTO dto = noticeService.getReadNotice(num);
		 
		 if(dto==null) {
			 ModelAndView mav = new ModelAndView();
			 mav.setViewName("redirect:/notice/noticeList?pageNum=" + pageNum);
		 }
		 
		 
		 //-----------------------------------------------------------------
		 
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("dto", dto);
		 mav.addObject("pageNum", pageNum);
		
		 
		 mav.setViewName("notice/noticeUpdate");
		 return mav;
	 }
	 
	 
	 
	 //공지사항 글 수정 noticeUpdate_ok
	 @PostMapping("/noticeUpdate_ok")
	 public ModelAndView noticeUpdate_ok(NoticeBoardDTO dto, HttpServletRequest request) throws Exception {
		 
		 System.out.println("공지사항 update_ok 수정페이지 진입");
		 
		 String pageNum = request.getParameter("pageNum");
		 
		 noticeService.updateNotice(dto);
		 
		 
		 ModelAndView mav = new ModelAndView();
		 
		 
		 mav.setViewName("redirect:/notice/noticeList");
		 
		 return mav;
		
	 }
	
	 
	 //공지사항 글 삭제 deleted_ok
	 @GetMapping("/notice/deleted_ok")
	 public ModelAndView deleted_ok(HttpServletRequest request) throws Exception {
		 System.out.println("공지사항 게시글 삭제 기능 진입");
		 
		 int num = Integer.parseInt(request.getParameter("num"));
		 
		 String pageNum = request.getParameter("pageNum");
		 
		 noticeService.deleteNotice(num);
		 
		 ModelAndView mav = new ModelAndView();
		 
		 mav.setViewName("redirect:/notice/noticeList");
		 
		 return mav;
		 
	 }
	 
	 
	 
}
