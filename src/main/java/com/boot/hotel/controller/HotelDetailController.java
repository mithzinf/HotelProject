package com.boot.hotel.controller;

import java.math.BigDecimal;
import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.mapper.ReviewMapper;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.HotelDetailService;
import com.boot.hotel.service.ReviewService;
import com.boot.hotel.util.MyUtil2;
import com.boot.hotel.util.MyUtil4;
@ResponseBody
@RestController
public class HotelDetailController {
   
   @Resource
   private HotelDetailService hotelDetailService;
   
   @Autowired
   private HttpSession httpSession;
   
   @Resource
   private ReviewService reviewService;
   
   @Autowired
   private ReviewMapper reviewMapper;
	
   @Autowired
   private MyUtil2 myUtil2;
   
   @Autowired
   private MyUtil4 myUtil4;
   
   
    @RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView detail(@RequestParam("hotel_id") int hotel_id, HttpServletRequest request) throws Exception {
      
    	System.out.println("디테일 컨트롤러 진입");
    	
    	String pageNum = request.getParameter("pageNum");
    	String searchValue = request.getParameter("searchValue");
    	
		String check_in_list = request.getParameter("check_in");
		String check_out_list = request.getParameter("check_out");
		
		//체크인, 체크아웃 시간을 받아 알맞은 형태로 바꾸고 변수에 저장
		LocalDate check_in_day = LocalDate.parse(check_in_list, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out_list, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 
		//숙박일수 계산
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		//아래 날짜에 따른 만실 체크를 위해 미리 준비
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int standard = 0;
		int sweet = 0;
		int deluxe = 0;
		String standardJudge = "";
		String sweetJudge = "";
		String deluxeJudge = "";
		
		Map<String, Object> resultMap1 = new HashMap<String, Object>();

		//스탠다드룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			standard = hotelDetailService.searchDayStandard(paramMap2);
			
			
			if(standard>4) {
				standardJudge = "만실";
				break;
			}else if(standard == 4) {
				standardJudge = "만실 임박";
			}else {
				if(standardJudge!="만실 임박") {
					standardJudge = "여유";
				}
			}
		}
		
		//스위트룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			sweet = hotelDetailService.searchDaySweet(paramMap2);
			
			if(sweet>4) {
				sweetJudge = "만실";
				break;
			}else if(sweet == 4) {
				sweetJudge = "만실 임박";
			}else {
				if(!sweetJudge.equals("만실 임박")) {
					sweetJudge = "여유";
				}
			}
		}
		
		//디럭스룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			deluxe = hotelDetailService.searchDayDeluxe(paramMap2);
			
