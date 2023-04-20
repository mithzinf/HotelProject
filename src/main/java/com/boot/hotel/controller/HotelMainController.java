package com.boot.hotel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.service.HotelMainService;

@RestController
public class HotelMainController {
	
	@Resource
	private HotelMainService hotelMainService;

	//호텔 메인페이지 진입
	@GetMapping("/hotel/main")
	public ModelAndView main() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> paramMap = new HashMap<>();
		int[] hotel_id = new int[8];
		for(int i=0;i<8;i++) {
			hotel_id[i] = 1 + i*6;
		}
		
		List<Map<String, Object>> mainHotel = new ArrayList<>();
		for(int i = 0; i < hotel_id.length; i++) {
		    List<Map<String, Object>> hotelData = hotelMainService.getHotelMain(hotel_id[i]);
		    mainHotel.addAll(hotelData);
		}
		
		
		
		mav.addObject("mainHotel",mainHotel);
		mav.setViewName("hotel/hotelMain");
		
		return mav;
	}
	
	
	
	
	//메인페이지(hotelMain.html)에서 검색시 여기로 진입
	@GetMapping("/hotel/test11")
	public ModelAndView test1(@RequestParam String searchValue) throws Exception {
		//검색값을 requestParam으로 받아서
		
		
		System.out.println("검색 테스트 컨트롤러 진입");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("searchValue", searchValue);
		//그걸 paramMap에 담음
		
		//검색값 결과를 찾는 sql문 호출하고 반환값을 result란 리스트에 저장
		//우리는 searchValue값만 입력하고, searchKey는 SQL문 안에서 임의로 지정함
		//=> 따라서 컨트롤러에 searchKey가 없다! 우린 searchKey값이 고정되어 있기 때문에..
		//검색값에 해당하는 hotel테이블의 hotel_name과 addr1을 동시에 뒤지게 됨
		List<Map<String, Object>> result = hotelMainService.searchMainHotelTest(paramMap);
		
		System.out.println(result.size()); //결과값 개수 출력
		System.out.println(result); //결과값 전체 출력
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("result",result); //이동할 html로 result담아서 보냄
		mav.setViewName("hotel/test"); //test.html로 이동
		
		return mav;
	}
	
	
	
	
	
}
