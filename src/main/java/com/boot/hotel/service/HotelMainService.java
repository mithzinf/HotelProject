package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

public interface HotelMainService {

	//메인 페이지의 추천 호텔 리스트 출력을 위해
	public List<Map<String, Object>> getHotelMain(int hotel_id) throws Exception;
	
	//호텔 기본 검색
	public List<Map<String, Object>> searchMainHotel(Map<String, Object> params) throws Exception;
	
	//호텔 날짜 검색
	public int searchDay(Map<String, Object> params) throws Exception;
	
}
