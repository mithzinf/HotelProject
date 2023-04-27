package com.boot.hotel.controller;

import com.boot.hotel.util.MyUtil3;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.AnswerBoardDTO;
import com.boot.hotel.dto.HotelInquiryDTO;
import com.boot.hotel.service.InquiryService;
import com.boot.hotel.service.PaymentService;

@RestController
public class InquiryController {
	
	@Resource
	private InquiryService inquiryService;
	
	@Resource
	private PaymentService paymentService;
	
	@Autowired
	MyUtil3 myUtil3;
	
	@GetMapping("/blog")
	public ModelAndView blog() throws Exception{
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("inquiry/blog-detail");
		return mav;
	}
	
	@GetMapping("/det")
	public ModelAndView det() throws Exception{
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("inquiry/detail");
		return mav;
	}
	
	
	@GetMapping("inquiry/inquiryList")
	public ModelAndView inquiryList(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		System.out.println("문의 리스트 진입");
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		int currentPage = 1;

		if(pageNum!=null) {
			currentPage = Integer.parseInt(pageNum);
		}
		
		if (searchValue == null || searchValue.equals("")) {
			searchKey = "subject";
	        searchValue = "";
	    }
		
		if (request.getMethod().equalsIgnoreCase("GET") && searchValue != null) {
	        searchValue = URLDecoder.decode(searchValue, "UTF-8");
        }
		
		Map<String, Object> params1 = new HashMap<>();
        params1.put("searchValue", searchValue);
        params1.put("searchKey", searchKey);
		
        int dataCount = inquiryService.getListsCount(params1);
		int numPerPage = 3;
		int totalPage = myUtil3.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
        int start = (currentPage-1)*numPerPage+1;
        int end = currentPage*numPerPage;
        
        params1.put("start", start);
        params1.put("end", end);
        
        List<HotelInquiryDTO> lists = inquiryService.getInqList(params1);
        
        
		String param = "";
		if(searchValue!=null && !searchValue.equals("")) {
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		String listUrl = "/inquiry/inquiryList";
		if(!param.equals("")) {
			listUrl += "?" + param;
		}
        
		String pageIndexList = myUtil3.pageIndexList(currentPage, totalPage, listUrl, searchValue);
		String articleUrl = "/inquiry/inquiryArcitle?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		
		
		mav.addObject("lists", lists);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("dataCount", dataCount);
		mav.addObject("pageNum", currentPage);
		mav.addObject("articleUrl", articleUrl);
		
		mav.setViewName("inquiry/inquiryList");
		return mav;
	}
	
	
	
	// TODO
	// 아래 정보는 세션을 통해 사용자id를 받은 후에 삭제한다
	@GetMapping("inquiry/inquiryCreate")
	public ModelAndView Create(Model model) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		String userid = "suzi";
		String username = "배수지";
		String tel = "010-2222-3333";
		String email = "111@naver.com";
		String category = "호텔";
		
		
		
		mav.addObject("userid",userid);
		mav.addObject("username",username);
		mav.addObject("tel",tel);
		mav.addObject("email",email);
		mav.addObject("category",category);
		
		
		/*
		List<Map<String, Object>> userInfo = paymentService.getUserInfo(userid);
		Object userid = userInfo.get(0).get("USERID");
		Object username = userInfo.get(0).get("USERNAME");
		Object tel = userInfo.get(0).get("TEL");
		Object email = userInfo.get(0).get("EMAIL");
		
		int hotel_id = 1;
		
		List<Map<String, Object>> hotelInfo = paymentService.getHotelInfo(hotel_id);
		Object category = hotelInfo.get(0).get("CATEGORY");
		*/
		
		
		
		mav.setViewName("inquiry/inquiryCreate");
		return mav;
	}
	
	
	
	
	@PostMapping("/inquirycreate_ok")
	public ModelAndView Create_ok(HotelInquiryDTO dto, @RequestParam String subject1) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		int maxInqNum = inquiryService.maxInqNum();
		
		dto.setSubject(subject1);
		dto.setInq_num(maxInqNum+1);
		
		inquiryService.insertInquiry(dto);
		
		mav.setViewName("redirect:/inquiry/inquiryList");
		return mav;
	}
	
