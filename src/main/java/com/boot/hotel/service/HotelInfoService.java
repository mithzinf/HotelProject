package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;

public interface HotelInfoService {

	
	public List<Map<String, Object>> getHotelList1(Map<String, Object> paramMap);
	public List<Map<String, Object>> getHotelList2(Map<String, Object> paramMap);
	public List<Map<String, Object>> getHotelList3(Map<String, Object> paramMap);
	
	public int getHotelCount();
	
}
