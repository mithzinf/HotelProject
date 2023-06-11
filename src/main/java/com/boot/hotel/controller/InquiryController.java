package com.boot.hotel.controller;

import com.boot.hotel.util.MyUtil3;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.InquiryService;
import com.boot.hotel.service.PaymentService;

@RestController
public class InquiryController {
	
	@Resource
	private InquiryService inquiryService;
	
	@Resource
	private PaymentService paymentService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	MyUtil3 myUtil3;
	
	//문의 리스트 출력 메소드
	@GetMapping("inquiry/inquiryList")
	public ModelAndView inquiryList(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		System.out.println("문의 리스트 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
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
		int numPerPage = 10;
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
	
	
	
	//문의글 작성 메소드 진입
	@GetMapping("inquiry/inquiryCreate")
	public ModelAndView Create(Model model) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		System.out.println("문의글 작성 컨트롤러 진입");
		
		String userid = null;
		if(httpSession.getAttribute("sessionUser")!=null) {
        	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        	userid = sessionUser.getId();
        }
		
		
		inquiryService.getUserInfo(userid);
		
		
		List<Map<String, Object>> userInfo = paymentService.getUserInfo(userid);
		Object username = userInfo.get(0).get("USERNAME");
		Object tel = userInfo.get(0).get("TEL");
		Object email = userInfo.get(0).get("EMAIL");
		
		mav.addObject("userid",userid);
		mav.addObject("username",username);
		mav.addObject("tel",tel);
		mav.addObject("email",email);
		
		mav.setViewName("inquiry/inquiryCreate");
		return mav;
	}
	
	
	
	//문의글 작성 처리 메소드
	@PostMapping("/inquirycreate_ok")
	public ModelAndView Create_ok(HttpServletRequest request, HotelInquiryDTO dto, @RequestParam String subject1) throws Exception{
		
		System.out.println("문의글 작성 처리 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		ModelAndView mav = new ModelAndView();
		int maxInqNum = inquiryService.maxInqNum();
		
		
		String context = request.getParameter("context1");
		
		String replaced = context.replaceAll("\r\n", "<br/>");
		
		dto.setContext(replaced);
		
		
		dto.setSubject(subject1);
		dto.setInq_num(maxInqNum+1);
		
		inquiryService.insertInquiry(dto);
		
		mav.setViewName("redirect:/inquiry/inquiryList");
		return mav;
	}
	
	
	//문의글 아티클 메소드 진입
	@RequestMapping(value = "/inquiry/inquiryArcitle",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView inquiryArcitle(HttpServletRequest request,
			HotelInquiryDTO hotDto) throws Exception{
		
		System.out.println("문의게시판 아티클 컨트롤러 진입");
		ModelAndView mav = new ModelAndView();
		
		String userid = null;
		if(httpSession.getAttribute("sessionUser")!=null) {
        	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        	userid = sessionUser.getId();
        }
		
		
		
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
			mav.setViewName("redirect:/inquiryArcitle?pageNum=" + pageNum);
			
			return mav;
		}
		
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		
		
		
		// -------------------------------------------------------
		
		List<HotelInquiryDTO> anwLists = inquiryService.getAnswerList(num);
		
		mav.addObject("anwLists",anwLists);
		
		// -------------------------------------------------------
		
		mav.addObject("userid",userid);
		mav.addObject("hotDto", hotDto);
		mav.addObject("dto", dto);
		mav.addObject("inq_num", num);
		mav.addObject("params", param);
		mav.addObject("pageNum", pageNum);
		
		mav.setViewName("inquiry/inquiryArcitle");
		
		return mav;
		
	}
	
	
	//문의글 답변 처리 메소드 진입
	@RequestMapping(value = "/inquiryAnswer",
			method = { RequestMethod.GET, RequestMethod.POST })
	public void inquiryAnswer(AnswerBoardDTO ansDto, HotelInquiryDTO inqDto, HttpServletRequest request
			) throws Exception{
		
		System.out.println("문의게시판 답변 처리 컨트롤러 진입");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		String userId = sessionUser.getId();
		
		
		
		String content = request.getParameter("content");
		
		String replaced = content.replaceAll("\r\n", "<br/>");
		
		inqDto.setContext(replaced);
		
		
		
		
		int num = Integer.parseInt(request.getParameter("inq_num"));
		
		int maxAnsNum = inquiryService.maxAnsNum();
		
		ansDto.setAns_num(maxAnsNum+1);
		
		inquiryService.insertAnswer(ansDto);
		
		inquiryService.updateAnsweredList(num);
		
		inqDto.setAnswer(maxAnsNum+1);
		
		
		
		
		return;
		
	
	}
	
	
	//문의게시판 답변 리스트 출력 메소드
	@RequestMapping(value = "/getAnwLists",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getAnwLists(AnswerBoardDTO dto, HttpServletRequest request
			) throws Exception{
		
		System.out.println("문의게시판 답변 리스트 컨트롤러 진입 ");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		ModelAndView mav = new ModelAndView();
		
		int num = Integer.parseInt(request.getParameter("inq_num"));
		
		
		int getAnswerList = inquiryService.getAnsOnly(num);
		
		
		
		mav.addObject("getAnswerList",getAnswerList);
		
		return mav;
		
	
	}
	
    
	
	
	//문의게시판 삭제 메소드
	@GetMapping("/deleted_ok")
	public ModelAndView deleted_ok(HttpServletRequest request, AnswerBoardDTO ansBdDto) throws Exception{
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		System.out.println("문의게시판 삭제 컨트롤러 진입");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
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
	
	
	
	
	//문의게시판 수정 페이지 메소드 진입
	@GetMapping("/inquiryUpdate")
	public ModelAndView inquiryUpdate(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");

		System.out.println("문의게시판 수정 페이지 컨트롤러 진입");
		
		
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
		
		
		String param = "num=" + num + "&pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		mav.addObject("num",num);
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		mav.addObject("params", param);
		mav.addObject("searchKey", searchKey);
		mav.addObject("searchValue", searchValue);
		
		mav.setViewName("inquiry/inquiryUpdate");
		return mav;
	}
	
	
	//문의게시판 수정 처리 메소드
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
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		System.out.println("문의게시판 수정 처리 컨트롤러 진입");
		
		String pageNum = request.getParameter("pageNum");
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
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
	
	

	
	
	//문의게시판 답변 삭제 메소드
	@RequestMapping(value = "/answerDelete",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView answerDelete(HttpServletRequest request, AnswerBoardDTO ansBdDto) throws Exception{
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		System.out.println("문의게시판 답변 삭제 처리 컨트롤러 진입");
		
		int ansNum = Integer.parseInt(request.getParameter("ans_num"));
		
		inquiryService.deleteAnswer(ansNum);
		
		ModelAndView mav = new ModelAndView();
		
		int num = Integer.parseInt(request.getParameter("inq_num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		mav.setViewName("redirect:/inquiryArcitle?pageNum=" + pageNum + "?num=" + num);
		
		return mav;
		
	}
	
	
	
	
}
