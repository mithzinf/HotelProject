package com.boot.hotel.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/hotel/main", method = { RequestMethod.GET, RequestMethod.POST })
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
	public ModelAndView test1(@RequestParam String searchValue, @RequestParam String checkin, 
			@RequestParam int days) throws Exception {
		
		
		
		System.out.println("검색 테스트 컨트롤러 진입");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("searchValue", searchValue);
		//그걸 paramMap에 담음
		
		LocalDate check_in_temp = LocalDate.parse(checkin, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
		System.out.println("checkin : " + checkin);
		System.out.println("check_in_temp : " + check_in_temp);
		
		LocalDate check_out_temp = check_in_temp.plusDays(1);
		int str = 0;
		boolean judge = true;
		
		
		//검색값 결과를 찾는 sql문 호출하고 반환값을 result란 리스트에 저장
		//우리는 searchValue값만 입력하고, searchKey는 SQL문 안에서 임의로 지정함
		//=> 따라서 컨트롤러에 searchKey가 없다! 우린 searchKey값이 고정되어 있기 때문에..
		//검색값에 해당하는 hotel테이블의 hotel_name과 addr1을 동시에 뒤지게 됨
		List<Map<String, Object>> result = hotelMainService.searchMainHotelTest(paramMap);
		
		System.out.println(result);
		
		int num = 0;
		for(Map<String,Object> i: result) {
			
			int hotel_id = Integer.parseInt(i.get("HOTEL_ID").toString());
			
			for(int j=0;j<days;j++) {
				
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("check_in", check_in_temp.plusDays(j));
				paramMap2.put("check_out", check_out_temp.plusDays(j));
				paramMap2.put("hotel_id", hotel_id);
				str = hotelMainService.searchDay(paramMap2);
				if(str>4) {
					judge = false;
				}
				
			}
			
			if(judge==false) {
				System.out.println(i.get("HOTEL_NAME").toString() + "는(은) 이미 만실입니다.");
				i.put("soldout", "yes");
				judge = true;
			}
			else {
				i.put("soldout", "no");
			}
			num++;
			
		}
		
		
		System.out.println(result.size()); //결과값 개수 출력
		System.out.println(result); //결과값 전체 출력
		
		ModelAndView mav = new ModelAndView();

		mav.addObject("result",result); //이동할 html로 result담아서 보냄
		mav.setViewName("hotel/test"); //test.html로 이동
		
		return mav;
	}
	
	
	
	
	
}
