package com.boot.hotel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.MemberService;
import com.boot.hotel.service.MyPageService;
import com.boot.hotel.util.MyUtil;

@RestController
@ResponseBody
public class MyPageController {

	@Autowired
	private HttpSession httpSession;
	
	@Resource
	private MyPageService myPageService;
	
	@Resource
	private MemberService memberService;
	
	@Autowired
    private MyUtil myUtil;
	
	//마이페이지 진입
	@RequestMapping(value = "/mypage/mypage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView mypage() throws Exception{
		
		System.out.println("마이페이지 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		MemberDTO dto = memberService.getReadDataMember(sessionUser.getId());
		
		String[] email = dto.getEmail().split("@");
		dto.setEmail1(email[0]);
		dto.setEmail2(email[1]);
		
		String[] tel = dto.getTel().split("-");
		dto.setTel1(tel[0]);
		dto.setTel2(tel[1]);
		dto.setTel3(tel[2]);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);
		mav.setViewName("mypage/mypage");
		
		return mav;
		
	}
	
	
	//회원 정보 수정 페이지 진입
	@RequestMapping(value = "/mypage/update", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView update() throws Exception{
		
		System.out.println("회원 정보 수정 페이지 컨트롤러 진입");
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		MemberDTO dto = memberService.getReadDataMember(sessionUser.getId());
		
		String[] email = dto.getEmail().split("@");
		dto.setEmail1(email[0]);
		dto.setEmail2(email[1]);
		
		String[] tel = dto.getTel().split("-");
		dto.setTel1(tel[0]);
		dto.setTel2(tel[1]);
		dto.setTel3(tel[2]);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);
		
		System.out.println(dto.getAuth());
		
		if(dto.getAuth().equals("소셜회원")) {
			
			mav.setViewName("mypage/update_oauth");
			
			return mav;
			
		}
		
		mav.setViewName("mypage/update");
		
		return mav;
		
	}
	
	
	//일반 회원정보 수정 처리
	@PostMapping("/mypage/update_ok")
	public ModelAndView update_ok(MemberDTO dto) throws Exception{
		
		System.out.println("일반 회원정보 수정 처리 컨트롤러 진입");
		
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		dto.setUserid(sessionUser.getId());
		
		myPageService.memberUpdate(dto);
		
		System.out.println("일반 회원정보 수정 성공!!");
		
		mav.setViewName("redirect:/mypage/mypage");
		
		return mav;
		
	}
	
	//회원정보 수정 처리
	@PostMapping("/mypage/update_ok_oauth")
	public ModelAndView update_ok_oauth(MemberDTO dto) throws Exception{
		
		System.out.println("소셜 회원정보 수정 처리 컨트롤러 진입");
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		dto.setUserid(sessionUser.getId());
		
		myPageService.memberUpdateOauth(dto);
		
		System.out.println("소셜 회원정보 수정 성공!!");
		
		mav.setViewName("redirect:/mypage/mypage");
		
		return mav;
		
	}
	
	
	//회원탈퇴 페이지 진입
	@RequestMapping(value = "/mypage/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView delete() throws Exception{
		
		System.out.println("회원탈퇴 컨트롤러 진입");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("mypage/delete");
		
		return mav;
		
	}
	
	
	//회원탈퇴 처리
	@RequestMapping(value = "/mypage/delete_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete_ok() throws Exception{
		
		System.out.println("회원탈퇴 처리 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		myPageService.memberDelete(sessionUser.getId());
		
		httpSession.invalidate();
		
		return "delete";
		
	}
	
	
	//마이페이지 진입
	@RequestMapping(value = "/mypage/mypage_test", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView mypaget() throws Exception{
		
		System.out.println("마이페이지 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		MemberDTO dto = memberService.getReadDataMember(sessionUser.getId());
		
		String[] email = dto.getEmail().split("@");
		dto.setEmail1(email[0]);
		dto.setEmail2(email[1]);
		
		String[] tel = dto.getTel().split("-");
		dto.setTel1(tel[0]);
		dto.setTel2(tel[1]);
		dto.setTel3(tel[2]);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);
		mav.setViewName("mypage/mypage_test");
		
		return mav;
		
	}
	
	//======================================================================================
	
	//호텔 찜 목록 진입
	@RequestMapping(value = "/mypage/basket", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView basket(HttpServletRequest request) throws Exception{
		
		System.out.println("호텔 찜 목록 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null){ //넘어오는 페이지 번호가 있다면
			currentPage = Integer.parseInt(pageNum);
		}
		
		List<Map<String, Object>> hotel_id_list = myPageService.searchBasket(sessionUser.getId());
		
		int dataCount = hotel_id_list.size();
				
		int numPerPage = 4;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1; 
		int end = currentPage*numPerPage;
		
		System.out.println(start + ":" + end);
		
		List<Map<String, Object>> basket = new ArrayList<>();
		
		for(Map<String,Object> i: hotel_id_list ) {
			
			int hotel_id = Integer.parseInt(i.get("HOTEL_ID").toString());
		    
			List<Map<String, Object>> basketData = myPageService.searchBasketHotel(hotel_id);
			basket.addAll(basketData);
		}
		
		start -= 1;
		
		if(dataCount<end) {
			end=dataCount;
		}
		
		List<Map<String, Object>> pagedBasket = new ArrayList<>();
		for (int i = start; i < end; i++) {
			pagedBasket.add(basket.get(i));
		}
		
		
		System.out.println(pagedBasket);

		String listUrl = "mypage/basket";
		
		String pageIndexList = 
				myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("pageNum",currentPage);
		mav.addObject("dataCount",dataCount);
		mav.addObject("pagedBasket", pagedBasket);
		mav.setViewName("mypage/basket");
		
		return mav;
		
	}
	
	
	@RequestMapping(value = "/mypage/text11", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView text11() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("mypage/text11");
		
		return mav;
	
	}

	
	
}