//	@GetMapping("/inquiry/inquiryArcitle")
	@RequestMapping(value = "/inquiry/inquiryArcitle",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView inquiryArcitle(HttpServletRequest request,
			HotelInquiryDTO hotDto) throws Exception{
		
		System.out.println("article 진입");
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		inquiryService.updateHitCount(num);
		HotelInquiryDTO dto = inquiryService.getReadData(num);

		if(dto == null) {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/inquiryArcitle?pageNum=" + pageNum);
			
			return mav;
		}
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		
		
		// -------------------------------------------------------
		System.out.println("답변 출력하기");
		
		List<HotelInquiryDTO> anwLists = inquiryService.getAnswerList(num);
		
		mav.addObject("anwLists",anwLists);
		
		// -------------------------------------------------------
		
		
		mav.addObject("dto", dto);
		mav.addObject("inq_num", num);
		mav.addObject("params", param);
		mav.addObject("pageNum", pageNum);
		
		mav.setViewName("inquiry/inquiryArcitle");
		
		return mav;
		
	}
	
	@GetMapping("/answeredList")
	public String answeredList(HttpServletRequest request, Model model) throws Exception {
	    System.out.println("나 보이는교");
	    int num = Integer.parseInt(request.getParameter("num"));
	    List<HotelInquiryDTO> anwLists = inquiryService.getAnswerList(num);
	    model.addAttribute("anwLists", anwLists);
	    return "inquiry/answeredList";
	}
	
    
	
	
	@GetMapping("/deleted_ok")
	public ModelAndView deleted_ok(HttpServletRequest request, AnswerBoardDTO ansBdDto) throws Exception{
		
		System.out.println("문의게시판 삭제하기");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		int i = inquiryService.delAns(num);
		
		inquiryService.deleteData(num);
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/inquiry/inquiryList?" + param);
		
		return mav;
		
	}
	
	
	
	
	@GetMapping("/inquiryUpdate")
	public ModelAndView inquiryUpdate(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		System.out.println("수정할 준비");
		
		String userid = "suzi";
		String username = "배수지";
		String tel = "010-2222-3333";
		String email = "111@naver.com";
		String category = "호텔";
		
		mav.addObject("userid",userid);
		mav.addObject("username",username);
		mav.addObject("tel",tel);
		mav.addObject("email",email);
		mav.addObject("category",category);
		
		
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		HotelInquiryDTO dto = inquiryService.getReadData(num);
		if(dto==null) {
			ModelAndView mav1 = new ModelAndView();
			mav1.setViewName("redirect:/inquiry/inquiryList?pageNum=" + pageNum);
			
			return mav1;
		}
		
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		mav.addObject("params", param);
		mav.addObject("searchKey", searchKey);
		mav.addObject("searchValue", searchValue);
		
		mav.setViewName("inquiry/inquiryUpdate");
		return mav;
	}
	
	@RequestMapping(value = "/inquiryUpdate_ok",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView inquiryUpdate_ok(HotelInquiryDTO dto,
			HttpServletRequest request,
			@RequestParam String inq_cate,
			@RequestParam String username,
			@RequestParam String userid,
			@RequestParam String tel,
			@RequestParam String email,
			@RequestParam String category
			,@RequestParam int inq_num
			) throws Exception{
		
		System.out.println("수정페이지 진입");
		
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		System.out.println(dto.getInq_num() + " 있는가");
		
		inquiryService.updateListData(dto);
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("param",param);
		
		mav.setViewName("redirect:/inquiry/inquiryList?" + param);
		
		return mav;
		
	}
	
	
//	@PostMapping("/inquiryAnswer")
	@RequestMapping(value = "/inquiryAnswer",
			method = { RequestMethod.GET, RequestMethod.POST })
	public void inquiryAnswer(AnswerBoardDTO dto, HttpServletRequest request
			) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		System.out.println("답변 달기 ");
		
		String pageNum = request.getParameter("pageNum");
		String content = request.getParameter("content");
		
		int num = Integer.parseInt(request.getParameter("inq_num"));
		
		dto.setContent(content);
		
		String articleUrl = "/inquiry/inquiryArcitle?pageNum=" + pageNum + "&num=" + num;
		
		int maxAnsNum = inquiryService.maxAnsNum();
		
		dto.setAns_num(maxAnsNum+1);
		
		inquiryService.insertAnswer(dto);
		
		inquiryService.updateAnsweredList(num);
		
		// -------------------------------------------------------
		// List<HotelInquiryDTO> anwLists = inquiryService.getAnswerList(num);
		
		// mav.addObject("anwLists",anwLists);
		
		// -------------------------------------------------------
		
		mav.addObject("articleUrl",articleUrl);
		
		mav.setViewName("/inquiry/inquiryArcitle");
		return;
		
	
	}
	
	
	
	
}
