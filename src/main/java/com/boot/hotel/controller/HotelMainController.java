package com.boot.hotel.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.mapper.ReviewMapper;
import com.boot.hotel.service.HotelBasketService;
import com.boot.hotel.service.HotelDetailService;
import com.boot.hotel.service.HotelInfoService;
import com.boot.hotel.service.HotelMainService;
import com.boot.hotel.util.MyUtil;

@RestController
public class HotelMainController {
	
	@Autowired
    private HotelInfoService hotelInfoService;
 
    @Autowired
    private HotelBasketService hotelBasketService;
    
    @Autowired
    private HotelDetailService hotelDetailService;
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Autowired
    private MyUtil myUtil;
	
	@Resource
	private HotelMainService hotelMainService;

	//호텔 메인페이지 진입
	@RequestMapping(value = "/hotel/main", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView main() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		//메인 추천 숙박 목록 띄우기
		int[] hotel_id = new int[8];
		for(int i=0;i<8;i++) {
			hotel_id[i] = 1 + i*6;
		}
		
		List<Map<String, Object>> mainHotel = new ArrayList<>();
		for(int i = 0; i < hotel_id.length; i++) {
		    List<Map<String, Object>> hotelData = hotelMainService.getHotelMain(hotel_id[i]);
		    
		    mainHotel.addAll(hotelData);
		}
		
		double avg = 0;
		int j = 0;
		
		//평점 출력
		for (Map<String, Object> map : mainHotel) {
			
			Map<String, Object> map1 = reviewMapper.searchReviewAvg(hotel_id[j]);
			
			map.put("AVG", map1.get("AVG"));
			map.put("COUNT", map1.get("COUNT"));
			j++;
			
		}
		
		
		//오늘,내일 날짜를 html에 보내기 위해
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());

	    // 내일 날짜로 설정
	    calendar.add(Calendar.DATE, 1);

	    String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		 

		List<Map<String, Object>> reviewData = hotelDetailService.getReviewData();

		System.out.println(reviewData);

		 
		
	    mav.addObject("reviewData",reviewData);
	    
	    mav.addObject("check_in_day", currentDate);
	    mav.addObject("check_out_day", tomorrowDate);
		mav.addObject("mainHotel",mainHotel);
		mav.setViewName("hotel/hotelMain");
		
		return mav;
	}
	
	
	
	
	
	
}
