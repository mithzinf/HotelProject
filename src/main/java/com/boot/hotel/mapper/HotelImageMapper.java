package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelImageMapper {
	
	public List<Map<String, Object>> searchDetailSweet(Map<String, Object> params) throws Exception;
	
}
