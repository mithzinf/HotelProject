package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMainMapper {
	
	public List<Map<String, Object>> getHotelMain(int hotel_id) throws Exception;
	
	public List<Map<String, Object>> searchMainHotel(Map<String, Object> params) throws Exception;
	
	//호텔 날짜 검색
	public int searchDay(Map<String, Object> params) throws Exception;
}
