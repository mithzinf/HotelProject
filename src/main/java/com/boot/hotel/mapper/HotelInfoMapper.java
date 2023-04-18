package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HotelInfoMapper {
	
	public List<Map<String, Object>> getHotelList1(Map<String, Object> paramMap);
	public List<Map<String, Object>> getHotelList2(Map<String, Object> paramMap);
	public List<Map<String, Object>> getHotelList3(Map<String, Object> paramMap);
	
	public int getHotelCount();

}


//	public List<ReviewDTO> getHotelList4();
//	public List<ReviewScoreDTO> getHotelList5();