			if(deluxe>4) {
				deluxeJudge = "만실";
				break;
			}else if(deluxe == 4) {
				deluxeJudge = "만실 임박";
			}else {
				if(deluxeJudge!="만실 임박") {
					deluxeJudge = "여유";
				}
			}
		}
			
			
		
		//잔여객실 체크후 각방의 상태를 json형태로 담아서 보낼 준비
		if(standardJudge.equals("만실")) {
			resultMap1.put("standard", "만실");
		}
		else if(standardJudge.equals("만실 임박")){
			resultMap1.put("standard", "만실 임박");
		}else {
			resultMap1.put("standard", "여유");
		}
		
		if(sweetJudge.equals("만실")) {
			resultMap1.put("sweet", "만실");
		}
		else if(sweetJudge.equals("만실 임박")){
			resultMap1.put("sweet", "만실 임박");
		}else {
			resultMap1.put("sweet", "여유");
		}
		
		if(deluxeJudge.equals("만실")) {
			resultMap1.put("deluxe", "만실");
		}
		else if(deluxeJudge.equals("만실 임박")){
			resultMap1.put("deluxe", "만실 임박");
		}else {
			resultMap1.put("deluxe", "여유");
		}
		
		 
		List<HotelDTO> dto1 = hotelDetailService.getHotelById(hotel_id);
		List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id);
		List<HotelFacilityDTO> dto3 = hotelDetailService.getHotelFacilityById(hotel_id);
		List<HotelFacilityInDTO> dto4 = hotelDetailService.getHotelFacilityInById(hotel_id);
		//List<HotelPictureDTO> searchHotelDetail = hotelDetailService.searchHotelDetail(paramMap);
		List<HotelPictureDTO> getTitle = hotelDetailService.getTitlePicture(hotel_id); //타이틀 사진 갖구와...
		List<HotelPictureDTO> getStandard = hotelDetailService.getStandardPicture(hotel_id); //스탠다드 사진 가지구 와..
		List<HotelPictureDTO> getDeluxe = hotelDetailService.getDeluxePicture(hotel_id); //디럭스 사진 갖고오렴..
		List<HotelPictureDTO> getSweet = hotelDetailService.getSweetPicture(hotel_id); //스위트룸 사진 가지구 와..
		  
		
		//================리뷰자리======================
		
		String pageNum1 = request.getParameter("pageNum1");
		
		int currentPage = 1;
		
		if(pageNum1!=null){ //넘어오는 페이지 번호가 있다면
			currentPage = Integer.parseInt(pageNum1);
		}
		
		
		int dataCount = reviewService.getReviewDataCount(hotel_id);
		
	
		
		int numPerPage = 4;
		int totalPage = myUtil2.getPageCount(numPerPage, dataCount);
		
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		

		
		int start = (currentPage-1)*numPerPage+1; 
		int end = currentPage*numPerPage;
		
		
	
		
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);  
        params.put("hotel_id",hotel_id);
        
        List<ReviewDTO> reviewList = reviewService.getReadReviewList(params);
        
     
		
		String listUrl = "detail";
		
		listUrl += "?hotel_id=" + hotel_id;
		
		if (pageNum != null) {
			listUrl += "&pageNum=" + pageNum;
        }
		
		if (searchValue != null && !searchValue.equals("")) {
			listUrl += "&searchValue=" + searchValue;
        }else {
        	listUrl += "&searchValue=";
        }
		
		listUrl += "&check_in=" + check_in_list + "&check_out=" + check_out_list;
		
		String pageIndexList = 
				myUtil4.pageIndexList(currentPage, totalPage, listUrl);
				
		Map<String, Object> map1 = reviewMapper.searchReviewAvg(hotel_id);
		
		double avg = ((BigDecimal) map1.get("AVG")).doubleValue();
		int count = ((BigDecimal) map1.get("COUNT")).intValue();
		
			
		//===============리뷰끝==================
		
    	
		ModelAndView mav = new ModelAndView();
		if(httpSession.getAttribute("sessionUser")!=null) {
        	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        	mav.addObject("user_id",sessionUser.getId());
        }
		
		//리뷰 데이터
		mav.addObject("pageIndexList",pageIndexList);
		mav.addObject("dataCount",dataCount);
		mav.addObject("reviewList",reviewList);
		mav.addObject("pageNum1",pageNum1);
		mav.addObject("avg",avg);
		mav.addObject("count",count);
		
		mav.addObject("listUrl", listUrl);
		mav.addObject("resultMap1",resultMap1);
		mav.addObject("check_in_day",check_in_day);
		mav.addObject("check_out_day",check_out_day);
		mav.addObject("hotel_id", hotel_id);
		mav.addObject("dto1", dto1);
		mav.addObject("dto2", dto2);
		mav.addObject("dto3", dto3);
		mav.addObject("dto4", dto4);
		mav.addObject("getTitle", getTitle);
		mav.addObject("getStandard", getStandard);
		mav.addObject("getDeluxe", getDeluxe);
		mav.addObject("getSweet", getSweet);
		mav.setViewName("hotel/detail");
			
		return mav;
			 
	}

    

    @RequestMapping(value = "/reviews", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> reviewPage (@RequestParam("hotel_id") int hotel_id,
			@RequestParam("listUrl") String listUrl, HttpServletRequest request) throws Exception {
    	
    	System.out.println("리뷰 작성 컨트롤러 진입");
    	
  
    	
		int score = Integer.parseInt(request.getParameter("score"));
		String context = request.getParameter("context");

		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		String userid = sessionUser.getId();
		String username = sessionUser.getName();

		ReviewDTO dto = new ReviewDTO();

		int maxNum = reviewService.maxNum();

		dto.setReview_num(maxNum + 1);
		dto.setUserid(userid);
		dto.setHotel_id(hotel_id);
		dto.setScore(score);
		dto.setContext(context);
		dto.setUsername(username);
		
		reviewService.insertReviewData(dto);
		
		System.out.println("리뷰 작성 처리 완료!!");
		
		
		int currentPage = 1;
		
		int dataCount = reviewService.getReviewDataCount(hotel_id);
		
		int numPerPage = 4;
		int totalPage = myUtil2.getPageCount(numPerPage, dataCount);
	
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1; 
		int end = currentPage*numPerPage;
		
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);  
        params.put("hotel_id",hotel_id);
        
        List<ReviewDTO> reviewList = reviewService.getReadReviewList(params);

		String pageIndexList = 
				myUtil4.pageIndexList(currentPage, totalPage, listUrl);
		
		
		Map<String, Object> reviewData = new HashMap<String, Object>();
		
		reviewData.put("pageIndexList", pageIndexList);
		reviewData.put("reviewList", reviewList);
		reviewData.put("dataCount", dataCount);
		
		return reviewData;

	}
 
    
    @GetMapping("/reviewPaging")
    public Map<String, Object> reviewPaging(@RequestParam("pageNum1") String pageNum1,
    		@RequestParam("hotel_id") int hotel_id, @RequestParam("listUrl") String listUrl,
    		HttpServletRequest request) {
        Map<String, Object> pagingData = new HashMap<>();
		
        System.out.println("리뷰 페이징 컨트롤러 진입");
        
		int currentPage = 1;
		
		if(pageNum1!=null){ //넘어오는 페이지 번호가 있다면
			currentPage = Integer.parseInt(pageNum1);
		}
		
		int dataCount = reviewService.getReviewDataCount(hotel_id);
		
		int numPerPage = 4;
		int totalPage = myUtil2.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage) {
			currentPage = totalPage;
		}
		
		int start = (currentPage-1)*numPerPage+1; 
		int end = currentPage*numPerPage;
		
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);  
        params.put("hotel_id",hotel_id);
        
        List<ReviewDTO> reviewList = reviewService.getReadReviewList(params);
		
		
		String pageIndexList = 
				myUtil4.pageIndexList(currentPage, totalPage, listUrl);
		
		pagingData.put("dataCount", dataCount);
		pagingData.put("reviewList", reviewList);
		pagingData.put("pageIndexList", pageIndexList);
        
        return pagingData;
        
    }
    
    
    
    
		   
	@ResponseBody
	@RequestMapping(value = "/detailDay", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> detailDay(@RequestParam String check_in, @RequestParam String check_out,
			@RequestParam int hotel_id) throws Exception {
   
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	   
		//체크인, 체크아웃 시간을 받아 알맞은 형태로 바꾸고 변수에 저장
		LocalDate check_in_day = LocalDate.parse(check_in, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		//숙박일수 계산
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		//아래 날짜에 따른 만실 체크를 위해 미리 준비
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int standard = 0;
		int sweet = 0;
		int deluxe = 0;
		String standardJudge = "";
		String sweetJudge = "";
		String deluxeJudge = "";
		
		
		//스탠다드룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			standard = hotelDetailService.searchDayStandard(paramMap2);
			
			
			if(standard>4) {
				standardJudge = "만실";
				break;
			}else if(standard == 4) {
				standardJudge = "만실 임박";
			}else {
				if(standardJudge!="만실 임박") {
					standardJudge = "여유";
				}
			}
		}
		
		//스위트룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			sweet = hotelDetailService.searchDaySweet(paramMap2);
			
			if(sweet>4) {
				sweetJudge = "만실";
				break;
			}else if(sweet == 4) {
				sweetJudge = "만실 임박";
			}else {
				if(sweetJudge!="만실 임박") {
					sweetJudge = "여유";
				}
			}
		}
		
		//디럭스룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			deluxe = hotelDetailService.searchDayDeluxe(paramMap2);
			
			if(deluxe>4) {
				deluxeJudge = "만실";
				break;
			}else if(deluxe == 4) {
				deluxeJudge = "만실 임박";
			}else {
				if(deluxeJudge!="만실 임박") {
					deluxeJudge = "여유";
				}
			}
		}
			
			
		
		//잔여객실 체크후 각방의 상태를 json형태로 담아서 보낼 준비
		if(standardJudge.equals("만실")) {
			resultMap.put("standard", "만실");
		}
		else if(standardJudge.equals("만실 임박")){
			resultMap.put("standard", "만실 임박");
		}else {
			resultMap.put("standard", "여유");
		}
		
		if(sweetJudge.equals("만실")) {
			resultMap.put("sweet", "만실");
		}
		else if(sweetJudge.equals("만실 임박")){
			resultMap.put("sweet", "만실 임박");
		}else {
			resultMap.put("sweet", "여유");
		}
		
		if(deluxeJudge.equals("만실")) {
			resultMap.put("deluxe", "만실");
		}
		else if(deluxeJudge.equals("만실 임박")){
			resultMap.put("deluxe", "만실 임박");
		}else {
			resultMap.put("deluxe", "여유");
		}

		   
		return resultMap;
	   
   }

   

   
}




