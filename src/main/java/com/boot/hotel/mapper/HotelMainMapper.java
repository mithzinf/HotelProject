package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMainMapper {
	
	public List<Map<String, Object>> getHotelMain(int hotel_id) throws Exception;
	
	public List<Map<String, Object>> searchMainHotelTest(Map<String, Object> params) throws Exception;
	
}
